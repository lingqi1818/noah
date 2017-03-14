package com.fangcloud.noah.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.fangcloud.noah.dao.entity.CollectParamConfigEntity;

import java.util.List;

/**
 * Created by chenke on 16-3-17.
 */
@Repository
public interface CollectParamConfigMapper {

    int insert(CollectParamConfigEntity record);

    CollectParamConfigEntity selectById(@Param("id") Integer id);

    int selectCollectParamConfigCount(@Param("applicationName") String applicationName);

    List<CollectParamConfigEntity> selectCollectParamConfigList(@Param("rowBegin") int rowBegin, @Param("rowNum") int rowNum, @Param("applicationName") String applicationName);


    void deleteById(@Param("id")Integer id);

    List<CollectParamConfigEntity> selectCollectParamConfigListWithDetail();

}
