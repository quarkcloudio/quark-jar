package io.quarkcloud.quarkadmin.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import io.quarkcloud.quarkadmin.component.message.Message;

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
    public Object imageHandle(@RequestParam("file") MultipartFile file) throws IOException, NoSuchAlgorithmException {

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
        String fileExt = FileUtil.extName(fileName);
        String newFileName = IdUtil.simpleUUID() + "." + fileExt;
        File uploadFile = FileUtil.file(dir, newFileName);

        // 将文件写入服务器指定目录
        FileUtil.writeBytes(fileBytes, uploadFile);
 
        // 文件保存路径
        String filePath = uploadDir + newFileName;

        // 文件类型
        String fileType = FileTypeUtil.getType(uploadFile);

        // 文件大小
        Long fileSize = uploadFile.length();
        
        // 文件hash
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(fileBytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        String fileHash = sb.toString();

        // 读取图片
        BufferedImage image = ImageIO.read(uploadFile);
        
        // 获取图片宽度和高度
        int width = image.getWidth();
        int height = image.getHeight();

        // 返回上传成功的消息
        return Message.success("上传成功", "", Map.of(
            "id", "",
            "contentType", fileType,
            "ext", fileExt,
            "hash", fileHash,
            "height", height,
            "width", width,
            "name", fileName,
            "path", filePath,
            "size", fileSize,
            "url", ""
        ));
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
