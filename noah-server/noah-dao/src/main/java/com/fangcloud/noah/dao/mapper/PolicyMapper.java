package com.fangcloud.noah.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.fangcloud.noah.dao.entity.PolicyEntity;

import java.util.List;

/**
 * Created by chenke on 16-3-17.
 */
@Repository
public interface PolicyMapper {

    int insert(PolicyEntity record);

    PolicyEntity selectById(@Param("id") Integer id);

    int selectPolicyCount(@Param("deviceType") String deviceType);

    List<PolicyEntity> selectPolicyList(@Param("rowBegin") int rowBegin, @Param("rowNum") int rowNum, @Param("deviceType") String deviceType);

    List<PolicyEntity> loadAllPolicy();

    PolicyEntity selectPolicyByEventTypeAndDeviceType(@Param("eventType") String eventType, @Param("deviceType") Integer deviceType);

    void deletePolicyById(@Param("id") int id);

    void updatePolicyById(PolicyEntity policyEntity);
}
