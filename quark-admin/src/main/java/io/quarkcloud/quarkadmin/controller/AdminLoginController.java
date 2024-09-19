package io.quarkcloud.quarkadmin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.quarkcloud.quarkadmin.annotation.AdminLoginCaptcha;
import io.quarkcloud.quarkadmin.annotation.AdminLoginCaptchaId;
import io.quarkcloud.quarkadmin.annotation.AdminLoginHandle;
import io.quarkcloud.quarkadmin.annotation.AdminLoginRender;
import io.quarkcloud.quarkadmin.annotation.AdminLoginLogout;
import io.quarkcloud.quarkcore.service.Context;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AdminLoginController {
    
    @RequestMapping(value = "/api/admin/login/{resource}/index", method = {RequestMethod.GET})
    @ResponseBody
    @AdminLoginRender
    public Object index(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping(value = "/api/admin/login/{resource}/handle", method = {RequestMethod.POST})
    @ResponseBody
    @AdminLoginHandle
    public Object handle(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping(value = "/api/admin/login/{resource}/captchaId", method = {RequestMethod.GET})
    @ResponseBody
    @AdminLoginCaptchaId
    public Object captchaId(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping(value = "/api/admin/login/{resource}/captcha/{id}", method = {RequestMethod.GET})
    @ResponseBody
    @AdminLoginCaptcha
    public Object captcha(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping(value = "/api/admin/logout/{resource}/handle", method = {RequestMethod.GET})
    @ResponseBody
    @AdminLoginLogout
    public Object logout(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }
}
