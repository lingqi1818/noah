package com.fangcloud.noah.service.processor.impl;

import com.fangcloud.noah.dao.entity.api.UsOrder;
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
public class PaymentEventProcessor extends EventProcessor {

    @Autowired
    private ApiService apiService;

    @Override
    protected void fillEventMap(Map<String, Object> eventMap) {

        String orderSeq = String.valueOf(eventMap.get("order_number"));

        UsOrder usOrder = apiService.getUsOrderByOrderSeq(orderSeq);

        if (usOrder != null) {
            eventMap.put("account_login", StringUtils.defaultString(usOrder.getUserMobile()));
            eventMap.put("account_mobile", StringUtils.defaultString(usOrder.getUserMobile()));

            String stage = String.valueOf(eventMap.get("stage"));

            String amount = "";

            if ("2".equals(stage)) {
                amount = String.valueOf(usOrder.getExtraFeePrice());
            } else if ("1".equals(stage)) {
                amount = String.valueOf(usOrder.getShouldPayPrice());
            }

            eventMap.put("pay_method", "balancePay");  //余额支付
            eventMap.put("pay_amount", amount);
            eventMap.put("pay_currency", "RMB");
        }



    }
}
