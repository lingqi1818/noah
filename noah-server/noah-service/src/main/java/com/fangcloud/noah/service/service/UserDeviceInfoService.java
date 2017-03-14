package com.fangcloud.noah.service.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.fangcloud.noah.api.exception.RiskRuntimeException;
import com.fangcloud.noah.api.model.DeviceInfo;
import com.fangcloud.noah.dao.entity.UserDeviceInfoEntity;
import com.fangcloud.noah.dao.enums.DeviceType;
import com.fangcloud.noah.dao.mapper.UserDeviceInfoMapper;
import com.fangcloud.noah.dao.model.UserDeviceInfoModel;
import com.fangcloud.noah.redis.RedisService;
import com.fangcloud.noah.service.common.CacheKeyEnum;
import com.fangcloud.noah.service.common.PageObject;
import com.fangcloud.noah.service.util.AppVersionUtil;
import com.fangcloud.noah.service.util.RSAUtil;

/**
 * Created by chenke on 16-4-15.
 */
@Service
public class UserDeviceInfoService {

    private static final Logger  logger = LoggerFactory.getLogger(UserDeviceInfoService.class);

    @Autowired
    private UserDeviceInfoMapper userDeviceInfoMapper;

    @Autowired
    private SecretKeyService     secretKeyService;

    @Autowired
    private RedisService         redisService;

    public void save(UserDeviceInfoModel model) {
        UserDeviceInfoEntity userDeviceInfo = new UserDeviceInfoEntity();
        userDeviceInfo.setUserAccount(model.getUserAccount());
        userDeviceInfo.setUserId(model.getUserId());
        userDeviceInfo.setUserType(model.getUserType());
        userDeviceInfo.setDeviceType(model.getDeviceType());
        userDeviceInfo.setSignVer(model.getSignVer());
        userDeviceInfo.setDeviceId(model.getDeviceId());
        userDeviceInfo.setCpuId(model.getCpuId());
        userDeviceInfo.setCpuMode(model.getCpuMode());
        userDeviceInfo.setValid(model.getValid());
        userDeviceInfo.setCreateTime(new Timestamp(new Date().getTime()));
        userDeviceInfo.setUpdateTime(userDeviceInfo.getUpdateTime());
        userDeviceInfoMapper.insert(userDeviceInfo);
    }

    public UserDeviceInfoEntity getUserDeviceInfo(String userAccount, Integer userType,
                                                  String deviceId) {
        return userDeviceInfoMapper.selectUserDeviceInfoByUserAndDeviceId(userAccount, userType,
                deviceId);
    }

    public DeviceInfo processDeviceInfo(String sequenceId, String appVersion, DeviceType deviceType,
                                        String encryptDeviceInfo) {

        DeviceInfo deviceInfo = null;

        if (StringUtils.isNotBlank(appVersion)
                && AppVersionUtil.compare(appVersion, "3.0.1") >= 0) {

            if (StringUtils.isBlank(encryptDeviceInfo)) {
                logger.info("设备加密信息为空，event sequenceId=" + sequenceId);
                return null;
            }

            long startTime = System.currentTimeMillis();
            long endTime = 0L;
            try {

                //兼容，客户端上传的加密串特殊字符会变成空格导致解密失败
                encryptDeviceInfo = encryptDeviceInfo.replaceAll("\\ ", "+");

                String deviceInfoStr = redisService
                        .get(CacheKeyEnum.DEVICE_INFO.getKey(encryptDeviceInfo));

                if (StringUtils.isBlank(deviceInfoStr)) {
                    deviceInfo = parseDeviceInfo(appVersion, deviceType.getName(),
                            encryptDeviceInfo);
                    if (deviceInfo != null && !deviceInfo.getDeviceId().equals("na")) {
                        redisService.setex(CacheKeyEnum.DEVICE_INFO.getKey(encryptDeviceInfo),
                                CacheKeyEnum.DEVICE_INFO.getExpireSeconds(),
                                JSON.toJSONString(deviceInfo));
                    }
                } else {
                    deviceInfo = JSON.parseObject(deviceInfoStr, DeviceInfo.class);
                }

                endTime = System.currentTimeMillis();
            } catch (Exception e) {
                logger.error("解析设备信息异常,riskEvent seqId:" + sequenceId, e);
                endTime = System.currentTimeMillis();
            }

            logger.info("DeviceInfoProcess process excute time:" + (endTime - startTime));
        }

        return deviceInfo;

    }

    public DeviceInfo parseDeviceInfo(String appVersion, String deviceType, String deviceInfoStr)
            throws Exception {

        if (StringUtils.isBlank(deviceInfoStr)) {
            throw new RiskRuntimeException("设备信息参数deviceInfo为空");
        }

        if (StringUtils.isBlank(deviceType)) {
            throw new RiskRuntimeException("设备信息参数deviceType为空");
        }

        if (!deviceType.equalsIgnoreCase(DeviceType.ANDROID.getName())
                && !deviceType.equalsIgnoreCase(DeviceType.IOS.getName())) {
            throw new RiskRuntimeException("设备信息参数deviceType无效");
        }

        String privateKey = secretKeyService.getPrivateKey(deviceType, appVersion);

        if (StringUtils.isBlank(privateKey)) {
            throw new RiskRuntimeException("没找到解密密钥");
        }

        long startTime = System.nanoTime();

        String deviceText = new String(RSAUtil.decryptWithBase64Encoded(privateKey, deviceInfoStr));

        long endTime = System.nanoTime();
        logger.info("RSAUtil decrypt excute time = " + (endTime - startTime));

        if (StringUtils.isNotBlank(deviceText)) {
            String[] deviceParams = deviceText.split("\\|");
            String osType = deviceParams[0];
            String signVer = deviceParams[1];
            String deviceId = deviceParams[2];
            String cpuInfo = deviceParams[3];
            String[] cpuParams = cpuInfo.split(",");
            String cpuId = cpuParams[0];
            String cpuMode = cpuParams[1];
            String date = deviceParams[4];
            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setDeviceId(deviceId);
            deviceInfo.setSignVer(signVer);
            deviceInfo.setCpuId(cpuId);
            deviceInfo.setCpuMode(cpuMode);
            deviceInfo.setOsType(osType);
            deviceInfo.setDate(date);
            return deviceInfo;
        } else {
            throw new RiskRuntimeException("解密后设备信息文本为空,deviceInfoStr=" + deviceInfoStr);
        }

    }

    /**
     * 保存设备信息
     *
     * @param deviceInfo
     * @param accountLogin
     */
    public void saveDeviceInfo(DeviceInfo deviceInfo, String accountLogin) {

        if (StringUtils.isBlank(deviceInfo.getDeviceId())) {
            throw new RiskRuntimeException("saveDeviceInfo deviceId is null");
        }

        if (StringUtils.isBlank(accountLogin) || accountLogin.equals("null")) {
            throw new RiskRuntimeException("accountLogin is null");
        }

        UserDeviceInfoEntity userDeviceInfo = getUserDeviceInfo(accountLogin,
                UserDeviceInfoEntity.USER_TYPE_COMMON, deviceInfo.getDeviceId());

        if (userDeviceInfo != null) {
            if (!StringUtils.equals(userDeviceInfo.getCpuId(), deviceInfo.getCpuId())) {
                String infoMsg = String.format("已存在此用户设备映射信息，cpuId不一致,deviceId=%s,accountLogin=%s",
                        deviceInfo.getDeviceId(), accountLogin);
                logger.info(infoMsg);
            }

            if (!StringUtils.equals(userDeviceInfo.getCpuMode(), deviceInfo.getCpuMode())) {
                String infoMsg = String.format(
                        "已存在此用户设备映射信息，cpuMode不一致,deviceId=%s,accountLogin=%s",
                        deviceInfo.getDeviceId(), accountLogin);
                logger.info(infoMsg);
            }
        } else {

            UserDeviceInfoModel userDeviceInfoModel = new UserDeviceInfoModel();
            userDeviceInfoModel.setUserId("");
            if (deviceInfo.getOsType().equals("A")) {
                userDeviceInfoModel.setDeviceType(DeviceType.ANDROID.getCode());
            } else if (deviceInfo.getOsType().equals("I")) {
                userDeviceInfoModel.setDeviceType(DeviceType.IOS.getCode());
            } else {
                throw new RiskRuntimeException("设备信息osType参数无效");
            }
            userDeviceInfoModel.setUserAccount(accountLogin);
            userDeviceInfoModel.setDeviceId(deviceInfo.getDeviceId());
            userDeviceInfoModel.setSignVer(deviceInfo.getSignVer());
            userDeviceInfoModel.setUserType(UserDeviceInfoEntity.USER_TYPE_COMMON);
            userDeviceInfoModel.setCpuId(deviceInfo.getCpuId());
            userDeviceInfoModel.setCpuMode(deviceInfo.getCpuMode());
            userDeviceInfoModel.setValid(UserDeviceInfoEntity.DATA_VALID);
            userDeviceInfoModel.setCreateTime(new Timestamp(new Date().getTime()));
            userDeviceInfoModel.setUpdateTime(userDeviceInfoModel.getCreateTime());
            save(userDeviceInfoModel);
        }
    }

    public PageObject<UserDeviceInfoEntity> queryForPage(Integer pageNum, String userAccount,
                                                         String deviceId) {

        int total = userDeviceInfoMapper.selectUserDeviceInfoCount(userAccount, deviceId);

        if (pageNum == null) {
            pageNum = 1;
        }
        PageObject<UserDeviceInfoEntity> datas = new PageObject<UserDeviceInfoEntity>(pageNum,
                total);
        List<UserDeviceInfoEntity> userDeviceInfos = userDeviceInfoMapper.selectUserDeviceInfoList(
                datas.getOffSet(), datas.getPageSize(), userAccount, deviceId);
        datas.initViewData(userDeviceInfos);
        return datas;

    }

    public UserDeviceInfoEntity getDeviceIdByAccountMobile(String accountMobile) {
        return userDeviceInfoMapper.selectUserDeviceInfoByUserAccount(accountMobile);
    }

    public List<String> getUserAccountByDeviceId(String deviceId) {

        List<String> userAccountList = new ArrayList<String>();

        List<UserDeviceInfoEntity> entityList = userDeviceInfoMapper
                .selectUserDeviceInfoByDeviceId(deviceId);

        if (CollectionUtils.isNotEmpty(entityList)) {
            for (UserDeviceInfoEntity u : entityList) {
                userAccountList.add(u.getUserAccount());
            }
        }

        return userAccountList;
    }

    public List<UserDeviceInfoEntity> getUserDeviceInfoListByUserAccount(String accountMobile) {
        return userDeviceInfoMapper.selectUserDeviceInfoListByUserAccount(accountMobile);
    }

}
