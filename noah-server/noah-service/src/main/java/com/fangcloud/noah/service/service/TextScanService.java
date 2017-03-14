package com.fangcloud.noah.service.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fangcloud.noah.api.api.enums.RiskWordType;
import com.fangcloud.noah.api.exception.RiskRuntimeException;
import com.fangcloud.noah.dao.entity.RiskTermEntity;
import com.fangcloud.noah.dao.mapper.RiskTermMapper;
import com.fangcloud.noah.fasttext.fasttext.decorator.DecoratorText;
import com.fangcloud.noah.fasttext.fasttext.extract.CompositeExtractText;
import com.fangcloud.noah.fasttext.fasttext.extract.ExtractText;
import com.fangcloud.noah.fasttext.fasttext.psoriasis.MappedDecoratorText;
import com.fangcloud.noah.fasttext.fasttext.psoriasis.SkipTermExtraInfo;

/**
 * 高危词汇过滤service Created by chenke on 16-9-23.
 */
@Service
public class TextScanService {

    private static final Logger            logger                   = LoggerFactory
            .getLogger(TextScanService.class);

    private DecoratorText                  decoratorText;

    private ExtractText                    extractText              = new CompositeExtractText();

    private RiskWordType[]                 riskWordTypes            = RiskWordType.values();

    private Map<String, DecoratorText>     decoratorTextMap         = new HashMap<String, DecoratorText>();

    @Autowired
    private RiskTermMapper                 riskTermMapper;

    private final ScheduledExecutorService scheduledExecutorService = Executors
            .newScheduledThreadPool(1);

    @PostConstruct
    public void init() {

        try {
            createDecoratorText();

            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("TextScanService scheduled createDecoratorText run...");
                        createDecoratorText();
                    } catch (Throwable t) {
                        logger.error("TextScanService 定时任务执行异常，" + t.getMessage());
                    }
                }
            }, 5, 5, TimeUnit.MINUTES);

            logger.info("TextScanService init finish!");
        } catch (Exception e) {
            logger.error("TextScanService init exception", e);
            throw new RiskRuntimeException(e);
        }
    }

    private void createDecoratorText() {

        List<SkipTermExtraInfo> skipTermExtraInfos = new ArrayList<SkipTermExtraInfo>();

        List<RiskTermEntity> riskTermEntities = riskTermMapper.loadAllRecords();

        Map<String, List<RiskTermEntity>> riskTermMap = new HashMap<String, List<RiskTermEntity>>();

        if (CollectionUtils.isNotEmpty(riskTermEntities)) {
            for (RiskTermEntity entity : riskTermEntities) {

                String wordType = entity.getWordType();

                if (StringUtils.isBlank(wordType)) {
                    //如果是空的，默认危险词应用范围为全局
                    wordType = RiskWordType.ALL.getCode();
                }
                String[] wordTypes = wordType.split(",");

                for (String wt : wordTypes) {
                    List<RiskTermEntity> infos = riskTermMap.get(wt);
                    if (CollectionUtils.isNotEmpty(infos)) {
                        infos.add(entity);
                    } else {
                        List<RiskTermEntity> riskTermEntityList = new ArrayList<RiskTermEntity>();
                        riskTermEntityList.add(entity);
                        riskTermMap.put(wt, riskTermEntityList);
                    }
                }
            }
        }

        Map<String, DecoratorText> decoratorTextTmpMap = new HashMap<String, DecoratorText>();
        for (Map.Entry<String, List<RiskTermEntity>> entry : riskTermMap.entrySet()) {
            String wt = entry.getKey();
            List<RiskTermEntity> riskTermEntityList = entry.getValue();

            if (!wt.equals(RiskWordType.ALL.getCode())) {
                riskTermEntityList.addAll(riskTermMap.get(RiskWordType.ALL.getCode()));
            }

            if (CollectionUtils.isNotEmpty(riskTermEntityList)) {

                List<SkipTermExtraInfo> skipTermExtraInfoList = new ArrayList<SkipTermExtraInfo>();

                for (RiskTermEntity entity : riskTermEntityList) {
                    String word = entity.getWord().trim();
                    boolean allowSkip = entity.getAllowSkip() == 1 ? true : false;
                    boolean wholeWord = entity.getWholeWord() == 1 ? true : false;
                    Integer weight = entity.getWeight();
                    SkipTermExtraInfo skipTermExtraInfo = new SkipTermExtraInfo(word, allowSkip,
                            wholeWord, weight / 10.0f);
                    skipTermExtraInfoList.add(skipTermExtraInfo);
                }

                decoratorText = new MappedDecoratorText(skipTermExtraInfoList, 2);

                decoratorTextTmpMap.put(wt, decoratorText);
            }

        }
        decoratorTextMap = decoratorTextTmpMap;
    }

    public List<String> parseTerms(String content, String riskWordType) {

        if (StringUtils.isBlank(content) || StringUtils.isBlank(riskWordType)) {
            return null;
        }

        try {
            DecoratorText decoratorText = decoratorTextMap.get(riskWordType);

            if (decoratorText != null) {
                return decoratorText.parseTerms(content, true, false);
            } else {
                return null;
            }

        } catch (Exception e) {
            logger.error("TextScanService parseTerms exception,content=" + content, e);
            return null;
        }
    }
}
