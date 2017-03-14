package com.fangcloud.noah.dao.entity;

import java.sql.Timestamp;

/**
 * Created by chenke on 16-8-20.
 */
public class UserDeviceInfoEntity {

    //普通用户
    public static final int USER_TYPE_COMMON = 0;
    //手艺人
    public static final int USER_TYPE_ARTISAN = 1;

    //有效
    public static final int DATA_VALID = 1;
    //无效
    public static final int DATA_INVALID = 0;

    private Integer id;

    private String userAccount;

    private String userId;

    private Integer userType;

    private Integer deviceType;

    private String signVer;

    private String deviceId;

    private String cpuId;

    private String cpuMode;

    private Integer valid;

    private Timestamp createTime;

    private Timestamp updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
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

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
