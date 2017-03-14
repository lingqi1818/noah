package com.fangcloud.noah.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fangcloud.noah.dao.entity.RuleEntity;
import com.fangcloud.noah.dao.mapper.RuleMapper;

import java.util.List;

/**
 * Created by chenke on 16-8-20.
 */
@Service
public class RuleService {

    @Autowired
    private RuleMapper ruleMapper;

    public void addRule(RuleEntity rule) {
        ruleMapper.insert(rule);
    }

    public List<RuleEntity> queryRuleByPolicyId(Integer policyId) {
        return ruleMapper.selectRuleByPolicyId(policyId);
    }

    public List<RuleEntity> loadAllRule() {

        return ruleMapper.loadAllRule();
    }

    public RuleEntity queryRuleById(Integer ruleId) {
        return ruleMapper.selectRuleById(ruleId);
    }

    public void updateRule(RuleEntity rule) {

        RuleEntity ruleEntity = ruleMapper.selectRuleById(rule.getId());

        if (ruleEntity != null) {
            ruleEntity.setPolicyId(rule.getPolicyId());
            ruleEntity.setName(rule.getName());
            ruleEntity.setCode(rule.getCode());
            ruleEntity.setRiskWeight(rule.getRiskWeight());
            ruleEntity.setExpression(rule.getExpression());
            ruleEntity.setStatus(rule.getStatus());
            ruleEntity.setActionName(rule.getActionName());
            ruleEntity.setDecisionConfig(rule.getDecisionConfig());
            ruleMapper.updateRule(ruleEntity);
        }
    }

    public void deleteRule(RuleEntity ruleEntity) {
        ruleMapper.deleteRuleById(ruleEntity.getId());
    }

    public void enableRule(RuleEntity ruleEntity) {
        ruleMapper.updateRule(ruleEntity);
    }
}
