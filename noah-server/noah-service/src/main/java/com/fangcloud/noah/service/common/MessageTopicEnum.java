package com.fangcloud.noah.service.common;

/**
 * Created by chenke on 16-12-9.
 */
public enum  MessageTopicEnum {

    RISK_EVENT("helijia_risk_event","风控事件");

    private String code;
    private String name;

    MessageTopicEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
