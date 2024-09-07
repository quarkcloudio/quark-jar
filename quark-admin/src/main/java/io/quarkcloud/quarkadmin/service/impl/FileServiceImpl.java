package io.quarkcloud.quarkadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

import io.quarkcloud.quarkadmin.entity.FileEntity;
import io.quarkcloud.quarkadmin.mapper.FileMapper;
import io.quarkcloud.quarkadmin.service.ConfigService;
import io.quarkcloud.quarkadmin.service.FileService;

@Service
public class FileServiceImpl extends ResourceServiceImpl<FileMapper, FileEntity> implements FileService {

    // 配置服务
    @Autowired
    private ConfigService configService;

    public String getPath(Object id) {
        String http = "";
        String path = "";
        String webSiteDomain = configService.getValue("WEB_SITE_DOMAIN");
        String webConfig = configService.getValue("SSL_OPEN");
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
        MPJLambdaWrapper<FileEntity> queryWrapper = new MPJLambdaWrapper<FileEntity>().eq("id", id);
        FileEntity file = this.getOne(queryWrapper);
        if (file != null && file.getId() != 0) {
            path = file.getUrl();
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

        return null;
    }

    @SuppressWarnings("unchecked")
    public String getFilePath(Object id) {
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
        MPJLambdaWrapper<FileEntity> queryWrapper = new MPJLambdaWrapper<FileEntity>().eq("id", id);
        FileEntity file = this.getOne(queryWrapper);
        if (file != null && file.getId() != 0) {
            path = file.getPath();
        }
        if (!path.isEmpty()) {
            return path;
        }

        return path;
    }
}
