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
import io.quarkcloud.quarkstarter.service.admin.action.BatchDelete;
import io.quarkcloud.quarkstarter.service.admin.action.BatchDisable;
import io.quarkcloud.quarkstarter.service.admin.action.BatchEnable;
import io.quarkcloud.quarkstarter.service.admin.action.CreateLink;
import io.quarkcloud.quarkstarter.service.admin.action.Delete;
import io.quarkcloud.quarkstarter.service.admin.action.EditLink;
import io.quarkcloud.quarkstarter.service.admin.action.FormBack;
import io.quarkcloud.quarkstarter.service.admin.action.FormExtraBack;
import io.quarkcloud.quarkstarter.service.admin.action.FormReset;
import io.quarkcloud.quarkstarter.service.admin.action.FormSubmit;
import io.quarkcloud.quarkstarter.service.admin.search.Input;
import io.quarkcloud.quarkstarter.service.admin.search.Status;

@Component
public class Config extends ResourceImpl<ConfigMapper, ConfigEntity> {

    // 构造函数
    public Config() {
        this.entity = new ConfigEntity();
        this.title = "配置";
        this.perPage = 10;
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

    // 搜索表单
    public List<Object> searches(Context context) {
        return Arrays.asList(
            new Input<ConfigEntity>("title", "标题"),
            new Input<ConfigEntity>("name", "名称"),
            new Status<ConfigEntity>()
        );
    }
    
    // 行为
    public List<Object> actions(Context context) {
        return Arrays.asList(
            new CreateLink<ConfigMapper, ConfigEntity>(this.getTitle()),
            new EditLink<ConfigMapper, ConfigEntity>(),
            new Delete<ConfigMapper, ConfigEntity>(),
            new BatchDelete<ConfigMapper, ConfigEntity>(),
            new BatchDisable<ConfigMapper, ConfigEntity>(),
            new BatchEnable<ConfigMapper, ConfigEntity>(),
            new FormExtraBack<ConfigMapper, ConfigEntity>(),
            new FormSubmit<ConfigMapper, ConfigEntity>(),
            new FormReset<ConfigMapper, ConfigEntity>(),
            new FormBack<ConfigMapper, ConfigEntity>()
        );
    }
}
