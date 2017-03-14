package com.fangcloud.noah.api.api.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import com.fangcloud.noah.api.api.enums.NameListType;
import com.fangcloud.noah.api.api.model.DeviceAccountMobileRelation;
import com.fangcloud.noah.api.api.model.NameList;
import com.fangcloud.noah.api.api.service.RiskQueryService;
import com.fangcloud.noah.redis.RedisService;

/**
 * 默认风控查询服务
 *
 * @author chenke
 * @date 2016年4月25日 下午6:14:28
 */
public class DefaultRiskQueryService implements RiskQueryService {

    private static final Logger logger = Logger.getLogger(DefaultRiskQueryService.class);

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
    private final static String SWITCH_VERIFY_CODE = "switch_verfiy_code";
    private RedisService redisService;

    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public NameList queryNameList(NameListType type, String content) {
        return null;
    }

    @Override
    public boolean isBlackList(NameListType type, String content) {
        return false;
    }

    @Override
    public boolean showVerifyCode(String deviceId, String ip, String beaconId, int riskLevel) {
        if (riskLevel == 9) {
            return true;
        }


        return validateDevice(deviceId, ip, beaconId);
    }


    private boolean validateDevice(String deviceId, String ip, String beaconId) {

        String switchStatus = redisService.get(SWITCH_VERIFY_CODE);

        if (StringUtils.isNotBlank(switchStatus) && switchStatus.equals("off")) {
            return false;
        }

        String today = sdf.format(new Date());
        Long inDeviceMobiles = redisService
                .scard("risk_device_id_account_" + today + "_" + deviceId);
        Long inBeaconMobiles = redisService
                .scard("risk_beacon_id_account_" + today + "_" + beaconId);
        Long inIpMobiles = redisService.scard("risk_ip_account_" + today + "_" + ip);
        //设备关联手机号码大于2个就需要弹出验证码
        if (inDeviceMobiles != null && inDeviceMobiles > 2) {
            return true;
        }
        //beaconId关联手机号码大于2个就需要弹出验证码
        if (inBeaconMobiles != null && inBeaconMobiles > 2) {
            return true;
        }
        //ip关联手机号码大于等于10个就需要弹出验证码
        if (inIpMobiles != null && inIpMobiles >= 10) {
            return true;
        }
        return false;
    }


    @Override
    public boolean showLoginVerifyCode(String mobile, int riskLevel) {

        if (riskLevel == 9) {
            return true;
        }

        String switchStatus = redisService.get(SWITCH_VERIFY_CODE);

        if (StringUtils.isNotBlank(switchStatus) && switchStatus.equals("off")) {
            return false;
        }

        String count = redisService.get("risk_login_failed_count_" + mobile);

        if (StringUtils.isNotBlank(count) && NumberUtils.isNumber(count)) {

            try {
                int faildCount = Integer.parseInt(count);

                if (faildCount > 2) {
                    return true;
                }

            } catch (Exception e) {
                logger.error("risk_login_failed_count parse exception", e);
            }
        }
        return false;
    }

    @Override
    public DeviceAccountMobileRelation queryDeviceAccountMobileRelation(String accountMobile) {
        return null;
    }

}
