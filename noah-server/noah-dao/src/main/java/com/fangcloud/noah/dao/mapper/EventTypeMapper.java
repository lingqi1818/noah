package com.fangcloud.noah.dao.mapper;

import org.apache.ibatis.annotations.Param;

import com.fangcloud.noah.dao.entity.EventType;

import java.util.List;

/**
 * Created by chenke on 16-8-23.
 */
public interface EventTypeMapper {

    void insert(EventType eventType);

    int selectEventTypeCount();

    List<EventType> selectEventTypeList(@Param("rowBegin") int rowBegin, @Param("rowNum") int rowNum);

    List<EventType> selectAllEventTyps();
}
