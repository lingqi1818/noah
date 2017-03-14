package com.fangcloud.noah.dao.entity;

/**
 * Created by chenke on 16-9-26.
 */
public class CollectParamDetailEntity {

    private Integer id;

    private String src;

    private String target;

    private String type;

    private Integer collectParamConfigId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCollectParamConfigId() {
        return collectParamConfigId;
    }

    public void setCollectParamConfigId(Integer collectParamConfigId) {
        this.collectParamConfigId = collectParamConfigId;
    }
}
