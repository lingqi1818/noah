package com.fangcloud.noah.api.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.fangcloud.noah.api.RiskServiceClient;
import com.fangcloud.noah.api.model.BlackListType;
import com.fangcloud.noah.api.model.EventCollectDefinition;
import com.fangcloud.noah.api.model.MappedCollectParam;
import com.fangcloud.noah.api.util.CookieUtil;
import com.fangcloud.noah.api.util.IPUtil;
import com.fangcloud.noah.common.api.util.Result;

/**
 * Created by chenke on 16-5-13.
 */
public class RiskCollectInterceptor extends HandlerInterceptorAdapter {

    private static final Logger   logger                     = LoggerFactory
            .getLogger(RiskCollectInterceptor.class);

    private RiskServiceClient     riskServiceClient;

    private static final String   DEFAULT_RESULT_CODE        = "00001";

    //beaconId非法
    private static final String   ILLEGAL_REQUEST_MESSAGE_01 = "非法请求";

    //mobile黑名单
    private static final String   ILLEGAL_REQUEST_MESSAGE_02 = "您的请求异常,请联系客服";

    private static final String   ILLEGAL_REQUEST_MESSAGE_03 = "您的操作太频繁,请稍后再试";

    //user-agent黑名单
    private static final String   ILLEGAL_REQUEST_MESSAGE_04 = "非法请求04";

    //ip黑名单
    private static final String   ILLEGAL_REQUEST_MESSAGE_05 = "非法请求05";

    //Referer 黑名单
    private static final String   ILLEGAL_REQUEST_MESSAGE_06 = "非法请求06";

    protected static final String CHARSET                    = "UTF-8";

    //注册事件
    private static final String   EVENT_TYPE_REGISTER        = "100";

    //登陆事件
    private static final String   EVENT_TYPE_LOGIN           = "200";

    //短信事件
    private static final String   EVENT_TYPE_MESSAGE         = "900";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler)
            throws Exception {
        try {

            if (!riskServiceClient.getSwitchStatus()) {
                return true;
            }

            String requestUri = request.getRequestURI();

            String contextPath = request.getContextPath();

            requestUri = requestUri.substring(contextPath.length());

            Map<String, EventCollectDefinition> eventCollectDefinitionMap = riskServiceClient
                    .getEventCollectDefinitionMap();

            if (eventCollectDefinitionMap != null
                    && eventCollectDefinitionMap.containsKey(requestUri)) {

                String userIp = IPUtil.getIpFromHttpServletRequest(request);

                if (riskServiceClient.isBlackList(BlackListType.RISK_BLACK_LIST_IP, userIp)) {
                    doResponse(response, ILLEGAL_REQUEST_MESSAGE_05,
                            "risk userIp=" + userIp + " is in ip blacklist");
                    return false;
                }

                String beaconId = CookieUtil.getBeaconId(request);

                //                if(StringUtils.isBlank(beaconId)){
                //                    doResponse(response,ILLEGAL_REQUEST_MESSAGE_01,"risk beaconId is null,requestUri="+requestUri);
                //                    return false;
                //                }

                EventCollectDefinition eventCollectDefinition = eventCollectDefinitionMap
                        .get(requestUri);

                String eventType = eventCollectDefinition.getEventType();

                if (eventType.equals(EVENT_TYPE_MESSAGE) || eventType.equals(EVENT_TYPE_REGISTER)
                        || eventType.equals(EVENT_TYPE_LOGIN)) {

                    String mobile = StringUtils.defaultString(request.getParameter("mobile"));

                    String refer = StringUtils.defaultString(request.getHeader("Referer"));

                    if (riskServiceClient.isBlackList(BlackListType.RISK_BLACK_LIST_MESSAGE_REFER,
                            refer)) {
                        doResponse(response, ILLEGAL_REQUEST_MESSAGE_06,
                                "risk refer=" + refer + " is in refer blacklist,requestUri="
                                        + requestUri + ",requeset mobile = " + mobile);
                        return false;
                    }

                    String userAgent = StringUtils.defaultString(request.getHeader("User-Agent"));

                    if (riskServiceClient.isBlackList(
                            BlackListType.RISK_BLACK_LIST_MESSAGE_USER_AGENT_SET, userAgent)) {
                        doResponse(response, ILLEGAL_REQUEST_MESSAGE_04,
                                "risk userAgent=" + userAgent
                                        + " is in userAgent blacklist,requestUri=" + requestUri
                                        + ",requeset mobile = " + mobile);
                        return false;
                    }
                }

                //短信事件处理
                if (eventType.equals(EVENT_TYPE_MESSAGE)) {

                    String mobile = StringUtils.defaultString(request.getParameter("mobile"));

                    if (riskServiceClient.isBlackList(
                            BlackListType.RISK_BLACK_LIST_MESSAGE_BEACONID, beaconId)) {

                        doResponse(response, ILLEGAL_REQUEST_MESSAGE_01, "risk beaconId=" + beaconId
                                + " is in beaconId blacklist,request mobile=" + mobile);
                        return false;
                    }

                    if (riskServiceClient.isBlackList(BlackListType.RISK_BLACK_LIST_MOBILE,
                            mobile)) {

                        doResponse(response, ILLEGAL_REQUEST_MESSAGE_02,
                                "risk mobile=" + mobile + " is in mobile blacklist");
                        return false;
                    }

                    if (riskServiceClient.isBlackList(
                            BlackListType.RISK_BLACK_LIST_MOBILE_TOO_FREQUENTLY, mobile)) {

                        doResponse(response, ILLEGAL_REQUEST_MESSAGE_03,
                                "risk mobile=" + mobile + " 您的操作太频繁,请稍后再试");
                        return false;
                    }

                }

                if (!eventCollectDefinition.isSwitchStatus()) {
                    return true;
                }

                @SuppressWarnings("unchecked")
                Map<String, String[]> requestParameterMap = request.getParameterMap();
                Map<String, Object> collectData = generateCollectData(eventCollectDefinition,
                        requestParameterMap);

                riskServiceClient.collect(request, eventCollectDefinition.getEventType(),
                        collectData);
            }

        } catch (Exception e) {
            logger.error("risk collect exception:", e);
        }

        return true;
    }

    private void doResponse(HttpServletResponse response, String message, String logError)
            throws IOException {

        String contentType = "application/json;charset=" + CHARSET;
        response.addHeader("Content-Type", contentType);

        Result result = new Result();
        result.setApiCodeMessage(DEFAULT_RESULT_CODE, message);
        result.setSuccess(false);
        response.getWriter().write(JSON.toJSONString(result));

        logger.error("request exception,requestId=" + result.getRequestId() + "," + logError);
    }

    /**
     * 生成risk上报数据
     *
     * @param eventCollectDefinition
     * @param requestParameterMap
     * @return
     */
    private Map<String, Object> generateCollectData(EventCollectDefinition eventCollectDefinition,
                                                    Map<String, String[]> requestParameterMap) {

        Map<String, Object> reportDataMap = new HashMap<>();

        List<MappedCollectParam> mappedCollectParamList = eventCollectDefinition
                .getMappedCollectParamList();

        Set<Map.Entry<String, String[]>> requestParameterEntrySet = requestParameterMap.entrySet();

        if (CollectionUtils.isNotEmpty(mappedCollectParamList)) {
            for (MappedCollectParam collectParam : mappedCollectParamList) {
                if (requestParameterMap.containsKey(collectParam.getSrc())) {
                    String[] value = requestParameterMap.get(collectParam.getSrc());

                    if (value != null && value.length == 1) {
                        reportDataMap.put(collectParam.getTarget(), value[0]);
                    } else if (value != null && value.length > 1) {
                        String tValue = StringUtils.join(value, ",");
                        reportDataMap.put(collectParam.getTarget(), tValue);
                    } else {
                        reportDataMap.put(collectParam.getTarget(), value);
                    }

                } else {
                    reportDataMap.put(collectParam.getTarget(), "");
                }
            }
        }

        return reportDataMap;
    }

    public RiskServiceClient getRiskServiceClient() {
        return riskServiceClient;
    }

    public void setRiskServiceClient(RiskServiceClient riskServiceClient) {
        this.riskServiceClient = riskServiceClient;
    }
}
