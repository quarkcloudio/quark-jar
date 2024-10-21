package io.quarkcloud.quarkadmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.quarkcloud.quarkadmin.entity.DepartmentEntity;
import io.quarkcloud.quarkadmin.mapper.DepartmentMapper;
import io.quarkcloud.quarkadmin.service.DepartmentService;

@Service
public class DepartmentServiceImpl extends ResourceServiceImpl<DepartmentMapper, DepartmentEntity> implements DepartmentService {

    // 获取当子部门的集合
    public List<DepartmentEntity> getChildrenDepartments(Long pid) {
        List<DepartmentEntity> list = new ArrayList<>();

        // 查询 pid 下的子部门
        QueryWrapper<DepartmentEntity> query = new QueryWrapper<>();
        query.eq("pid", pid)
             .eq("status", 1)
             .orderByAsc("sort", "id");
        List<DepartmentEntity> departments = list(query); // 使用 MyBatis-Plus 查询

        // 如果没有子部门，返回空列表
        if (departments.isEmpty()) {
            return list;
        }

        // 遍历子部门并递归获取子部门的数据
        for (DepartmentEntity department : departments) {
            List<DepartmentEntity> children = getChildrenDepartments(department.getId());
            
            // 如果有子部门，将子部门加入到列表中
            if (!children.isEmpty()) {
                list.addAll(children);
            }
            
            // 当前部门加入到列表中
            list.add(department);
        }

        return list;
    }

    // 获取部门列表
    public List<DepartmentEntity> getList() {
        QueryWrapper<DepartmentEntity> query = new QueryWrapper<>();
        query.eq("status", 1).orderByAsc("sort", "id");
        return list(query);
    }

    public List<Long> getChildrenIds(Long pid) {
        QueryWrapper<DepartmentEntity> query = new QueryWrapper<>();
        query.eq("pid", pid)
             .eq("status", 1);
        List<DepartmentEntity> departments = list(query);

        List<Long> list = new ArrayList<>();
        if (departments.isEmpty()) {
            return list;
        }

        // 递归获取子部门
        for (DepartmentEntity department : departments) {
            List<Long> children = getChildrenIds(department.getId());
            if (!children.isEmpty()) {
                list.addAll(children);
            }
            list.add(department.getId());
        }

        return list;
    }
}
