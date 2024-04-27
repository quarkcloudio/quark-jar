package io.quarkcloud.quarkadmin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminIndexController {

    @RequestMapping("/admin")
    @ResponseBody
    public Object index() {
        return "forward:admin/index.html";
    }
}
