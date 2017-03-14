package com.fangcloud.noah.dao.enums;

/**
 * Created by chenke on 16-9-26.
 */
public enum  ApplicationType {

    helijia_api("helijia_api"),
    helijia_h5_customer("helijia_h5_customer"),
    helijia_app_customer("helijia_app_customer"),
    helijia_app_user("helijia_app_user");

    private String name;

    ApplicationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
