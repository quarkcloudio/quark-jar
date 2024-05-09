package io.quarkcloud.quarkadmin.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.quarkcloud.quarkadmin.annotation.AdminLoginCaptcha;
import io.quarkcloud.quarkadmin.annotation.AdminLoginCaptchaId;
import io.quarkcloud.quarkadmin.annotation.AdminLoginHandle;
import io.quarkcloud.quarkadmin.annotation.AdminLoginRender;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AdminLoginController {

    @RequestMapping("/api/admin/login/{resource}/index")
    @ResponseBody
    @AdminLoginRender
    public Object index(@PathVariable("resource") String resource, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("resource", resource);
        map.put("request", request);

        return map;
    }

    @RequestMapping("/api/admin/login/{resource}/handle")
    @ResponseBody
    @AdminLoginHandle
    public Object handle(@PathVariable("resource") String resource, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("resource", resource);
        map.put("request", request);

        return map;
    }

    @RequestMapping("/api/admin/login/{resource}/captchaId")
    @ResponseBody
    @AdminLoginCaptchaId
    public Object captchaId(@PathVariable("resource") String resource, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("resource", resource);
        map.put("request", request);

        return map;
    }

    @RequestMapping("/api/admin/login/{resource}/captcha/{id}")
    @ResponseBody
    @AdminLoginCaptcha
    public Object captcha(@PathVariable("resource") String resource, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("resource", resource);
        map.put("request", request);
        map.put("response", response);

        return map;
    }
}
