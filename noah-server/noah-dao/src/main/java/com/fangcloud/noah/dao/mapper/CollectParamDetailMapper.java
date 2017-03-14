package com.fangcloud.noah.dao.mapper;

import org.springframework.stereotype.Repository;

import com.fangcloud.noah.dao.entity.CollectParamDetailEntity;

import java.util.List;

/**
 * Created by chenke on 16-3-17.
 */
@Repository
public interface CollectParamDetailMapper {

    int insert(CollectParamDetailEntity record);

    CollectParamDetailEntity selectById(Integer id);

    List<CollectParamDetailEntity> selectCollectParamDetailList(Integer collectParamConfigId);

    void update(CollectParamDetailEntity collectParamDetailEntity);

    void deleteById(Integer id);


}
