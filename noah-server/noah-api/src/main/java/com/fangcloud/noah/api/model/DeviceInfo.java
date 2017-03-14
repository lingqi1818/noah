package com.fangcloud.noah.api.model;

import java.io.Serializable;

/**
 * Created by chenke on 16-7-13.
 */
public class DeviceInfo implements Serializable{

    private static final long serialVersionUID = -372912725072130153L;


    private static final String TYPE_ANDROID = "A";
    private static final String TYPE_IOS = "I";

    private String osType;
    private String signVer;
    private String deviceId;
    private String cpuInfo;
    private String cpuId;
    private String cpuMode;
    private String date;

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getSignVer() {
        return signVer;
    }

    public void setSignVer(String signVer) {
        this.signVer = signVer;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCpuInfo() {
        return cpuInfo;
    }

    public void setCpuInfo(String cpuInfo) {
        this.cpuInfo = cpuInfo;
    }

    public String getCpuId() {
        return cpuId;
    }

    public void setCpuId(String cpuId) {
        this.cpuId = cpuId;
    }

    public String getCpuMode() {
        return cpuMode;
    }

    public void setCpuMode(String cpuMode) {
        this.cpuMode = cpuMode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
