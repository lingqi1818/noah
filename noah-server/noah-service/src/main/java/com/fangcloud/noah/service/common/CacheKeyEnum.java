package com.fangcloud.noah.service.common;

/**
 * Created by chenke on 16-8-20.
 */
public enum CacheKeyEnum {

    DEVICE_ID_ACCOUNT("risk_device_id_account", 60 * 60 * 24),//设备关联账户 缓存24小时
    BEACON_ID_ACCOUNT("risk_beacon_id_account", 60 * 60 * 24),//beaconId关联账户
    IP_ACCOUNT("risk_ip_account", 60 * 60 * 24),//ip关联账户
    DEVICE_INFO("risk_device_info", 60 * 60 * 48),//设备信息缓存
    FRAUD_RULE("risk_fraud_rule_set", 0),


    //ruleEngine function key
    FUNCTION_CR("risk_function_cr", 60 * 60 * 24 * 7),
    FUNCTION_CT("risk_function_ct", 60 * 60 * 24 * 7),
    FUNCTION_FAIL_COUNT("risk_function_fail_count", 60 * 60 * 24 * 7),

    //sortedset need clear
    RISK_NEED_CLEAR_SET("risk_need_clear_set",0),

    RISK_NEED_CLEAR_KEY_MAPPING("risk_need_clear_key_mapping",0),


    //beaconId黑名单
    RISK_BLACK_LIST_BEACONID("risk_black_list_beacon_id",60*60*24),
    //mobile  黑名单
    RISK_BLACK_LIST_MOBILE("risk_black_list_mobile",60*60),

    //风控应用采集配置
    APPLICATION_COLLECT_DEFINITION("risk_application_collect",0),



    //秒杀业务黑名单
    RISK_SECOND_KILL_BLACK_LIST("risk_second_kill_black_list",0);


    String keyPrefix;

    Integer expireSeconds;


    CacheKeyEnum(String keyPrefix, Integer expireSeconds) {
        this.keyPrefix = keyPrefix;
        this.expireSeconds = expireSeconds;
    }


    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public Integer getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(Integer expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public String getKey(String key) {
        return keyPrefix + "_" + key;
    }

    public String getKey() {
        return keyPrefix;
    }
}
