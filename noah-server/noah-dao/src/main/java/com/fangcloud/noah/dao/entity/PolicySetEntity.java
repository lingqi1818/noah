package com.fangcloud.noah.dao.entity;

import java.util.List;

/**
 * Created by chenke on 16-5-6.
 */
public class PolicySetEntity {

    private int id;

    private String name;

    private String eventType;

    private String eventId;

    private int appType;

    private String remark;

    private List<PolicyEntity> policyList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<PolicyEntity> getPolicyList() {
        return policyList;
    }

    public void setPolicyList(List<PolicyEntity> policyList) {
        this.policyList = policyList;
    }
}
