package io.quarkcloud.quarkadmin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.quarkcloud.quarkadmin.annotation.AdminLayoutRender;
import io.quarkcloud.quarkcore.service.Context;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AdminLayoutController {
    
    @RequestMapping("/api/admin/layout/{resource}/index")
    @ResponseBody
    @AdminLayoutRender
    public Object index(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }
}
