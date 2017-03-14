package com.fangcloud.noah.api.api.enums;

/**
 * Created by chenke on 16-5-3.
 */
public enum NameListGrade {


    BLACK(1),
    WHITE(2),
    GRAY(3);

    private int code;

    NameListGrade(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
