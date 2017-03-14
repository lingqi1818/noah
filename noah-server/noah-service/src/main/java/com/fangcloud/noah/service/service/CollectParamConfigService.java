package com.fangcloud.noah.service.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.fangcloud.noah.api.model.EventCollectDefinition;
import com.fangcloud.noah.api.model.MappedCollectParam;
import com.fangcloud.noah.dao.entity.CollectParamConfigEntity;
import com.fangcloud.noah.dao.entity.CollectParamDetailEntity;
import com.fangcloud.noah.dao.mapper.CollectParamConfigMapper;
import com.fangcloud.noah.dao.mapper.CollectParamDetailMapper;
import com.fangcloud.noah.redis.RedisService;
import com.fangcloud.noah.service.common.CacheKeyEnum;
import com.fangcloud.noah.service.common.PageObject;

/**
 * Created by chenke on 16-9-26.
 */
@Service
public class CollectParamConfigService {


    @Autowired
    private CollectParamConfigMapper collectParamConfigMapper;

    @Autowired
    private CollectParamDetailMapper collectParamDetailMapper;

    @Autowired
    private RedisService redisService;

    public PageObject<CollectParamConfigEntity> queryCollectParamConfigList(Integer pageNum, String applicationName) {

        if (pageNum == null) {
            pageNum = 1;
        }

        int total = collectParamConfigMapper.selectCollectParamConfigCount(applicationName);

        PageObject<CollectParamConfigEntity> datas = new PageObject<CollectParamConfigEntity>(pageNum, total);

        List<CollectParamConfigEntity> result = collectParamConfigMapper.selectCollectParamConfigList(datas.getOffSet(), datas.getPageSize(), applicationName);

        datas.initViewData(result);

        return datas;
    }

    public void addCollectParamConfig(CollectParamConfigEntity configEntity) {

        collectParamConfigMapper.insert(configEntity);
    }

    public List<CollectParamDetailEntity> queryCollectDetailByConfigId(Integer configId) {
        return collectParamDetailMapper.selectCollectParamDetailList(configId);

    }

    public void deleteCollectParamConfig(Integer configId) {
        collectParamConfigMapper.deleteById(configId);
    }

    public void cacheConfigToRedis() {

        List<CollectParamConfigEntity> configList =collectParamConfigMapper.selectCollectParamConfigListWithDetail();

        Map<String,Map<String,EventCollectDefinition>> applicationEventCollectDefinitionMap = new HashMap<String,Map<String,EventCollectDefinition>>();

        Map<String,EventCollectDefinition>  eventCollectDefinitionMap = new HashMap<String,EventCollectDefinition>();

        if(CollectionUtils.isNotEmpty(configList)){

            for(CollectParamConfigEntity c:configList){

                EventCollectDefinition eventCollectDefinition = new EventCollectDefinition();
                eventCollectDefinition.setApplicationName(c.getApplicationName());
                eventCollectDefinition.setUri(c.getUri());
                eventCollectDefinition.setEventType(c.getEventType());
                eventCollectDefinition.setSwitchStatus(c.getSwitchStatus()==1?true:false);

                List<CollectParamDetailEntity> detailEntitys = c.getCollectParamList();

                List<MappedCollectParam> mapperCollectParamList = new ArrayList<MappedCollectParam>();

                if(CollectionUtils.isNotEmpty(detailEntitys)){
                    for(CollectParamDetailEntity e:detailEntitys){
                        MappedCollectParam p = new MappedCollectParam();
                        p.setSrc(e.getSrc());
                        p.setTarget(e.getTarget());
                        p.setType(e.getType());
                        mapperCollectParamList.add(p);
                    }

                }
                eventCollectDefinition.setMappedCollectParamList(mapperCollectParamList);

                eventCollectDefinitionMap.put(c.getUri(),eventCollectDefinition);

            }
        }

        Set<Map.Entry<String, EventCollectDefinition>> eventCollectDefinitionSet = eventCollectDefinitionMap.entrySet();

        for(Map.Entry<String,EventCollectDefinition> entry:eventCollectDefinitionSet){

            EventCollectDefinition eventCollectDefinition = entry.getValue();

            if(applicationEventCollectDefinitionMap.containsKey(eventCollectDefinition.getApplicationName())){
                Map<String,EventCollectDefinition> eMap = applicationEventCollectDefinitionMap.get(eventCollectDefinition.getApplicationName());
                eMap.put(eventCollectDefinition.getUri(),eventCollectDefinition);
            }else {
                Map<String,EventCollectDefinition> eMap = new HashMap<String,EventCollectDefinition>();
                eMap.put(eventCollectDefinition.getUri(),eventCollectDefinition);
                applicationEventCollectDefinitionMap.put(eventCollectDefinition.getApplicationName(),eMap);
            }
        }


        Set<Map.Entry<String,Map<String,EventCollectDefinition>>> mapSet = applicationEventCollectDefinitionMap.entrySet();

        for(Map.Entry<String,Map<String,EventCollectDefinition>> entry:mapSet){
            String applicationName = entry.getKey();
            Map<String,EventCollectDefinition> collectDefinitionMap = entry.getValue();

            System.out.printf("applicationEventCollectDefinition:"+JSON.toJSONString(collectDefinitionMap));

            redisService.set(CacheKeyEnum.APPLICATION_COLLECT_DEFINITION.getKey(applicationName), JSON.toJSONString(collectDefinitionMap));
        }
    }
}
