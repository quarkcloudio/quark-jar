package io.quarkcloud.quarkadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import io.quarkcloud.quarkadmin.annotation.AdminLogin;

@Controller
public class LoginController {

    @RequestMapping("/api/admin/login/{resource}/index")
    @ResponseBody
    @AdminLogin
    public Object index(@PathVariable("resource") String resource) {
        return resource;
    }
}
