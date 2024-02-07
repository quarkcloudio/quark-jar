package io.quarkcloud.quarkstarter.admin.controller;

import io.quarkcloud.quarkengine.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController extends BaseController<String> {

    @RequestMapping("/api/admin/demo/index")
    @ResponseBody
    public String index() {
        return this.jsonOk("demo controller");
    }
}
