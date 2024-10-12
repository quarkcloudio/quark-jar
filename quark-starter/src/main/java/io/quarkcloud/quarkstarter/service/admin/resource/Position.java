package io.quarkcloud.quarkstarter.service.admin.resource;

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
import io.quarkcloud.quarkstarter.service.admin.action.BatchDelete;
import io.quarkcloud.quarkstarter.service.admin.action.BatchDisable;
import io.quarkcloud.quarkstarter.service.admin.action.BatchEnable;
import io.quarkcloud.quarkstarter.service.admin.action.ChangeStatus;
import io.quarkcloud.quarkstarter.service.admin.action.CreateModal;
import io.quarkcloud.quarkstarter.service.admin.action.Delete;
import io.quarkcloud.quarkstarter.service.admin.action.EditModal;
import io.quarkcloud.quarkstarter.service.admin.search.Input;
import io.quarkcloud.quarkstarter.service.admin.search.Status;

@Component
public class Position extends ResourceImpl<PositionMapper, PositionEntity> {

    // 构造函数
    public Position() {
        this.entity = new PositionEntity();
        this.title = "职位";
        this.perPage = 10;
        this.queryOrder = Map.of("sort", "asc");
    }

    // 字段
    public List<Object> fields(Context context) {
        return Arrays.asList(
            Field.id("id", "ID"),
            Field.text("name", "名称").setRules(Arrays.asList(
                Rule.required(true, "名称必须填写"),
                Rule.min(2, "名称不能少于2个字符"),
                Rule.max(100, "名称不能超过100个字符")
            )),
            Field.number("sort", "排序").setEditable(true).setDefaultValue(0),
            Field.textarea("remark", "备注"),
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
            new Input<PositionEntity>("name", "名称"),
            new Status<PositionEntity>()
        );
    }
    
    // 行为
    public List<Object> actions(Context context) {
        return Arrays.asList(
            new CreateModal<PositionMapper, PositionEntity>(this.getTitle(),this.creationApi(context), this.creationFields(context), this.creationData(context)),
            new ChangeStatus<PositionMapper, PositionEntity>(),
            new EditModal<PositionMapper, PositionEntity>("编辑", this.editApi(context), this.editValueApi(context), this.editFields(context)),
            new Delete<PositionMapper, PositionEntity>(),
            new BatchDelete<PositionMapper, PositionEntity>(),
            new BatchDelete<PositionMapper, PositionEntity>(),
            new BatchDisable<PositionMapper, PositionEntity>(),
            new BatchEnable<PositionMapper, PositionEntity>()
        );
    }
}
