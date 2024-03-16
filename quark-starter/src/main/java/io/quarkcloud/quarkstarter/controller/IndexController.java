package io.quarkcloud.quarkstarter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import io.quarkcloud.quarkadmin.annotation.Resource;

@Controller
public class IndexController {

    @RequestMapping("/")
    @ResponseBody
    @Resource("abcd")
    public String index() {
        return "Hello World!";
    }
}
