package com.fangcloud.noah.service.processor.impl;

import org.springframework.stereotype.Component;

import com.fangcloud.noah.service.processor.EventProcessor;

import java.util.Map;

/**
 * Created by chenke on 16-8-23.
 */
@Component
public class RegisterEventProcessor extends EventProcessor {


    @Override
    protected void fillEventMap(Map<String, Object> eventMap) {


        String account_mobile = String.valueOf(eventMap.get("account_mobile"));

        eventMap.put("account_login", account_mobile);
    }
}
