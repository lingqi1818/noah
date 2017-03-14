package com.fangcloud.noah.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.fangcloud.noah.dao.entity.ActionScriptEntity;
import com.fangcloud.noah.dao.entity.ListDataEntity;

import java.util.List;

/**
 * Created by chenke on 16-8-23.
 */
@Repository
public interface ActionScriptMapper {

    void insert(ActionScriptEntity entity);

    int selectActionScriptCount(@Param("type") String type, @Param("name") String name);

    List<ActionScriptEntity> selectActionScriptList(@Param("offSet") int offSet, @Param("pageSize") int pageSize, @Param("type") String type, @Param("name") String name);

    ActionScriptEntity selectById(Integer id);

    void deleteById(Integer id);

    List<ActionScriptEntity> selectAllDatas();

}
