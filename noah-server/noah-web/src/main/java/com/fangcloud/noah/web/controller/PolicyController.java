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
import com.fangcloud.noah.dao.entity.EventType;
import com.fangcloud.noah.dao.entity.PolicyEntity;
import com.fangcloud.noah.dao.entity.RuleEntity;
import com.fangcloud.noah.service.common.PageObject;
import com.fangcloud.noah.service.service.EventTypeService;
import com.fangcloud.noah.service.service.PolicyService;
import com.fangcloud.noah.service.service.RuleService;

/**
 * Created by chenke on 16-5-6.
 */
@Controller
@RequestMapping("policy")
public class PolicyController {

    private final Logger     logger = LoggerFactory.getLogger(PolicyController.class);

    @Autowired
    private PolicyService    policyService;

    @Autowired
    private RuleService      ruleService;

    @Autowired
    private EventTypeService eventTypeService;

    @RequestMapping("query")
    public String query(Integer pageNum, String deviceType, HttpServletRequest request) {

        PageObject<PolicyEntity> pageObj = policyService.queryPolicyList(pageNum, deviceType);

        List<EventType> eventTypes = eventTypeService.queryAllEventTypes();

        request.setAttribute("pageObj", pageObj);
        request.setAttribute("deviceType", deviceType);
        request.setAttribute("eventTypes", eventTypes);
        return "policy/list";
    }

    @RequestMapping("detail")
    public String detail(Integer policyId, HttpServletRequest request) {

        List<RuleEntity> ruleList = ruleService.queryRuleByPolicyId(policyId);
        request.setAttribute("policyId", policyId);
        request.setAttribute("ruleList", ruleList);
        return "policy/detail";
    }

    @RequestMapping("add")
    @ResponseBody
    public Result addPolicy(@Valid PolicyEntity policy, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            List<ObjectError> errorList = bindingResult.getAllErrors();

            StringBuilder error = new StringBuilder("参数错误");
            for (ObjectError objectError : errorList) {
                error.append("," + objectError.getDefaultMessage());
                return new Result(false).setApiMessage(error.toString());
            }
        }

        policyService.addPolicy(policy);
        return new Result();
    }

    @RequestMapping("showUpdate")
    @ResponseBody
    public Result showUpdate(Integer policyId) {
        PolicyEntity policyEntity = policyService.queryPolicyById(policyId);
        if (policyEntity != null) {
            return new Result(true).setData(policyEntity);
        } else {
            return new Result(false).setApiMessage("不存在要修改的策略!");
        }
    }

    @RequestMapping("update")
    @ResponseBody
    public Result updatePolicy(@Valid PolicyEntity policy, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            List<ObjectError> errorList = bindingResult.getAllErrors();

            StringBuilder error = new StringBuilder("参数错误");
            for (ObjectError objectError : errorList) {
                error.append("," + objectError.getDefaultMessage());
                return new Result(false).setApiMessage(error.toString());
            }
        }

        try {
            policyService.updatePolicy(policy);
            return new Result();
        } catch (Exception e) {
            logger.error("修改策略异常：", e);
            return new Result(false).setApiMessage("修改策略异常!");
        }

    }

    @RequestMapping("delete")
    @ResponseBody
    public Result deletePolicy(Integer policyId) {

        PolicyEntity policyEntity = policyService.queryPolicyById(policyId);

        if (policyEntity == null) {
            return new Result(false).setApiMessage("不存在要删除的策略!");
        }

        List<RuleEntity> ruleEntityList = ruleService.queryRuleByPolicyId(policyId);

        if (CollectionUtils.isNotEmpty(ruleEntityList)) {
            return new Result(false).setApiMessage("策略下面存在规则，不能删除!");
        }

        policyService.deletePolicy(policyEntity);

        return new Result();
    }
}
