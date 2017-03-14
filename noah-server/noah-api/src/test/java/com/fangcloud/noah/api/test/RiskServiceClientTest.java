package com.fangcloud.noah.api.test;


import com.fangcloud.noah.api.impl.DefaultRiskServiceClient;
import com.fangcloud.noah.redis.RedisService;
import com.fangcloud.noah.redis.RedisServiceImpl;

import junit.framework.TestCase;

public class RiskServiceClientTest extends TestCase {
    public void testRiskServiceClient() {
        RedisService redis = new RedisServiceImpl("127.0.0.1:6379");
        DefaultRiskServiceClient riskService = new DefaultRiskServiceClient();
        riskService.setRedisService(redis);
        //        Map<String, Object> data = new HashMap<String, Object>();
        //        data.put("mobile", "13644550531");
        //        Risk risk = new Risk("/test.do","127.0.0.1","", "","","",RiskType.login_web,data);
        //        riskService.collect(risk);
    }
}
