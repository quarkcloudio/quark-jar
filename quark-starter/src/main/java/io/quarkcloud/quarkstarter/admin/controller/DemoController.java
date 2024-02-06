package io.quarkcloud.quarkstarter.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    @RequestMapping("/api/admin/demo/index")
    @ResponseBody
    public String index() {
        return "admin data";
    }
}
