package io.quarkcloud.quarkadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkcloud.quarkadmin.entity.PictureEntity;
import io.quarkcloud.quarkadmin.mapper.PictureMapper;
import io.quarkcloud.quarkadmin.service.ConfigService;
import io.quarkcloud.quarkadmin.service.PictureService;

@Service
public class PictureServiceImpl extends ResourceServiceImpl<PictureMapper, PictureEntity> implements PictureService {

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
            if (getId.contains("/public") && !getId.contains("{")) {
                return http + webSiteDomain + getId.replace("/public/", "/");
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
                if (path.contains("/public")) {
                    path = path.replace("/public/", "/");
                }
                if (!path.isEmpty()) {
                    return http + webSiteDomain + path;
                }
            }
        }

        // 查询数据库
        this.queryWrapper.eq("id", id);
        PictureEntity picture = this.getOne(queryWrapper);
        if (picture != null && picture.getId() != 0) {
            path = picture.getUrl();
            if (path.contains("//")) {
                return path;
            }
            if (path.contains("/public")) {
                path = path.replace("/public/", "/");
            }
        }
        if (!path.isEmpty()) {
            return http + webSiteDomain + path;
        }

        return http + webSiteDomain + "/admin/default.png";
    }
}
