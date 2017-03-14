package com.fangcloud.noah.apiservice;

import com.alibaba.fastjson.JSON;
import com.fangcloud.noah.api.api.enums.NameListGrade;
import com.fangcloud.noah.api.api.enums.NameListType;
import com.fangcloud.noah.api.api.model.NameList;
import com.fangcloud.noah.dao.entity.api.CardOrder;
import com.fangcloud.noah.dao.entity.api.UsOrder;
import com.fangcloud.noah.dao.entity.api.User;
import com.fangcloud.noah.dao.mapper.NameListMapper;
import com.fangcloud.noah.service.apiservice.ApiService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;

/**
 * Created by chenke on 16-8-30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/spring-context-ws.xml"})
public class ApiServiceTest {

    @Autowired
    private ApiService apiService;

    @Test
    public void testGetUsOrderByOrderSeq() {

        String orderSeq = "201405200000173";
        UsOrder usOrder = apiService.getUsOrderByOrderSeq(orderSeq);
        System.out.println(JSON.toJSONString(usOrder));
        Assert.assertNotNull(usOrder);
    }

    @Test
    public void testGetCardOrderByOrderSeq(){

        String orderSeq = "160830163332610";
        CardOrder cardOrder = apiService.getCardOrderByOrderSeq(orderSeq);
        System.out.println(JSON.toJSONString(cardOrder));
        Assert.assertNotNull(cardOrder);

    }

    @Test
    public void testGetUserByUserId(){

        String userId = "15552e33f4cf452884c631d86d65e3b0";
        User user = apiService.getUserByUserId(userId);
        Assert.assertNotNull(user);

    }


    @Test
    public void testIsSecondKillProduct(){

        String productId = "001b65031f0a4c08a96f024c9a05c3f4";
        Boolean bool = apiService.isSecondKillProduct(productId);
        Assert.assertTrue(bool);

    }

    @Test
    public void testSaveBlackList(){

        NameList nameList = new NameList();
        nameList.setContent("18810188123");
        nameList.setType(NameListType.MOBILE.getCode());
        nameList.setGrade(NameListGrade.BLACK.getCode());
        nameList.setRemark("测试");
        nameList.setGmtCreated(new Timestamp(System.currentTimeMillis()));
        nameList.setGmtModified(nameList.getGmtCreated());
        apiService.saveBlackList(nameList);
    }

    @Test
    public void testUpdateBlackList2White(){

        String mobile = "18810188123";
        apiService.updateBlackList2White(mobile);
    }
}
