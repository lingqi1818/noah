package com.fangcloud.noah.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fangcloud.noah.common.api.util.Result;
import com.fangcloud.noah.dao.entity.EventType;
import com.fangcloud.noah.service.common.PageObject;
import com.fangcloud.noah.service.service.EventTypeService;

/**
 * Created by chenke on 16-8-23.
 */
@Controller
@RequestMapping("eventType")
public class EventTypeController {

    @Autowired
    private EventTypeService eventTypeService;

    @RequestMapping("query")
    public String query(Integer pageNum, String eventType, HttpServletRequest request) {

        PageObject<EventType> pageObj = eventTypeService.queryEventTypeList(pageNum);

        request.setAttribute("pageObj", pageObj);
        return "eventType/list";

    }

    @RequestMapping("add")
    @ResponseBody
    public Result add(EventType eventType) {

        eventTypeService.add(eventType);
        return new Result();
    }
}
