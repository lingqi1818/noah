package com.fangcloud.noah.api.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenke on 16-9-8.
 */
public class RiskResult implements Serializable{


    private static final long serialVersionUID = 508890358911510830L;

    public static final Integer FINAL_DECISION_ACCEPT = 1;

    public static final Integer FINAL_DECISION_REVIEW = 2;

    public static final Integer FINAL_DECISION_REJECT = 3;

    //事件sequenceId
    private String sequenceId;

    //调用是否成功
    private boolean success;

    //提示信息
    private String message;

    //最终决策  1 ACCEPT ，2 REVIEW ，3 REJECT
    private Integer finalDecision;

    //事件最终评分
    private Integer finalScore;

    //命中规则　key 为规则code
    private Map<String,HitRule> hitRuleMap = new HashMap<String,HitRule>();

    //裁决结果Map
    private Map<String,String> decisionMap = new HashMap<String,String>();

    private List<HitRule> hitRules = new ArrayList<HitRule>();

    //耗时
    private long speedTime;


    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getFinalDecision() {
        return finalDecision;
    }

    public void setFinalDecision(Integer finalDecision) {
        this.finalDecision = finalDecision;
    }


    public Integer getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Integer finalScore) {
        this.finalScore = finalScore;
    }

    public List<HitRule> getHitRules() {
        return hitRules;
    }

    public void setHitRules(List<HitRule> hitRules) {
        this.hitRules = hitRules;
    }

    public Map<String, HitRule> getHitRuleMap() {
        return hitRuleMap;
    }

    public void setHitRuleMap(Map<String, HitRule> hitRuleMap) {
        this.hitRuleMap = hitRuleMap;
    }

    public Map<String, String> getDecisionMap() {
        return decisionMap;
    }

    public void setDecisionMap(Map<String, String> decisionMap) {
        this.decisionMap = decisionMap;
    }

    public long getSpeedTime() {
        return speedTime;
    }

    public void setSpeedTime(long speedTime) {
        this.speedTime = speedTime;
    }
}
