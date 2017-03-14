package com.fangcloud.noah.service.vo;

import java.io.Serializable;

/**
 * Created by chenke on 16-12-23.
 */
public class EventStatisticsTmp implements Serializable {

    private String id;

    private EventStatisticsVo value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EventStatisticsVo getValue() {
        return value;
    }

    public void setValue(EventStatisticsVo value) {
        this.value = value;
    }
}
