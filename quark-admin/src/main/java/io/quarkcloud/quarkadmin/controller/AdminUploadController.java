package io.quarkcloud.quarkadmin.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

@RestController
public class AdminUploadController {

    @RequestMapping("/api/admin/upload/image/getList")
    @ResponseBody
    public Object getImageList(@RequestParam("file") MultipartFile file) {
        return "";
    }

    @RequestMapping("/api/admin/upload/image/delete")
    @ResponseBody
    public Object imageDelete(@RequestParam("file") MultipartFile file) {
        return "";
    }

    @RequestMapping("/api/admin/upload/image/crop")
    @ResponseBody
    public Object imageCrop(@RequestParam("file") MultipartFile file) {
        return "";
    }

    @RequestMapping("/api/admin/upload/image/handle")
    @ResponseBody
    public Object imageHandle(@RequestParam("file") MultipartFile file) throws IOException {

        byte[] fileBytes = file.getBytes();
        String fileName = file.getOriginalFilename();

        // 文件不存在或文件名为空
        if (fileBytes == null || StrUtil.isBlank(fileName)) {
            throw new RuntimeException("文件不存在或文件名为空");
        }
 
        // 获取当前日期
        LocalDate now = LocalDate.now();

        // 格式化日期
        String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 指定上传文件夹
        String uploadDir = "public/storage/images/"+formattedDate+"/";
        File dir = FileUtil.file(uploadDir);
        if (!FileUtil.exist(dir)) {
            FileUtil.mkdir(dir);
        }
 
        // 生成新的文件名，避免文件名冲突
        String newFileName = IdUtil.simpleUUID() + "." +FileUtil.extName(fileName);
        File uploadFile = FileUtil.file(dir, newFileName);

        // 将文件写入服务器指定目录
        FileUtil.writeBytes(fileBytes, uploadFile);
 
        // 返回上传文件的路径
        String filePath = uploadFile.getAbsolutePath();
        
        return filePath;
    }

    @RequestMapping("/api/admin/upload/image/base64Handle")
    @ResponseBody
    public Object imageBase64Handle(@RequestParam("file") MultipartFile file) throws IOException {
        return "";
    }

    @RequestMapping("/api/admin/upload/file/handle")
    @ResponseBody
    public Object fileHandle(@RequestParam("file") MultipartFile file) throws IOException {
        byte[] fileBytes = file.getBytes();
        String fileName = file.getOriginalFilename();

        // 文件不存在或文件名为空
        if (fileBytes == null || StrUtil.isBlank(fileName)) {
            throw new RuntimeException("文件不存在或文件名为空");
        }
 
        // 获取当前日期
        LocalDate now = LocalDate.now();

        // 格式化日期
        String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 指定上传文件夹
        String uploadDir = "public/storage/files/"+formattedDate+"/";
        File dir = FileUtil.file(uploadDir);
        if (!FileUtil.exist(dir)) {
            FileUtil.mkdir(dir);
        }
 
        // 生成新的文件名，避免文件名冲突
        String newFileName = IdUtil.simpleUUID() + "." +FileUtil.extName(fileName);
        File uploadFile = FileUtil.file(dir, newFileName);

        // 将文件写入服务器指定目录
        FileUtil.writeBytes(fileBytes, uploadFile);
 
        // 返回上传文件的路径
        String filePath = uploadFile.getAbsolutePath();

        return filePath;
    }
}
