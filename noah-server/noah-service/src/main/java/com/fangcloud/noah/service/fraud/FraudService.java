package com.fangcloud.noah.service.fraud;
//package com.helijia.risk.fraud;
//
//import cn.fraudmetrix.riskservice.object.HitRule;
//import com.alibaba.fastjson.JSON;
//import com.fangcloud.noah.dao.entity.PolicyEntity;
//import com.fangcloud.noah.dao.enums.DeviceType;
//import com.fangcloud.noah.dao.enums.EventType;
//import com.fangcloud.noah.dao.enums.NameListStatusType;
//import com.helijia.framework.redis.RedisService;
//import com.helijia.framework.risk.RiskServiceClient;
//import com.helijia.framework.risk.api.enums.NameListGrade;
//import com.helijia.framework.risk.api.enums.NameListType;
//import com.helijia.framework.risk.api.model.NameList;
//import com.helijia.framework.risk.exception.RiskRuntimeException;
//import com.helijia.framework.risk.fraud.FraudApiResponse;
//import com.helijia.risk.common.CacheKeyEnum;
//import com.helijia.risk.service.NameListService;
//import com.helijia.risk.util.PropertyUtil;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.collections.MapUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.sql.Timestamp;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by chenke on 16-8-26.
// */
//@Service
//public class
//FraudService {
//
//    private static final Logger logger = LoggerFactory.getLogger(FraudService.class);
//
//    //合作方标识
//    private static final String PARTNER_CODE = "helijia";
//
//    //app密钥
//    private static final String SECRET_KEY_WEB = PropertyUtil.getProperty("fraud.secretkey.web", "fd9f6fda81404b9fa88aaeb7714afe2f");
//
//    //app密钥
//    private static final String SECRET_KEY_ANDROID = PropertyUtil.getProperty("fraud.secretkey.android", "fd9f6fda81404b9fa88aaeb7714afe2f");
//
//    //app密钥
//    private static final String SECRET_KEY_IOS = PropertyUtil.getProperty("fraud.secretkey.ios", "fd9f6fda81404b9fa88aaeb7714afe2f");
//
//    @Autowired
//    private RiskServiceClient riskServiceClient;
//
//    @Autowired
//    private RedisService redisService;
//
//    @Autowired
//    private NameListService nameListService;
//
//
//    public FraudApiResponse invokeFraud(Map<String, Object> eventDataMap) {
//        try {
//
//            if (eventDataMap == null) {
//                return null;
//            }
//
//            Map<String, Object> fraudMap = transfromerDataMap(eventDataMap);
//
//            logger.info("send to fraud data:" + JSON.toJSONString(fraudMap));
//
//            FraudApiResponse fraudApiResponse = riskServiceClient.sendToFraud(fraudMap);
//
//            if (fraudApiResponse == null) {
//                return null;
//            }
//
//            List<HitRule> hitRules = fraudApiResponse.getHit_rules();
//
//            if (CollectionUtils.isNotEmpty(hitRules)) {
//                for (HitRule hitRule : hitRules) {
//                    String ruleId = hitRule.getId();
//
//                    if (redisService.sismember(CacheKeyEnum.FRAUD_RULE.getKey(), ruleId)) {
//
//                        saveNameList(eventDataMap, hitRule.getId() + "#" + hitRule.getName());
//                    }
//
//                    //TODO 先这么搞
//                    if(ruleId.equals("2435883") || ruleId.equals("2455011") || ruleId.equals("2455013")){
//                        //保存黑名单beaconid到redis 失效时间1小时
//                        redisService.setex(CacheKeyEnum.RISK_BLACK_LIST_BEACONID.getKey(String.valueOf(eventDataMap.get("beaconId"))),CacheKeyEnum.RISK_BLACK_LIST_BEACONID.getExpireSeconds(),"1");
//                    }
//
//                }
//            }
//
//            //判断ip地址和设备id是否在黑名单列表中
//
//            String ipAddress = String.valueOf(eventDataMap.get("ipAddress"));
//
//            if (nameListService.isBlackNameList(NameListType.IP.getCode(), ipAddress)) {
//
//                saveNameList(eventDataMap, "命中黑名单ip：" + ipAddress);
//            }
//
//
//            String deviceId = String.valueOf(eventDataMap.get("deviceId"));
//
//            if (nameListService.isBlackNameList(NameListType.DEVICE.getCode(), deviceId)) {
//                saveNameList(eventDataMap, "命中黑名单设备id：" + deviceId);
//            }
//
//
//
//            return fraudApiResponse;
//        } catch (Exception e) {
//            logger.error("send to fraud exception", e);
//            return null;
//        }
//    }
//
//    private Map<String, Object> transfromerDataMap(Map<String, Object> eventDataMap) {
//
//
//        Map<String, Object> parameterMap = new HashMap<String, Object>();
//
//        String policyJsonStr = String.valueOf(eventDataMap.get("policy"));
//
//        PolicyEntity policyEntity = JSON.parseObject(policyJsonStr, PolicyEntity.class);
//
//        parameterMap.put("partner_code", PARTNER_CODE);
//
//        parameterMap.put("secret_key", getSecretKey(policyEntity.getDeviceType()));
//        parameterMap.put("ip_address", String.valueOf(eventDataMap.get("ipAddress")));
//        parameterMap.put("event_id", policyEntity.getEventId());
//
//
//        if (policyEntity.getDeviceType() == DeviceType.WEB.getCode()) {
//
//            String tokenId = String.valueOf(eventDataMap.get("tokenId"));
//
//            if (StringUtils.isNotBlank(tokenId)) {
//                parameterMap.put("token_id", tokenId);
//            } else {
//                String beaconId = String.valueOf(eventDataMap.get("beaconId"));
//                parameterMap.put("token_id", beaconId);
//            }
//
//            String referer = String.valueOf(eventDataMap.get("referer"));
//
//            parameterMap.put("refer_cust", referer);
//        }
//
//        if (policyEntity.getDeviceType() == DeviceType.ANDROID.getCode() || policyEntity.getDeviceType() == DeviceType.IOS.getCode()) {
//
//            String blackBox = String.valueOf(eventDataMap.get("blackBox"));
//
//            parameterMap.put("black_box", blackBox);
//        }
//
//
//        if (MapUtils.isNotEmpty(eventDataMap)) {
//            parameterMap.putAll(eventDataMap);
//        }
//
//        parameterMap.remove("policy");
//        parameterMap.remove("applicationName");
//        parameterMap.remove("uri");
//        parameterMap.remove("ipAddress");
//        parameterMap.remove("userAgent");
//        parameterMap.remove("referer");
//        parameterMap.remove("deviceType");
//        parameterMap.remove("blackBox");
//        parameterMap.remove("tokenId");
//        parameterMap.remove("beaconId");
//        parameterMap.remove("eventType");
//        parameterMap.remove("deviceInfo");
//        parameterMap.remove("appVersion");
//        parameterMap.remove("sequenceId");
//        parameterMap.remove("eventId");
//        parameterMap.remove("deviceId");
//
//        return parameterMap;
//    }
//
//
//    protected String getSecretKey(Integer deviceType) {
//        if (deviceType.equals(DeviceType.WEB.getCode())) {
//            return SECRET_KEY_WEB;
//        } else if (deviceType.equals(DeviceType.ANDROID.getCode())) {
//            return SECRET_KEY_ANDROID;
//        } else if (deviceType.equals(DeviceType.IOS.getCode())) {
//            return SECRET_KEY_IOS;
//        } else {
//            throw new RiskRuntimeException("错误的deviceType，getSecretKey exception");
//        }
//    }
//
//
//    private void saveNameList(Map<String, Object> eventDataMap, String remark) {
//        NameList nameList = new NameList();
//        String mobile;
//
//        String eventType = String.valueOf(eventDataMap.get("eventType"));
//        if (eventType.equals(EventType.marketing.getCode()) || eventType.equals(EventType.message.getCode())) {
//            mobile = String.valueOf(eventDataMap.get("account_mobile"));
//        } else {
//            mobile = String.valueOf(eventDataMap.get("account_login"));
//        }
//
//        if (StringUtils.isBlank(mobile) || "null".equals(mobile)) {
//            return;
//        }
//        nameList.setContent(mobile);
//        nameList.setType(NameListType.MOBILE.getCode());
//        nameList.setStatus(NameListStatusType.INIT.getCode());
//        nameList.setGrade(NameListGrade.BLACK.getCode());
//        nameList.setRemark(remark);
//        nameList.setGmtCreated(new Timestamp(new Date().getTime()));
//        nameList.setGmtModified(nameList.getGmtCreated());
//        nameListService.saveNameList(nameList);
//        return;
//    }
//}
