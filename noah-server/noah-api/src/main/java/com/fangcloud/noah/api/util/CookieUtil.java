package com.fangcloud.noah.api.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chenke on 16-5-13.
 */
public class CookieUtil {

    private static final Logger logger = LoggerFactory.getLogger(CookieUtil.class);

    public static String getBeaconId(HttpServletRequest request) {

        try {
            String beacon_id = "";
            String cookieStr = request.getHeader("Cookie");
            if (StringUtils.isNoneBlank(cookieStr)) {
                String[] cookieArray = cookieStr.split(";");
                if (cookieArray != null && cookieArray.length != 0) {
                    for (String str : cookieArray) {
                        if (str.contains("beacon_id")) {
                            beacon_id = str.substring(str.indexOf("=") + 1);
                        }
                    }
                }
            }

            return beacon_id;
        } catch (Exception e) {
            logger.error("CookieUtil get beacon_id from cookie exception", e);
        }

        return "";
    }


}
