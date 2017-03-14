package com.fangcloud.noah.service.service;

import com.fangcloud.noah.dao.entity.CollectParamConfigEntity;
import com.fangcloud.noah.dao.entity.CollectParamDetailEntity;
import com.fangcloud.noah.dao.mapper.CollectParamConfigMapper;
import com.fangcloud.noah.dao.mapper.CollectParamDetailMapper;
import com.fangcloud.noah.service.common.PageObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenke on 16-9-26.
 */
@Service
public class CollectParamDetailService {


    @Autowired
    private CollectParamDetailMapper collectParamDetailMapper;

    public List<CollectParamDetailEntity> queryCollectParamDetailList(Integer collectParamConfigId) {

        return collectParamDetailMapper.selectCollectParamDetailList(collectParamConfigId);
    }

    public void addCollectParamDetail(CollectParamDetailEntity entity) {
        collectParamDetailMapper.insert(entity);
    }

    public CollectParamDetailEntity queryCollectParamDetailById(Integer id) {
        return collectParamDetailMapper.selectById(id);
    }

    public void deleteById(Integer id) {
        collectParamDetailMapper.deleteById(id);
    }
}
