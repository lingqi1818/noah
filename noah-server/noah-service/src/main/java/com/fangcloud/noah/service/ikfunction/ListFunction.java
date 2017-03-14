package com.fangcloud.noah.service.ikfunction;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fangcloud.noah.service.service.ListDataService;
import com.fangcloud.noah.service.util.SpringContextUtil;

/**
 * Created by chenke on 17-1-3.
 */
public class ListFunction {

    private static final Logger logger = LoggerFactory.getLogger(ListFunction.class);

    private ListDataService listDataService = SpringContextUtil.getBean(ListDataService.class);

    public boolean inList(String listType,String dataValue){
        if(StringUtils.isBlank(listType) || StringUtils.isBlank(dataValue)){
            return false;
        }
        return listDataService.inList(listType,dataValue);
    }

    public boolean notInList(String listType,String dataValue){
        return !inList(listType,dataValue);
    }

}
