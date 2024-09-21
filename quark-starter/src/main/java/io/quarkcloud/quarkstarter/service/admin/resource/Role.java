package io.quarkcloud.quarkstarter.service.admin.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.component.form.Rule;
import io.quarkcloud.quarkadmin.component.message.Message;
import io.quarkcloud.quarkadmin.entity.RoleEntity;
import io.quarkcloud.quarkadmin.mapper.RoleMapper;
import io.quarkcloud.quarkadmin.service.MenuService;
import io.quarkcloud.quarkadmin.service.PermissionService;
import io.quarkcloud.quarkadmin.service.RoleService;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkstarter.service.admin.action.BatchDelete;
import io.quarkcloud.quarkstarter.service.admin.action.CreateLink;
import io.quarkcloud.quarkstarter.service.admin.action.Delete;
import io.quarkcloud.quarkstarter.service.admin.action.EditLink;
import io.quarkcloud.quarkstarter.service.admin.action.FormBack;
import io.quarkcloud.quarkstarter.service.admin.action.FormExtraBack;
import io.quarkcloud.quarkstarter.service.admin.action.FormReset;
import io.quarkcloud.quarkstarter.service.admin.action.FormSubmit;
import io.quarkcloud.quarkstarter.service.admin.search.Input;

@Component
public class Role extends ResourceImpl<RoleMapper, RoleEntity> {

    // 注入菜单服务
    @Autowired
    private MenuService menuService;

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
            Field.text("name", "名称").setRules(Arrays.asList(
                Rule.required(true, "名称必须填写")
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
            new CreateLink<RoleMapper, RoleEntity>(this.getTitle()),
            new EditLink<RoleMapper, RoleEntity>(),
            new Delete<RoleMapper, RoleEntity>(),
            new BatchDelete<RoleMapper, RoleEntity>(),
            new FormExtraBack<RoleMapper, RoleEntity>(),
            new FormSubmit<RoleMapper, RoleEntity>(),
            new FormReset<RoleMapper, RoleEntity>(),
            new FormBack<RoleMapper, RoleEntity>()
        );
    }

    // 编辑页面显示前回调
    public RoleEntity beforeEditing(Context context, RoleEntity data) {
        List<Long> menuIds = roleService.getMenuIdsById(this.entity.getId());
        data.setMenuIds(menuIds);
        return data;
    }

    // 保存数据后回调
    public Object afterSaved(Context context, RoleEntity result) {
        if (context.isImport()) {
            return result;
        }
        if (result == null) {
            return Message.error("操作失败！");
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

        if (!insertAllResult) {
            return Message.error("操作失败！");
        }

        String redirectUrl = "/layout/index?api=/api/admin/{resource}/index".replace("{resource}", context.getPathVariable("resource"));
        return Message.success("操作成功！", redirectUrl);
    }
}
