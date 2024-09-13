package io.quarkcloud.quarkstarter.service.admin.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.component.form.Rule;
import io.quarkcloud.quarkadmin.entity.ConfigEntity;
import io.quarkcloud.quarkadmin.mapper.ConfigMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkstarter.service.admin.action.FormBack;
import io.quarkcloud.quarkstarter.service.admin.action.FormExtraBack;
import io.quarkcloud.quarkstarter.service.admin.action.FormReset;
import io.quarkcloud.quarkstarter.service.admin.action.FormSubmit;

@Component(value = "webConfigResource")
public class WebConfig extends ResourceImpl<ConfigMapper, ConfigEntity> {

    // 构造函数
    public WebConfig() {
        this.entity = new ConfigEntity();
        this.title = "网站配置";
    }

    // 字段
    public List<Object> fields(Context context) {
        return Arrays.asList(
            Field.id("id", "ID"),
            Field.text("title", "标题").setRules(Arrays.asList(
                Rule.required(true, "标题必须填写")
            )),
            Field.text("name", "名称").setRules(Arrays.asList(
                Rule.required(true, "名称必须填写")
            )),
            Field.radio("type", "表单类型").
                setOptions(Arrays.asList(
                    Field.radioOption("输入框","text"),
                    Field.radioOption("文本域","textarea"),
                    Field.radioOption("图片","picture"),
                    Field.radioOption("文件","file"),
                    Field.radioOption("开关","switch")
                )).
                setDefaultValue("text"),
            Field.text("sort", "排序").setEditable(true).setDefaultValue(0).setHelp("值越小越靠前"),
            Field.text("groupName", "分组名称"),
            Field.text("remark", "备注"),
            Field.switchField("status", "状态").
                setTrueValue("正常").
                setFalseValue("禁用").
                setEditable(true).
                setDefaultValue(true)
        );
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
}
