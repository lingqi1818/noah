package com.fangcloud.noah.service.service;

import com.fangcloud.noah.dao.entity.ListDataEntity;
import com.fangcloud.noah.dao.mapper.ListDataMapper;
import com.fangcloud.noah.service.common.PageObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenke on 17-1-3.
 */
@Service
public class ListDataService implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(ListDataService.class);

    private Map<String,String> listDataMap = new HashMap<String,String>();

    @Autowired
    private ListDataMapper listDataMapper;

    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);


    public void saveListData(ListDataEntity record) {
        listDataMapper.insert(record);
    }

    public PageObject<ListDataEntity> queryListDatas(Integer pageNum, String dataType,String dataValue) {
        int totalItem = listDataMapper.selectListDataCount(dataType,dataValue);
        if (pageNum == null) {
            pageNum = 1;
        }
        PageObject<ListDataEntity> datas = new PageObject<ListDataEntity>(pageNum, totalItem);
        List<ListDataEntity> rst = listDataMapper.selectListDatas(datas.getOffSet(), datas.getPageSize(), dataType,dataValue);
        datas.initViewData(rst);
        return datas;
    }

    public void deleteListDataById(Integer id) {
        listDataMapper.deleteById(id);
    }

    public ListDataEntity queryListDataById(Integer id) {

        return listDataMapper.selectById(id);
    }

    public boolean inList(String listType, String dataValue) {

        if(StringUtils.isBlank(listType) || StringUtils.isBlank(dataValue)){
            return false;
        }

        return listDataMap.containsKey(listType.trim()+"_"+dataValue.trim());
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("ListDataService scheduled run...");
                    List<ListDataEntity> listDatas = listDataMapper.selectAllDatas();
                    if(CollectionUtils.isNotEmpty(listDatas)){

                        Map<String,String> tmpListDataMap = new HashMap<String, String>();

                        for(ListDataEntity entity:listDatas){
                            tmpListDataMap.put(entity.getDataType()+"_"+entity.getDataValue(),entity.getDataValue());
                        }

                        listDataMap = tmpListDataMap;
                    }


                }catch (Throwable t){
                    logger.error("ListDataService 定时任务执行异常，"+t.getMessage());
                }
            }
        }, 0, 5, TimeUnit.MINUTES);
    }
}
