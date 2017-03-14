package com.fangcloud.noah.api.api.enums;

/**
 * @author: YJL  Date: 16-2-19
 */
public enum  NameListType {

    MOBILE(1),
    IP(2),
    DEVICE(3),
    BEACON_ID(4);

    private int code;

    NameListType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
