package com.fangcloud.noah.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.fangcloud.noah.api.api.model.NameList;

/**
 * Created by chenke on  Date: 15-11-30
 */
@Repository
public interface NameListMapper {

    int insert(NameList nameList);

    void update(NameList nameList);

    List<NameList> selectNameListForSync(@Param("startTime") String startTime);

    void updateNameListStatus(NameList record);

    NameList selectNameListByContent(@Param("type") Integer type, @Param("content") String content);

    List<NameList> selectNameList(@Param("rowBegin") int rowBegin, @Param("rowNum") int rowNum, @Param("nameType") Integer nameType, @Param("nameGrade") Integer nameGrade, @Param("content") String content);

    int selectNameListCount(@Param("nameType") Integer nameType, @Param("nameGrade") Integer nameGrade, @Param("content") String content);

    NameList selectNameListById(@Param("nameId") Integer nameId);


}
