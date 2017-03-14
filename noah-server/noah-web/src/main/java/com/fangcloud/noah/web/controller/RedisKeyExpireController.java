package com.fangcloud.noah.web.controller;

import java.util.Iterator;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fangcloud.noah.redis.RedisService;
import com.fangcloud.noah.service.common.CacheKeyEnum;
import com.fangcloud.noah.service.common.RiskConstants;

/**
 * Created by chenke on 16-10-8.
 */
@Controller
@RequestMapping("redisKey")
public class RedisKeyExpireController {

    private static final Logger logger = LoggerFactory.getLogger(RedisKeyExpireController.class);

    @Autowired
    private RedisService        redisService;

    @RequestMapping("clearKey")
    public void clearKey() {

        System.out.println("RedisKeyExpireController clear start...");

        for (int i = RiskConstants.SET_KEY_NUM_MIN; i <= RiskConstants.SET_KEY_NUM_MAX; i++) {

            try {

                String childKey = CacheKeyEnum.RISK_NEED_CLEAR_SET.getKey(String.valueOf(i));
                Set<String> keySet = redisService.smembers(childKey);

                if (CollectionUtils.isNotEmpty(keySet)) {
                    System.out.println(
                            "risk need clear set num = " + i + ",keySet size :" + keySet.size());

                    Iterator<String> keys = keySet.iterator();

                    while (keys.hasNext()) {
                        String key = keys.next();

                        //清除3天前的数据
                        long nowTime = System.currentTimeMillis();

                        long beforeTime = nowTime - 3600 * 24 * 7;

                        redisService.zremrangeByScore(key, 0, beforeTime);

                        Long count = redisService.zcount(key, 0, nowTime + 3600);

                        if (count < 1) {
                            //过期zset
                            redisService.expire(key, 0);

                            //过期mapping key
                            String shardKey = CacheKeyEnum.RISK_NEED_CLEAR_KEY_MAPPING.getKey(key);
                            redisService.expire(shardKey, 0);

                            //移除　存在clear set里的key　
                            redisService.srem(childKey, key);
                            logger.info("RedisKeyExpireController remove key:" + key
                                    + ",clear set num=" + i);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("RedisKeyExpireController clearKey exception", e);
            }

        }
    }

}
