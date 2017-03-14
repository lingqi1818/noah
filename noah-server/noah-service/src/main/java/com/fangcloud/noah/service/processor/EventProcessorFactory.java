package com.fangcloud.noah.service.processor;

import com.fangcloud.noah.dao.enums.EventType;
import com.fangcloud.noah.service.processor.impl.*;
import com.fangcloud.noah.service.util.SpringContextUtil;

/**
 * Created by chenke on 16-8-23.
 */
public class EventProcessorFactory {

    public static EventProcessor get(String eventType) {

        if (eventType.equals(EventType.login.getCode())) {
            return SpringContextUtil.getBean(LoginEventProcessor.class);
        } else if (eventType.equals(EventType.register.getCode())) {
            return SpringContextUtil.getBean(RegisterEventProcessor.class);
        }if (eventType.equals(EventType.message.getCode())) {
            return SpringContextUtil.getBean(MessageEventProcessor.class);
        }else if (eventType.equals(EventType.lottery.getCode())) {
            return SpringContextUtil.getBean(LotteryEventProcessor.class);
        }else if (eventType.equals(EventType.submitOrder.getCode())) {
            return SpringContextUtil.getBean(SubmitOrderEventProcessor.class);
        }else if (eventType.equals(EventType.recharge.getCode())) {
            return SpringContextUtil.getBean(RechargeEventProcessor.class);
        }else if (eventType.equals(EventType.payment.getCode())) {
            return SpringContextUtil.getBean(PaymentEventProcessor.class);
        }else if(eventType.equals(EventType.active_hongbao.getCode())){
            return SpringContextUtil.getBean(ActiveHongBaoProcessor.class);
        }else {
            return SpringContextUtil.getBean(DefaultEventProcessor.class);
        }
    }
}
