package io.quarkcloud.quarkstarter.controllers;

import io.quarkcloud.quarkadmin.service.resource.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        Resource resource = new Resource();
        resource.index();

        return "Hello World!";
    }
}
