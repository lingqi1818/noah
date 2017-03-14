package com.fangcloud.noah.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.fangcloud.noah.dao.entity.IpEntity;
import com.fangcloud.noah.dao.entity.PhoneEntity;

/**
 * Created by chenke on 16-3-17.
 */
@Repository
public interface IpMapper {

    /**
     * 根据ip地址查询ip信息
     * @param ip
     * @return
     */
    IpEntity selectByIp(@Param("ip") String ip);

}
