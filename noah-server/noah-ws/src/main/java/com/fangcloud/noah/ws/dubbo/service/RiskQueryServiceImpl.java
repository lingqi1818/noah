package com.fangcloud.noah.ws.dubbo.service;

import com.fangcloud.noah.api.api.enums.NameListGrade;
import com.fangcloud.noah.api.api.enums.NameListType;
import com.fangcloud.noah.api.api.model.DeviceAccountMobileRelation;
import com.fangcloud.noah.api.api.model.NameList;
import com.fangcloud.noah.api.api.service.RiskQueryService;
import com.fangcloud.noah.api.exception.RiskRuntimeException;
import com.fangcloud.noah.dao.entity.UserDeviceInfoEntity;
import com.fangcloud.noah.dao.mapper.NameListMapper;
import com.fangcloud.noah.service.service.UserDeviceInfoService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by chenke on  Date: 16-2-3
 */
@Component("riskQueryService")
public class RiskQueryServiceImpl implements RiskQueryService {

    private static final Logger logger = LoggerFactory.getLogger(RiskQueryServiceImpl.class);

    @Autowired
    private NameListMapper nameListMapper;

    @Autowired
    private UserDeviceInfoService userDeviceInfoService;



    @Override
    public NameList queryNameList(NameListType type, String content) {
        try {

            if (type == null || StringUtils.isBlank(content)) {
                String errorMsg = String.format("参数为空,type = %s,content = %s", type, content);
                logger.error(errorMsg);
                throw new RiskRuntimeException(errorMsg);
            }

            NameList nameList = nameListMapper.selectNameListByContent(type.getCode(), content);

            return nameList;

        } catch (Exception e) {
            logger.error("risk query NameList exception:", e);
            throw new RiskRuntimeException("risk query NameList exception:" + e.getMessage());
        }
    }

    @Override
    public boolean isBlackList(NameListType type, String content) {

        NameList result = this.queryNameList(type, content);

        if (result != null && result.getGrade().equals(NameListGrade.BLACK.getCode())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean showVerifyCode(String deviceId, String ip, String beaconId, int riskLevel) {
        return true;
    }

    @Override
    public boolean showLoginVerifyCode(String s, int i) {
        return false;
    }

    @Override
    public DeviceAccountMobileRelation queryDeviceAccountMobileRelation(String accountMobile) {

        if(StringUtils.isBlank(accountMobile)){
            return null;
        }

        try {

            UserDeviceInfoEntity userDeviceInfoEntity = userDeviceInfoService.getDeviceIdByAccountMobile(accountMobile);

            if(userDeviceInfoEntity!=null){

                DeviceAccountMobileRelation deviceAccountMobileRelation = new DeviceAccountMobileRelation();
                deviceAccountMobileRelation.setLatestDeviceId(userDeviceInfoEntity.getDeviceId());

                List<String> userAccountList = userDeviceInfoService.getUserAccountByDeviceId(userDeviceInfoEntity.getDeviceId());

                deviceAccountMobileRelation.setRelationMobileList(userAccountList);

                return deviceAccountMobileRelation;
            }

            return null;

        }catch (Exception e){
            logger.error("risk  queryDeviceAccountMobileRelation exception",e);
            throw new RiskRuntimeException("risk  queryDeviceAccountMobileRelation exception:" + e.getMessage());
        }
    }

}
