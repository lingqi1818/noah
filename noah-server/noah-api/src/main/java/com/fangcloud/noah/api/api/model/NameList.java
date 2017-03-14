package com.fangcloud.noah.api.api.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 名单模型
 *
 * @author: YJL  Date: 16-1-30
 */
public class NameList implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer id;

    /**
     * 名单内容
     */
    private String content;

    /**
     * 名单类型
     * 1 手机号、2 ip、3 设备id 4 beacon_id
     */
    private Integer type;

    /**
     * 名单等级
     *
     * 1 黑名单 2 白名单 3 灰名单
     */
    private Integer grade;

    private Timestamp gmtCreated;

    private Timestamp gmtModified;

    private Integer status;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Timestamp getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Timestamp gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
