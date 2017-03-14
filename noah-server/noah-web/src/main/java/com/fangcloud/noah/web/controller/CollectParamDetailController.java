package com.fangcloud.noah.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fangcloud.noah.common.api.util.Result;
import com.fangcloud.noah.dao.entity.CollectParamDetailEntity;
import com.fangcloud.noah.service.service.CollectParamDetailService;

/**
 * Created by chenke on 16-9-27.
 */
@Controller
@RequestMapping("collectParamDetail")
public class CollectParamDetailController {

    private final Logger logger = LoggerFactory.getLogger(CollectParamDetailController.class);


    @Autowired
    private CollectParamDetailService collectParamDetailService;

    @ResponseBody
    @RequestMapping("add")
    public Result add(@Valid @RequestBody CollectParamDetailEntity entity, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            List<ObjectError> errorList = bindingResult.getAllErrors();

            StringBuilder error = new StringBuilder("参数错误");
            for (ObjectError objectError : errorList) {
                error.append("," + objectError.getDefaultMessage());
                return new Result(false).setApiMessage(error.toString());
            }
        }
        collectParamDetailService.addCollectParamDetail(entity);
        return new Result();
    }


    @RequestMapping("delete")
    @ResponseBody
    public Result deletePolicy(Integer id) {

        CollectParamDetailEntity entity = collectParamDetailService.queryCollectParamDetailById(id);

        if (entity == null) {
            return new Result(false).setApiMessage("没找到要删除的记录!");
        }

        collectParamDetailService.deleteById(id);

        return new Result();
    }
}
