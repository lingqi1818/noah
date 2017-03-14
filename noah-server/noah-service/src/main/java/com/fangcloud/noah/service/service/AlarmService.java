package com.fangcloud.noah.service.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by chenke on 17-1-9.
 */
@Service
public class AlarmService {

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private JdbcTemplate slaveApiJdbcTemplate;

    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void sendDailyAlarm(String[] to,Integer days,Integer artisanCouponLimitCount,Integer accountCouponLimitCount){


        if(ArrayUtils.isEmpty(to)){
            return;
        }

        if(days == null){
            days = 1;
        }

        if(artisanCouponLimitCount == null){
            artisanCouponLimitCount = 5;
        }

        if(accountCouponLimitCount == null){
            accountCouponLimitCount = 5;
        }

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE,-days);

        Date date = calendar.getTime();

        String beginTime = sdf.format(date);


        String mailSubject = "风控系统新客券使用异常报警";

        String templateLocation = "vm/alarm_coupon.vm";

        Map<String, Object> velocityContext = new HashMap<String,Object>();

        String querySql = "select a.artisan_id,a.nick,count(1) num\n" +
                "from us_order us\n" +
                "join user u on us.user_id = u.userid\n" +
                "join artisan a on us.artisan_id = a.artisan_id\n" +
                "join coupon_use c on us.coupon_id = c.id\n" +
                "where \n" +
                "us.create_time >'"+beginTime+"' \n" +
                "and us.status != '20' \n" +
                "and us.coupon_price>10 and c.service_type = '10'\n" +
                "group by a.artisan_id,a.nick  having count(1)>="+artisanCouponLimitCount+"  order by count(1) desc limit 30 ";

        List<Map<String,Object>> artisanCouponResult = slaveApiJdbcTemplate.queryForList(querySql);


        String querySql2 = "select t.account,count(1) num from (select distinct us.id,\n" +
                "us.order_seq ,\n" +
                "u.mobile,\n" +
                "a.artisan_id,\n" +
                "a.nick,\n" +
                "us.user_contact,\n" +
                "r.beacon_id,\n" +
                "us.create_time,\n" +
                "u.channel,\n" +
                "c.coupon_code,\n" +
                "us.product_trade_price,\n" +
                "us.coupon_price,\n" +
                "us.should_pay_price,\n" +
                "us.user_address,\n" +
                "us.coupon_id,\n" +
                "(select max(d.account) from sms.pay_detail d join sms.pay p on d.pay_id = p.id \n" +
                "where p.order_no = us.order_seq and p.status = 1 and d.status = 1 )  account\n" +
                "from us_order us\n" +
                "join user u on us.user_id = u.userid\n" +
                "join artisan a on us.artisan_id = a.artisan_id\n" +
                "join coupon_use c on us.coupon_id = c.id\n" +
                "left join device_coupon_use_record r on r.coupon_id = us.coupon_id\n" +
                "where \n" +
                "us.create_time >'"+beginTime+"' \n" +
                "and us.status != '20' \n" +
                "and us.coupon_price>10 and c.service_type = '10'  \n" +
                "order by us.id desc ) t  where t.account != '' group by t.account having count(1)>="+accountCouponLimitCount+" order by count(1) desc limit 30";


        List<Map<String,Object>> payAccountCouponResult = slaveApiJdbcTemplate.queryForList(querySql2);

        if(CollectionUtils.isEmpty(artisanCouponResult) && CollectionUtils.isEmpty(payAccountCouponResult)){
            return;
        }

        velocityContext.put("artisanCouponResult",artisanCouponResult);
        velocityContext.put("payAccountCouponResult",payAccountCouponResult);

        velocityContext.put("days",days);
        velocityContext.put("artisanCouponLimitCount",artisanCouponLimitCount);
        velocityContext.put("accountCouponLimitCount",accountCouponLimitCount);


        mailSenderService.sendMailWithVelocityTemplate(mailSubject,to,templateLocation,velocityContext);
    }
}
