package io.quarkcloud.quarkstarter.service.admin.action;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.service.ResourceService;
import io.quarkcloud.quarkadmin.template.resource.impl.action.ModalFormImpl;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkcore.util.Message;
import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.component.form.Rule;
import io.quarkcloud.quarkadmin.component.form.fields.Tree.TreeData;
import io.quarkcloud.quarkadmin.entity.RoleEntity;
import io.quarkcloud.quarkadmin.service.DepartmentService;
import io.quarkcloud.quarkadmin.service.RoleService;

public class DataScope<M, T> extends ModalFormImpl<ResourceMapper<T>, T> {

    // 部门服务
    public DepartmentService departmentService;

    // 角色服务
    public RoleService roleService;

    // 构造器
    public DataScope () {

        // 设置名称
        this.name = "数据权限";

        // 设置类型
        this.type = "link";

        // 设置按钮大小, large | middle | small | default
        this.size = "small";

        // 关闭时销毁 Modal 里的子元素
        this.destroyOnClose = true;

        // 设置展示位置 (假设这个方法是存在的)
        setOnlyOnIndexTableRow(true);

        // 行为接口接收的参数
        setApiParams(Arrays.asList("id", "name"));
    }

    // 注入部门服务
    public DataScope<M, T> setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
        return this;
    }

    // 注入角色服务
    public DataScope<M, T> setRoleService(RoleService roleService) {
        this.roleService = roleService;
        return this;
    }

    // 字段
    public List<Object> fields(Context context) {
        List<TreeData> departments = departmentService.tree();
        return List.of(
            Field.hidden("id", "ID"), // 隐藏字段
            Field.text("name", "名称"), // 禁用文本字段
            Field.selectField("data_scope", "数据范围")
                .setOptions(
                    List.of(
                        Field.selectOption("全部数据权限", 1),
                        Field.selectOption("自定数据权限", 2),
                        Field.selectOption("本部门数据权限", 3),
                        Field.selectOption("本部门及以下数据权限", 4),
                        Field.selectOption("仅本人数据权限", 5)
                    )
                )
                .setRules(List.of(Rule.required(true,"请选择数据范围")))
                .setDefaultValue(1),
            Field.dependency()
                .setWhen("data_scope", 2, () -> {
                    return List.of(
                        Field.tree("department_ids", "数据权限")
                            .setDefaultExpandAll(true)
                            .setTreeData(departments)
                    );
                })
        );
    }

    // 表单数据（异步获取）
    public Map<String, Object> data(Context context) {
        String id = context.getParameter("id");
        Map<String, Object> data = new HashMap<String, Object>();
        if (id != null && !id.isEmpty()) {
            RoleEntity entity = roleService.getById(id);
            data.put("id", entity.getId());
            data.put("name", entity.getName());
            data.put("data_scope", entity.getDataScope());
            data.put("department_ids", roleService.getDepartmentIdsById(Long.parseLong(id)));
        }
        return data;
    }

    // 执行行为句柄
    public Object handle(Context context, UpdateWrapper<T> updateWrapper, ResourceService<ResourceMapper<T>, T> resourceService) {
        return Message.success("操作成功");
    }
}
