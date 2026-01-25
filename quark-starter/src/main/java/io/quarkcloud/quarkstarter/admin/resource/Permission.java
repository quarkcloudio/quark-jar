package io.quarkcloud.quarkstarter.admin.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.component.form.Rule;
import io.quarkcloud.quarkadmin.entity.PermissionEntity;
import io.quarkcloud.quarkadmin.mapper.PermissionMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkstarter.admin.action.BatchDelete;
import io.quarkcloud.quarkstarter.admin.action.CreateModal;
import io.quarkcloud.quarkstarter.admin.action.Delete;
import io.quarkcloud.quarkstarter.admin.action.EditModal;
import io.quarkcloud.quarkstarter.admin.action.SyncPermission;
import io.quarkcloud.quarkstarter.admin.search.Input;

@Component
public class Permission extends ResourceImpl<PermissionMapper, PermissionEntity> {

    // 构造函数
    public Permission() {
        this.entity = new PermissionEntity();
        this.title = "权限";
        this.pageSize = 10;
    }

    // 字段
    public List<Object> fields(Context context) {
        return Arrays.asList(
            Field.id("id", "ID"),
            Field.text("name", "名称")
                .setRules(Arrays.asList(
                    Rule.required("名称必须填写")
                )),
            Field.text("path", "路径")
                .setRules(Arrays.asList(
                    Rule.required("路径必须填写")
                )),
            Field.select("method", "方法")
                .setOptions(Arrays.asList(
                    Field.selectOption("Any","Any"),
                    Field.selectOption("GET","GET"),
                    Field.selectOption("HEAD","HEAD"),
                    Field.selectOption("OPTIONS","OPTIONS"),
                    Field.selectOption("POST","POST"),
                    Field.selectOption("PUT","PUT"),
                    Field.selectOption("PATCH","PATCH"),
                    Field.selectOption("DELETE","DELETE")
                ))
                .setFilters(true)
                .setDefaultValue("GET"),
            Field.text("remark", "备注")
        );
    }

    // 搜索表单
    public List<Object> searches(Context context) {
        return Arrays.asList(
            new Input<PermissionEntity>("name", "名称"),
            new Input<PermissionEntity>("path", "路径")
        );
    }
    
    // 行为
    public List<Object> actions(Context context) {
        return Arrays.asList(
            new CreateModal<PermissionMapper, PermissionEntity>(context, this),
            new BatchDelete<PermissionMapper, PermissionEntity>(),
            new SyncPermission<PermissionMapper, PermissionEntity>(),
            new EditModal<PermissionMapper, PermissionEntity>(context, this),
            new Delete<PermissionMapper, PermissionEntity>()
        );
    }
}
