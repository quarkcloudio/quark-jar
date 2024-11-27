package io.quarkcloud.quarkstarter.admin.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.component.form.Rule;
import io.quarkcloud.quarkadmin.component.table.Table.Expandable;
import io.quarkcloud.quarkadmin.entity.DepartmentEntity;
import io.quarkcloud.quarkadmin.mapper.DepartmentMapper;
import io.quarkcloud.quarkadmin.service.DepartmentService;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkstarter.admin.action.BatchDelete;
import io.quarkcloud.quarkstarter.admin.action.BatchDisable;
import io.quarkcloud.quarkstarter.admin.action.BatchEnable;
import io.quarkcloud.quarkstarter.admin.action.ChangeStatus;
import io.quarkcloud.quarkstarter.admin.action.CreateModal;
import io.quarkcloud.quarkstarter.admin.action.DeleteSpecial;
import io.quarkcloud.quarkstarter.admin.action.EditModal;
import io.quarkcloud.quarkstarter.admin.search.Input;
import io.quarkcloud.quarkstarter.admin.search.Status;

@Component
public class Department extends ResourceImpl<DepartmentMapper, DepartmentEntity> {

    @Autowired
    private DepartmentService departmentService;

    // 构造函数
    public Department() {
        this.table
            .setExpandable((new Expandable())
            .setDefaultExpandedRowKeys(List.of(1)));
        this.entity = new DepartmentEntity();
        this.title = "部门";
        this.queryOrder = Map.of("sort", "asc","id","asc");
        this.tableListToTree = true;
        this.pageSize = false;
    }

    // 字段
    public List<Object> fields(Context context) {

        // 部门列表
        List<DepartmentEntity> departments = departmentService.getList();

        return Arrays.asList(
            Field.hidden("id", "ID"), // 列表读取且不展示的字段

            Field.hidden("pid", "PID").onlyOnIndex(), // 列表读取且不展示的字段

            Field.text("name", "名称")
                .setRules(Arrays.asList(
                    Rule.required("名称必须填写")
                )),

            Field.treeSelect("pid", "父节点")
                .setRules(Arrays.asList(
                    Rule.required("请选择父节点")
                ))
                .setTreeData(departments,"pid","name","id")
                .setDefaultValue(1)
                .onlyOnCreating(),

            Field.dependency()
                .setWhen("id", ">", 1, () -> Arrays.asList(
                    Field.treeSelect("pid", "父节点")
                        .setRules(Arrays.asList(
                            Rule.required("请选择父节点")
                        ))
                        .setTreeData(departments,"pid","name","id")
                        .setDefaultValue(1)
                        .onlyOnUpdating()
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
            new CreateModal<DepartmentMapper, DepartmentEntity>()
                .setTitle(this.getTitle())
                .setApi(this.creationApi(context))
                .setFields(this.creationFields(context))
                .setData(this.creationData(context)),
            new ChangeStatus<DepartmentMapper, DepartmentEntity>(),
            new EditModal<DepartmentMapper, DepartmentEntity>()
                .setTitle("编辑")
                .setApi(this.editApi(context))
                .setInitApi(this.editValueApi(context))
                .setFields(this.editFields(context)),
            new DeleteSpecial<DepartmentMapper, DepartmentEntity>(),
            new BatchDelete<DepartmentMapper, DepartmentEntity>(),
            new BatchDisable<DepartmentMapper, DepartmentEntity>(),
            new BatchEnable<DepartmentMapper, DepartmentEntity>()
        );
    }
}
