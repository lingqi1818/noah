package com.fangcloud.noah.web.test;

import java.text.ParseException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.fangcloud.noah.service.service.RiskEventStatisticsService;
import com.fangcloud.noah.service.vo.EventStatisticsVo;

/**
 * Created by chenke on 16-12-9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-context-risk.xml" })
public class RiskEventStatisticsServiceTest {

    @Autowired
    private RiskEventStatisticsService riskEventStatisticsService;

    @Test
    public void testGetTodayEventStatistics() throws ParseException {
        EventStatisticsVo vo = riskEventStatisticsService.getTodayEventStatistics("");
        System.out.println(JSON.toJSONString(vo));
    }

    @Test
    public void testQueryEventStatistics() throws ParseException {
        String deviceType = "";
        String eventType = "";
        String startTime = "2016-12-25 00:00:00";
        String endTime = "2016-12-26 00:00:00";
        List<EventStatisticsVo> result = riskEventStatisticsService.queryEventStatistics(deviceType,
                eventType, startTime, endTime);
        System.out.println(JSON.toJSONString(result));
    }

}
