package com.fangcloud.noah.ws.dubbo.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fangcloud.noah.api.api.enums.NameListBusinessType;
import com.fangcloud.noah.api.api.enums.NameListGrade;
import com.fangcloud.noah.api.api.enums.NameListType;
import com.fangcloud.noah.api.api.model.NameList;
import com.fangcloud.noah.api.api.service.NameListService;
import com.fangcloud.noah.dao.mapper.NameListMapper;
import com.fangcloud.noah.redis.RedisService;
import com.fangcloud.noah.service.common.CacheKeyEnum;

/**
 * Created by chenke on 16-11-10.
 */
@Component("nameListServiceWs")
public class NameListServiceImpl implements NameListService {

    private static final Logger logger = LoggerFactory.getLogger(NameListServiceImpl.class);

    @Autowired
    private RedisService        redisService;

    @Autowired
    private NameListMapper      nameListMapper;

    @Override
    public boolean isBlackList(String mobile, NameListBusinessType type) {

        if (StringUtils.isBlank(mobile) || type == null) {
            return false;
        }

        try {

            if (type.getCode().equals(NameListBusinessType.SECOND_KILL.getCode())) {
                String result = redisService
                        .get(CacheKeyEnum.RISK_SECOND_KILL_BLACK_LIST.getKey(mobile));

                if (StringUtils.isNotBlank(result) && result.equals("1")) {
                    return true;
                }

                NameList nameList = nameListMapper
                        .selectNameListByContent(NameListType.MOBILE.getCode(), mobile);

                if (nameList != null && nameList.getGrade().equals(NameListGrade.BLACK.getCode())) {
                    return true;
                }
            }

        } catch (Exception e) {
            logger.error("NameListServiceImpl isBlackList exception", e);
        }
        return false;
    }
}
