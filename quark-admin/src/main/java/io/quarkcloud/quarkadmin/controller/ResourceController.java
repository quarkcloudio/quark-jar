package io.quarkcloud.quarkadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ResourceController {

    @RequestMapping("/api/admin/{resource}/index")
    @ResponseBody
    public String index() {
        return "ResourceController";
    }
}
