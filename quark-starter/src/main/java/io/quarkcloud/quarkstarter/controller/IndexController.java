package io.quarkcloud.quarkstarter.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Hello World!");
        return "index";
    }
}
