package io.quarkcloud.quarkadmin.engine;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import io.quarkcloud.quarkengine.loader.Loader;

@RestController
@RequestMapping("/api/admin")
public class ResourceController {
    @RequestMapping("/{resource}/index")
    @ResponseBody
    public Object index(@PathVariable("resource") String resource) {
        Object result = new Loader("io.quarkcloud.quarkadmin.service.resource", resource).callMethod("getTitle");

        return result;
    }
}
