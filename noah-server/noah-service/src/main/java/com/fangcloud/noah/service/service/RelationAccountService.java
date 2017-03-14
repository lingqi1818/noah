package com.fangcloud.noah.service.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fangcloud.noah.redis.RedisService;
import com.fangcloud.noah.service.common.CacheKeyEnum;

/**
 * Created by chenke on 16-8-25.
 */
@Service
public class RelationAccountService {

    private final Logger     logger = LoggerFactory.getLogger(RelationAccountService.class);

    @Autowired
    private RedisService     redisService;

    private SimpleDateFormat sdf    = new SimpleDateFormat("yyMMdd");

    public void cacheRelationAccount(String accountLogin, String deviceInfo, String ipAddress,
                                     String beaconId) {

        if (StringUtils.isBlank(accountLogin)) {
            return;
        }
        try {

            if (StringUtils.isNotBlank(ipAddress)) {
                ipAddress = StringUtils.trim(ipAddress.split(",")[0]);
            }

            CacheKeyEnum cacheKeyEnum = null;

            String cacheId = "";

            Date date = new Date();

            String currentDate = sdf.format(date);

            if (StringUtils.isNotBlank(deviceInfo)) {

                cacheKeyEnum = CacheKeyEnum.DEVICE_ID_ACCOUNT;
                cacheId = deviceInfo;

                cacheToRedis(accountLogin, cacheKeyEnum, currentDate, cacheId);
            }

            if (StringUtils.isNotBlank(beaconId)) {
                cacheKeyEnum = CacheKeyEnum.BEACON_ID_ACCOUNT;
                cacheId = beaconId;

                cacheToRedis(accountLogin, cacheKeyEnum, currentDate, cacheId);
            }

            if (StringUtils.isNotBlank(ipAddress)) {
                cacheKeyEnum = CacheKeyEnum.IP_ACCOUNT;
                cacheId = ipAddress;
                cacheToRedis(accountLogin, cacheKeyEnum, currentDate, cacheId);
            }
        } catch (Exception e) {
            logger.error("processCacheRelationAccount exception", e);
        }
    }

    private void cacheToRedis(String accountLogin, CacheKeyEnum cacheKeyEnum, String currentDate,
                              String cacheId) {

        try {
            redisService.sadd(cacheKeyEnum.getKey(cacheId), accountLogin);

            cacheId = currentDate + "_" + cacheId;

            if (redisService.scard(cacheKeyEnum.getKey(cacheId)).equals(0L)) {
                redisService.sadd(cacheKeyEnum.getKey(cacheId), accountLogin);
                redisService.expire(cacheKeyEnum.getKey(cacheId), cacheKeyEnum.getExpireSeconds());
            } else {
                redisService.sadd(cacheKeyEnum.getKey(cacheId), accountLogin);
            }

        } catch (Exception e) {
            logger.error("relation account cache to redis exception", e);
        }
    }
}
