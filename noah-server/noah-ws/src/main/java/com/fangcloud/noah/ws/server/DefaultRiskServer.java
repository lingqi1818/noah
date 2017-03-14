package com.fangcloud.noah.ws.server;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.fangcloud.noah.api.model.RiskEvent;
import com.fangcloud.noah.redis.RedisService;
import com.fangcloud.noah.service.processor.EventProcessResult;
import com.fangcloud.noah.service.processor.EventProcessor;
import com.fangcloud.noah.service.processor.EventProcessorFactory;

/**
 * 默认RiskService实现
 */
@Component("riskServer")
public class DefaultRiskServer implements RiskServer {

    private static final Logger     logger                 = LoggerFactory
            .getLogger(DefaultRiskServer.class);

    private static final String     RISK_REDIS_SUB_CHANNEL = "noah_risk_channel_2";

    private BlockingQueue<Runnable> workQueue              = new LinkedBlockingQueue<Runnable>(
            100000);

    private ExecutorService         executorService        = new ThreadPoolExecutor(20, 30, 3000,
            TimeUnit.MILLISECONDS, workQueue, new DiscardOldestAndLogPolicy());

    @Autowired
    private RedisService            redisService;

    //系统退出开关
    private boolean                 stopFlag               = false;

    @Override
    public void start() {

        while (true) {

            try {

                final String message = redisService.rPop(RISK_REDIS_SUB_CHANNEL);

                if (StringUtils.isBlank(message)) {
                    try {
                        //如果取得的数据为空，线程sleep 2秒
                        Thread.sleep(2000);
                        logger.info("sleep 2000 mills");
                    } catch (InterruptedException e) {
                        logger.error("DefaultRiskServer Thread InterruptedException :", e);
                    }
                    continue;
                }

                logger.info("process channel message:" + message);
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            long eventTime = System.currentTimeMillis();

                            final RiskEvent riskEvent = JSON.parseObject(message, RiskEvent.class);

                            EventProcessor eventProcessor = EventProcessorFactory
                                    .get(riskEvent.getEventType());

                            Map<String, Object> eventMap = transformRiskEvent2Map(riskEvent);

                            eventMap.put("eventTime", eventTime);

                            EventProcessResult eventProcessResult = eventProcessor
                                    .processEvent(eventMap);

                        } catch (Exception e) {
                            logger.error("executorservice submit execute exception", e);
                        }
                    }
                });

            } catch (Exception e) {
                logger.error("DefaultRiskServer process exception:", e);
            }
        }
    }

    static class DiscardOldestAndLogPolicy extends ThreadPoolExecutor.DiscardOldestPolicy {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            logger.error("ThreadPoolExecutor rejected!!!");
            super.rejectedExecution(r, e);
        }

        public DiscardOldestAndLogPolicy() {
            super();
        }
    }

    @Override
    public void stop() {
        //结束while循环
        stopFlag = false;
    }

    public boolean isStopFlag() {
        return stopFlag;
    }

    public void setStopFlag(boolean stopFlag) {
        this.stopFlag = stopFlag;
    }

    private Map<String, Object> transformRiskEvent2Map(RiskEvent riskEvent) {

        Map<String, Object> eventMap = new HashMap<String, Object>();
        if (riskEvent != null) {

            Field[] fields = riskEvent.getClass().getDeclaredFields();
            for (Field field : fields) {

                String nameFirst = field.getName().substring(0, 1).toUpperCase();
                String getter = "get" + nameFirst + field.getName().substring(1);

                if (getter.equals("getSerialVersionUID") || getter.equals("getCollectParameters")) {
                    continue;
                }

                try {
                    Method method = riskEvent.getClass().getMethod(getter, new Class[] {});
                    try {
                        Object value = method.invoke(riskEvent, new Object[] {});
                        eventMap.put(field.getName(), value);
                    } catch (IllegalAccessException e) {
                        logger.error("transformRiskEvent2Map IllegalAccessException", e);
                    } catch (InvocationTargetException e) {
                        logger.error("transformRiskEvent2Map InvocationTargetException", e);
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            eventMap.putAll(riskEvent.getCollectParameters());
        }
        return eventMap;
    }
}
