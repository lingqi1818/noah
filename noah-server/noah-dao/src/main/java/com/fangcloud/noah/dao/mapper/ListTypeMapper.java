package com.fangcloud.noah.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.fangcloud.noah.dao.entity.ListTypeEntity;

import java.util.List;

/**
 * Created by chenke on 16-8-23.
 */
@Repository
public interface ListTypeMapper {

    void insert(ListTypeEntity listTypeEntity);

    List<ListTypeEntity> selectAllListTypes();
}
