package io.quarkcloud.quarkstarter.service.admin.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.component.form.Rule;
import io.quarkcloud.quarkadmin.entity.RoleEntity;
import io.quarkcloud.quarkadmin.mapper.RoleMapper;
import io.quarkcloud.quarkadmin.service.MenuService;
import io.quarkcloud.quarkadmin.service.PermissionService;
import io.quarkcloud.quarkadmin.service.DepartmentService;
import io.quarkcloud.quarkadmin.service.RoleService;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkstarter.service.admin.action.BatchDeleteRole;
import io.quarkcloud.quarkstarter.service.admin.action.CreateLink;
import io.quarkcloud.quarkstarter.service.admin.action.DeleteRole;
import io.quarkcloud.quarkstarter.service.admin.action.EditLink;
import io.quarkcloud.quarkstarter.service.admin.action.FormBack;
import io.quarkcloud.quarkstarter.service.admin.action.FormExtraBack;
import io.quarkcloud.quarkstarter.service.admin.action.FormReset;
import io.quarkcloud.quarkstarter.service.admin.action.FormSubmit;
import io.quarkcloud.quarkstarter.service.admin.action.DataScope;
import io.quarkcloud.quarkstarter.service.admin.search.Input;

@Component
public class Role extends ResourceImpl<RoleMapper, RoleEntity> {

    // 注入菜单服务
    @Autowired
    private MenuService menuService;

    // 注入部门服务
    @Autowired
    public DepartmentService departmentService;

    // 注入角色服务
    @Autowired
    private RoleService roleService;

    // 注入权限服务
    @Autowired
    private PermissionService permissionService;

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
            Field.text("name", "名称")
                .setRules(Arrays.asList(
                    Rule.required(true, "名称必须填写")
                )),
            Field.text("guardName", "守卫").setDefaultValue("admin"),
            Field.tree("menuIds", "菜单")
                .setTreeData(menuService.tree())
                .onlyOnForms(),
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
            new DataScope<RoleMapper, RoleEntity>()
                .setDepartmentService(departmentService)
                .setRoleService(roleService),
            new CreateLink<RoleMapper, RoleEntity>(this.getTitle()),
            new EditLink<RoleMapper, RoleEntity>(),
            new DeleteRole<RoleMapper, RoleEntity>(),
            new BatchDeleteRole<RoleMapper, RoleEntity>(),
            new FormExtraBack<RoleMapper, RoleEntity>(),
            new FormSubmit<RoleMapper, RoleEntity>(),
            new FormReset<RoleMapper, RoleEntity>(),
            new FormBack<RoleMapper, RoleEntity>()
        );
    }

    // 编辑页面显示前回调
    public RoleEntity beforeEditing(Context context, RoleEntity data) {
        List<Long> menuIds = roleService.getMenuIdsById(data.getId());
        data.setMenuIds(menuIds);
        return data;
    }

    // 保存数据后回调
    public boolean afterSaved(Context context, RoleEntity result) {
        if (result == null) {
            return false;
        }

        // 保存关联
        Long roleId = result.getId();
        List<Long> menuIds = result.getMenuIds();
        boolean insertAllResult = true;
        if (menuIds.size() > 0) {
            roleService.removeAllMenus(roleId);
            roleService.removeAllPermissions(roleId);
            for (Long menuId : menuIds) {
                boolean insertMenuResult = roleService.addMenu(roleId, menuId);
                List<Long> permissionIds = permissionService.getIdsByMenuId(menuId);
                for (Long permissionId : permissionIds) {
                    boolean insertPermissionResult = roleService.addPermission(roleId, permissionId);
                    if (insertPermissionResult == false) {
                        insertAllResult = false;
                    }
                }
                if (insertMenuResult == false) {
                    insertAllResult = false;
                }
            }
        }

        return insertAllResult;
    }
}
