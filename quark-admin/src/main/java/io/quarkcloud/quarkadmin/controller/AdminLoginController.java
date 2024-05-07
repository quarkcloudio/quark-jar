package io.quarkcloud.quarkadmin.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.quarkcloud.quarkadmin.annotation.AdminLoginHandle;
import io.quarkcloud.quarkadmin.annotation.AdminLoginRender;

@RestController
public class AdminLoginController {

    @RequestMapping("/api/admin/login/{resource}/index")
    @ResponseBody
    @AdminLoginRender
    public Object index(@PathVariable("resource") String resource) {
        return resource;
    }

    @RequestMapping("/api/admin/login/{resource}/handle")
    @ResponseBody
    @AdminLoginHandle
    public Object handle(@PathVariable("resource") String resource) {
        return resource;
    }
}
