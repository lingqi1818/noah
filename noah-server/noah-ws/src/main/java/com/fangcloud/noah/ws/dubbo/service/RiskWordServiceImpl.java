package com.fangcloud.noah.ws.dubbo.service;

import com.fangcloud.noah.api.api.service.RiskWordService;
import com.fangcloud.noah.service.service.TextScanService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenke on 16-9-23.
 */
@Component("riskWordService")
public class RiskWordServiceImpl implements RiskWordService {

    private static final Logger logger = LoggerFactory.getLogger(TextScanService.class);

    @Autowired
    private TextScanService textScanService;


    @Override
    public List<String> containTerm(String content,String wordType) {
        if(StringUtils.isBlank(content) ||StringUtils.isBlank(wordType)){
            logger.error("调用危险词接口参数为空!");
            return null;
        }

        try {
            return textScanService.parseTerms(content,wordType);
        }catch (Exception e){
            logger.error("RiskWordServiceImpl containTerm exception,content="+content,e);
            return null;
        }
    }

    @Override
    public List<List<String>> containTerm(List<String> contentList,String wordType) {

        List<List<String>> riskWordList = new ArrayList<List<String>>();

        if(CollectionUtils.isEmpty(contentList) ||StringUtils.isBlank(wordType)){
            logger.error("调用危险词接口参数为空!");
            return null;
        }

        for(String content:contentList){

            List<String> contentResult = textScanService.parseTerms(content,wordType);

            riskWordList.add(contentResult);

        }

        return riskWordList;
    }
}
