package com.fangcloud.noah.service.util;

import org.apache.commons.lang3.StringUtils;

import com.fangcloud.noah.dao.enums.DeviceType;

/**
 * 通过userAgent判断客户端平台类型工具类
 * Created by chenke on 16-8-25.
 */

public class UseAgentUtil {

    private static String[] webKeyWords = {"chrome", "firefox", "msie", "opera", "360se"};

    public static DeviceType parse(String userAgent) {

        if (StringUtils.isBlank(userAgent)) {
            return DeviceType.UNKNOW;
        }
        if (userAgent.toLowerCase().contains("iphone") || userAgent.toLowerCase().contains("ipad")) {
            return DeviceType.IOS;
        } else if (userAgent.toLowerCase().contains("android")) {
            return DeviceType.ANDROID;
        } else {
            for (String keyWord : webKeyWords) {
                if (userAgent.toLowerCase().contains(keyWord)) {
                    return DeviceType.WEB;
                }
            }
        }
        return DeviceType.UNKNOW;
    }
}
