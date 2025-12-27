package io.quarkcloud.quarkadmin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.quarkcloud.quarkadmin.annotation.AdminAuthCaptcha;
import io.quarkcloud.quarkadmin.annotation.AdminAuthCaptchaId;
import io.quarkcloud.quarkadmin.annotation.AdminAuthLogin;
import io.quarkcloud.quarkadmin.annotation.AdminAuthLoginRender;
import io.quarkcloud.quarkadmin.annotation.AdminAuthLogout;
import io.quarkcloud.quarkcore.service.Context;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AdminAuthController {
    
    @RequestMapping(value = "/api/admin/auth/{resource}/index", method = {RequestMethod.GET})
    @ResponseBody
    @AdminAuthLoginRender
    public Object index(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping(value = "/api/admin/auth/{resource}/login", method = {RequestMethod.POST})
    @ResponseBody
    @AdminAuthLogin
    public Object handle(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping(value = "/api/admin/auth/{resource}/captchaId", method = {RequestMethod.GET})
    @ResponseBody
    @AdminAuthCaptchaId
    public Object captchaId(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping(value = "/api/admin/auth/{resource}/captcha/{id}", method = {RequestMethod.GET})
    @ResponseBody
    @AdminAuthCaptcha
    public Object captcha(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping(value = "/api/admin/auth/{resource}/logout", method = {RequestMethod.GET})
    @ResponseBody
    @AdminAuthLogout
    public Object logout(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }
}
