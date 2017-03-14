package com.fangcloud.noah.service.service;

import com.fangcloud.noah.dao.entity.IpEntity;
import com.fangcloud.noah.dao.mapper.IpMapper;
import com.fangcloud.noah.service.processor.EventProcessor;
import com.fangcloud.noah.service.util.ip.IpSearch;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chenke on 16-10-28.
 */
@Service
public class IpService {

    private final Logger logger = LoggerFactory.getLogger(IpService.class);


    @Autowired
    private IpMapper ipMapper;



    public IpEntity getIpInfo(String ip){

        if(StringUtils.isBlank(ip)){
            return null;
        }

        try {

            IpSearch finder = IpSearch.getInstance();
            String result = finder.Get(ip);

            //亚洲|中国|广东|深圳|宝安|电信|440306|China|CN|113.88311|22.55371
            if(StringUtils.isNotBlank(result)){

                String[] ipInfo = result.split("\\|");

                IpEntity ipEntity = new IpEntity();
                ipEntity.setContinent(ipInfo[0]);
                ipEntity.setCountry(ipInfo[1]);
                ipEntity.setProvince(ipInfo[2]);
                ipEntity.setCity(ipInfo[3]);
                ipEntity.setDistrict(ipInfo[4]);
                ipEntity.setIsp(ipInfo[5]);
                ipEntity.setAreaCode(ipInfo[6]);
                ipEntity.setCountryEnglish(ipInfo[7]);
                ipEntity.setCountryCode(ipInfo[8]);
                ipEntity.setLongitude(ipInfo[9]);
                if(ipInfo.length>10){
                    //兼容局域网 ip 和127.0.0.1
                    ipEntity.setLatitude(ipInfo[10]);
                }


                return ipEntity;
            }else {
                logger.info("根据ip获取归宿地为空,ip="+ip);
            }
        }catch (Exception e){
            logger.error("获取ip信息异常,ip={}",ip,e);
        }
        return null;
    }
}
