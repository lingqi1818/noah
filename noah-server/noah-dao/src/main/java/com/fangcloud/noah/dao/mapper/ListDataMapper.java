package com.fangcloud.noah.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.fangcloud.noah.dao.entity.ListDataEntity;

import java.util.List;

/**
 * Created by chenke on 16-8-23.
 */
@Repository
public interface ListDataMapper {

    void insert(ListDataEntity listDataEntity);

    int selectListDataCount(@Param("dataType") String dataType,@Param("dataValue") String dataValue);

    List<ListDataEntity> selectListDatas(@Param("offSet") int offSet, @Param("pageSize") int pageSize, @Param("dataType") String dataType,@Param("dataValue") String dataValue);

    ListDataEntity selectById(Integer id);

    void deleteById(Integer id);

    List<ListDataEntity> selectAllDatas();

}
