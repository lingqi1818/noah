package com.fangcloud.noah.dao.enums;

/**
 * Created by chenke on 16-8-23.
 */
public enum Decision {

    ACCEPT(1, "ACCEPT"),
    REVIEW(2, "REVIEW"),
    REJECT(3, "REJECT");

    private int code;
    private String name;

    Decision(int code, String name) {
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
