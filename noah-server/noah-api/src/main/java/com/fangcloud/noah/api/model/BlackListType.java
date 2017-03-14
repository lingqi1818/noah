package com.fangcloud.noah.api.model;

/**
 * Created by chenke on 16-10-12.
 */
public enum  BlackListType {

    //全局ip黑名单
    RISK_BLACK_LIST_IP("risk_black_list_ip"),

    //短信接口 beaconId黑名单
    RISK_BLACK_LIST_MESSAGE_BEACONID("risk_black_list_beacon_id"),

    //短信接口 请求来源refer黑名单
    RISK_BLACK_LIST_MESSAGE_REFER("risk_black_list_message_refer"),


    //短信接口 请求来源user agent黑名单 redis set实现
    RISK_BLACK_LIST_MESSAGE_USER_AGENT_SET("risk_black_list_message_user_agent_set"),

    //短信接口 请求来源手机号黑名单
    RISK_BLACK_LIST_MOBILE("risk_black_list_message_mobile"),

    //短信接口 手机号获取验证码过于频繁黑名单
    RISK_BLACK_LIST_MOBILE_TOO_FREQUENTLY("risk_black_list_message_mobile_too_frequently");

    private String value;

    BlackListType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
