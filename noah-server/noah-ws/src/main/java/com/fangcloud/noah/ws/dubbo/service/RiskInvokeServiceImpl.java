package com.fangcloud.noah.ws.dubbo.service;


import com.alibaba.fastjson.JSON;
import com.fangcloud.noah.api.api.model.HitRule;
import com.fangcloud.noah.api.api.model.RiskResult;
import com.fangcloud.noah.api.api.service.RiskInvokeService;
import com.fangcloud.noah.service.executor.RuleExecuteResult;
import com.fangcloud.noah.service.processor.EventProcessResult;
import com.fangcloud.noah.service.processor.EventProcessor;
import com.fangcloud.noah.service.processor.EventProcessorFactory;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by chenke on 16-9-8.
 */
@Component("riskInvokeService")
public class RiskInvokeServiceImpl implements RiskInvokeService {

    private static final Logger logger = LoggerFactory.getLogger(RiskInvokeServiceImpl.class);


    @Override
    public RiskResult invoke(Map<String, Object> parameterMap) {

        RiskResult riskResult = new RiskResult();
        String sequenceId = UUID.randomUUID().toString();

        try {

            long eventTime = System.currentTimeMillis();

            parameterMap.put("eventTime",eventTime);
            parameterMap.put("sequenceId", sequenceId);

            logger.info("process invoke:" + JSON.toJSONString(parameterMap));

            String eventType = String.valueOf(parameterMap.get("eventType"));

            EventProcessor eventProcessor = EventProcessorFactory.get(eventType);

            EventProcessResult eventProcessResult = eventProcessor.processEvent(parameterMap);

            if (eventProcessResult != null) {
                riskResult.setSuccess(true);
                riskResult.setSequenceId(sequenceId);
                riskResult.setFinalDecision(eventProcessResult.getFinalDecision());
                riskResult.setFinalScore(eventProcessResult.getFinalScore());
                riskResult.setMessage(eventProcessResult.getMessage());
                riskResult.setSpeedTime(eventProcessResult.getSpendTime());

                if (CollectionUtils.isNotEmpty(eventProcessResult.getHitRules())) {
                    List<HitRule> hitRuleList = new ArrayList<HitRule>();
                    Map<String,HitRule> hitRuleMap = new HashMap<String,HitRule>();
                    Map<String,String> decisionMap = new HashMap<String,String>();
                    for (RuleExecuteResult ruleResult : eventProcessResult.getHitRules()) {
                        HitRule hitRule = new HitRule();
                        hitRule.setId(ruleResult.getId());
                        hitRule.setCode(ruleResult.getCode());
                        hitRule.setName(ruleResult.getName());
                        hitRule.setScore(ruleResult.getScore());
                        hitRuleList.add(hitRule);
                        hitRuleMap.put(ruleResult.getCode(),hitRule);
                        Map<String,String> ruleDecisionMap = ruleResult.getDecisionMap();
                        if(ruleDecisionMap!=null){
                            decisionMap.putAll(ruleDecisionMap);
                            hitRule.setDecisionMap(ruleDecisionMap);
                        }
                    }
                    riskResult.setHitRules(hitRuleList);
                    riskResult.setHitRuleMap(hitRuleMap);
                    riskResult.setDecisionMap(decisionMap);
                }

            } else {
                riskResult.setSuccess(false);
                riskResult.setSequenceId(sequenceId);
                riskResult.setFinalDecision(0);
                riskResult.setFinalScore(0);
                riskResult.setMessage("事件处理结果为空");
                riskResult.setSpeedTime(0);
            }
        } catch (Exception e) {
            riskResult.setSuccess(false);
            riskResult.setSequenceId(sequenceId);
            riskResult.setFinalDecision(0);
            riskResult.setFinalScore(0);
            riskResult.setMessage("事件处理异常:" + e.getMessage());
            riskResult.setSpeedTime(0);
        }
        return riskResult;
    }
}
