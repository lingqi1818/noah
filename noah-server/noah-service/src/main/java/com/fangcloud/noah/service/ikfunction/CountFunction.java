package com.fangcloud.noah.service.ikfunction;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.fangcloud.noah.api.api.enums.RiskWordType;
import com.fangcloud.noah.redis.RedisService;
import com.fangcloud.noah.service.common.CacheKeyEnum;
import com.fangcloud.noah.service.common.RiskConstants;
import com.fangcloud.noah.service.service.TextScanService;
import com.fangcloud.noah.service.util.EventContextUtil;
import com.fangcloud.noah.service.util.RandomUtil;
import com.fangcloud.noah.service.util.SpringContextUtil;

/**
 * 此类中的统计方法使用了redis 的zset实现，由于redis的zset没有过期策略,
 * 对于zset里的数据过期在RedisKeyExpireController.java中通过crontab定时任务实现。 Created by chenke
 * on 16-8-25.
 */
public class CountFunction {

    /**
     * 统计一段时间内，某个属性出现的次数
     *
     * @param property
     * @param second
     * @return
     */
    public long count(String eventType, String keyPrefix, String property, int second) {

        if (StringUtils.isBlank(eventType) || StringUtils.isBlank(keyPrefix)
                || StringUtils.isBlank(property)) {
            return -1;
        }

        RedisService redisService = SpringContextUtil.getBean(RedisService.class);

        String key = CacheKeyEnum.FUNCTION_CT.getKey(eventType + "_" + keyPrefix + "_" + property);

        sortedSetKeyShard(key, redisService);

        long eventTime = EventContextUtil.getEventTime();

        redisService.zadd(key, eventTime, UUID.randomUUID().toString());

        Long count = redisService.zcount(key, (eventTime - second * 1000), eventTime);

        if (count == null) {
            count = Long.valueOf(-1);
        }

        return count;
    }

    /**
     * 统计一段时间内，某2个属性出现的次数 如 同一个ip、手机号一段时间内出现的次数
     * 
     * @param eventType
     * @param keyPrefix
     * @param property1
     * @param property2
     * @param second
     * @return
     */
    public long unionCount(String eventType, String keyPrefix, String property1, String property2,
                           int second) {

        if (StringUtils.isBlank(eventType) || StringUtils.isBlank(property1)
                || StringUtils.isBlank(property2)) {
            return -1;
        }

        RedisService redisService = SpringContextUtil.getBean(RedisService.class);

        String key = CacheKeyEnum.FUNCTION_CT
                .getKey(eventType + "_" + keyPrefix + "_" + property1 + "_" + property2);

        sortedSetKeyShard(key, redisService);

        long eventTime = EventContextUtil.getEventTime();

        redisService.zadd(key, eventTime, UUID.randomUUID().toString());

        Long count = redisService.zcount(key, (eventTime - second * 1000), eventTime);

        if (count == null) {
            count = Long.valueOf(-1);
        }

        return count;
    }

    /**
     * 统计一段时间内主属性关联从属性的个数
     *
     * @param eventType
     * @param mProperty
     * @param sProperty
     * @param second
     * @return
     */
    public long countRelation(String eventType, String keyPrefix, String mProperty,
                              String sProperty, int second) {

        if (StringUtils.isBlank(eventType) || StringUtils.isBlank(mProperty)
                || StringUtils.isBlank(sProperty)) {
            return -1;
        }

        RedisService redisService = SpringContextUtil.getBean(RedisService.class);

        String key = CacheKeyEnum.FUNCTION_CR.getKey(eventType + "_" + keyPrefix + "_" + mProperty);

        sortedSetKeyShard(key, redisService);

        long eventTime = EventContextUtil.getEventTime();

        redisService.zadd(key, eventTime, sProperty);

        Long count = redisService.zcount(key, (eventTime - second * 1000), eventTime);

        if (count == null) {
            count = Long.valueOf(-1);
        }

        return count;
    }

    /**
     * 统计一段时间内，某个操作的失败次数
     * 
     * @param eventType
     * @param keyPrefix
     * @param property
     * @param state 0 表示成功 1表示失败 2 未知异常
     * @param second
     * @return
     */
    public long failCount(String eventType, String keyPrefix, String property, String state,
                          int second) {

        if (StringUtils.isBlank(eventType) || StringUtils.isBlank(keyPrefix)
                || StringUtils.isBlank(property) || StringUtils.isBlank(state)) {
            return -1;
        }

        RedisService redisService = SpringContextUtil.getBean(RedisService.class);

        String key = CacheKeyEnum.FUNCTION_FAIL_COUNT
                .getKey(eventType + "_" + keyPrefix + "_" + property);

        long eventTime = EventContextUtil.getEventTime();

        if (StringUtils.isNotBlank(state) && state.equals("1")) {

            sortedSetKeyShard(key, redisService);

            redisService.zadd(key, eventTime, UUID.randomUUID().toString());
        }

        Long count = redisService.zcount(key, (eventTime - second * 1000), eventTime);

        if (count == null) {
            count = Long.valueOf(-1);
        }

        return count;
    }

    public void sortedSetKeyShard(String key, RedisService redisService) {

        String shardKey = CacheKeyEnum.RISK_NEED_CLEAR_KEY_MAPPING.getKey(key);

        String shardKeyValue = redisService.get(shardKey);

        if (StringUtils.isBlank(shardKeyValue)) {

            String shardNum = String.valueOf(RandomUtil.random(RiskConstants.SET_KEY_NUM_MIN,
                    RiskConstants.SET_KEY_NUM_MAX));
            redisService.set(shardKey, shardNum);

            redisService.sadd(CacheKeyEnum.RISK_NEED_CLEAR_SET.getKey(shardNum), key);
        }
    }

    /**
     * 统计一段时间内主属性关联从属性的个数
     *
     * @param eventType
     * @param mProperty
     * @param sProperty
     * @param second
     * @return
     */
    public long countRelationRiskTerm(String eventType, String keyPrefix, String mProperty,
                                      String sProperty, int second) {

        if (StringUtils.isBlank(eventType) || StringUtils.isBlank(mProperty)
                || StringUtils.isBlank(sProperty)) {
            return -1;
        }

        TextScanService textScanService = SpringContextUtil.getBean(TextScanService.class);
        List<String> scanResult = textScanService.parseTerms(sProperty, RiskWordType.ALL.getCode());
        if (scanResult == null || scanResult.size() <= 0) {
            return -1;
        }
        RedisService redisService = SpringContextUtil.getBean(RedisService.class);

        String key = CacheKeyEnum.FUNCTION_CR.getKey(eventType + "_" + keyPrefix + "_" + mProperty);

        sortedSetKeyShard(key, redisService);

        long eventTime = EventContextUtil.getEventTime();

        redisService.zadd(key, eventTime, mProperty);

        Long count = redisService.zcount(key, (eventTime - second * 1000), eventTime);

        if (count == null) {
            count = Long.valueOf(-1);
        }

        return count;
    }
}
