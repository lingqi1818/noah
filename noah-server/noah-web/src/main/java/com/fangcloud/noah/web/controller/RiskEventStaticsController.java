package com.fangcloud.noah.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fangcloud.noah.api.exception.RiskRuntimeException;
import com.fangcloud.noah.common.api.util.Result;
import com.fangcloud.noah.service.service.EventTypeService;
import com.fangcloud.noah.service.service.RiskEventStatisticsService;
import com.fangcloud.noah.service.vo.EventStatisticsVo;

/**
 * Created by chenke on 16-12-27.
 */
@Controller
@RequestMapping("statics")
public class RiskEventStaticsController {


    private final Logger logger = LoggerFactory.getLogger(RiskEventStaticsController.class);

    @Autowired
    private RiskEventStatisticsService riskEventStatisticsService;

    @Autowired
    private EventTypeService eventTypeService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping("today")
    @ResponseBody
    public Result todayRiskEventStatiscs(String deviceType, HttpServletRequest request) {

        //获取今日 各状态事件统计数量
        EventStatisticsVo eventStatistics = null;
        Integer todayAccept = 0;
        Integer todayReview = 0;
        Integer todayReject = 0;
        Integer totayTotal = 0;
        try {
            eventStatistics = riskEventStatisticsService.getTodayEventStatistics(deviceType);

            todayAccept = eventStatistics.getAcceptCount();
            if(todayAccept==null){
                todayAccept = 0;
            }
            todayReview = eventStatistics.getReviewCount();
            if(todayReview==null){
                todayReview = 0;
            }
            todayReject = eventStatistics.getRejectCount();

            if(todayReject==null){
                todayReject = 0;
            }
            totayTotal = todayAccept + todayReview + todayReject;
        } catch (ParseException e) {
            logger.error("今日统计查询异常",e);
        }

        Map<String,Object> todayStatistics = new HashMap<String,Object>();
        todayStatistics.put("todayAccept",todayAccept);
        todayStatistics.put("todayReview",todayReview);
        todayStatistics.put("todayReject",todayReject);
        todayStatistics.put("totayTotal",totayTotal);
        todayStatistics.put("deviceType",deviceType);

        return new Result(true).setData(todayStatistics);
    }


    @RequestMapping("query")
    @ResponseBody
    public Result query(String deviceType, String eventType, String startTime, String endTime, HttpServletRequest request)  {

        if(StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)){
            throw new RiskRuntimeException("查询时间不能为空,请选择");
        }
        try {
            List<EventStatisticsVo> eventStatisticsVoList = riskEventStatisticsService.queryEventStatistics(deviceType,eventType,startTime,endTime);
            return new Result(true).setData(eventStatisticsVoList);
        }catch (Exception e){
            logger.error("事件统计查询异常",e);
        }

        return new Result(false);


    }


}
