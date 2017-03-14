package com.fangcloud.noah.service.processor.impl;

import com.fangcloud.noah.dao.entity.api.CardOrder;
import com.fangcloud.noah.service.apiservice.ApiService;
import com.fangcloud.noah.service.processor.EventProcessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by chenke on 16-8-23.
 */
@Component
public class RechargeEventProcessor extends EventProcessor {

    @Autowired
    private ApiService apiService;

    @Override
    protected void fillEventMap(Map<String, Object> eventMap) {

        String orderSeq = String.valueOf(eventMap.get("orderNum"));

        CardOrder cardOrder = apiService.getCardOrderByOrderSeq(orderSeq);

        if (cardOrder != null) {
            eventMap.put("account_login", cardOrder.getUserMobile());
            eventMap.put("account_mobile", cardOrder.getUserMobile());
        }

        eventMap.put("pay_method", eventMap.get("pay_method"));
        eventMap.put("pay_amount", eventMap.get("pay_amount"));
    }
}
