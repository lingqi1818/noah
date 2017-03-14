package com.fangcloud.noah.api.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenke on 16-9-8.
 */
public class RequestParamCollector {

    public static Map<String, Object> collect(HttpServletRequest request,String eventType,String applicationName,Map<String,Object> businessParamMap) {

        Map<String, Object> paramMap = new HashMap<String, Object>();

        if (request == null) {
            return paramMap;
        }

        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();

        paramMap.put("uri", requestUri.substring(contextPath.length()));
        paramMap.put("ipAddress", IPUtil.getIpFromHttpServletRequest(request));
        paramMap.put("userAgent", StringUtils.defaultString(request.getHeader("User-Agent")));
        paramMap.put("referer", StringUtils.defaultString(request.getHeader("Referer")));
        paramMap.put("deviceType", StringUtils.defaultString(request.getParameter("device_type")));
        paramMap.put("blackBox", StringUtils.defaultString(request.getParameter("black_box")));
        paramMap.put("tokenId", StringUtils.defaultString(request.getParameter("fraud_token_id")));
        paramMap.put("beaconId", CookieUtil.getBeaconId(request));
        paramMap.put("deviceInfo", StringUtils.defaultString(request.getParameter("y_order_id")));
        paramMap.put("appVersion", StringUtils.defaultString(request.getParameter("version")));

        paramMap.put("eventType",eventType);
        paramMap.put("applicationName",applicationName);
        paramMap.putAll(businessParamMap);

        return paramMap;
    }

    public static Map<String, Object> collect(HttpServletRequest request) {

        Map<String, Object> paramMap = new HashMap<String, Object>();

        if (request == null) {
            return paramMap;
        }

        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();

        paramMap.put("uri", requestUri.substring(contextPath.length()));
        paramMap.put("ipAddress", IPUtil.getIpFromHttpServletRequest(request));
        paramMap.put("userAgent", StringUtils.defaultString(request.getHeader("User-Agent")));
        paramMap.put("referer", StringUtils.defaultString(request.getHeader("Referer")));
        paramMap.put("deviceType", StringUtils.defaultString(request.getParameter("device_type")));
        paramMap.put("blackBox", StringUtils.defaultString(request.getParameter("black_box")));
        paramMap.put("tokenId", StringUtils.defaultString(request.getParameter("fraud_token_id")));
        paramMap.put("beaconId", CookieUtil.getBeaconId(request));
        paramMap.put("deviceInfo", StringUtils.defaultString(request.getParameter("y_order_id")));
        paramMap.put("appVersion", StringUtils.defaultString(request.getParameter("version")));

        return paramMap;
    }
}
