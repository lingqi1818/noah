package com.fangcloud.noah.service.executor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.fangcloud.noah.api.exception.RiskRuntimeException;
import com.fangcloud.noah.dao.entity.ActionScriptEntity;
import com.fangcloud.noah.dao.entity.PolicyEntity;
import com.fangcloud.noah.dao.entity.RuleEntity;
import com.fangcloud.noah.ruleengine.DefaultRuleEngine;
import com.fangcloud.noah.ruleengine.RuleEngine;
import com.fangcloud.noah.ruleengine.rule.Rule;
import com.fangcloud.noah.ruleengine.rule.action.Action;
import com.fangcloud.noah.service.processor.EventProcessor;
import com.fangcloud.noah.service.service.ActionScriptService;
import com.fangcloud.noah.service.service.RuleService;

import groovy.lang.GroovyClassLoader;

/**
 * Created by chenke on 16-8-25.
 */
@Component("ruleExecutor")
public class DefaultRuleExecutor implements RuleExecutor, InitializingBean {

    private final Logger                   logger                   = LoggerFactory
            .getLogger(EventProcessor.class);

    private Map<Integer, List<RuleEntity>> policyIdRuleMap          = new ConcurrentHashMap<Integer, List<RuleEntity>>();

    private Map<String, Action>            ruleActionMap            = new ConcurrentHashMap<String, Action>();

    private Map<String, Action>            policyActionMap          = new ConcurrentHashMap<String, Action>();

    private final ScheduledExecutorService scheduledExecutorService = Executors
            .newScheduledThreadPool(1);

    @Autowired
    private ActionScriptService            actionScriptService;

    @Autowired
    private RuleService                    ruleService;

    @Override
    public void init() {
        try {
            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {

                    try {
                        Map<Integer, List<RuleEntity>> tmpMap = new HashMap<Integer, List<RuleEntity>>();

                        List<RuleEntity> ruleEntityList = ruleService.loadAllRule();
                        if (CollectionUtils.isNotEmpty(ruleEntityList)) {
                            for (RuleEntity ruleEntity : ruleEntityList) {

                                String decisionConfig = ruleEntity.getDecisionConfig();

                                Map<String, String> decisionMap = new HashMap<String, String>();
                                if (StringUtils.isNotBlank(decisionConfig)) {
                                    String[] decisionConfigArray = decisionConfig.split(",");
                                    if (decisionConfigArray != null) {
                                        for (String d : decisionConfigArray) {
                                            if (StringUtils.isNotBlank(d)) {
                                                String[] dArray = d.split("#");
                                                if (dArray != null && dArray.length == 2) {
                                                    decisionMap.put(dArray[0], dArray[1]);
                                                }
                                            }
                                        }
                                    }
                                }

                                ruleEntity.setDecisionMap(decisionMap);

                                List<RuleEntity> ruleEntities = tmpMap
                                        .get(ruleEntity.getPolicyId());
                                if (ruleEntities != null) {
                                    ruleEntities.add(ruleEntity);
                                } else {
                                    List<RuleEntity> tmpList = new ArrayList<RuleEntity>();
                                    tmpList.add(ruleEntity);
                                    tmpMap.put(ruleEntity.getPolicyId(), tmpList);
                                }
                            }
                        }
                        policyIdRuleMap = tmpMap;

                    } catch (Throwable t) {
                        logger.error("定时任务执行异常，" + t.getMessage());
                    }

                }
            }, 0, 1, TimeUnit.MINUTES);

            loadAction();

            logger.info("Rule Executor init finish!");
        } catch (Exception e) {
            logger.error("RuleExecutor init exception", e);
            throw new RiskRuntimeException(e);
        }
    }

    private void loadAction() {

        ClassLoader parent = getClass().getClassLoader();
        GroovyClassLoader loader = new GroovyClassLoader(parent);

        List<ActionScriptEntity> actionScriptEntityList = actionScriptService
                .queryAllActionScripts();

        if (CollectionUtils.isNotEmpty(actionScriptEntityList)) {
            for (ActionScriptEntity entity : actionScriptEntityList) {
                String scriptContent = entity.getScriptContent();
                try {
                    Class groovyClass = loader.parseClass(scriptContent);
                    Action groovyAction = (Action) groovyClass.newInstance();
                    if (entity.getType().equals(ActionScriptEntity.TYPE_RULE)) {
                        ruleActionMap.put(entity.getId() + "", groovyAction);
                    } else if (entity.getType().equals(ActionScriptEntity.TYPE_POLICY)) {
                        policyActionMap.put(entity.getId() + "", groovyAction);
                    }
                } catch (Exception e) {
                    logger.error("加载action脚本异常,entityId=" + entity.getId(), e);
                }

            }
        }
    }

    @Override
    public List<RuleExecuteResult> execute(final PolicyEntity policyEntity,
                                           final Map<String, Object> contextMap) {

        List<RuleExecuteResult> ruleExecuteResultList = new ArrayList<RuleExecuteResult>();

        List<RuleEntity> ruleEntityList = policyIdRuleMap.get(policyEntity.getId());

        if (CollectionUtils.isEmpty(ruleEntityList)) {
            return ruleExecuteResultList;
        }

        for (RuleEntity ruleEntity : ruleEntityList) {

            long ruleStartTime = System.currentTimeMillis();

            Action action = null;
            if (StringUtils.isNotBlank(ruleEntity.getActionName())) {
                action = ruleActionMap.get(ruleEntity.getActionName());
            }

            RuleExecuteResult ruleResult = new RuleExecuteResult();
            ruleResult.setId(ruleEntity.getId());
            ruleResult.setName(ruleEntity.getName());
            ruleResult.setCode(ruleEntity.getCode());
            ruleResult.setDecisionMap(ruleEntity.getDecisionMap());
            ruleResult.setScore(ruleEntity.getRiskWeight());

            Object result = null;

            try {

                RuleEngine ruleEngine = new DefaultRuleEngine();

                ruleEngine.putDomainToContext(contextMap);

                Rule rule = new Rule(String.valueOf(ruleEntity.getId()), ruleEntity.getName(),
                        ruleEntity.getExpression());

                result = ruleEngine.execute(rule);

            } catch (Exception e) {
                logger.error("规则执行异常,ruleId=" + ruleEntity.getId(), e);
            }

            long ruleEndTime = System.currentTimeMillis();

            logger.info("rule execute speed time:" + (ruleEndTime - ruleStartTime));

            ruleResult.setSpeedTime(ruleEndTime - ruleStartTime);

            if (result != null) {
                if ((result instanceof Boolean)) {
                    Boolean bool = (Boolean) result;
                    if (bool) {
                        //自定义规则
                        ruleResult.setHit(true);

                        if (action != null) {
                            executeRuleAction(action, contextMap,
                                    ruleEntity.getId() + "#" + ruleEntity.getName());
                        }
                    } else {
                        ruleResult.setHit(false);
                    }
                }
                //                 else if (result instanceof FraudApiResponse) {
                //                    //调用同盾返回结果
                //                    FraudApiResponse fraudApiResponse = (FraudApiResponse) result;
                //                    ruleResult.setIsFraud(true);
                //                    ruleResult.setScore(fraudApiResponse.getFinal_score());
                //                    ruleResult.setFraudResponse(JSON.toJSONString(fraudApiResponse));
                //                    ruleResult.setHit(true);
                //
                //                } 

                else {
                    logger.error("规则执行结果异常，result为非法类型,result=" + JSON.toJSONString(result));
                }
            } else {
                logger.error("规则执行结果异常，result为空，ruleId=" + ruleEntity.getId());
            }
            ruleExecuteResultList.add(ruleResult);
        }
        return ruleExecuteResultList;
    }

    private void executeRuleAction(final Action action, final Map<String, Object> contextMap,
                                   final String description) {

        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    action.execute(contextMap, description);
                } catch (Exception e) {
                    logger.error("action excute exception:", e);
                }
            }
        });
    }

    @Override
    public void addRule(Integer policyId, RuleEntity ruleEntity) {

        List<RuleEntity> ruleEntityList = policyIdRuleMap.get(policyId);

        if (ruleEntityList.contains(ruleEntity)) {
            ruleEntityList.remove(ruleEntity);
            ruleEntityList.add(ruleEntity);
        } else {
            ruleEntityList.add(ruleEntity);
        }
    }

    @Override
    public void removeRule(Integer policyId, RuleEntity ruleEntity) {

        List<RuleEntity> ruleEntityList = policyIdRuleMap.get(policyId);

        if (ruleEntityList.contains(ruleEntity)) {
            ruleEntityList.remove(ruleEntity);
        }
    }

    @Override
    public void addPolicyAction(String actionName, Action action) {
        policyActionMap.put(actionName, action);
    }

    @Override
    public void removePolicyAction(String actionName) {
        policyActionMap.remove(actionName);
    }

    @Override
    public void addRuleAction(String actionName, Action action) {
        ruleActionMap.put(actionName, action);
    }

    @Override
    public void removeRuleAction(String actionName) {
        ruleActionMap.remove(actionName);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}
