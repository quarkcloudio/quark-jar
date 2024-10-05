package io.quarkcloud.quarkadmin.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import io.quarkcloud.quarkadmin.component.message.Message;
import io.quarkcloud.quarkadmin.entity.PictureEntity;
import io.quarkcloud.quarkadmin.entity.FileEntity;
import io.quarkcloud.quarkadmin.entity.PictureCategoryEntity;
import io.quarkcloud.quarkadmin.service.PictureService;
import jakarta.servlet.http.HttpServletRequest;
import io.quarkcloud.quarkadmin.service.ConfigService;
import io.quarkcloud.quarkadmin.service.FileService;
import io.quarkcloud.quarkadmin.service.PictureCategoryService;

@RestController
public class AdminUploadController {
    
    // 注入图片服务
    @Autowired
    private PictureService pictureService;

    // 注入图片分类服务
    @Autowired
    private PictureCategoryService pictureCategoryService;

    // 注入文件服务
    @Autowired
    private FileService fileService;

    // 注入读取配置服务
    @Autowired
    private ConfigService configService;

    @Value("${app.key}")
    private String appKey;

    @Value("${upload.image.size}")
    private Long uploadImageSize;

    @Value("${upload.image.type}")
    private List<String> uploadImageType;

    @Value("${upload.image.save-path}")
    private String uploadImageSavePath;

    @Value("${upload.file.size}")
    private Long uploadFileSize;

    @Value("${upload.file.type}")
    private List<String> uploadFileType;

    @Value("${upload.file.save-path}")
    private String uploadFileSavePath;

    // 计算文件哈希值
    private String calculateHash(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] buffer = new byte[1024];
        int bytesRead;
        
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            digest.update(buffer, 0, bytesRead);
        }

        byte[] hashBytes = digest.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @RequestMapping(value = "/api/admin/upload/image/getList", method = {RequestMethod.GET})
    @ResponseBody
    public Object getImageList(
        @RequestParam(value = "page", defaultValue = "1") Integer page,
        @RequestParam(value = "pictureCategoryId", defaultValue = "") Integer categoryId,
        @RequestParam(value = "pictureSearchName", defaultValue = "") String name,
        @RequestParam(value = "pictureSearchDate", defaultValue = "") String[] date,
        @RequestHeader(value = "Authorization", defaultValue = "") String authToken) {
        
        // 检查token
        if (authToken.isEmpty() || !authToken.startsWith("Bearer ")) {
            return Message.error("token不能为空");
        }
        String token = authToken.substring("Bearer ".length());
        if (!JWT.of(token).setKey(appKey.getBytes()).verify()) {
            return Message.error("token验证失败");
        }

        // 验证token是否过期
        try {
            JWTValidator.of(token).validateDate(DateUtil.date());
        } catch (Exception e) {
            return Message.error(e.getMessage());
        }

        // 解析token
        final JWT jwt = JWTUtil.parseToken(token);
        if(!jwt.getPayload("guard_name").equals("admin")) {
            return Message.error("无权限");
        }

        // 获取列表
        IPage<PictureEntity> result = pictureService.getListBySearch("ADMIN", jwt.getPayload("id"), categoryId, name, date[0], date[1], page);

        // 获取分类
        List<PictureCategoryEntity> categorys = pictureCategoryService.getListByObj("ADMIN", jwt.getPayload("id"));

        // 返回数据
        return Message.success("获取成功","", Map.of(
            "pagination",Map.of("total", result.getTotal(), "pageSize", result.getSize(), "current", result.getCurrent(),"defaultCurrent",1),
            "list", result.getRecords(),
            "categorys", categorys
        ));
    }

    @RequestMapping(value = "/api/admin/upload/image/delete", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object imageDelete(@RequestParam("id") String id) {
        if (id.isEmpty()) {
            return Message.error("参数错误");
        }
        boolean result = pictureService.removeById(id);
        if (!result) {
            return Message.error("操作失败，请重试");
        }

        return Message.success("操作成功");
    }

    @RequestMapping(value = "/api/admin/upload/image/crop", method = {RequestMethod.POST})
    @ResponseBody
    public Object imageCrop(
        @RequestParam(value = "limitW", required = false) String limitW,
        @RequestParam(value = "limitH", required = false) String limitH,
        @RequestBody Map<String, Object> data,
        @RequestHeader(value = "Authorization", defaultValue = "") String authToken,
        HttpServletRequest request) {

        // 获取参数
        String id = (String) data.get("id");
        if (id == null) {
            return Message.error("参数错误！");
        }

        PictureEntity pictureInfo = pictureService.getById(Integer.parseInt(id));
        if (pictureInfo == null || pictureInfo.getId() == 0) {
            return Message.error("文件不存在");
        }

        // 检查文件是否为空
        if (data.get("file") == null) {
            return Message.error("请选择上传的文件");
        }

        String[] files = ((String) data.get("file")).split(",");
        if (files.length != 2) {
            return Message.error("文件格式错误");
        }

        byte[] fileData;
        try {
            fileData = Base64.getDecoder().decode(files[1]); // 解码Base64文件
        } catch (IllegalArgumentException e) {
            return Message.error(e.getMessage());
        }

        // 文件不存在或文件名为空
        if (fileData == null) {
            return Message.error("文件格式错误");
        }

        // 检查token
        if (authToken.isEmpty() || !authToken.startsWith("Bearer ")) {
            return Message.error("token不能为空");
        }
        String token = authToken.substring("Bearer ".length());
        if (!JWT.of(token).setKey(appKey.getBytes()).verify()) {
            return Message.error("token验证失败");
        }

        // 验证token是否过期
        try {
            JWTValidator.of(token).validateDate(DateUtil.date());
        } catch (Exception e) {
            return Message.error(e.getMessage());
        }

        // 解析token
        final JWT jwt = JWTUtil.parseToken(token);
        if(!jwt.getPayload("guard_name").equals("admin")) {
            return Message.error("无权限");
        }
    
        // 文件输入流
        InputStream fileInputStream = new ByteArrayInputStream(fileData);
        String fileExt = FileTypeUtil.getType(fileInputStream);
        Long fileSize = (long) fileData.length;
        String fileHash;
        try {
            fileHash = calculateHash(fileInputStream);
        } catch (NoSuchAlgorithmException | IOException e) {
            return Message.error(e.getMessage());
        }

        // 文件大小
        if (fileSize > uploadImageSize) {
            return Message.error("文件大小超出限制");
        }

        if (!uploadImageType.contains(fileExt)) {
            return Message.error("文件类型不允许");
        }

        // 读取图片
        BufferedImage image;
        try {
            image = ImageIO.read(fileInputStream);
        } catch (IOException e) {
            return Message.error(e.getMessage());
        }
        int fileWidth = image.getWidth();
        int fileHeight = image.getHeight();

        // 上传到OSS
        if (configService.getValue("OSS_OPEN").equals("1")) {
            String endpoint = (String) configService.getValue("OSS_ENDPOINT");
            String accessKeyId = (String) configService.getValue("OSS_ACCESS_KEY_ID");
            String accessKeySecret = (String) configService.getValue("OSS_ACCESS_KEY_SECRET");
            String bucketName = (String) configService.getValue("OSS_BUCKET");
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            
            // 上传文件
            try {
                ossClient.putObject(new PutObjectRequest(bucketName, pictureInfo.getPath(), fileInputStream));
            } catch (OSSException | ClientException e) {
                return Message.error(e.getMessage());
            }
            ossClient.shutdown();
        } else {
            // 上传到本地
            File dest = new File(pictureInfo.getPath());
            try {
                FileUtil.writeBytes(fileData, dest);
            } catch (IllegalStateException e) {
                return Message.error(e.getMessage());
            }
        }

        pictureInfo.setObjType("ADMIN");
        pictureInfo.setObjId(Long.parseLong(jwt.getPayload("id").toString()));
        pictureInfo.setSize(fileSize);
        pictureInfo.setHash(fileHash);
        pictureInfo.setWidth(fileWidth);
        pictureInfo.setHeight(fileHeight);
        boolean result = pictureService.updateById(pictureInfo);
        if (!result) {
            return Message.error("操作失败，请重试！");
        }

        // 返回上传成功的消息
        return Message.success("上传成功", "", Map.of(
            "id", pictureInfo.getId(),
            "contentType", fileExt,
            "ext", pictureInfo.getExt(),
            "hash", fileHash,
            "width", fileWidth,
            "height", fileHeight,
            "name", pictureInfo.getName(),
            "path", pictureInfo.getPath(),
            "size", fileSize,
            "url", pictureInfo.getUrl()
        ));
    }

    @RequestMapping(value = "/api/admin/upload/image/handle", method = {RequestMethod.POST})
    @ResponseBody
    public Object imageHandle(
        @RequestParam("file") MultipartFile file,
        @RequestParam(value = "limitW", required = false) String limitW,
        @RequestParam(value = "limitH", required = false) String limitH,
        @RequestHeader(value = "Authorization", defaultValue = "") String authToken) {
        
        // 检查文件是否为空
        if (file.isEmpty()) {
            return Message.error("请选择上传的文件");
        }

        // 检查token
        if (authToken.isEmpty() || !authToken.startsWith("Bearer ")) {
            return Message.error("token不能为空");
        }
        String token = authToken.substring("Bearer ".length());
        if (!JWT.of(token).setKey(appKey.getBytes()).verify()) {
            return Message.error("token验证失败");
        }

        // 验证token是否过期
        try {
            JWTValidator.of(token).validateDate(DateUtil.date());
        } catch (Exception e) {
            return Message.error(e.getMessage());
        }

        // 解析token
        final JWT jwt = JWTUtil.parseToken(token);
        if(!jwt.getPayload("guard_name").equals("admin")) {
            return Message.error("无权限");
        }

        // 文件保存路径
        String savePath = uploadImageSavePath + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "/";

        // 上传到本地
        if (!configService.getValue("OSS_OPEN").equals("1")) {
            // 目录不存在，则创建目录
            File dir = FileUtil.file(savePath);
            if (!FileUtil.exist(dir)) {
                FileUtil.mkdir(dir);
            }
        }
 
        // 生成新的文件名，避免文件名冲突
        String originalFilename = file.getOriginalFilename();
        String fileExt = FileUtil.extName(originalFilename);
        String fileName = IdUtil.simpleUUID() + "." + fileExt;
        String filePath = savePath + fileName;
        String fileType = file.getContentType();
        Long fileSize = file.getSize();
        String fileHash;
        try {
            fileHash = calculateHash(file.getInputStream());
        } catch (NoSuchAlgorithmException | IOException e) {
            return Message.error(e.getMessage());
        }

        // 文件大小
        if (fileSize > uploadImageSize) {
            return Message.error("文件大小超出限制");
        }

        if (!uploadImageType.contains(fileType) && !uploadImageType.contains(fileExt)) {
            return Message.error("文件类型不允许");
        }

        // 读取图片
        BufferedImage image;
        try {
            image = ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            return Message.error(e.getMessage());
        }
        int fileWidth = image.getWidth();
        int fileHeight = image.getHeight();

        PictureEntity pictureEntity = new PictureEntity();
        pictureEntity.setObjType("ADMIN");
        pictureEntity.setObjId(Long.parseLong(jwt.getPayload("id").toString()));
        pictureEntity.setName(originalFilename);
        pictureEntity.setPath(filePath);
        pictureEntity.setSize(fileSize);
        pictureEntity.setHash(fileHash);
        pictureEntity.setExt(fileExt);
        pictureEntity.setWidth(fileWidth);
        pictureEntity.setHeight(fileHeight);

        String fileUrl = "";
        // 上传到OSS
        if (configService.getValue("OSS_OPEN").equals("1")) {
            String endpoint = (String) configService.getValue("OSS_ENDPOINT");
            String accessKeyId = (String) configService.getValue("OSS_ACCESS_KEY_ID");
            String accessKeySecret = (String) configService.getValue("OSS_ACCESS_KEY_SECRET");
            String bucketName = (String) configService.getValue("OSS_BUCKET");
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            
            // 上传文件
            try {
                ossClient.putObject(new PutObjectRequest(bucketName, filePath, file.getInputStream()));
            } catch (OSSException | ClientException | IOException e) {
                return Message.error(e.getMessage());
            }
            ossClient.shutdown();
    
            String ossMydomain = (String) configService.getValue("OSS_MYDOMAIN"); // 生成外网访问的URL
            if (!ossMydomain.equals("")) {
                fileUrl = String.format("https://%s/%s", ossMydomain, filePath);
            } else {
                fileUrl = String.format("https://%s.%s/%s", bucketName, endpoint, filePath);
            }
        } else {
            // 上传到本地
            File dest = new File(filePath);
            try {
                FileUtil.writeBytes(file.getBytes(), dest);
            } catch (IllegalStateException | IOException e) {
                return Message.error(e.getMessage());
            }

            fileUrl = pictureService.getPath(filePath); // 获取文件Url路径
        }

        pictureEntity.setUrl(fileUrl);

        // 保存文件记录
        Long fileId = pictureService.saveGetId(pictureEntity);

        // 返回上传成功的消息
        return Message.success("上传成功", "", Map.of(
            "id", fileId,
            "contentType", fileType,
            "ext", fileExt,
            "hash", fileHash,
            "width", fileWidth,
            "height", fileHeight,
            "name", fileName,
            "path", filePath,
            "size", fileSize,
            "url", fileUrl
        ));
    }

    @RequestMapping(value = "/api/admin/upload/image/base64Handle", method = {RequestMethod.POST})
    @ResponseBody
    public Object imageBase64Handle(
            @RequestParam(value = "limitW", required = false) String limitW,
            @RequestParam(value = "limitH", required = false) String limitH,
            @RequestBody Map<String, Object> data,
            @RequestHeader(value = "Authorization", defaultValue = "") String authToken,
            HttpServletRequest request) {

        // 检查文件是否为空
        if (data.get("file") == null) {
            return Message.error("请选择上传的文件");
        }

        String[] files = ((String) data.get("file")).split(",");
        if (files.length != 2) {
            return Message.error("文件格式错误");
        }

        byte[] fileData;
        try {
            fileData = Base64.getDecoder().decode(files[1]); // 解码Base64文件
        } catch (IllegalArgumentException e) {
            return Message.error(e.getMessage());
        }

        // 文件不存在或文件名为空
        if (fileData == null) {
            return Message.error("文件格式错误");
        }

        // 检查token
        if (authToken.isEmpty() || !authToken.startsWith("Bearer ")) {
            return Message.error("token不能为空");
        }
        String token = authToken.substring("Bearer ".length());
        if (!JWT.of(token).setKey(appKey.getBytes()).verify()) {
            return Message.error("token验证失败");
        }

        // 验证token是否过期
        try {
            JWTValidator.of(token).validateDate(DateUtil.date());
        } catch (Exception e) {
            return Message.error(e.getMessage());
        }

        // 解析token
        final JWT jwt = JWTUtil.parseToken(token);
        if(!jwt.getPayload("guard_name").equals("admin")) {
            return Message.error("无权限");
        }

        // 文件保存路径
        String savePath = uploadImageSavePath + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "/";
        
        // 上传到本地
        if (!configService.getValue("OSS_OPEN").equals("1")) {
            // 目录不存在，则创建目录
            File dir = FileUtil.file(savePath);
            if (!FileUtil.exist(dir)) {
                FileUtil.mkdir(dir);
            }
        }
 
        // 文件输入流
        InputStream fileInputStream = new ByteArrayInputStream(fileData);
        String fileExt = FileTypeUtil.getType(fileInputStream);
        String originalFilename = IdUtil.simpleUUID() + "." + fileExt;
        String fileName = IdUtil.simpleUUID() + "." + fileExt;
        String filePath = savePath + fileName;
        String fileType = fileExt;
        Long fileSize = (long) fileData.length;
        String fileHash;
        try {
            fileHash = calculateHash(fileInputStream);
        } catch (NoSuchAlgorithmException | IOException e) {
            return Message.error(e.getMessage());
        }

        // 文件大小
        if (fileSize > uploadImageSize) {
            return Message.error("文件大小超出限制");
        }

        if (!uploadImageType.contains(fileType) && !uploadImageType.contains(fileExt)) {
            return Message.error("文件类型不允许");
        }

        // 读取图片
        BufferedImage image;
        try {
            image = ImageIO.read(fileInputStream);
        } catch (IOException e) {
            return Message.error(e.getMessage());
        }
        int fileWidth = image.getWidth();
        int fileHeight = image.getHeight();

        PictureEntity pictureEntity = new PictureEntity();
        pictureEntity.setObjType("ADMIN");
        pictureEntity.setObjId(Long.parseLong(jwt.getPayload("id").toString()));
        pictureEntity.setName(originalFilename);
        pictureEntity.setPath(filePath);
        pictureEntity.setSize(fileSize);
        pictureEntity.setHash(fileHash);
        pictureEntity.setExt(fileExt);
        pictureEntity.setWidth(fileWidth);
        pictureEntity.setHeight(fileHeight);

        String fileUrl = "";
        // 上传到OSS
        if (configService.getValue("OSS_OPEN").equals("1")) {
            String endpoint = (String) configService.getValue("OSS_ENDPOINT");
            String accessKeyId = (String) configService.getValue("OSS_ACCESS_KEY_ID");
            String accessKeySecret = (String) configService.getValue("OSS_ACCESS_KEY_SECRET");
            String bucketName = (String) configService.getValue("OSS_BUCKET");
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            
            // 上传文件
            try {
                ossClient.putObject(new PutObjectRequest(bucketName, filePath, fileInputStream));
            } catch (OSSException | ClientException e) {
                return Message.error(e.getMessage());
            }
            ossClient.shutdown();
    
            String ossMydomain = (String) configService.getValue("OSS_MYDOMAIN"); // 生成外网访问的URL
            if (!ossMydomain.equals("")) {
                fileUrl = String.format("https://%s/%s", ossMydomain, filePath);
            } else {
                fileUrl = String.format("https://%s.%s/%s", bucketName, endpoint, filePath);
            }
        } else {
            // 文件字节数组
            File dest = new File(filePath);
            try {
                FileUtil.writeBytes(fileData, dest);
            } catch (IllegalStateException e) {
                return Message.error(e.getMessage());
            }

            fileUrl = pictureService.getPath(filePath); // 获取文件Url路径
        }

        pictureEntity.setUrl(fileUrl);

        // 保存文件记录
        Long fileId = pictureService.saveGetId(pictureEntity);

        // 返回上传成功的消息
        return Message.success("上传成功", "", Map.of(
            "id", fileId,
            "contentType", fileType,
            "ext", fileExt,
            "hash", fileHash,
            "width", fileWidth,
            "height", fileHeight,
            "name", fileName,
            "path", filePath,
            "size", fileSize,
            "url", fileUrl
        ));
    }

    @RequestMapping(value = "/api/admin/upload/file/handle", method = {RequestMethod.POST})
    @ResponseBody
    public Object fileHandle(
        @RequestParam("file") MultipartFile file,
        @RequestHeader(value = "Authorization", defaultValue = "") String authToken) {
        
        // 检查文件是否为空
        if (file.isEmpty()) {
            return Message.error("请选择上传的文件");
        }

        // 检查token
        if (authToken.isEmpty() || !authToken.startsWith("Bearer ")) {
            return Message.error("token不能为空");
        }
        String token = authToken.substring("Bearer ".length());
        if (!JWT.of(token).setKey(appKey.getBytes()).verify()) {
            return Message.error("token验证失败");
        }

        // 验证token是否过期
        try {
            JWTValidator.of(token).validateDate(DateUtil.date());
        } catch (Exception e) {
            return Message.error(e.getMessage());
        }

        // 解析token
        final JWT jwt = JWTUtil.parseToken(token);
        if(!jwt.getPayload("guard_name").equals("admin")) {
            return Message.error("无权限");
        }

        // 文件保存路径
        String savePath = uploadFileSavePath + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "/";
        
        // 上传到本地
        if (!configService.getValue("OSS_OPEN").equals("1")) {
            // 目录不存在，则创建目录
            File dir = FileUtil.file(savePath);
            if (!FileUtil.exist(dir)) {
                FileUtil.mkdir(dir);
            }
        }
 
        // 生成新的文件名，避免文件名冲突
        String originalFilename = file.getOriginalFilename();
        String fileExt = FileUtil.extName(originalFilename);
        String fileName = IdUtil.simpleUUID() + "." + fileExt;
        String filePath = savePath + fileName;
        String fileType = file.getContentType();
        Long fileSize = file.getSize();
        String fileHash;
        try {
            fileHash = calculateHash(file.getInputStream());
        } catch (NoSuchAlgorithmException | IOException e) {
            return Message.error(e.getMessage());
        }

        // 文件大小
        if (fileSize > uploadFileSize) {
            return Message.error("文件大小超出限制");
        }

        if (!uploadFileType.contains(fileType) && !uploadFileType.contains(fileExt)) {
            return Message.error("文件类型不允许");
        }

        FileEntity fileEntity = new FileEntity();
        fileEntity.setObjType("ADMIN");
        fileEntity.setObjId(Long.parseLong(jwt.getPayload("id").toString()));
        fileEntity.setName(originalFilename);
        fileEntity.setPath(filePath);
        fileEntity.setSize(fileSize);
        fileEntity.setHash(fileHash);
        fileEntity.setExt(fileExt);

        String fileUrl = "";
        // 上传到OSS
        if (configService.getValue("OSS_OPEN").equals("1")) {
            String endpoint = (String) configService.getValue("OSS_ENDPOINT");
            String accessKeyId = (String) configService.getValue("OSS_ACCESS_KEY_ID");
            String accessKeySecret = (String) configService.getValue("OSS_ACCESS_KEY_SECRET");
            String bucketName = (String) configService.getValue("OSS_BUCKET");
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            
            // 上传文件
            try {
                ossClient.putObject(new PutObjectRequest(bucketName, filePath, file.getInputStream()));
            } catch (OSSException | ClientException | IOException e) {
                return Message.error(e.getMessage());
            }
            ossClient.shutdown();
    
            String ossMydomain = (String) configService.getValue("OSS_MYDOMAIN"); // 生成外网访问的URL
            if (!ossMydomain.equals("")) {
                fileUrl = String.format("https://%s/%s", ossMydomain, filePath);
            } else {
                fileUrl = String.format("https://%s.%s/%s", bucketName, endpoint, filePath);
            }
        } else {
            // 文件字节数组
            File dest = new File(filePath);
            try {
                FileUtil.writeBytes(file.getBytes(), dest);
            } catch (IllegalStateException | IOException e) {
                return Message.error(e.getMessage());
            }
        }

        fileEntity.setUrl(fileUrl);

        // 保存文件记录
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
