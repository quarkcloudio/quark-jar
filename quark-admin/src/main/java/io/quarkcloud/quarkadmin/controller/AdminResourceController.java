package io.quarkcloud.quarkadmin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.quarkcloud.quarkadmin.annotation.AdminResourceIndexRender;
import io.quarkcloud.quarkcore.service.Context;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AdminResourceController {

    @RequestMapping("/api/admin/{resource}/index")
    @ResponseBody
    @AdminResourceIndexRender
    public Object index(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }
}
