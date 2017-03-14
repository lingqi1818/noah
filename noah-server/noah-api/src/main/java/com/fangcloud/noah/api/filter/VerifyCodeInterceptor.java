package com.fangcloud.noah.api.filter;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fangcloud.noah.api.api.service.RiskQueryService;
import com.fangcloud.noah.api.util.CookieUtil;
import com.fangcloud.noah.api.util.IPUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 校验是否需要弹出验证码的拦截器
 *
 * @author chenke
 * @date 2016年4月28日 上午11:05:16
 */
public class VerifyCodeInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = Logger.getLogger(VerifyCodeInterceptor.class);
    private RiskQueryService riskQueryService;
    private int riskLevel;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object target) {
        String ip = IPUtil.getIpFromHttpServletRequest(request);
        String beaconId = CookieUtil.getBeaconId(request);
        String deviceId = request.getParameter("y_order_id");
        if (riskQueryService.showVerifyCode(deviceId, ip, beaconId, riskLevel)) {
            request.setAttribute("show_verify_code", true);
        }

        String mobile = request.getParameter("mobile");

        if (riskQueryService.showVerifyCode(deviceId, ip, beaconId, riskLevel)) {
            request.setAttribute("show_verify_code_login", true);
        }
        return true;
    }


    public void setRiskQueryService(RiskQueryService riskQueryService) {
        this.riskQueryService = riskQueryService;
    }

    public void setRiskLevel(int riskLevel) {
        this.riskLevel = riskLevel;
    }
}
