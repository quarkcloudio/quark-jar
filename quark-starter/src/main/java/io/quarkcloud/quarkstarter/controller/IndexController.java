package io.quarkcloud.quarkstarter.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "Hello World!";
    }

    @GetMapping("/routes")
    @ResponseBody
    public Map<String, String> getAllRoutes() {
        RequestMappingHandlerMapping requestMappingHandlerMapping = new RequestMappingHandlerMapping();
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();

        Map<String, String> routes = new HashMap<>();
        handlerMethods.forEach((info, method) -> routes.put(info.getPatternsCondition().toString(), method.toString()));

        return routes;
    }
}
