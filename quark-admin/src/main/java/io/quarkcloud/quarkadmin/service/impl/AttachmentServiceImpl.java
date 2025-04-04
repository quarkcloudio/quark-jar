package io.quarkcloud.quarkadmin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

import io.quarkcloud.quarkadmin.entity.AttachmentEntity;
import io.quarkcloud.quarkadmin.mapper.AttachmentMapper;
import io.quarkcloud.quarkadmin.service.ConfigService;
import io.quarkcloud.quarkadmin.service.AttachmentService;

@Service
public class AttachmentServiceImpl extends ResourceServiceImpl<AttachmentMapper, AttachmentEntity> implements AttachmentService {

    // 配置服务
    @Autowired
    private ConfigService configService;

    // 根据ID获取附件访问URL
    public String getUrl(Object id) {
       return this.getUrl("",id);
    }

    // 根据ID、类型获取附件访问URL
    public String getUrl(String type, Object id) {
        String http = "";
        String path = "";
        String webSiteDomain = (String) configService.getValue("WEB_SITE_DOMAIN");
        String webConfig = (String) configService.getValue("SSL_OPEN");
        if (webSiteDomain != null && !webSiteDomain.isEmpty()) {
            if ("1".equals(webConfig)) {
                http = "https://";
            } else {
                http = "http://";
            }
        }
        if (id instanceof String) {
            String getId = (String) id;
            if (getId.contains("//") && !getId.contains("{")) {
                return getId;
            }
            if (getId.contains("public") && !getId.contains("{")) {
                return http + webSiteDomain + getId.replace("public/", "/");
            }
            if (getId.contains("/") && !getId.contains("{")) {
                return http + webSiteDomain + getId;
            }

            // JSON字符串处理
            if (getId.contains("{")) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    Object jsonData = objectMapper.readValue(getId, Object.class);
                    if (jsonData instanceof Map) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> mapData = (Map<String, Object>) jsonData;
                        path = (String) mapData.get("url");
                    }
                    if (jsonData instanceof List) {
                        @SuppressWarnings("unchecked")
                        List<Map<String, Object>> arrayData = (List<Map<String, Object>>) jsonData;
                        path = (String) arrayData.get(0).get("url");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (path.contains("//")) {
                    return path;
                }
                if (path.contains("public")) {
                    path = path.replace("public/", "/");
                }
                if (!path.isEmpty()) {
                    return http + webSiteDomain + path;
                }
            }
        }

        // 查询条件
        MPJLambdaWrapper<AttachmentEntity> queryWrapper = new MPJLambdaWrapper<AttachmentEntity>().eq("id", id);
        AttachmentEntity picture = this.getOne(queryWrapper);
        if (picture != null && !picture.getId().equals(0L)) {
            path = picture.getUrl();
            if (path.contains("//")) {
                return path;
            }
            if (path.contains("public")) {
                path = path.replace("public/", "/");
            }
        }
        if (!path.isEmpty()) {
            return http + webSiteDomain + path;
        }

        if (type.equals("IMAGE")) {
            return http + webSiteDomain + "/admin/default.png"; 
        }

        return "";
    }

    // 根据ID获取文件访问URL
    public String getFileUrl(Object id) {
        return this.getUrl("FILE", id);
    }

    // 根据ID获取图片访问URL
    public String getImageUrl(Object id) {
        return this.getUrl("IMAGE", id);
    }

    // 获取多文件访问URL
    public List<String> getUrls(Object id, String type){
        List<String> paths = new ArrayList<>();
        String http = "";
        String webSiteDomain = (String) configService.getValue("WEB_SITE_DOMAIN");
        String webConfig = (String) configService.getValue("SSL_OPEN");
        
        if (webSiteDomain != null && !webSiteDomain.isEmpty()) {
            if ("1".equals(webConfig)) {
                http = "https://";
            } else {
                http = "http://";
            }
        }

        if (id instanceof String) {
            String getId = (String) id;
            // 处理 JSON 字符串
            if (getId.contains("{")) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    List<Map<String, Object>> jsonData = objectMapper.readValue(getId, new TypeReference<List<Map<String, Object>>>() {});
                    for (Map<String, Object> v : jsonData) {
                        String path = (String) v.get("url");
                        if (path.contains("//")) {
                            paths.add(path);
                        } else {
                            if (path.contains("public")) {
                                path = path.replace("public", "/");
                            }
                            if (!path.isEmpty()) {
                                path = http + webSiteDomain + path;
                            }
                            paths.add(path);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return paths;
    }

    // 根据ID获取附件存储路径
    public String getPath(Object id) {
        String path = "";
        if (id instanceof String) {
            String getId = (String) id;
            if (getId.contains("{")) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    Object jsonData = objectMapper.readValue(getId, Object.class);
                    if (jsonData instanceof Map) {
                        Map<String, Object> mapData = (Map<String, Object>) jsonData;
                        path = (String) mapData.get("path");
                    }
                    if (jsonData instanceof List) {
                        List<Map<String, Object>> arrayData = (List<Map<String, Object>>) jsonData;
                        path = (String) arrayData.get(0).get("path");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!path.isEmpty()) {
                    return path;
                }
            }
        }

        // 查询条件
        MPJLambdaWrapper<AttachmentEntity> queryWrapper = new MPJLambdaWrapper<AttachmentEntity>().eq("id", id);
        AttachmentEntity file = this.getOne(queryWrapper);
        if (file != null && !file.getId().equals(0L)) {
            path = file.getPath();
        }
        if (!path.isEmpty()) {
            return path;
        }

        return path;
    }

    // 根据ID获取文件存储路径
    public String getFilePath(Object id) {
        return this.getPath(id);
    }

    // 根据ID获取图片存储路径
    public String getImagePath(Object id) {
        return this.getPath(id);
    }

    // 获取多文件存储路径
    public List<String> getPaths(Object id) {
        List<String> paths = new ArrayList<>();

        if (id instanceof String) {
            String getId = (String) id;
            if (getId.contains("{")) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    List<Map<String, Object>> jsonData = objectMapper.readValue(
                            getId, new TypeReference<List<Map<String, Object>>>() {}
                    );
                    for (Map<String, Object> v : jsonData) {
                        if (v.containsKey("id")) {
                            paths.add(getPath(v.get("id")));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace(); // 可以根据实际情况选择是否忽略
                }
            }
        }

        return paths;
    }

    // 查询列表
    public IPage<AttachmentEntity> getListBySearch(Object adminId,String type, Object categoryId, String name, String startDate, String endDate, Integer page) {
        MPJLambdaWrapper<AttachmentEntity> queryWrapper = new MPJLambdaWrapper<AttachmentEntity>()
                .eq(AttachmentEntity::getType, type)
                .eq(AttachmentEntity::getUid, adminId)
                .eq(AttachmentEntity::getCategoryId, categoryId)
                .like(AttachmentEntity::getName, name)
                .between(AttachmentEntity::getCreatedAt,startDate, endDate);
                IPage<AttachmentEntity> pageData = new Page<>(page, 8);

                return this.page(pageData, queryWrapper);
    }
}
