package com.fangcloud.noah.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fangcloud.noah.dao.entity.UserDeviceInfoEntity;
import com.fangcloud.noah.service.apiservice.ApiService;
import com.fangcloud.noah.service.common.PageObject;
import com.fangcloud.noah.service.service.UserDeviceInfoService;

/**
 * Created by chenke on 16-6-27.
 */
@Controller
@RequestMapping("userDevice")
public class UserDeviceController {

    @Autowired
    private UserDeviceInfoService userDeviceInfoService;

    @Autowired
    private ApiService            apiService;

    @RequestMapping("query")
    public String query(Integer pageNum, String userAccount, String deviceId,
                        HttpServletRequest req) {

        PageObject<UserDeviceInfoEntity> pageObj = userDeviceInfoService.queryForPage(pageNum,
                userAccount, deviceId);

        req.setAttribute("userAccount", userAccount);
        req.setAttribute("deviceId", deviceId);
        req.setAttribute("pageObj", pageObj);

        return "device/list";
    }

    @RequestMapping("couponUseRecord")
    public String couponUseRecord(String userAccount, HttpServletRequest req) {

        if (StringUtils.isBlank(userAccount)) {
            return "device/couponUseRecord";
        }
        List<UserDeviceInfoEntity> userDeviceInfoEntityList = userDeviceInfoService
                .getUserDeviceInfoListByUserAccount(userAccount);

        List<String> deviceIdList = new ArrayList<String>();

        if (CollectionUtils.isNotEmpty(userDeviceInfoEntityList)) {
            for (UserDeviceInfoEntity entity : userDeviceInfoEntityList) {
                deviceIdList.add(entity.getDeviceId());
            }
        }

        List<Map<String, Object>> result = apiService.getDeviceCouponUseRecord(deviceIdList);

        req.setAttribute("userAccount", userAccount);
        req.setAttribute("result", result);
        req.setAttribute("flag", "q");

        return "device/couponUseRecord";
    }
}
