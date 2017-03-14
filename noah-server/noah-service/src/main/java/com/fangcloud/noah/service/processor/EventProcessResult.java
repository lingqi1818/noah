package com.fangcloud.noah.service.processor;


import java.util.ArrayList;
import java.util.List;

import com.fangcloud.noah.service.executor.RuleExecuteResult;

/**
 * Created by chenke on 16-8-22.
 */
public class EventProcessResult {

    private boolean success;

    private String message;

    private Integer finalDecision = 1;

    private Integer finalScore = 0;

    private List<RuleExecuteResult> hitRules = new ArrayList<RuleExecuteResult>();

    private List<RuleExecuteResult> allRuleExecuteResults = new ArrayList<RuleExecuteResult>();

    private String eventType;

    private String sequenceId;

    private long spendTime;

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

    public List<RuleExecuteResult> getHitRules() {
        return hitRules;
    }

    public void setHitRules(List<RuleExecuteResult> hitRules) {
        this.hitRules = hitRules;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public long getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(long spendTime) {
        this.spendTime = spendTime;
    }

    public List<RuleExecuteResult> getAllRuleExecuteResults() {
        return allRuleExecuteResults;
    }

    public void setAllRuleExecuteResults(List<RuleExecuteResult> allRuleExecuteResults) {
        this.allRuleExecuteResults = allRuleExecuteResults;
    }
}
