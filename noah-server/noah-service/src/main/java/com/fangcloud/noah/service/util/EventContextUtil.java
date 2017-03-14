package com.fangcloud.noah.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fangcloud.noah.service.service.IpService;

import java.util.Map;

/**
 * Created by chenke on 17-1-19.
 */
public class EventContextUtil {

    private static final Logger logger = LoggerFactory.getLogger(EventContextUtil.class);


    private static ThreadLocal<Map<String,Object>> eventContextThreadLocal = new ThreadLocal<Map<String,Object>>();

    public static void putEventContext(Map<String,Object> eventContext) {
        eventContextThreadLocal.set(eventContext);
    }

    public static Map<String,Object> getEventContext() {
        return eventContextThreadLocal.get();
    }

    public static void removeEventContext() {
        eventContextThreadLocal.remove();
    }


    public static long getEventTime(){

        try {
            Map<String,Object> eventContext = eventContextThreadLocal.get();

            if(eventContext!=null){
                Object eventTime = eventContext.get("eventTime");

                return Long.valueOf(String.valueOf(eventTime)).longValue();
            }

        }catch (Exception e){
            logger.error("EventContextUtil getEventTime exception",e);
        }

        return System.currentTimeMillis();
    }
}
