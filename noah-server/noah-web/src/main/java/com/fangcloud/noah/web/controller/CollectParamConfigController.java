package com.fangcloud.noah.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fangcloud.noah.common.api.util.Result;
import com.fangcloud.noah.dao.entity.CollectParamConfigEntity;
import com.fangcloud.noah.dao.entity.CollectParamDetailEntity;
import com.fangcloud.noah.dao.entity.EventType;
import com.fangcloud.noah.dao.enums.ApplicationType;
import com.fangcloud.noah.service.common.PageObject;
import com.fangcloud.noah.service.service.CollectParamConfigService;
import com.fangcloud.noah.service.service.CollectParamDetailService;
import com.fangcloud.noah.service.service.EventTypeService;

/**
 * Created by chenke on 16-5-6.
 */
@Controller
@RequestMapping("collectParamConfig")
public class CollectParamConfigController {

    private final Logger logger = LoggerFactory.getLogger(CollectParamConfigController.class);


    @Autowired
    private CollectParamConfigService collectParamConfigService;

    @Autowired
    private CollectParamDetailService collectParamDetailService;

    @Autowired
    private EventTypeService eventTypeService;


    @RequestMapping("query")
    public String query(Integer pageNum, String applicationName, HttpServletRequest request) {

        PageObject<CollectParamConfigEntity> pageObj = collectParamConfigService.queryCollectParamConfigList(pageNum, applicationName);

        ApplicationType[] applicationTypes = ApplicationType.values();
        List<EventType> eventTypes = eventTypeService.queryAllEventTypes();
        request.setAttribute("pageObj", pageObj);
        request.setAttribute("applicationTypes", applicationTypes);
        request.setAttribute("eventTypes", eventTypes);
        request.setAttribute("applicationName",applicationName);
        return "collect/list";
    }


    @RequestMapping("detail")
    public String detail(Integer configId, HttpServletRequest request) {

        List<CollectParamDetailEntity> detailList = collectParamDetailService.queryCollectParamDetailList(configId);

        request.setAttribute("configId", configId);
        request.setAttribute("detailList", detailList);
        return "collect/detail";
    }


    @RequestMapping("add")
    @ResponseBody
    public Result addCollectParamConfig(@Valid CollectParamConfigEntity configEntity,BindingResult bindingResult) {

        if(bindingResult.hasErrors()){

            List<ObjectError> errorList = bindingResult.getAllErrors();

            StringBuilder error= new StringBuilder("参数错误");
            for(ObjectError objectError:errorList){
                error.append(","+objectError.getDefaultMessage());
                return new Result(false).setApiMessage(error.toString());
            }
        }

        collectParamConfigService.addCollectParamConfig(configEntity);

        return new Result();
    }


    @RequestMapping("delete")
    @ResponseBody
    public Result deleteCollectParamConfig(Integer id) {


        List<CollectParamDetailEntity> entityList = collectParamConfigService.queryCollectDetailByConfigId(id);

        if(CollectionUtils.isNotEmpty(entityList)){
            return new Result(false).setApiMessage("配置下面有参数数据，不能删除!");
        }

        collectParamConfigService.deleteCollectParamConfig(id);

        return new Result();
    }



    @RequestMapping("cacheToRedis")
    public void cacheToRedis() {

        logger.info("CollectParamConfigController cacheToRedis starting...");

        try {
            collectParamConfigService.cacheConfigToRedis();
        } catch (Exception e) {
            logger.error("CollectParamConfigController cacheToRedis异常：", e);

        }
        logger.info("CollectParamConfigController cacheToRedis end...");
    }
}
