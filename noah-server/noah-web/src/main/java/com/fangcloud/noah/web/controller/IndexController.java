package com.fangcloud.noah.web.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fangcloud.noah.dao.entity.EventType;
import com.fangcloud.noah.service.service.EventTypeService;
import com.fangcloud.noah.service.service.RiskEventStatisticsService;

/**
 * 默认首页控制器
 *
 * @author: YJL Date: 16-1-19
 */
@Controller
public class IndexController {

    private final Logger               logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private RiskEventStatisticsService riskEventStatisticsService;

    @Autowired
    private EventTypeService           eventTypeService;

    private SimpleDateFormat           sdf    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping("/")
    public String index(HttpServletRequest request) {

        List<EventType> eventTypes = eventTypeService.queryAllEventTypes();

        request.setAttribute("eventTypes", eventTypes);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date endDate = calendar.getTime();

        request.setAttribute("endTime", sdf.format(endDate));

        calendar.add(Calendar.DATE, -7);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startDate = calendar.getTime();
        request.setAttribute("startTime", sdf.format(startDate));

        return "index";
    }
}
