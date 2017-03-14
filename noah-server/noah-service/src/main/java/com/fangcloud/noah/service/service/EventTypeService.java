package com.fangcloud.noah.service.service;

import com.fangcloud.noah.dao.entity.EventType;
import com.fangcloud.noah.dao.mapper.EventTypeMapper;
import com.fangcloud.noah.service.common.PageObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenke on 16-8-23.
 */
@Service
public class EventTypeService {

    @Autowired
    private EventTypeMapper eventTypeMapper;

    public PageObject<EventType> queryEventTypeList(Integer pageNum) {
        if (pageNum == null) {
            pageNum = 1;
        }

        int total = eventTypeMapper.selectEventTypeCount();

        PageObject<EventType> datas = new PageObject<EventType>(pageNum, total);

        List<EventType> result = eventTypeMapper.selectEventTypeList(datas.getOffSet(), datas.getPageSize());

        datas.initViewData(result);

        return datas;
    }

    public void add(EventType eventType) {

        eventTypeMapper.insert(eventType);
    }

    public List<EventType> queryAllEventTypes() {

        return eventTypeMapper.selectAllEventTyps();
    }
}
