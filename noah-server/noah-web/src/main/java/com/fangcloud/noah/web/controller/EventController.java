package com.fangcloud.noah.web.controller;

import com.alibaba.fastjson.JSON;
import com.fangcloud.noah.dao.entity.Event;
import com.fangcloud.noah.dao.entity.EventType;
import com.fangcloud.noah.dao.model.EventQuery;
import com.fangcloud.noah.service.common.PageObject;
import com.fangcloud.noah.service.service.EventService;
import com.fangcloud.noah.service.service.EventTypeService;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenke on 16-5-23.
 */
@Controller
@RequestMapping("event")
public class EventController {


    @Autowired
    private EventService eventService;

    @Autowired
    private EventTypeService eventTypeService;


    @RequestMapping("query")
    public String query(EventQuery eventQuery, HttpServletRequest req) {

        PageObject<Event> pageObj = eventService.queryPage(eventQuery);

        List<EventType> eventTypes = eventTypeService.queryAllEventTypes();

        Map<String,String> eventTypeMap = new HashMap<String,String>();

        for(EventType e:eventTypes){
            eventTypeMap.put(e.getCode(),e.getName());
        }

        List<Event> eventList = pageObj.getDatas();

        if(CollectionUtils.isNotEmpty(eventList)){
            for(Event event:eventList){
                event.setEventType(String.valueOf(eventTypeMap.get(event.getEventType())));
            }
        }

        req.setAttribute("eventQuery", eventQuery);
        req.setAttribute("pageObj", pageObj);
        req.setAttribute("eventTypes", eventTypes);

        return "invoke/list";
    }


    @RequestMapping("detail")
    public String detail(String id,HttpServletRequest req){

        Event event = eventService.queryEventById(id);

        List<EventType> eventTypes = eventTypeService.queryAllEventTypes();

        Map<String,String> eventTypeMap = new HashMap<String,String>();

        for(EventType e:eventTypes){
            eventTypeMap.put(e.getCode(),e.getName());
        }

        event.setEventType(eventTypeMap.get(event.getEventType()));
        req.setAttribute("event",event);


        return "invoke/detail";
    }


}
