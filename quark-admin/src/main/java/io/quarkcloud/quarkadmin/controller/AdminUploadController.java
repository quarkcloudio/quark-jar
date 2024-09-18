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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import io.quarkcloud.quarkadmin.component.message.Message;
import io.quarkcloud.quarkadmin.entity.PictureEntity;
import io.quarkcloud.quarkadmin.entity.FileEntity;
import io.quarkcloud.quarkadmin.service.PictureService;
import io.quarkcloud.quarkcore.service.Env;
import io.quarkcloud.quarkadmin.service.FileService;

@RestController
public class AdminUploadController {
    
    // 注入图片服务
    @Autowired
    private PictureService pictureService;

    // 注入文件服务
    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/api/admin/upload/image/getList", method = {RequestMethod.GET})
    @ResponseBody
    public Object getImageList(@RequestParam("file") MultipartFile file) {
        return "";
    }

    @RequestMapping(value = "/api/admin/upload/image/delete", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object imageDelete(@RequestParam("file") MultipartFile file) {
        return "";
    }

    @RequestMapping(value = "/api/admin/upload/image/crop", method = {RequestMethod.POST})
    @ResponseBody
    public Object imageCrop(@RequestParam("file") MultipartFile file) {
        return "";
    }

    @RequestMapping(value = "/api/admin/upload/image/handle", method = {RequestMethod.POST})
    @ResponseBody
    public Object imageHandle(@RequestParam("file") MultipartFile file, @RequestHeader(value = "Authorization", defaultValue = "") String authorization) throws IOException, NoSuchAlgorithmException {
        byte[] fileBytes = file.getBytes();
        String fileName = file.getOriginalFilename();

        // 文件不存在或文件名为空
        if (fileBytes == null || StrUtil.isBlank(fileName)) {
            throw new RuntimeException("文件不存在或文件名为空");
        }

        if (authorization == null || authorization.isEmpty() || !authorization.startsWith("Bearer ")) {
            throw new RuntimeException("参数错误");
        }
        String token = authorization.substring("Bearer ".length());
        String appKey = Env.getProperty("app.key");
        if (!JWT.of(token).setKey(appKey.getBytes()).verify()) {
            throw new RuntimeException("无权操作");
        }
        try {
            JWTValidator.of(token).validateDate(DateUtil.date());
        } catch (Exception e) {
            throw new RuntimeException("认证已过期");
        }
        final JWT jwt = JWTUtil.parseToken(token);
 
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

        PictureEntity pictureEntity = new PictureEntity();
        pictureEntity.setObjType("ADMINID");
        pictureEntity.setObjId((Long) jwt.getPayload("id"));
        pictureEntity.setName(fileName);
        pictureEntity.setPath(filePath);
        pictureEntity.setSize(fileSize);
        pictureEntity.setHash(fileHash);
        pictureEntity.setExt(fileExt);
        pictureEntity.setHeight(height);
        pictureEntity.setWidth(width);

        // 获取文件路径
        String fileUrl = pictureService.getPath(filePath);
        pictureEntity.setUrl(fileUrl);

        Long fileId = pictureService.saveGetId(pictureEntity);

        // 返回上传成功的消息
        return Message.success("上传成功", "", Map.of(
            "id", fileId,
            "contentType", fileType,
            "ext", fileExt,
            "hash", fileHash,
            "height", height,
            "width", width,
            "name", fileName,
            "path", filePath,
            "size", fileSize,
            "url", fileUrl
        ));
    }

    @RequestMapping(value = "/api/admin/upload/image/base64Handle", method = {RequestMethod.POST})
    @ResponseBody
    public Object imageBase64Handle(@RequestParam("file") MultipartFile file) throws IOException {
        return "";
    }

    @RequestMapping(value = "/api/admin/upload/file/handle", method = {RequestMethod.POST})
    @ResponseBody
    public Object fileHandle(@RequestParam("file") MultipartFile file, @RequestHeader(value = "Authorization", defaultValue = "") String authorization) throws IOException, NoSuchAlgorithmException {
        byte[] fileBytes = file.getBytes();
        String fileName = file.getOriginalFilename();

        // 文件不存在或文件名为空
        if (fileBytes == null || StrUtil.isBlank(fileName)) {
            throw new RuntimeException("文件不存在或文件名为空");
        }

        if (authorization == null || authorization.isEmpty()|| !authorization.startsWith("Bearer ")) {
            throw new RuntimeException("参数错误");
        }
        String token = authorization.substring("Bearer ".length());
        String appKey = Env.getProperty("app.key");
        if (!JWT.of(token).setKey(appKey.getBytes()).verify()) {
            throw new RuntimeException("无权操作");
        }
        try {
            JWTValidator.of(token).validateDate(DateUtil.date());
        } catch (Exception e) {
            throw new RuntimeException("认证已过期");
        }
        final JWT jwt = JWTUtil.parseToken(token);
 
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

        FileEntity fileEntity = new FileEntity();
        fileEntity.setObjType("ADMINID");
        fileEntity.setObjId((Long) jwt.getPayload("id"));
        fileEntity.setName(fileName);
        fileEntity.setPath(filePath);
        fileEntity.setSize(fileSize);
        fileEntity.setHash(fileHash);
        fileEntity.setExt(fileExt);

        // 获取文件路径
        String fileUrl = fileService.getPath(filePath);
        fileEntity.setUrl(fileUrl);

        Long fileId = fileService.saveGetId(fileEntity);

        // 返回上传成功的消息
        return Message.success("上传成功", "", Map.of(
            "id", fileId,
            "contentType", fileType,
            "ext", fileExt,
            "hash", fileHash,
            "name", fileName,
            "path", filePath,
            "size", fileSize,
            "url", fileUrl
        ));
    }
}
