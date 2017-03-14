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
import com.fangcloud.noah.dao.entity.RuleEntity;
import com.fangcloud.noah.service.service.RuleService;

/**
 * Created by chenke on 16-8-20.
 */
@Controller
@RequestMapping("rule")
public class RuleController {

    private final Logger logger = LoggerFactory.getLogger(RuleController.class);


    @Autowired
    private RuleService ruleService;

    @ResponseBody
    @RequestMapping("add")
    public Result add(@Valid @RequestBody RuleEntity ruleEntity, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            List<ObjectError> errorList = bindingResult.getAllErrors();

            StringBuilder error = new StringBuilder("参数错误");
            for (ObjectError objectError : errorList) {
                error.append("," + objectError.getDefaultMessage());
                return new Result(false).setApiMessage(error.toString());
            }
        }
        ruleService.addRule(ruleEntity);
        return new Result();
    }


    @RequestMapping("showUpdate")
    @ResponseBody
    public Result showUpdate(Integer ruleId) {
        RuleEntity ruleEntity = ruleService.queryRuleById(ruleId);
        if (ruleEntity != null) {
            return new Result(true).setData(ruleEntity);
        } else {
            return new Result(false).setApiMessage("不存在要修改的规则!");
        }
    }


    @RequestMapping("update")
    @ResponseBody
    public Result updateRule(@Valid @RequestBody RuleEntity rule, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            List<ObjectError> errorList = bindingResult.getAllErrors();

            StringBuilder error = new StringBuilder("参数错误");
            for (ObjectError objectError : errorList) {
                error.append("," + objectError.getDefaultMessage());
                return new Result(false).setApiMessage(error.toString());
            }
        }

        try {
            ruleService.updateRule(rule);
            return new Result();
        } catch (Exception e) {
            logger.error("修改规则异常：", e);
            return new Result(false).setApiMessage("修改规则异常!");
        }


    }


    @RequestMapping("enable")
    @ResponseBody
    public Result enableRule(Integer ruleId) {

        RuleEntity ruleEntity = ruleService.queryRuleById(ruleId);

        if (ruleEntity == null) {
            return new Result(false).setApiMessage("没找到要修改的规则!");
        }

        if(ruleEntity.getStatus().equals(RuleEntity.RULE_STATUS_ENABLE)){
            ruleEntity.setStatus(RuleEntity.RULE_STATUS_Disable);
        }else {
            ruleEntity.setStatus(RuleEntity.RULE_STATUS_ENABLE);
        }

        try {


            ruleService.enableRule(ruleEntity);
            return new Result();
        } catch (Exception e) {
            logger.error("修改规则异常：", e);
            return new Result(false).setApiMessage("修改规则异常!");
        }


    }

    @RequestMapping("delete")
    @ResponseBody
    public Result deletePolicy(Integer ruleId) {

        RuleEntity ruleEntity = ruleService.queryRuleById(ruleId);

        if (ruleEntity == null) {
            return new Result(false).setApiMessage("没找到要删除的规则!");
        }

        ruleService.deleteRule(ruleEntity);

        return new Result();
    }
}
