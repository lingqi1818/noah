package com.fangcloud.noah.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fangcloud.noah.service.service.AlarmService;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by chenke on 17-1-9.
 */
@Controller
@RequestMapping("alarm")
public class AlarmController {

    @Autowired
    private AlarmService alarmService;

    @RequestMapping("dailyAlarm")
    public void dailyAlarm(String receives, Integer days, Integer artisanCouponLimitCount,Integer accountCouponLimitCount){

        String[] to = receives.split(",");
        alarmService.sendDailyAlarm(to,days,artisanCouponLimitCount,accountCouponLimitCount);
    }
}
