package com.fangcloud.noah.api.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 风险事件 Created by chenke on 16-3-29.
 */
public class RiskEvent implements Serializable {

    private static final long   serialVersionUID  = 1L;

    //请求uri
    private String              uri;

    //请求ip
    private String              ipAddress;

    //userAgent
    private String              userAgent;

    //网页请求referer
    private String              referer;

    //设备类型
    private String              deviceType;

    //同盾blackBox
    private String              blackBox;

    //同盾tokenId
    private String              tokenId;

    //beaconId
    private String              beaconId;

    //事件类型
    private String              eventType;

    //设备信息
    private String              deviceInfo;

    //app版本
    private String              appVersion;

    private String              sequenceId;

    private String              applicationName;

    //采集参数
    private Map<String, Object> collectParameters = new HashMap<String, Object>();

    public RiskEvent() {
    }

    public RiskEvent(String uri, String ipAddress, String userAgent, String referer,
                     String deviceType, String blackBox, String tokenId, String beaconId,
                     String eventType, String deviceInfo, String appVersion, String sequenceId,
                     String applicationName, Map<String, Object> collectParameters) {
        this.uri = uri;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.referer = referer;
        this.deviceType = deviceType;
        this.blackBox = blackBox;
        this.tokenId = tokenId;
        this.beaconId = beaconId;
        this.eventType = eventType;
        this.deviceInfo = deviceInfo;
        this.appVersion = appVersion;
        this.sequenceId = sequenceId;
        this.applicationName = applicationName;
        this.collectParameters = collectParameters;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getBlackBox() {
        return blackBox;
    }

    public void setBlackBox(String blackBox) {
        this.blackBox = blackBox;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(String beaconId) {
        this.beaconId = beaconId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public Map<String, Object> getCollectParameters() {
        return collectParameters;
    }

    public void setCollectParameters(Map<String, Object> collectParameters) {
        this.collectParameters = collectParameters;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
