package io.quarkcloud.quarkstarter.admin.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.component.form.Rule;
import io.quarkcloud.quarkadmin.entity.PositionEntity;
import io.quarkcloud.quarkadmin.mapper.PositionMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkstarter.admin.action.BatchDelete;
import io.quarkcloud.quarkstarter.admin.action.BatchDisable;
import io.quarkcloud.quarkstarter.admin.action.BatchEnable;
import io.quarkcloud.quarkstarter.admin.action.ChangeStatus;
import io.quarkcloud.quarkstarter.admin.action.CreateModal;
import io.quarkcloud.quarkstarter.admin.action.Delete;
import io.quarkcloud.quarkstarter.admin.action.EditModal;
import io.quarkcloud.quarkstarter.admin.search.Input;
import io.quarkcloud.quarkstarter.admin.search.Status;

@Component
public class Position extends ResourceImpl<PositionMapper, PositionEntity> {

    // 构造函数
    public Position() {
        this.entity = new PositionEntity();
        this.title = "职位";
        this.pageSize = 10;
        this.queryOrder = Map.of("sort", "asc");
    }

    // 字段
    public List<Object> fields(Context context) {
        return Arrays.asList(
            Field.id("id", "ID"),
            Field.text("name", "名称")
                .setRules(Arrays.asList(
                    Rule.required("名称必须填写"),
                    Rule.min(2, "名称不能少于2个字符"),
                    Rule.max(100, "名称不能超过100个字符")
                )),
            Field.number("sort", "排序")
                .setEditable(true)
                .setDefaultValue(0),
            Field.textarea("remark", "备注"),
            Field.switchField("status", "状态")
                .setTrueValue("正常")
                .setFalseValue("禁用")
                .setEditable(true)
                .setDefaultValue(true)
        );
    }

    // 搜索表单
    public List<Object> searches(Context context) {
        return Arrays.asList(
            new Input<PositionEntity>("name", "名称"),
            new Status<PositionEntity>()
        );
    }
    
    // 行为
    public List<Object> actions(Context context) {
        return Arrays.asList(
            new CreateModal<PositionMapper, PositionEntity>()
                .setTitle(this.getTitle())
                .setApi(this.creationApi(context))
                .setFields(this.creationFields(context))
                .setData(this.creationData(context)),
            new ChangeStatus<PositionMapper, PositionEntity>(),
            new EditModal<PositionMapper, PositionEntity>()
                .setTitle("编辑")
                .setApi(this.editApi(context))
                .setInitApi(this.editValueApi(context))
                .setFields(this.editFields(context)),
            new Delete<PositionMapper, PositionEntity>(),
            new BatchDelete<PositionMapper, PositionEntity>(),
            new BatchDisable<PositionMapper, PositionEntity>(),
            new BatchEnable<PositionMapper, PositionEntity>()
        );
    }
}
