package io.quarkcloud.quarkstarter.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.quarkcloud.quarkbase.engine.BaseController;

@Controller
public class DemoController extends BaseController<String> {

    @RequestMapping("/api/admin/demo/index")
    @ResponseBody
    public String index() {
        return this.jsonOk("demo controller");
    }
}
