package com.fangcloud.noah.web.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fangcloud.noah.common.api.util.Result;
import com.fangcloud.noah.redis.RedisService;

/**
 * Created by chenke on 16-5-14.
 */
@Controller
@RequestMapping("switch")
public class SwitchController {


    private final static String SWITCH_VERIFY_CODE = "switch_verfiy_code";

    private static final String SWITCH_RISK = "switch_risk";

    @Autowired
    private RedisService redisService;

    @RequestMapping("query")
    public String detail(HttpServletRequest request) {

        String verifyCodeSwitchStatus = redisService.get(SWITCH_VERIFY_CODE);

        String riskSwitchStatus = redisService.get(SWITCH_RISK);

        if (StringUtils.isNotBlank(verifyCodeSwitchStatus) && verifyCodeSwitchStatus.equals("off")) {
            request.setAttribute(SWITCH_VERIFY_CODE, false);
        } else {
            request.setAttribute(SWITCH_VERIFY_CODE, true);
        }

        if (StringUtils.isNotBlank(riskSwitchStatus) && riskSwitchStatus.equals("off")) {
            request.setAttribute(SWITCH_RISK, false);
        } else {
            request.setAttribute(SWITCH_RISK, true);
        }

        return "switch/switch";
    }


    @ResponseBody
    @RequestMapping("switchState")
    public Result switchState(String switchName, String state, HttpServletRequest request) {

        if (StringUtils.isBlank(switchName) || StringUtils.isBlank(state)) {
            return new Result(false);
        }

        if (!switchName.equals(SWITCH_VERIFY_CODE) && !switchName.equals(SWITCH_RISK)) {
            return new Result(false);
        }

        if (!state.equals("on") && !state.equals("off")) {
            return new Result(false);
        }

        redisService.set(switchName, state);

        return new Result(true);

    }
}
