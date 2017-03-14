package com.fangcloud.noah.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fangcloud.noah.api.exception.RiskRuntimeException;
import com.fangcloud.noah.redis.RedisService;
import com.fangcloud.noah.service.common.CacheKeyEnum;

import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

/**
 * Created by chenke on 16-5-5.
 */
@Controller
@RequestMapping("relationaccount")
public class RelationAccountController {

    @Autowired
    private RedisService     redisService;

    //默认最大显示条数
    private static final int defaulSize = 10;

    private SimpleDateFormat sdf        = new SimpleDateFormat("yyMMdd");

    @RequestMapping("show")
    public String show(HttpServletRequest request) {

        request.setAttribute("nextCursor", "0");
        return "relationaccount/list";
    }

    @RequestMapping("query")
    public String queryRelationAccount(String queryType, String queryKey, String rangeType,
                                       String cursor, HttpServletRequest request) {

        CacheKeyEnum cacheKeyEnum = null;
        if (queryType.equals(CacheKeyEnum.BEACON_ID_ACCOUNT.getKeyPrefix())) {
            cacheKeyEnum = CacheKeyEnum.BEACON_ID_ACCOUNT;
        } else if (queryType.equals(CacheKeyEnum.DEVICE_ID_ACCOUNT.getKeyPrefix())) {
            cacheKeyEnum = CacheKeyEnum.DEVICE_ID_ACCOUNT;
        } else if (queryType.equals(CacheKeyEnum.IP_ACCOUNT.getKeyPrefix())) {
            cacheKeyEnum = CacheKeyEnum.IP_ACCOUNT;
        } else {
            throw new RiskRuntimeException("queryType param is error");
        }

        long total = 0L;
        long nextCursor = 0L;
        List<String> resultList = new ArrayList<String>();

        String queryKeyNew;

        if (rangeType.equals("1")) {
            Date date = new Date();

            String currentDate = sdf.format(date);

            queryKeyNew = currentDate + "_" + queryKey;

        } else {
            queryKeyNew = queryKey;
        }

        total = redisService.scard(cacheKeyEnum.getKey(queryKeyNew));

        if (total > 0) {

            ScanParams scanParams = new ScanParams();
            scanParams.count(defaulSize);

            ScanResult<String> scanResult = redisService.sScan(cacheKeyEnum.getKey(queryKeyNew),
                    cursor, scanParams);

            if (scanResult != null) {
                nextCursor = Long.valueOf(new String(scanResult.getCursorAsBytes()));
                resultList = scanResult.getResult();
            }

        }

        request.setAttribute("total", total);
        request.setAttribute("nextCursor", nextCursor);
        request.setAttribute("result", resultList);
        request.setAttribute("queryType", queryType);
        request.setAttribute("rangeType", rangeType);
        request.setAttribute("queryKey", queryKey);

        return "relationaccount/list";
    }
}
