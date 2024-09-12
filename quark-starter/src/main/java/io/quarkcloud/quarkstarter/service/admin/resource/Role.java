package io.quarkcloud.quarkstarter.service.admin.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.component.form.Rule;
import io.quarkcloud.quarkadmin.entity.AdminEntity;
import io.quarkcloud.quarkadmin.entity.RoleEntity;
import io.quarkcloud.quarkadmin.mapper.AdminMapper;
import io.quarkcloud.quarkadmin.mapper.RoleMapper;
import io.quarkcloud.quarkadmin.service.MenuService;
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

@Component(value = "roleResource")
public class Role extends ResourceImpl<RoleMapper, RoleEntity> {

    // 注入菜单服务
    @Autowired
    private MenuService menuService;

    // 构造函数
    public Role() {
        this.entity = new RoleEntity();
        this.title = "角色";
        this.perPage = 10;
    }

    // 字段
    public List<Object> fields(Context context) {
        return Arrays.asList(
            Field.id("id", "ID"),
            Field.text("name", "名称").setRules(Arrays.asList(
                Rule.required(true, "用户名必须填写")
            )),
            Field.text("guardName", "守卫"),
            Field.tree("menuIds", "权限").setTreeData(menuService.tree()).onlyOnForms(),
            Field.datetime("createdAt", "创建时间").onlyOnIndex(),
            Field.datetime("updatedAt", "更新时间").onlyOnIndex()
        );
    }

    // 搜索表单
    public List<Object> searches(Context context) {
        return Arrays.asList(
            new Input<RoleEntity>("name", "名称")
        );
    }
    
    // 行为
    public List<Object> actions(Context context) {
        return Arrays.asList(
            new Create<AdminMapper, AdminEntity>(this.getTitle()),
            new Edit<AdminMapper, AdminEntity>(),
            new Delete<RoleMapper, RoleEntity>(),
            new FormExtraBack<RoleMapper, RoleEntity>(),
            new FormSubmit<RoleMapper, RoleEntity>(),
            new FormReset<RoleMapper, RoleEntity>(),
            new FormBack<RoleMapper, RoleEntity>()
        );
    }
}
