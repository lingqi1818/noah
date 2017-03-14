package com.fangcloud.noah.service.executor;

import java.util.List;
import java.util.Map;

import com.fangcloud.noah.dao.entity.PolicyEntity;
import com.fangcloud.noah.dao.entity.RuleEntity;
import com.fangcloud.noah.ruleengine.rule.action.Action;

/**
 * Created by chenke on 16-8-25.
 */
public interface RuleExecutor {


    void init();

    List<RuleExecuteResult> execute(PolicyEntity policyEntity, Map<String, Object> contextMap);

    void addRule(Integer policyId, RuleEntity ruleEntity);

    void removeRule(Integer policyId, RuleEntity ruleEntity);

    void addPolicyAction(String actionName, Action action);

    void removePolicyAction(String actionName);

    void addRuleAction(String actionName, Action action);

    void removeRuleAction(String actionName);


}


