package com.fangcloud.noah.test;

import com.fangcloud.noah.api.impl.DefaultRiskServiceClient;
import com.fangcloud.noah.api.model.RiskEvent;
import com.fangcloud.noah.redis.RedisServiceImpl;

public class Main {

    public static void main(String[] args) {
        String dtext = "大宝剑";
        String eventType = "test_action2";
        RedisServiceImpl redis = new RedisServiceImpl("127.0.0.1:6379");
        DefaultRiskServiceClient client = new DefaultRiskServiceClient();
        client.setApplicationName("test_client");
        client.setRedisService(redis);
        RiskEvent e = new RiskEvent();
        e.getCollectParameters().put("deviceId", "000002");
        e.setEventType(eventType);
        e.getCollectParameters().put("account_mobile", "1300000001");
        e.getCollectParameters().put("u_text", dtext);
        RiskEvent e1 = new RiskEvent();
        e1.getCollectParameters().put("deviceId", "000002");
        e1.setEventType(eventType);
        e1.getCollectParameters().put("account_mobile", "1300000002");
        e1.getCollectParameters().put("u_text", dtext);
        RiskEvent e2 = new RiskEvent();
        e2.getCollectParameters().put("deviceId", "000002");
        e2.setEventType(eventType);
        e2.getCollectParameters().put("account_mobile", "1300000003");
        e2.getCollectParameters().put("u_text", dtext);
        RiskEvent e3 = new RiskEvent();
        e3.getCollectParameters().put("deviceId", "000002");
        e3.setEventType(eventType);
        e3.getCollectParameters().put("account_mobile", "1300000004");
        e3.getCollectParameters().put("u_text", dtext);
        RiskEvent e4 = new RiskEvent();
        e4.getCollectParameters().put("deviceId", "000002");
        e4.setEventType(eventType);
        e4.getCollectParameters().put("account_mobile", "1300000005");
        e4.getCollectParameters().put("u_text", dtext);
        RiskEvent e5 = new RiskEvent();
        e5.getCollectParameters().put("deviceId", "000002");
        e5.setEventType(eventType);
        e5.getCollectParameters().put("account_mobile", "1300000006");
        e5.getCollectParameters().put("u_text", dtext);
        client.collect(e);
        client.collect(e1);
        client.collect(e2);
        client.collect(e3);
        client.collect(e4);
        client.collect(e5);
    }

}
