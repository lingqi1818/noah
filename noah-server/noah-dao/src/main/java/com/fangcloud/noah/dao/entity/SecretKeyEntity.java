package com.fangcloud.noah.dao.entity;

/**
 * Created by chenke on 16-4-14.
 */
public class SecretKeyEntity {

    private Integer id;

    private String appVersionBegin;

    private String appVersionEnd;

    private Integer deviceType;

    private String publicKey;

    private String privateKey;

    private Integer valid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppVersionBegin() {
        return appVersionBegin;
    }

    public void setAppVersionBegin(String appVersionBegin) {
        this.appVersionBegin = appVersionBegin;
    }

    public String getAppVersionEnd() {
        return appVersionEnd;
    }

    public void setAppVersionEnd(String appVersionEnd) {
        this.appVersionEnd = appVersionEnd;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }
}
