package io.quarkcloud.quarkadmin.engine;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class ResourceController {
    @RequestMapping("/{resource}/index")
    @ResponseBody
    public String index() {
        return "Hello Resource!";
    }
}
