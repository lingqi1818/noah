package com.fangcloud.noah.service.processor.impl;

import com.fangcloud.noah.dao.entity.api.User;
import com.fangcloud.noah.service.apiservice.ApiService;
import com.fangcloud.noah.service.processor.EventProcessor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by chenke on 16-8-23.
 */
@Component
public class SubmitOrderEventProcessor extends EventProcessor {

    @Autowired
    private ApiService apiService;

    @Override
    protected void fillEventMap(Map<String, Object> eventMap) {

        String userId = String.valueOf(eventMap.get("user_id"));

        User user = apiService.getUserByUserId(userId);

        if (user != null) {
            eventMap.put("account_login", user.getMobile());
            eventMap.put("account_mobile", user.getMobile());
        }

        String productId = String.valueOf(eventMap.get("product_id"));

        if (StringUtils.isNotBlank(productId)) {

            boolean secondKillFlag = apiService.isSecondKillProduct(productId);
            if (secondKillFlag) {
                eventMap.put("ext_secondkill_state", "1");
            } else {
                eventMap.put("ext_secondkill_state", "0");
            }
        }

        eventMap.put("ext_user_address", String.valueOf(eventMap.get("user_address")));
        eventMap.put("ext_user_contact", String.valueOf(eventMap.get("user_contact")));
    }
}
