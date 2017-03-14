package com.fangcloud.noah.service.service;

import com.fangcloud.noah.dao.entity.ActionScriptEntity;
import com.fangcloud.noah.dao.entity.ListDataEntity;
import com.fangcloud.noah.dao.mapper.ActionScriptMapper;
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
public class ActionScriptService  {

    private static final Logger logger = LoggerFactory.getLogger(ActionScriptService.class);

    @Autowired
    private ActionScriptMapper actionScriptMapper;


    public void saveActionScript(ActionScriptEntity record) {
        actionScriptMapper.insert(record);
    }

    public PageObject<ActionScriptEntity> queryActionScripts(Integer pageNum, String type,String name) {

        int totalItem = actionScriptMapper.selectActionScriptCount(type,name);
        if (pageNum == null) {
            pageNum = 1;
        }
        PageObject<ActionScriptEntity> datas = new PageObject<ActionScriptEntity>(pageNum, totalItem);
        List<ActionScriptEntity> rst = actionScriptMapper.selectActionScriptList(datas.getOffSet(), datas.getPageSize(), type,name);
        datas.initViewData(rst);
        return datas;
    }

    public void deleteActionScriptById(Integer id) {
        actionScriptMapper.deleteById(id);
    }

    public ActionScriptEntity queryActionScriptById(Integer id) {

        return actionScriptMapper.selectById(id);
    }

    public List<ActionScriptEntity> queryAllActionScripts(){
        return actionScriptMapper.selectAllDatas();
    }
}
