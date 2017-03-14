package com.fangcloud.noah.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.fangcloud.noah.dao.entity.UserDeviceInfoEntity;

import java.util.List;

/**
 * Created by chenke on 16-8-20.
 */
@Repository
public interface UserDeviceInfoMapper {

    /**
     * 新增密钥
     *
     * @param record
     */
    void insert(UserDeviceInfoEntity record);

    List<UserDeviceInfoEntity> selectUserDeviceInfos(UserDeviceInfoEntity userDeviceInfoQuery);

    UserDeviceInfoEntity selectUserDeviceInfoByUserAndDeviceId(@Param("userAccount") String userAccount, @Param("userType") Integer userType, @Param("deviceId") String deviceId);

    int selectUserDeviceInfoCount(@Param("userAccount") String userAccount, @Param("deviceId") String deviceId);

    List<UserDeviceInfoEntity> selectUserDeviceInfoList(@Param("rowBegin") int rowBegin, @Param("rowNum") int rowNum, @Param("userAccount") String userAccount, @Param("deviceId") String deviceId);

    UserDeviceInfoEntity selectUserDeviceInfoByUserAccount(@Param("userAccount") String userAccount);

    List<UserDeviceInfoEntity> selectUserDeviceInfoByDeviceId(@Param("deviceId") String deviceId);

    List<UserDeviceInfoEntity> selectUserDeviceInfoListByUserAccount(@Param("userAccount") String userAccount);

}

