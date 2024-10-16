package io.quarkcloud.quarkstarter.service.admin.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.component.message.Message;
import io.quarkcloud.quarkadmin.component.tabs.TabPane;
import io.quarkcloud.quarkadmin.entity.ConfigEntity;
import io.quarkcloud.quarkadmin.mapper.ConfigMapper;
import io.quarkcloud.quarkadmin.service.ResourceService;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkstarter.service.admin.action.FormBack;
import io.quarkcloud.quarkstarter.service.admin.action.FormExtraBack;
import io.quarkcloud.quarkstarter.service.admin.action.FormReset;
import io.quarkcloud.quarkstarter.service.admin.action.FormSubmit;

@Component(value = "webConfigResource")
public class WebConfig extends ResourceImpl<ConfigMapper, ConfigEntity> {

    @Autowired
    ResourceService<ConfigMapper, ConfigEntity> resourceService;

    // 构造函数
    public WebConfig() {
        this.entity = new ConfigEntity();
        this.title = "网站配置";
    }

    // 字段
    public List<Object> fields(Context context) {
        List<String> groupNames = resourceService.listObjs(
            new QueryWrapper<ConfigEntity>()
                .select("DISTINCT group_name")
                .eq("status", 1),
            obj -> ((ConfigEntity) obj).getGroupName()
        );

        List<Object> tabPanes = new ArrayList<>();
        for (String groupName : groupNames) {
            List<Map<String, Object>> configs = resourceService.listMaps(
                new QueryWrapper<ConfigEntity>()
                    .eq("status", 1)
                    .eq("group_name", groupName)
                    .orderByAsc("sort")
            );

            List<Object> fields = new ArrayList<>();
            for (Map<String, Object> config : configs) {
                String remark = (String) config.getOrDefault("remark", "");
                String type = (String) config.get("type");
                String name = (String) config.get("name");
                String title = (String) config.get("title");

                switch (type) {
                    case "text":
                        fields.add(Field.text(name, title).setExtra(remark));
                        break;
                    case "textarea":
                        fields.add(Field.textarea(name, title).setExtra(remark));
                        break;
                    case "file":
                        fields.add(Field.file(name, title).setButton("上传" + title).setExtra(remark));
                        break;
                    case "picture":
                        fields.add(Field.image(name, title).setButton("上传" + title).setExtra(remark));
                        break;
                    case "switch":
                        fields.add(Field.switchField(name, title)
                            .setTrueValue("正常")
                            .setFalseValue("禁用")
                            .setExtra(remark));
                        break;
                }
            }
            tabPanes.add(new TabPane().setTitle(groupName).setBody(fields));
        }
        return tabPanes;
    }

    // 行为
    public List<Object> actions(Context context) {
        return Arrays.asList(
            new FormExtraBack<ConfigMapper, ConfigEntity>(),
            new FormSubmit<ConfigMapper, ConfigEntity>(),
            new FormReset<ConfigMapper, ConfigEntity>(),
            new FormBack<ConfigMapper, ConfigEntity>()
        );
    }

    public Object beforeFormShowing(Context context) {
        List<ConfigEntity> configs = resourceService.list(
            new QueryWrapper<ConfigEntity>().eq("status", 1)
        );
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();
        for (ConfigEntity config : configs) {
            String name = config.getName();
            String type = config.getType();
            Object value = config.getValue();
            
            data.put(name, value);
            
            if ("switch".equals(type)) {
                data.put(name, !"0".equals(value));
            } else if ("picture".equals(type) || "file".equals(type)) {
                if (value != null && value instanceof String && !((String) value).isEmpty()) {
                    try {
                        Object jsonData = objectMapper.readValue((String) value, new TypeReference<Object>() {});
                        if (jsonData instanceof Map) {
                            data.put(name, jsonData);
                        } else if (jsonData instanceof List) {
                            data.put(name, jsonData);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return data;
    }

    // 表单执行
    public Object formHandle(Context context) {
        Map<?,?> data = context.getRequestBody(Map.class);
        if (data.isEmpty()) {
            return Message.error("参数错误！");
        }

        boolean result = true;
        for (Map.Entry<?, ?> entry : data.entrySet()) {
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            ConfigEntity configEntity = new ConfigEntity();
            UpdateWrapper<ConfigEntity> newUpdateWrapper = new UpdateWrapper<>();
            newUpdateWrapper.eq("name", key);
            if (value instanceof List<?>) {
                try {
                    value = new ObjectMapper().writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            } else if (value instanceof Map<?, ?>) {
                try {
                    value = new ObjectMapper().writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            configEntity.setValue(value);
            boolean updateResult = resourceService.update(configEntity, newUpdateWrapper);
            if (!updateResult) {
                result = false;
            }
        }
        if (!result) {
            return Message.error("操作失败，请重试！");
        }
        return Message.success("操作成功！");
    }
}
