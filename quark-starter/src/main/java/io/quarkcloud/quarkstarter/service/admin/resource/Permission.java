package io.quarkcloud.quarkstarter.service.admin.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.entity.PermissionEntity;
import io.quarkcloud.quarkadmin.mapper.PermissionMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkstarter.service.admin.action.Create;
import io.quarkcloud.quarkstarter.service.admin.action.Delete;
import io.quarkcloud.quarkstarter.service.admin.action.Edit;
import io.quarkcloud.quarkstarter.service.admin.action.FormBack;
import io.quarkcloud.quarkstarter.service.admin.action.FormExtraBack;
import io.quarkcloud.quarkstarter.service.admin.action.FormReset;
import io.quarkcloud.quarkstarter.service.admin.action.FormSubmit;
import io.quarkcloud.quarkstarter.service.admin.search.Input;
import io.quarkcloud.quarkstarter.service.admin.search.Status;

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
            new Input<PermissionEntity>("name", "名称"),
            new Status<PermissionEntity>()
        );
    }
    
    // 行为
    public List<Object> actions(Context context) {
        return Arrays.asList(
            new Create<PermissionMapper, PermissionEntity>(this.title),
            new Edit<PermissionMapper, PermissionEntity>(),
            new Delete<PermissionMapper, PermissionEntity>(),
            new FormExtraBack<PermissionMapper, PermissionEntity>(),
            new FormSubmit<PermissionMapper, PermissionEntity>(),
            new FormReset<PermissionMapper, PermissionEntity>(),
            new FormBack<PermissionMapper, PermissionEntity>()
        );
    }
}
