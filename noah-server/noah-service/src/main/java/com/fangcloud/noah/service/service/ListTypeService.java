package com.fangcloud.noah.service.service;

import com.fangcloud.noah.dao.entity.ListTypeEntity;
import com.fangcloud.noah.dao.mapper.ListTypeMapper;
import com.fangcloud.noah.service.common.PageObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenke on 16-8-23.
 */
@Service
public class ListTypeService {

    @Autowired
    private ListTypeMapper listTypeMapper;


    public List<ListTypeEntity> queryAllListType(){
        return listTypeMapper.selectAllListTypes();
    }

}
