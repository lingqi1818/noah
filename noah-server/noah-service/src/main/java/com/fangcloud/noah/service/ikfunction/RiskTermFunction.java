package com.fangcloud.noah.service.ikfunction;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.fangcloud.noah.service.service.TextScanService;
import com.fangcloud.noah.service.util.SpringContextUtil;

import java.util.List;


/**
 * ik 高危词汇函数
 * Created by chenke on 16-9-23.
 */
public class RiskTermFunction {

    /**
     * 判断文本内容是否包含危险词汇
     * @param content
     * @return
     */
    public boolean containTerm(String content){

        if(StringUtils.isBlank(content)){
            return false;
        }

        TextScanService textScanService = SpringContextUtil.getBean(TextScanService.class);

        List<String> resultList = textScanService.parseTerms(content,"");

        return CollectionUtils.isNotEmpty(resultList);
    }
}
