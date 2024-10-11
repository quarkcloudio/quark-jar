package io.quarkcloud.quarkstarter.service.admin.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.component.form.Rule;
import io.quarkcloud.quarkadmin.component.form.fields.TreeSelect;
import io.quarkcloud.quarkadmin.component.table.Table.Expandable;
import io.quarkcloud.quarkadmin.entity.DepartmentEntity;
import io.quarkcloud.quarkadmin.mapper.DepartmentMapper;
import io.quarkcloud.quarkadmin.service.DepartmentService;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkcore.util.Lister;
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
public class Department extends ResourceImpl<DepartmentMapper, DepartmentEntity> {

    @Autowired
    private DepartmentService departmentService;

    // 构造函数
    public Department() {
        this.table.setExpandable((new Expandable()).setDefaultExpandedRowKeys(List.of(1)));
        this.entity = new DepartmentEntity();
        this.title = "部门";
        this.perPage = false;
        this.queryOrder = Map.of("sort", "asc","id","asc");
    }

    // 字段
    public List<Object> fields(Context context) {

        // 菜单列表
        List<TreeSelect.TreeData> menus = departmentService.treeSelect();

        return Arrays.asList(
            Field.hidden("id", "ID"), // 列表读取且不展示的字段

            Field.hidden("pid", "PID").onlyOnIndex(), // 列表读取且不展示的字段

            Field.text("name", "名称")
                .setRules(Arrays.asList(
                    Rule.required(true, "名称必须填写")
                )),

            Field.dependency()
                .setWhen("id", ">", 1, () -> Arrays.asList(
                    Field.treeSelect("pid", "父节点")
                        .setTreeData(menus)
                        .setDefaultValue(1)
                        .onlyOnForms()
                )),

            Field.number("sort", "排序")
                .setEditable(true)
                .setDefaultValue(0),

            Field.switchField("status", "状态")
                .setTrueValue("正常")
                .setFalseValue("禁用")
                .setEditable(true)
                .setDefaultValue(true)
        );
    }

    // 搜索表单
    public List<Object> searches(Context context) {
        return Arrays.asList(
            new Input<DepartmentEntity>("name", "名称"),
            new Status<DepartmentEntity>()
        );
    }
    
    // 行为
    public List<Object> actions(Context context) {
        return Arrays.asList(
            new CreateModal<DepartmentMapper, DepartmentEntity>(this.getTitle(), this.creationApi(context), this.creationFields(context), this.creationData(context)),
            new ChangeStatus<DepartmentMapper, DepartmentEntity>(),
            new EditModal<DepartmentMapper, DepartmentEntity>("编辑", this.editApi(context), this.editValueApi(context), this.editFields(context)),
            new Delete<DepartmentMapper, DepartmentEntity>(),
            new BatchDelete<DepartmentMapper, DepartmentEntity>(),
            new BatchDisable<DepartmentMapper, DepartmentEntity>(),
            new BatchEnable<DepartmentMapper, DepartmentEntity>()
        );
    }

    public Object beforeIndexShowing(Context context, List<DepartmentEntity> list) {
        String search = context.getParameter("search");
        if (search!=null && !search.isEmpty() && !search.equals("{}")) {
            // 返回原始列表
            return list;
        }

        // 转换成树形结构
        return Lister.listToTree(list, "id", "pid", "children", 0L);
    }
}
