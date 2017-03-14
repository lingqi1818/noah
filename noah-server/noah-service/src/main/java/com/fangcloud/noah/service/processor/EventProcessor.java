package com.fangcloud.noah.service.processor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fangcloud.noah.api.model.DeviceInfo;
import com.fangcloud.noah.dao.entity.Event;
import com.fangcloud.noah.dao.entity.IpEntity;
import com.fangcloud.noah.dao.entity.PhoneEntity;
import com.fangcloud.noah.dao.entity.PolicyEntity;
import com.fangcloud.noah.dao.enums.Decision;
import com.fangcloud.noah.dao.enums.DeviceType;
import com.fangcloud.noah.dao.enums.EventType;
import com.fangcloud.noah.service.executor.RuleExecuteResult;
import com.fangcloud.noah.service.executor.RuleExecutor;
import com.fangcloud.noah.service.service.EventService;
import com.fangcloud.noah.service.service.IpService;
import com.fangcloud.noah.service.service.PhoneService;
import com.fangcloud.noah.service.service.PolicyService;
import com.fangcloud.noah.service.service.RelationAccountService;
import com.fangcloud.noah.service.service.UserDeviceInfoService;
import com.fangcloud.noah.service.util.EventContextUtil;

/**
 * Created by chenke on 16-8-22.
 */

public abstract class EventProcessor {

    private final Logger           logger = LoggerFactory.getLogger(EventProcessor.class);

    @Autowired
    private RuleExecutor           ruleExecutor;

    @Autowired
    private UserDeviceInfoService  userDeviceInfoService;

    @Autowired
    private RelationAccountService relationAccountService;

    @Autowired
    private PolicyService          policyService;

    @Autowired
    private PhoneService           phoneService;

    @Autowired
    private IpService              ipService;

    @Autowired
    private EventService           eventService;

    public EventProcessResult processEvent(Map<String, Object> eventMap) {

        long startTime = System.currentTimeMillis();

        try {

            EventContextUtil.putEventContext(eventMap);

            String sequenceId = String.valueOf(eventMap.get("sequenceId"));

            if (StringUtils.isBlank(sequenceId)) {
                sequenceId = UUID.randomUUID().toString();
                eventMap.put("sequenceId", sequenceId);
            }

            repairEventMap(eventMap);

            fillEventMap(eventMap);

            DeviceType deviceType = parseDeviceType(eventMap);
            String deviceId = (String) eventMap.get("deviceId");
//            String deviceId = processDeviceId(sequenceId, deviceType, eventMap);
//
//            if (StringUtils.isNotBlank(deviceId)
//                    && deviceId.equalsIgnoreCase("00000000-0000-0000-0000-000000000000")) {
//                //兼容ios前端bug
//                eventMap.put("deviceId", "");
//            } else {
//                eventMap.put("deviceId", deviceId);
//            }

            eventMap.put("ext_did", deviceId);

            String eventType = String.valueOf(eventMap.get("eventType"));

            PolicyEntity policyEntity = getPolicy(eventType, deviceType.getCode());

            EventProcessResult eventProcessResult = new EventProcessResult();

            Integer finalScore = 0;
            Integer finalDecision = Decision.ACCEPT.getCode();
            List<RuleExecuteResult> hitRules = new ArrayList<RuleExecuteResult>();

            if (policyEntity != null) {

                eventMap.put("eventId", policyEntity.getEventId());

                eventMap.put("policy", JSON.toJSONString(policyEntity));

                eventMap.put("fraud", JSON.toJSONString(eventMap));

                fillPhoneAndIpInfo(eventType, eventMap);

                try {
                    List<RuleExecuteResult> ruleExecuteResultList = ruleExecutor
                            .execute(policyEntity, eventMap);

                    if (CollectionUtils.isNotEmpty(ruleExecuteResultList)) {

                        for (RuleExecuteResult ruleExecuteResult : ruleExecuteResultList) {
                            if (ruleExecuteResult.isHit()) {
                                finalScore += ruleExecuteResult.getScore();
                                hitRules.add(ruleExecuteResult);
                            }
                        }

                        if (finalScore >= policyEntity.getMaxRisk()) {
                            finalDecision = Decision.REJECT.getCode();
                        } else if (finalScore >= policyEntity.getMinRisk()) {
                            finalDecision = Decision.REVIEW.getCode();
                        }
                    }
                    eventProcessResult.setAllRuleExecuteResults(ruleExecuteResultList);
                    eventProcessResult.setSuccess(true);

                } catch (Exception e) {
                    logger.error("ruleExecutor processEvent exception ", e);
                    eventProcessResult.setSuccess(false);
                    eventProcessResult.setMessage("事件处理异常:" + e.getMessage());
                }

            } else {
                eventProcessResult = new EventProcessResult();
                eventProcessResult.setSuccess(false);
                eventProcessResult.setMessage("没找到对应的策略");
            }

            eventProcessResult.setHitRules(hitRules);
            eventProcessResult.setFinalDecision(finalDecision);
            eventProcessResult.setFinalScore(finalScore);
            eventProcessResult.setEventType(eventType);
            eventProcessResult.setSequenceId(sequenceId);

            long endTime = System.currentTimeMillis();

            eventProcessResult.setSpendTime((endTime - startTime));
            saveEvent(sequenceId, eventType, eventMap, eventProcessResult, deviceType);

            return eventProcessResult;

        } catch (Exception e) {
            logger.error("EventProcessor processEvent exception:", e);
            return null;
        } finally {
            EventContextUtil.removeEventContext();
        }
    }

    private void fillPhoneAndIpInfo(String eventType, Map<String, Object> eventMap) {

        String mobile = "";
        if (eventType.equals(EventType.marketing.getCode())
                || eventType.equals(EventType.message.getCode())) {
            mobile = String.valueOf(eventMap.get("account_mobile"));
        } else {
            mobile = String.valueOf(eventMap.get("account_login"));
        }

        PhoneEntity phoneEntity = phoneService.getPhoneInfo(mobile);

        eventMap.put("phoneEntity", phoneEntity);

        if (phoneEntity != null) {
            eventMap.put("phoneCity", phoneEntity.getCity());
            eventMap.put("phoneProvince", phoneEntity.getProvince());
        }

        String ip = String.valueOf(eventMap.get("ipAddress"));

        IpEntity ipEntity = ipService.getIpInfo(ip);

        eventMap.put("ipEntity", ipEntity);

        if (ipEntity != null) {
            eventMap.put("ipCity", ipEntity.getCity());
            eventMap.put("ipProvince", ipEntity.getProvince());
        }
    }

    private void repairEventMap(Map<String, Object> eventParamMap) {
        if (MapUtils.isNotEmpty(eventParamMap)) {
            Set<Map.Entry<String, Object>> parameterEntrySet = eventParamMap.entrySet();

            for (Map.Entry<String, Object> entry : parameterEntrySet) {
                Object object = entry.getValue();

                String value = "";
                if (object instanceof Object[]) {
                    Object[] o = (Object[]) object;
                    value = String.valueOf(o[0]);
                } else {
                    value = String.valueOf(entry.getValue());
                }

                if (StringUtils.isBlank(value) || value.equals("null")) {
                    eventParamMap.put(entry.getKey(), "");
                } else {
                    eventParamMap.put(entry.getKey(), value);
                }
            }
        }
    }

    protected abstract void fillEventMap(Map<String, Object> eventMap);

    private String processDeviceId(String sequenceId, DeviceType deviceType,
                                   Map<String, Object> eventMap) {

        String deviceId = "";

        String appVersion = String.valueOf(eventMap.get("appVersion"));

        String encryptDeviceInfo = String.valueOf(eventMap.get("deviceInfo"));

        String accountLogin = String.valueOf(eventMap.get("account_login"));

        DeviceInfo deviceInfo = userDeviceInfoService.processDeviceInfo(sequenceId, appVersion,
                deviceType, encryptDeviceInfo);

        if (deviceInfo != null) {

            deviceId = deviceInfo.getDeviceId();
            if (deviceId.equals("na")) {
                deviceId = "";
            }

            if (StringUtils.isNotBlank(accountLogin) && StringUtils.isNotBlank(deviceId)) {
                try {
                    if (!accountLogin.equals("null")) {
                        userDeviceInfoService.saveDeviceInfo(deviceInfo, accountLogin);
                    }

                } catch (Exception e) {
                    logger.error("保存设备账户关联关系异常", e);
                }
            }
        }

        return deviceId;
    }

    protected void cacheRelationAccount(Map<String, Object> eventMap) {

        String accountLogin = String.valueOf(eventMap.get("account_login"));
        String encryptDeviceInfo = String.valueOf(eventMap.get("deviceInfo"));
        String ipAddress = String.valueOf(eventMap.get("ipAddress"));
        String beaconId = String.valueOf(eventMap.get("beaconId"));

        relationAccountService.cacheRelationAccount(accountLogin, encryptDeviceInfo, ipAddress,
                beaconId);
    }

    protected void saveEvent(final String sequenceId, final String eventType,
                             final Map<String, Object> eventMap,
                             final EventProcessResult eventProcessResult,
                             final DeviceType deviceType) {

        try {
            Event event = new Event();

            event.setSequenceId(sequenceId);
            event.setAccountLogin(String.valueOf(eventMap.get("account_login")));
            event.setAccountMobile(String.valueOf(eventMap.get("account_mobile")));
            event.setState(String.valueOf(eventMap.get("state")));
            event.setDeviceId(String.valueOf(eventMap.get("ext_did")));
            event.setIp(String.valueOf(eventMap.get("ipAddress")));
            event.setBeaconId(String.valueOf(eventMap.get("beaconId")));
            event.setEventType(eventType);
            event.setEventDetail(JSON.toJSONString(eventMap));

            if (eventProcessResult != null) {
                event.setScore(eventProcessResult.getFinalScore());
                event.setDecision(eventProcessResult.getFinalDecision());
                event.setDecisionDetail(JSON.toJSONString(eventProcessResult,
                        SerializerFeature.DisableCircularReferenceDetect));
                event.setDecisionParty(Event.DECISION_PARTY_HLJ);
            }
            event.setUserAgent(String.valueOf(eventMap.get("userAgent")));
            event.setDeviceType(deviceType.getCode());
            event.setCreateTime(new Timestamp(new Date().getTime()));
            event.setUpdateTime(event.getCreateTime());
            event.setEventTime(String.valueOf(eventMap.get("eventTime")));
            event.setEventDataStatus(0);

            Object phoneInfo = eventMap.get("phoneEntity");
            if (phoneInfo != null && phoneInfo instanceof PhoneEntity) {
                event.setPhoneEntity((PhoneEntity) phoneInfo);
            }
            Object ipInfo = eventMap.get("ipEntity");

            if (ipInfo != null && ipInfo instanceof IpEntity) {
                event.setIpEntity((IpEntity) ipInfo);
            }
            event.setRemark("");
            eventService.processRiskEventMessage(event);

        } catch (Exception e) {
            logger.error("send event message exception", e);
        }
    }

    private PolicyEntity getPolicy(String eventType, Integer deviceType) {

        return policyService.queryPolicyByEventTypeAndDeviceType(eventType, deviceType);
    }

    private DeviceType parseDeviceType(Map<String, Object> eventParamMap) {

        String eventType = String.valueOf(eventParamMap.get("eventType"));

        if (eventType.equals("500") || eventType.equals("800") || eventType.equals("1010")
                || eventType.equals("1020")) {
            return DeviceType.WEB;
        }

        String deviceType = String.valueOf(eventParamMap.get("deviceType"));

        if (deviceType.equalsIgnoreCase("android")) {
            return DeviceType.ANDROID;
        } else if (deviceType.equalsIgnoreCase("ios")) {
            return DeviceType.IOS;
        } else {
            //无法解析就默认为web
            return DeviceType.WEB;
        }
    }
}
