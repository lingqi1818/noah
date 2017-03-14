package com.fangcloud.noah.service.processor.impl;

import org.springframework.stereotype.Component;

import com.fangcloud.noah.service.processor.EventProcessor;

import java.util.Map;

/**
 * Created by chenke on 16-9-21.
 */
@Component
public class ActiveHongBaoProcessor extends EventProcessor{
    @Override
    protected void fillEventMap(Map<String, Object> eventMap) {
        String accountLogin = String.valueOf(eventMap.get("account_login"));

        eventMap.put("account_mobile", accountLogin);
    }
}
