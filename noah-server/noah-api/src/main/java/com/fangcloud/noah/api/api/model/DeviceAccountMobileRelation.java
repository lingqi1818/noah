package com.fangcloud.noah.api.api.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenke on 16-9-27.
 */
public class DeviceAccountMobileRelation implements Serializable {


    private static final long serialVersionUID = -972939441743408111L;
    private String latestDeviceId;

    private String latestBeaconId;

    List<String> relationMobileList;

    public String getLatestDeviceId() {
        return latestDeviceId;
    }

    public void setLatestDeviceId(String latestDeviceId) {
        this.latestDeviceId = latestDeviceId;
    }

    public String getLatestBeaconId() {
        return latestBeaconId;
    }

    public void setLatestBeaconId(String latestBeaconId) {
        this.latestBeaconId = latestBeaconId;
    }

    public List<String> getRelationMobileList() {
        return relationMobileList;
    }

    public void setRelationMobileList(List<String> relationMobileList) {
        this.relationMobileList = relationMobileList;
    }
}
