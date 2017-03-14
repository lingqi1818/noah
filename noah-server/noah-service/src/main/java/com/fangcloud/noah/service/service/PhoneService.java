package com.fangcloud.noah.service.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fangcloud.noah.dao.entity.PhoneEntity;
import com.fangcloud.noah.dao.mapper.PhoneMapper;

/**
 * Created by chenke on 16-10-28.
 */
@Service
public class PhoneService {

    @Autowired
    private PhoneMapper phoneMapper;

    public PhoneEntity getPhoneInfo(String phone){

        if(StringUtils.isBlank(phone)){
            return null;
        }

        String phone7 = phone;

        if(phone.length() >= 7){
            phone7 = phone.substring(0,7);
        }

        return phoneMapper.selectByPhone7(phone7);
    }

}
