package com.fangcloud.noah.service.executor;

import java.util.Map;

/**
 * Created by chenke on 16-8-22.
 */
public class RuleExecuteResult {

    private Integer id;

    private String name;

    private String code;

    private Integer score;

    private boolean isHit = false;

    private Map<String,String> decisionMap;

    private long speedTime;

    private boolean isFraud = false;

    private String fraudResponse;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public long getSpeedTime() {
        return speedTime;
    }

    public void setSpeedTime(long speedTime) {
        this.speedTime = speedTime;
    }

    public boolean isFraud() {
        return isFraud;
    }

    public void setIsFraud(boolean isFraud) {
        this.isFraud = isFraud;
    }

    public String getFraudResponse() {
        return fraudResponse;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setFraudResponse(String fraudResponse) {
        this.fraudResponse = fraudResponse;
    }

    public Map<String, String> getDecisionMap() {
        return decisionMap;
    }

    public void setDecisionMap(Map<String, String> decisionMap) {
        this.decisionMap = decisionMap;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

}
