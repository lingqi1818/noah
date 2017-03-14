package com.fangcloud.noah.api.api.service;

import com.fangcloud.noah.api.api.enums.NameListType;
import com.fangcloud.noah.api.api.model.DeviceAccountMobileRelation;
import com.fangcloud.noah.api.api.model.NameList;

/**
 * 风控查询服务
 * 
 * @author: YJL Date: 16-1-30
 */
public interface RiskQueryService {

    /**
     * 黑白灰名单查询
     * 
     * @param type 名单类型 1 手机号、2 ip、3 设备id
     * @param content 名单内容 具体的手机号 ip 或设备信息
     * @return
     */
    NameList queryNameList(NameListType type, String content);

    /**
     * 判断是否是黑名单
     * 
     * @param type 名单类型 1 手机号、2 ip、3 设备id
     * @param content 名单内容 具体的手机号 ip 或设备信息
     * @return
     */
    boolean isBlackList(NameListType type, String content);

    /**
     * 是否显示验证码
     * 
     * @return
     */
    boolean showVerifyCode(String deviceId, String ip, String beaconId, int riskLevel);


    /**
     * 是否显示验证码(登陆、注册)
     * @param mobile
     * @return
     */
    boolean showLoginVerifyCode(String mobile,int riskLevel);


    /**
     * 根据账号手机查询设备账号关联信息
     * @param accountMobile
     * @return
     */
    DeviceAccountMobileRelation queryDeviceAccountMobileRelation(String accountMobile);
}
