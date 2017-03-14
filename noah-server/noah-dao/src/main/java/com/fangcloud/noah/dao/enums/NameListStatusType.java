package com.fangcloud.noah.dao.enums;

/**
 * Created by chenke on 16-8-20.
 */
public enum NameListStatusType {

    INIT(0, "新增"),

    SYNC_FAIL(1, "同步API数据库黑名单失败"),

    SYNC_SUCCESS(2, "同步API数据库黑名单成功"),

    INVALID(3, "无效");

    private int code;

    private String name;


    NameListStatusType(int code, String name) {
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
