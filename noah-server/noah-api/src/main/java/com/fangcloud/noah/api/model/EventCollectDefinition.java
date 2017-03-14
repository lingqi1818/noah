package com.fangcloud.noah.api.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenke on 16-3-29.
 */
public class EventCollectDefinition implements Serializable {

    private static final long serialVersionUID = -866803019142642849L;
    private String uri;

    private String applicationName;

    private String eventType;

    private boolean switchStatus;

    List<MappedCollectParam> mappedCollectParamList;

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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isSwitchStatus() {
        return switchStatus;
    }

    public void setSwitchStatus(boolean switchStatus) {
        this.switchStatus = switchStatus;
    }

    public List<MappedCollectParam> getMappedCollectParamList() {
        return mappedCollectParamList;
    }

    public void setMappedCollectParamList(List<MappedCollectParam> mappedCollectParamList) {
        this.mappedCollectParamList = mappedCollectParamList;
    }
}
