package io.quarkcloud.quarkadmin.service;

import java.util.List;

import io.quarkcloud.quarkadmin.entity.DepartmentEntity;
import io.quarkcloud.quarkadmin.mapper.DepartmentMapper;

public interface DepartmentService extends ResourceService<DepartmentMapper,DepartmentEntity> {

    // 获取当前部门及子部门的ID集合
    public List<Long> getChildrenIds(Long pid);

    // 获取当前部门集合
    public List<DepartmentEntity> getList();

    // 获取当子部门的集合
    public List<DepartmentEntity> getChildrenDepartments(Long pid);
}
