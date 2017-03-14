package com.fangcloud.noah.api.api.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by chenke on 16-9-8.
 */
public class HitRule implements Serializable{

    private static final long serialVersionUID = 2323573769125000295L;

    private Integer id;

    private String code;

    private String name;

    private Integer score;

    private long speedTime;

    private Map<String,String> decisionMap;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Map<String, String> getDecisionMap() {
        return decisionMap;
    }

    public void setDecisionMap(Map<String, String> decisionMap) {
        this.decisionMap = decisionMap;
    }
}
