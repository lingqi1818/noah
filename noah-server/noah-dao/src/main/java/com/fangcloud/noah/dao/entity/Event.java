package com.fangcloud.noah.dao.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 事件对象
 * Created by chenke on 16-3-17.
 */
public class Event implements Serializable {

    public static final int DECISION_NOTHING = 0;
    public static final int DECISION_ACCEPT = 1;
    public static final int DECISION_REVIEW = 2;
    public static final int DECISION_REJECT = 3;

    public static final int DECISION_PARTY_FRAUD = 1;
    public static final int DECISION_PARTY_HLJ = 2;

    private String id;

    //事件唯一id
    private String sequenceId;

    //账号
    private String accountLogin;

    //手机
    private String accountMobile;

    //ip
    private String ip;

    //状态校验结果
    private String state;

    //设备id
    private String deviceId;

    //beaconId
    private String beaconId;

    //事件类型
    private String eventType;

    //事件详情
    private String eventDetail;

    //裁决评分
    private int score;

    //裁决结果
    private int decision;

    //裁决详情
    private String decisionDetail;

    //裁决方
    private int decisionParty;

    //userAgent
    private String userAgent;

    //设备类型
    private int deviceType;

    private Timestamp createTime;

    private Timestamp updateTime;

    //事件时间
    private String eventTime;

    //状态
    private int eventDataStatus;

    private IpEntity ipEntity;

    private PhoneEntity phoneEntity;

    //备注
    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(String accountLogin) {
        this.accountLogin = accountLogin;
    }

    public String getAccountMobile() {
        return accountMobile;
    }

    public void setAccountMobile(String accountMobile) {
        this.accountMobile = accountMobile;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(String beaconId) {
        this.beaconId = beaconId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventDetail() {
        return eventDetail;
    }

    public void setEventDetail(String eventDetail) {
        this.eventDetail = eventDetail;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getDecision() {
        return decision;
    }

    public void setDecision(int decision) {
        this.decision = decision;
    }

    public String getDecisionDetail() {
        return decisionDetail;
    }

    public void setDecisionDetail(String decisionDetail) {
        this.decisionDetail = decisionDetail;
    }

    public int getDecisionParty() {
        return decisionParty;
    }

    public void setDecisionParty(int decisionParty) {
        this.decisionParty = decisionParty;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
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

    public int getEventDataStatus() {
        return eventDataStatus;
    }

    public void setEventDataStatus(int eventDataStatus) {
        this.eventDataStatus = eventDataStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public IpEntity getIpEntity() {
        return ipEntity;
    }

    public void setIpEntity(IpEntity ipEntity) {
        this.ipEntity = ipEntity;
    }

    public PhoneEntity getPhoneEntity() {
        return phoneEntity;
    }

    public void setPhoneEntity(PhoneEntity phoneEntity) {
        this.phoneEntity = phoneEntity;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
}
