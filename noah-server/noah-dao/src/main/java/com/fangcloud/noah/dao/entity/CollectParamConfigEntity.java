package com.fangcloud.noah.dao.entity;

import java.util.List;

/**
 * Created by chenke on 16-9-26.
 */
public class CollectParamConfigEntity {

    private Integer id;

    private String uri;

    private String applicationName;

    private String eventType;

    private Integer switchStatus;

    private List<CollectParamDetailEntity> collectParamList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getSwitchStatus() {
        return switchStatus;
    }

    public void setSwitchStatus(Integer switchStatus) {
        this.switchStatus = switchStatus;
    }

    public List<CollectParamDetailEntity> getCollectParamList() {
        return collectParamList;
    }

    public void setCollectParamList(List<CollectParamDetailEntity> collectParamList) {
        this.collectParamList = collectParamList;
    }
}
