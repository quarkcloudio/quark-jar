package io.quarkcloud.quarkstarter.admin.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.component.form.Rule;
import io.quarkcloud.quarkadmin.component.form.fields.Transfer.DataSource;
import io.quarkcloud.quarkadmin.component.form.fields.TreeSelect;
import io.quarkcloud.quarkadmin.entity.MenuEntity;
import io.quarkcloud.quarkadmin.mapper.MenuMapper;
import io.quarkcloud.quarkadmin.service.MenuService;
import io.quarkcloud.quarkadmin.service.PermissionService;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkstarter.admin.action.BatchDelete;
import io.quarkcloud.quarkstarter.admin.action.BatchDisable;
import io.quarkcloud.quarkstarter.admin.action.BatchEnable;
import io.quarkcloud.quarkstarter.admin.action.ChangeStatus;
import io.quarkcloud.quarkstarter.admin.action.Delete;
import io.quarkcloud.quarkstarter.admin.action.MenuCreateDrawer;
import io.quarkcloud.quarkstarter.admin.action.MenuEditDrawer;
import io.quarkcloud.quarkstarter.admin.search.Input;
import io.quarkcloud.quarkstarter.admin.search.Status;

@Component
public class Menu extends ResourceImpl<MenuMapper, MenuEntity> {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private MenuService menuService;
    
    // 添加 ObjectMapper 实例
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 构造函数
    public Menu() {
        this.entity = new MenuEntity();
        this.title = "菜单";
        this.queryOrder = Map.of("sort", "asc");
        this.tableListToTree = true;
        this.pageSize = false;
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
            Field.hidden("query", "查询参数"),
            Field.hidden("api", "API接口"),
            Field.hidden("url", "URL地址"),
            Field.hidden("component", "组件"),
    
            Field.group(Arrays.asList(
                Field.text("name", "名称")
                    .setRules(Arrays.asList(
                        Rule.required("名称必须填写")
                    )),

                Field.text("guardName", "守卫")
                    .setRules(Arrays.asList(
                        Rule.required("守卫必须填写")
                    ))
                    .setDefaultValue("admin")
                    .onlyOnForms(),

                Field.radio("type", "类型")
                    .setOptions(Arrays.asList(
                        Field.radioOption("目录", 1),
                        Field.radioOption("菜单", 2),
                        Field.radioOption("按钮", 3)
                    ))
                    .setRules(Arrays.asList(
                        Rule.required("类型必须选择")
                    ))
                    .setDefaultValue(1)
            )),

            Field.group(Arrays.asList(
                Field.icon("icon", "图标").onlyOnForms(),

                Field.number("sort", "排序")
                    .setEditable(true)
                    .setDefaultValue(0),

                Field.treeSelect("pid", "上级菜单")
                    .setTreeData(menus)
                    .setDefaultValue(0)
                    .onlyOnForms()
            )),

            Field.dependency()
                .setWhen("type", 1, () -> Arrays.asList(
                    Field.text("path", "路由")
                        .setRules(Arrays.asList(
                            Rule.required("路由必须填写")
                        ))
                        .setEditable(true)
                        .setHelp("访问的路由地址，如：`user`")
                        .setWidth("400px")
                )),

            Field.dependency()
                .setWhen("type", 2, () -> Arrays.asList(
                    Field.radio("pageType", "页面类型")
                        .setOptions(Arrays.asList(
                            Field.radioOption("默认", 1),
                            Field.radioOption("引擎", 2),
                            Field.radioOption("外链", 3),
                            Field.radioOption("iframe", 4)
                        ))
                        .setRules(Arrays.asList(
                            Rule.required("页面类型必须选择")
                        ))
                        .setDefaultValue(1)
                        .onlyOnForms(),

                    Field.dependency()
                        .setWhen("pageType", 1, () -> Arrays.asList(
                            Field.text("path", "路由地址")
                                .setRules(Arrays.asList(
                                    Rule.required("路由地址必须填写")
                                ))
                                .setEditable(true)
                                .setHelp("访问的路由地址，如：`user`")
                                .setWidth("400px")
                                .onlyOnForms(),

                            Field.text("component", "组件路径")
                                .setRules(Arrays.asList(
                                    Rule.required("组件路径必须填写")
                                ))
                                .setEditable(true)
                                .setHelp("访问的组件路径，如：`user/index`")
                                .setWidth("400px")
                                .onlyOnForms()
                        )),

                    Field.dependency()
                        .setWhen("pageType", 2, () -> Arrays.asList(
                            Field.text("path", "路由地址")
                                .setRules(Arrays.asList(
                                    Rule.required("路由地址必须填写")
                                ))
                                .setEditable(true)
                                .setHelp("访问的路由地址，如：`user`")
                                .setWidth("400px")
                                .onlyOnForms(),

                            Field.text("api", "接口地址")
                                .setRules(Arrays.asList(
                                    Rule.required("接口地址必须填写")
                                ))
                                .setEditable(true)
                                .setHelp("引擎接口地址，如：`/api/admin/user/index`")
                                .setWidth("400px")
                                .onlyOnForms()
                        )),

                    Field.dependency()
                        .setWhen("pageType", 3, () -> Arrays.asList(
                            Field.text("path", "外链地址")
                                .setRules(Arrays.asList(
                                    Rule.required("外链地址必须填写")
                                ))
                                .setEditable(true)
                                .setHelp("访问的外链地址，以`http(s)://`开头")
                                .setWidth("400px")
                                .onlyOnForms()
                        )),

                    Field.dependency()
                        .setWhen("pageType", 4, () -> Arrays.asList(
                            Field.text("path", "路由地址")
                                .setRules(Arrays.asList(
                                    Rule.required("路由地址必须填写")
                                ))
                                .setEditable(true)
                                .setHelp("访问的路由地址，如：`user`")
                                .setWidth("400px")
                                .onlyOnForms(),

                            Field.text("url", "iframe地址")
                                .setRules(Arrays.asList(
                                    Rule.required("iframe地址必须填写")
                                ))
                                .setEditable(true)
                                .setHelp("访问的iframe地址，以`http(s)://`开头")
                                .setWidth("400px")
                                .onlyOnForms()
                        ))
                )),

            Field.dependency()
                .setWhen("type", ">", 1, () -> Arrays.asList(
                    Field.text("permission", "权限标识")
                        .setHelp("鉴权标识，如：`user:index`")
                        .setWidth("400px")
                )),

            Field.group(Arrays.asList(
                Field.switchField("visible", "显示")
                    .setTrueValue("显示")
                    .setFalseValue("隐藏")
                    .setEditable(true)
                    .setDefaultValue(true),

                Field.switchField("status", "状态")
                    .setTrueValue("正常")
                    .setFalseValue("禁用")
                    .setEditable(true)
                    .setDefaultValue(true)
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
            new MenuCreateDrawer<MenuMapper, MenuEntity>(context, this),
            new ChangeStatus<MenuMapper, MenuEntity>(),
            new MenuEditDrawer<MenuMapper, MenuEntity>(context, this),
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
        
        Integer pageType = data.getPageType();
        if (pageType != null) {
            switch (pageType) {
                case 2:
                    try {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> query = objectMapper.readValue(
                            data.getQuery(),
                            Map.class
                        );
                        data.setApi((String) query.get("api"));
                    } catch (JsonProcessingException e) {
                        // 处理解析错误
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> query = objectMapper.readValue(
                            data.getQuery(), 
                            Map.class
                        );
                        data.setUrl((String) query.get("url"));
                    } catch (JsonProcessingException e) {
                        // 处理解析错误
                        e.printStackTrace();
                    }
                    break;
            }
        }
        
        return data;
    }
    
    public MenuEntity beforeSaving(Context ctx, MenuEntity submitData) {
        Integer pageType = submitData.getPageType();
        if (pageType != null) {
            if (pageType == 2) {
                submitData.setQuery(String.format("{\"api\":\"%s\"}", 
                    submitData.getApi()));
            } else if (pageType == 4) {
                submitData.setQuery(String.format("{\"url\":\"%s\"}", 
                    submitData.getUrl()));
            }
        }
        
        return submitData;
    }
    
    // 保存数据后回调
    public boolean afterSaved(Context context,MenuEntity result) {
        if (result == null) {
            return false;
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

        return insertAllResult;
    }
}