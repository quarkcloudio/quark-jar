package io.quarkcloud.quarkstarter.service.admin.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.entity.PermissionEntity;
import io.quarkcloud.quarkadmin.mapper.PermissionMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkstarter.service.admin.action.BatchDelete;
import io.quarkcloud.quarkstarter.service.admin.action.CreateModal;
import io.quarkcloud.quarkstarter.service.admin.action.Delete;
import io.quarkcloud.quarkstarter.service.admin.action.EditModal;
import io.quarkcloud.quarkstarter.service.admin.action.SyncPermission;
import io.quarkcloud.quarkstarter.service.admin.search.Input;

@Component(value = "permissionResource")
public class Permission extends ResourceImpl<PermissionMapper, PermissionEntity> {

    // 构造函数
    public Permission() {
        this.entity = new PermissionEntity();
        this.title = "权限";
        this.perPage = 10;
    }

    // 字段
    public List<Object> fields(Context context) {
        return Arrays.asList(
            Field.id("id", "ID"),
            Field.text("name", "名称"),
            Field.text("path", "路径"),
            Field.select("method", "方法").
                setOptions(Arrays.asList(
                    Field.selectOption("Any","Any"),
                    Field.selectOption("GET","GET"),
                    Field.selectOption("HEAD","HEAD"),
                    Field.selectOption("OPTIONS","OPTIONS"),
                    Field.selectOption("POST","POST"),
                    Field.selectOption("PUT","PUT"),
                    Field.selectOption("PATCH","PATCH"),
                    Field.selectOption("DELETE","DELETE")
                )).
                setFilters(true).
                setDefaultValue("GET"),
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
            new SyncPermission<PermissionMapper, PermissionEntity>(),
            new CreateModal<PermissionMapper, PermissionEntity>(this.getTitle(), this.creationApi(context), this.creationFields(context), this.creationData(context)),
            new EditModal<PermissionMapper, PermissionEntity>("编辑", this.editApi(context), this.editValueApi(context), this.editFields(context)),
            new Delete<PermissionMapper, PermissionEntity>(),
            new BatchDelete<PermissionMapper, PermissionEntity>()
        );
    }
}
