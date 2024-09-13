package io.quarkcloud.quarkstarter.service.admin.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.component.tabs.TabPane;
import io.quarkcloud.quarkadmin.entity.ConfigEntity;
import io.quarkcloud.quarkadmin.mapper.ConfigMapper;
import io.quarkcloud.quarkadmin.service.ResourceService;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkstarter.service.admin.action.ChangeWebConfig;
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

    public String formApi(Context context) {
        return "/api/admin/webConfig/action/change-web-config-action";
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
            new ChangeWebConfig<ConfigMapper, ConfigEntity>(),
            new FormExtraBack<ConfigMapper, ConfigEntity>(),
            new FormSubmit<ConfigMapper, ConfigEntity>(),
            new FormReset<ConfigMapper, ConfigEntity>(),
            new FormBack<ConfigMapper, ConfigEntity>()
        );
    }
}
