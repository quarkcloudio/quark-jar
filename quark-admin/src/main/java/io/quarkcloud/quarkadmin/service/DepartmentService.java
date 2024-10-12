package io.quarkcloud.quarkadmin.service;

import java.util.List;

import io.quarkcloud.quarkadmin.component.form.fields.TreeSelect.TreeData;
import io.quarkcloud.quarkadmin.component.table.TreeBar;
import io.quarkcloud.quarkadmin.entity.DepartmentEntity;
import io.quarkcloud.quarkadmin.mapper.DepartmentMapper;

public interface DepartmentService extends ResourceService<DepartmentMapper,DepartmentEntity> {

    // 获取树结构数据，根节点可选
    public List<TreeBar.TreeData> tableTree();

    // 获取树结构数据，根节点可选
    public List<TreeData> treeSelect();

    // 获取当前部门及子部门的ID集合
    public List<Long> getChildrenIds(Long pid);
}
