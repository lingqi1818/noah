package com.fangcloud.noah.dao.enums;

/**
 * Created by chenke on 16-8-20.
 */
public enum DeviceType {

    ANDROID(0, "android"),
    IOS(1, "ios"),
    WEB(2, "web"),
    UNKNOW(3, "unknow");

    private int code;
    private String name;

    DeviceType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
