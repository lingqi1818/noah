package com.fangcloud.noah.service.processor.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.fangcloud.noah.service.processor.EventProcessor;

import java.util.Map;

/**
 * Created by chenke on 16-8-23.
 */
@Component
public class LoginEventProcessor extends EventProcessor {

    @Override
    protected void fillEventMap(Map<String, Object> eventMap) {

        String account_login = String.valueOf(eventMap.get("account_login"));
        String account_mobile = String.valueOf(eventMap.get("account_mobile"));

        if (StringUtils.isNotEmpty(account_login) && !account_login.equals("null")) {
            eventMap.put("account_login", account_login);
            eventMap.put("account_mobile", account_login);
        } else {
            eventMap.put("account_login", account_mobile);
            eventMap.put("account_mobile", account_mobile);
        }
    }
}
