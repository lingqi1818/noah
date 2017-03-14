package com.fangcloud.noah.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by chenke on 16-4-14.
 */
@Controller
@RequestMapping("login")
public class LoginController {

    @RequestMapping("check")
    public void check(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
            if (username.equals("risk") && password.equals("risk#$1234")) {
                Cookie cookie = new Cookie("login_account", "risk");
                cookie.setMaxAge(3600 * 8);
                cookie.setPath("/");
                response.addCookie(cookie);
                response.sendRedirect(request.getContextPath() + "/");
            } else {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }

        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
}
