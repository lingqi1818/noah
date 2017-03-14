package com.fangcloud.noah.service.service;

import com.fangcloud.noah.dao.entity.SecretKeyEntity;
import com.fangcloud.noah.dao.enums.DeviceType;
import com.fangcloud.noah.dao.mapper.SecretKeyMapper;
import com.fangcloud.noah.service.util.AppVersionUtil;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenke on 16-4-15.
 */
@Service
public class SecretKeyService implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(SecretKeyService.class);

    @Autowired
    private SecretKeyMapper secretKeyMapper;


    private Map<String, List<SecretKeyEntity>> secretKeyMap = new HashMap<String, List<SecretKeyEntity>>();


    public String getPrivateKey(String deviceType, String appVersion) {
        List<SecretKeyEntity> secretKeys = secretKeyMap.get(deviceType.toLowerCase());

        for (SecretKeyEntity secretKey : secretKeys) {
            if (AppVersionUtil.compare(appVersion, secretKey.getAppVersionBegin()) >= 0 &&
                    AppVersionUtil.compare(appVersion, secretKey.getAppVersionEnd()) < 0) {
                return secretKey.getPrivateKey();
            }
        }

        return "";
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        List<SecretKeyEntity> secretKeyList = secretKeyMapper.selectValidSecretKeys();

        List<SecretKeyEntity> androidSecretKeyList = new ArrayList<SecretKeyEntity>();

        List<SecretKeyEntity> iosSecretKeyList = new ArrayList<SecretKeyEntity>();

        if (CollectionUtils.isNotEmpty(secretKeyList)) {

            for (SecretKeyEntity secretKey : secretKeyList) {
                if (secretKey.getDeviceType().equals(DeviceType.ANDROID.getCode())) {
                    androidSecretKeyList.add(secretKey);
                } else if (secretKey.getDeviceType().equals(DeviceType.IOS.getCode())) {
                    iosSecretKeyList.add(secretKey);
                }
            }
        }

        secretKeyMap.put(DeviceType.ANDROID.getName(), androidSecretKeyList);
        secretKeyMap.put(DeviceType.IOS.getName(), iosSecretKeyList);

    }
}
