package io.quarkcloud.quarkadmin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AdminUploadController {
    
    @RequestMapping("/api/admin/upload/image/handle")
    @ResponseBody
    public Object image(@RequestParam("file") MultipartFile file) {
        return file.getOriginalFilename();
    }

    @RequestMapping("/api/admin/upload/file/handle")
    @ResponseBody
    public Object file(@RequestParam("file") MultipartFile file) {
        return file.getOriginalFilename();
    }
}
