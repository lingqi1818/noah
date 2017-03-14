package com.fangcloud.noah.api.model;

import java.io.Serializable;

/**
 * Created by chenke on 16-9-24.
 */
public class MappedCollectParam implements Serializable{

    private static final long serialVersionUID = 2652643154985307661L;
    private String src;

    private String target;

    private String type;

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

}
