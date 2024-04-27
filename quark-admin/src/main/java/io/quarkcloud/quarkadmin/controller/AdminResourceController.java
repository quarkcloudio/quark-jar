package io.quarkcloud.quarkadmin.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminResourceController {

    @RequestMapping("/api/admin/{resource}/index")
    @ResponseBody
    public String index(@PathVariable("resource") String resource) {
        return resource;
    }
}
