package com.fangcloud.noah.api.api.enums;

/**
 * Created by chenke on 16-11-8.
 */
public enum RiskWordType {

    ALL("all", "全局");

    private String code;

    private String name;

    RiskWordType(String code, String name) {
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
