package com.fangcloud.noah.service.processor.impl;

import org.springframework.stereotype.Component;

import com.fangcloud.noah.service.processor.EventProcessor;

import java.util.Map;

/**
 * Created by chenke on 16-8-23.
 */
@Component
public class LotteryEventProcessor extends EventProcessor {
    @Override
    protected void fillEventMap(Map<String, Object> eventMap) {

        String accountLogin = String.valueOf(eventMap.get("account_login"));

        eventMap.put("account_mobile", accountLogin);
    }
}
