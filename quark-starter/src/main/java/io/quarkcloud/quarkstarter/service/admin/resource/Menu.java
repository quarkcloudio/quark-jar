package io.quarkcloud.quarkstarter.service.admin.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.component.form.Rule;
import io.quarkcloud.quarkadmin.component.form.fields.Transfer.DataSource;
import io.quarkcloud.quarkadmin.component.form.fields.TreeSelect;
import io.quarkcloud.quarkadmin.component.message.Message;
import io.quarkcloud.quarkadmin.entity.MenuEntity;
import io.quarkcloud.quarkadmin.mapper.MenuMapper;
import io.quarkcloud.quarkadmin.service.MenuService;
import io.quarkcloud.quarkadmin.service.PermissionService;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkcore.util.Lister;
import io.quarkcloud.quarkstarter.service.admin.action.MenuCreateDrawer;
import io.quarkcloud.quarkstarter.service.admin.action.BatchDelete;
import io.quarkcloud.quarkstarter.service.admin.action.BatchDisable;
import io.quarkcloud.quarkstarter.service.admin.action.BatchEnable;
import io.quarkcloud.quarkstarter.service.admin.action.ChangeStatus;
import io.quarkcloud.quarkstarter.service.admin.action.Delete;
import io.quarkcloud.quarkstarter.service.admin.action.MenuEditDrawer;
import io.quarkcloud.quarkstarter.service.admin.search.Input;
import io.quarkcloud.quarkstarter.service.admin.search.Status;

@Component
public class Menu extends ResourceImpl<MenuMapper, MenuEntity> {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private MenuService menuService;

    // 构造函数
    public Menu() {
        this.entity = new MenuEntity();
        this.title = "菜单";
        this.perPage = false;
        this.queryOrder = Map.of("sort", "asc");
    }

    public Object beforeIndexShowing(Context context, List<MenuEntity> list) {
        String search = context.getParameter("search");
        if (search!=null && !search.isEmpty() && !search.equals("{}")) {
            // 返回原始列表
            return list;
        }

        // 转换成树形结构
        return Lister.listToTree(list, "id", "pid", "children", 0L);
    }

    // 字段
    public List<Object> fields(Context context) {

        // 权限列表
        List<DataSource> permissions = permissionService.getDataSource();

        // 菜单列表
        List<TreeSelect.TreeData> menus = menuService.treeSelect(true);

        return Arrays.asList(
            Field.hidden("id", "ID"), // 列表读取且不展示的字段

            Field.hidden("pid", "PID").onlyOnIndex(), // 列表读取且不展示的字段

            Field.group(Arrays.asList(
                Field.text("name", "名称")
                    .setRules(Arrays.asList(
                        Rule.required(true, "名称必须填写")
                    )),

                Field.text("guardName", "守卫")
                    .setRules(Arrays.asList(
                        Rule.required(true, "守卫必须填写")
                    ))
                    .setDefaultValue("admin")
                    .onlyOnForms(),

                Field.icon("icon", "图标").onlyOnForms()
            )),

            Field.group(Arrays.asList(
                Field.number("sort", "排序")
                    .setEditable(true)
                    .setDefaultValue(0),

                Field.treeSelect("pid", "父节点")
                    .setTreeData(menus)
                    .setDefaultValue(0)
                    .onlyOnForms(),

                Field.switchField("status", "状态")
                    .setTrueValue("正常")
                    .setFalseValue("禁用")
                    .setEditable(true)
                    .setDefaultValue(true)
            )),

            Field.group(Arrays.asList(
                Field.radio("type", "类型")
                    .setOptions(Arrays.asList(
                        Field.radioOption("目录", 1),
                        Field.radioOption("菜单", 2) ,
                        Field.radioOption("按钮", 3)
                    ))
                    .setRules(Arrays.asList(
                        Rule.required(true, "类型必须选择")
                    ))
                    .setDefaultValue(1),

                Field.switchField("show", "显示")
                    .setTrueValue("显示")
                    .setFalseValue("隐藏")
                    .setEditable(true)
                    .setDefaultValue(true)
            )),

            Field.dependency()
                .setWhen("type", 1, () -> Arrays.asList(
                    Field.text("path", "路由")
                        .setRules(Arrays.asList(
                            Rule.required(true, "路由必须填写")
                        ))
                        .setEditable(true)
                        .setHelp("前端路由")
                        .setWidth(400)
                        .buildFrontendRules(context.getRequestURI())
                )),

            Field.dependency()
                .setWhen("type", 2, () -> Arrays.asList(
                    Field.switchField("isEngine", "引擎组件")
                        .setTrueValue("是")
                        .setFalseValue("否")
                        .setDefaultValue(true),

                    Field.switchField("isLink", "外部链接")
                        .setTrueValue("是")
                        .setFalseValue("否")
                        .setDefaultValue(false),

                    Field.text("path", "路由")
                        .setRules(Arrays.asList(
                            Rule.required(true, "路由必须填写")
                        ))
                        .setEditable(true)
                        .setHelp("前端路由或后端api")
                        .setWidth(400)
                        .onlyOnForms()
                        .buildFrontendRules(context.getRequestURI())
                )),

            Field.dependency()
                .setWhen("type", 3, () -> Arrays.asList(
                    Field.transfer("permissionIds", "绑定权限")
                        .setDataSource(permissions)
                        .setListStyle(Map.of(
                            "width", 320,
                            "height", 300
                        ))
                        .setShowSearch(true)
                        .onlyOnForms()
                ))
        );
    }

    // 搜索表单
    public List<Object> searches(Context context) {
        return Arrays.asList(
            new Input<MenuEntity>("name", "名称"),
            new Input<MenuEntity>("path", "路由"),
            new Status<MenuEntity>()
        );
    }
    
    // 行为
    public List<Object> actions(Context context) {
        return Arrays.asList(
            new MenuCreateDrawer<MenuMapper, MenuEntity>(this.getTitle(), this.creationApi(context), this.creationFields(context), this.creationData(context)),
            new ChangeStatus<MenuMapper, MenuEntity>(),
            new MenuEditDrawer<MenuMapper, MenuEntity>("编辑", this.editApi(context), this.editValueApi(context), this.editFields(context)),
            new Delete<MenuMapper, MenuEntity>(),
            new BatchDelete<MenuMapper, MenuEntity>(),
            new BatchDisable<MenuMapper, MenuEntity>(),
            new BatchEnable<MenuMapper, MenuEntity>()
        );
    }

    // 编辑页面显示前回调
    public MenuEntity beforeEditing(Context context,MenuEntity data) {
        List<Long> permissionIds = permissionService.getIdsByMenuId(data.getId());
        data.setPermissionIds(permissionIds);
        return data;
    }

    // 保存数据后回调
    public Object afterSaved(Context context,MenuEntity result) {
        if (context.isImport()) {
            return result;
        }
        if (result == null) {
            return Message.error("操作失败！");
        }

        // 保存菜单权限关联
        Long menuId = result.getId();
        List<Long> permissionIds = result.getPermissionIds();
        boolean insertAllResult = true;
        if (permissionIds !=null && permissionIds.size() > 0) {
            menuService.removeAllPermissions(menuId);
            for (Long permissionId : permissionIds) {
                boolean insertResult = menuService.addPermission(menuId, permissionId);
                if (insertResult == false) {
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
