package com.fangcloud.noah.dao.entity;

import java.sql.Timestamp;

/**
 * Created by chenke on 16-9-23.
 */
public class RiskTermEntity {

    private Integer id;
    //单词
    private String word;

    //单词应用范围
    private String wordType;

    private String wordTypeStr;

    //是否整词匹配 1 是, 0 否
    private Integer wholeWord;
    //是否跳字匹配 1 是, 0 否
    private Integer allowSkip;
    //权重
    private Integer weight;

    private Timestamp createTime;

    private Timestamp updateTime;
    //状态 1有效 ，0 无效
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getWholeWord() {
        return wholeWord;
    }

    public void setWholeWord(Integer wholeWord) {
        this.wholeWord = wholeWord;
    }

    public Integer getAllowSkip() {
        return allowSkip;
    }

    public void setAllowSkip(Integer allowSkip) {
        this.allowSkip = allowSkip;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
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

    public String getWordType() {
        return wordType;
    }

    public void setWordType(String wordType) {
        this.wordType = wordType;
    }

    public String getWordTypeStr() {
        return wordTypeStr;
    }

    public void setWordTypeStr(String wordTypeStr) {
        this.wordTypeStr = wordTypeStr;
    }
}
