package io.quarkcloud.quarkstarter.controllers;

import io.quarkcloud.quarkadmin.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @RequestMapping("/")
    @ResponseBody
    public String index(User user) {
        String username = user.getUsername();

        return username;
    }
}
