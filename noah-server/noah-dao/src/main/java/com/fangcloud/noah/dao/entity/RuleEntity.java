package com.fangcloud.noah.dao.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenke on 16-8-20.
 */
public class RuleEntity {

    public static final Integer RULE_STATUS_ENABLE = 0;

    public static final Integer RULE_STATUS_Disable = 1;


    private Integer id;

    private Integer policyId;

    @NotEmpty(message = "名称不能为空")
    private String name;

    @NotEmpty(message = "编号不能为空")
    private String code;

    @NotNull(message = "风险权重不能为空")
    private Integer riskWeight;

    @NotEmpty(message = "表达式不能为空")
    private String expression;

    private Timestamp createTime;

    private Timestamp updateTime;

    private Integer status;

    private Integer valid;

    private String actionName;

    private String decisionConfig;

    private Map<String,String> decisionMap;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Integer policyId) {
        this.policyId = policyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getRiskWeight() {
        return riskWeight;
    }

    public void setRiskWeight(Integer riskWeight) {
        this.riskWeight = riskWeight;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getDecisionConfig() {
        return decisionConfig;
    }

    public void setDecisionConfig(String decisionConfig) {
        this.decisionConfig = decisionConfig;
    }

    public Map<String, String> getDecisionMap() {
        return decisionMap;
    }

    public void setDecisionMap(Map<String, String> decisionMap) {
        this.decisionMap = decisionMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RuleEntity that = (RuleEntity) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
