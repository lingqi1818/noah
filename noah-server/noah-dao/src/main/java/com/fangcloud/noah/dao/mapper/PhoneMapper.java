package com.fangcloud.noah.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.fangcloud.noah.dao.entity.PhoneEntity;
import com.fangcloud.noah.dao.entity.PolicyEntity;

import java.util.List;

/**
 * Created by chenke on 16-3-17.
 */
@Repository
public interface PhoneMapper {

    /**
     * 根据手机号前７位号段查询手机信息
     * @param phone
     * @return
     */
    PhoneEntity selectByPhone7(@Param("phone") String phone);

}
