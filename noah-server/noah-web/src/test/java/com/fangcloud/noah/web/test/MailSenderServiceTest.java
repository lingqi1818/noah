package com.fangcloud.noah.web.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fangcloud.noah.service.service.AlarmService;
import com.fangcloud.noah.service.service.MailSenderService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenke on 16-12-9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/spring-context-risk.xml"})
public class MailSenderServiceTest {

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private AlarmService alarmService;

    @Test
    public void testSendMailWithVelocityTemplate()  {

        String mailSubject ="风控系统新客券使用异常报警";
        String[] to = {"chenke@helijia.com"};
        String templateLocation = "vm/alarm_coupon.vm";
        Map<String, Object> velocityContext = new HashMap<String,Object>();
        velocityContext.put("appName","testApp");
        mailSenderService.sendMailWithVelocityTemplate(mailSubject,to,templateLocation,velocityContext);
    }

    @Test
    public void testSendDailyAlarm()  {

        String[] to = {"chenke@helijia.com"};

        alarmService.sendDailyAlarm(to,301,7,1);
    }
}
