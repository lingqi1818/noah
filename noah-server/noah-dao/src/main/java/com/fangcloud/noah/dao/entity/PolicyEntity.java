package com.fangcloud.noah.dao.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by chenke on 16-5-6.
 */
public class PolicyEntity {

    private int id;
    @NotEmpty(message = "名称不能为空")
    private String name;
    @NotEmpty(message = "事件类型不能为空")
    private String eventType;
    @NotEmpty(message = "事件标示不能为空")
    private String eventId;

    private int deviceType;

    private int minRisk;

    private int maxRisk;

    private String remark;
    @Pattern(regexp = "\\s*|^[\\w-]+(\\.groovy)$",message = "执行动作必须以.groovy结尾")
    private String actionName;

    private String eventTypeName;


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

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getMinRisk() {
        return minRisk;
    }

    public void setMinRisk(int minRisk) {
        this.minRisk = minRisk;
    }

    public int getMaxRisk() {
        return maxRisk;
    }

    public void setMaxRisk(int maxRisk) {
        this.maxRisk = maxRisk;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getEventTypeName() {
        return eventTypeName;
    }

    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PolicyEntity that = (PolicyEntity) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
