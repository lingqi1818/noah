package com.fangcloud.noah.api.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fangcloud.noah.api.RiskServiceClient;
import com.fangcloud.noah.api.exception.RiskRuntimeException;
import com.fangcloud.noah.api.model.BlackListType;
import com.fangcloud.noah.api.model.DeviceInfo;
import com.fangcloud.noah.api.model.EventCollectDefinition;
import com.fangcloud.noah.api.model.RiskEvent;
import com.fangcloud.noah.api.util.CookieUtil;
import com.fangcloud.noah.api.util.IPUtil;
import com.fangcloud.noah.redis.RedisService;

public class DefaultRiskServiceClient implements RiskServiceClient {

    private static final Logger                 logger                            = LoggerFactory
            .getLogger(DefaultRiskServiceClient.class);

    private RedisService                        redisService;

    private String                              fraudApiUrl;

    private String                              applicationName;

    //private FraudApiInvoker                     fraudApiInvoke;

    private BlockingQueue<Runnable>             workQueue                         = new LinkedBlockingQueue<Runnable>(
            100000);

    private ExecutorService                     executorService                   = new ThreadPoolExecutor(
            20, 30, 3000, TimeUnit.MILLISECONDS, workQueue, new DiscardAndLogPolicy());

    public static final String                  SWITCH_RISK                       = "switch_risk";

    private volatile boolean                    switchStatus                      = true;

    private final ScheduledExecutorService      scheduledExecutorService          = Executors
            .newScheduledThreadPool(1);

    private final ScheduledExecutorService      executorServiceForEventDefinition = Executors
            .newScheduledThreadPool(1);

    private Map<String, EventCollectDefinition> eventCollectDefinitionMap         = new HashMap<String, EventCollectDefinition>();

    public void init() {

        if (redisService == null) {
            return;
        }

        initEventCollectDefinition();

        //获取风控开关状态定时任务
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                try {
                    logger.info("获取风控开关状态start");
                    String riskSwictchstatus = redisService.get(SWITCH_RISK);

                    if (StringUtils.isNotBlank(riskSwictchstatus)
                            && riskSwictchstatus.equals("off")) {
                        switchStatus = false;
                    } else {
                        switchStatus = true;
                    }

                    logger.info("获取风控开关状态end,status=" + switchStatus);

                } catch (Throwable t) {
                    logger.error("获取风控开关状态定时任务执行异常," + t.getMessage());
                }
            }
        }, 0, 1, TimeUnit.MINUTES);

        //获取事件采集定义参数
        executorServiceForEventDefinition.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                try {
                    initEventCollectDefinition();
                } catch (Throwable t) {
                    logger.error("获取事件采集定义参数执行异常," + t.getMessage());
                }
            }
        }, 5, 5, TimeUnit.MINUTES);
    }

    private void initEventCollectDefinition() {

        try {

            String collectDefinitionStr = redisService
                    .get(APPLICATION_COLLECT_DEFINITION + "_" + applicationName);

            if (StringUtils.isNotBlank(collectDefinitionStr)) {

                Map<String, EventCollectDefinition> resultMap = JSON.parseObject(
                        collectDefinitionStr,
                        new TypeReference<Map<String, EventCollectDefinition>>() {
                        });
                if (resultMap != null) {
                    eventCollectDefinitionMap = resultMap;
                }
            }

        } catch (Exception e) {
            logger.info("获取风控采集参数异常", e);
            throw new RiskRuntimeException("init EventCollectDefinition from redis exception", e);
        }
    }

    @Override
    public void collect(final RiskEvent riskEvent) {
        if (riskEvent == null) {
            throw new IllegalArgumentException("[risk api]:the risk is null .");
        }

        try {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    redisService.lPush(CHANNEL_NAME, JSON.toJSONString(riskEvent));
                }
            });

        } catch (Exception e) {
            logger.error("DefaultRiskServiceClient collect exception:", e);
        }

    }

    public void collect(HttpServletRequest request, String eventType,
                        Map<String, Object> collectData) {
        try {

            if (!switchStatus) {
                return;
            }

            String requestUri = request.getRequestURI();
            String contextPath = request.getContextPath();

            requestUri = requestUri.substring(contextPath.length());

            String userAgent = StringUtils.defaultString(request.getHeader("User-Agent"));
            String deviceType = StringUtils.defaultString(request.getParameter("device_type"));
            String referer = StringUtils.defaultString(request.getHeader("Referer"));
            String blackBox = StringUtils.defaultString(request.getParameter("black_box"));
            String tokenId = StringUtils.defaultString(request.getParameter("fraud_token_id"));
            String beaconId = CookieUtil.getBeaconId(request);
            String userIp = IPUtil.getIpFromHttpServletRequest(request);
            String deviceInfo = StringUtils.defaultString(request.getParameter("y_order_id"));
            String appVersion = StringUtils.defaultString(request.getParameter("version"));
            String sequenceId = UUID.randomUUID().toString();

            RiskEvent riskEvent = new RiskEvent(requestUri, userIp, userAgent, referer, deviceType,
                    blackBox, tokenId, beaconId, eventType, deviceInfo, appVersion, sequenceId,
                    applicationName, collectData);

            this.collect(riskEvent);

        } catch (Exception e) {
            logger.error("risk collect exception:", e);
        }
    }

    //    @Override
    //    public FraudApiResponse sendToFraud(Map<String, Object> data) {
    //        FraudApiResponse apiResp = null;
    //        try {
    //            apiResp = fraudApiInvoke.invoke(data);
    //        } catch (IOException e) {
    //            logger.error("Invoke FraudApi exception:", e);
    //        }
    //        return apiResp;
    //    }

    @Override
    public boolean getSwitchStatus() {
        return switchStatus;
    }

    static class DiscardAndLogPolicy extends ThreadPoolExecutor.DiscardPolicy {
        public DiscardAndLogPolicy() {
            super();
        }

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            logger.error("DefaultRiskServiceClient ThreadPoolExecutor rejectedExecution:", e);
            super.rejectedExecution(r, e);
        }
    }

    //    public void setFraudApiUrl(String fraudApiUrl) {
    //        this.fraudApiUrl = fraudApiUrl;
    //
    //        fraudApiInvoke = new FraudApiInvoker(fraudApiUrl);
    //    }

    @Override
    public String getDeviceId(String yOrderId) {

        if (StringUtils.isBlank(yOrderId)) {
            return "";
        }

        try {

            //兼容空格情况
            yOrderId = yOrderId.replaceAll("\\ ", "+");

            String deviceInfoStr = redisService.get(REDIS_DEVICE_INFO_KEY_PREFIX + "_" + yOrderId);

            if (StringUtils.isNotBlank(deviceInfoStr)) {
                DeviceInfo deviceInfo = JSON.parseObject(deviceInfoStr, DeviceInfo.class);

                if (deviceInfo != null) {
                    return deviceInfo.getDeviceId();
                }

            }

        } catch (Exception e) {
            logger.error("DefaultRiskServiceClient getDeviceId Exception", e);
        }

        return "";

    }

    public Map<String, EventCollectDefinition> getEventCollectDefinitionMap() {
        return eventCollectDefinitionMap;
    }

    @Override
    public boolean isBlackList(BlackListType blackListType, String value) {

        if (blackListType == null || StringUtils.isBlank(value)) {
            return false;
        }

        if (blackListType.equals(BlackListType.RISK_BLACK_LIST_MESSAGE_USER_AGENT_SET)) {

            return redisService.sismember(blackListType.getValue(), value);

        } else {
            String blackListResult = redisService.get(blackListType.getValue() + "_" + value);

            if (StringUtils.isNotBlank(blackListResult)) {
                return true;
            } else {
                return false;
            }
        }

    }

    public void setEventCollectDefinitionMap(Map<String, EventCollectDefinition> eventCollectDefinitionMap) {
        this.eventCollectDefinitionMap = eventCollectDefinitionMap;
    }

    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
