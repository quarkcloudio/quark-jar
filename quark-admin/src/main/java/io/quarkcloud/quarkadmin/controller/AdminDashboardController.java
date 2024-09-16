package io.quarkcloud.quarkadmin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.quarkcloud.quarkadmin.annotation.AdminDashboardRender;
import io.quarkcloud.quarkcore.service.Context;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AdminDashboardController {

    @GetMapping("/api/admin/dashboard/{resource}/index")
    @ResponseBody
    @AdminDashboardRender
    public Object index(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }
}
