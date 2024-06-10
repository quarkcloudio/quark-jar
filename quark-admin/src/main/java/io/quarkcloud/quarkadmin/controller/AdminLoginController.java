package io.quarkcloud.quarkadmin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.quarkcloud.quarkadmin.annotation.AdminLoginCaptcha;
import io.quarkcloud.quarkadmin.annotation.AdminLoginCaptchaId;
import io.quarkcloud.quarkadmin.annotation.AdminLoginHandle;
import io.quarkcloud.quarkadmin.annotation.AdminLoginRender;
import io.quarkcloud.quarkcore.service.Context;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AdminLoginController {
    
    @RequestMapping("/api/admin/login/{resource}/index")
    @ResponseBody
    @AdminLoginRender
    public Object index(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping("/api/admin/login/{resource}/handle")
    @ResponseBody
    @AdminLoginHandle
    public Object handle(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping("/api/admin/login/{resource}/captchaId")
    @ResponseBody
    @AdminLoginCaptchaId
    public Object captchaId(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping("/api/admin/login/{resource}/captcha/{id}")
    @ResponseBody
    @AdminLoginCaptcha
    public Object captcha(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }
}
