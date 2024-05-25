package io.quarkcloud.quarkadmin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import io.quarkcloud.quarkadmin.annotation.AdminLoginRender;
import io.quarkcloud.quarkcore.service.Context;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AdminDashboardController {
    @RequestMapping("/api/admin/dashboard/{resource}/index")
    @ResponseBody
    public Object index(HttpServletRequest request, HttpServletResponse response) {
        return "abcd";
    }
}
