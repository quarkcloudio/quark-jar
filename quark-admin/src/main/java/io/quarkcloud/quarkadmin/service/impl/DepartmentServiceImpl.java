package io.quarkcloud.quarkadmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.quarkcloud.quarkadmin.component.form.fields.TreeSelect.TreeData;
import io.quarkcloud.quarkadmin.component.table.TreeBar;
import io.quarkcloud.quarkadmin.entity.DepartmentEntity;
import io.quarkcloud.quarkadmin.mapper.DepartmentMapper;
import io.quarkcloud.quarkadmin.service.DepartmentService;

@Service
public class DepartmentServiceImpl extends ResourceServiceImpl<DepartmentMapper, DepartmentEntity> implements DepartmentService {

    // 获取树结构数据，根节点可选
    public List<TreeBar.TreeData> tableTree() {
        List<TreeBar.TreeData> list = new ArrayList<>();
        list.addAll(findTableTreeSelectNode(0L)); // 添加子节点
        return list;
    }

    // 递归查找树选择节点
    private List<TreeBar.TreeData> findTableTreeSelectNode(Long pid) {
        QueryWrapper<DepartmentEntity> query = new QueryWrapper<>();
        query.eq("pid", pid)
             .eq("status", 1)
             .orderByAsc("sort", "id");
        List<DepartmentEntity> departments = list(query); // 使用 MyBatis-Plus 查询
        List<TreeBar.TreeData> list = new ArrayList<>();
        if (departments.isEmpty()) return list; // 如果没有菜单则返回空列表

        for (DepartmentEntity department : departments) {
            TreeBar.TreeData item = new TreeBar.TreeData(department.getName(), department.getId());
            List<TreeBar.TreeData> children = findTableTreeSelectNode(department.getId()); // 查找子节点
            if (!children.isEmpty()) {
                item.setChildren(children); // 如果有子节点，设置子节点
            }
            list.add(item);
        }
        return list;
    }

    // 获取树结构数据，根节点可选
    public List<TreeData> treeSelect() {
        List<TreeData> list = new ArrayList<>();
        list.addAll(findTreeSelectNode(0L)); // 添加子节点
        return list;
    }

    // 递归查找树选择节点
    private List<TreeData> findTreeSelectNode(Long pid) {
        QueryWrapper<DepartmentEntity> query = new QueryWrapper<>();
        query.eq("pid", pid)
             .eq("status", 1)
             .orderByAsc("sort", "id");
        List<DepartmentEntity> departments = list(query); // 使用 MyBatis-Plus 查询
        List<TreeData> list = new ArrayList<>();
        if (departments.isEmpty()) return list; // 如果没有菜单则返回空列表

        for (DepartmentEntity department : departments) {
            TreeData item = new TreeData(department.getName(), department.getId());
            List<TreeData> children = findTreeSelectNode(department.getId()); // 查找子节点
            if (!children.isEmpty()) {
                item.setChildren(children); // 如果有子节点，设置子节点
            }
            list.add(item);
        }
        return list;
    }

    // 获取树结构数据，根节点可选
    public List<io.quarkcloud.quarkadmin.component.form.fields.Tree.TreeData> tree() {
        List<io.quarkcloud.quarkadmin.component.form.fields.Tree.TreeData> list = new ArrayList<>();
        list.addAll(findTreeNode(0L)); // 添加子节点
        return list;
    }

    // 递归查找树选择节点
    private List<io.quarkcloud.quarkadmin.component.form.fields.Tree.TreeData> findTreeNode(Long pid) {
        QueryWrapper<DepartmentEntity> query = new QueryWrapper<>();
        query.eq("pid", pid)
             .eq("status", 1)
             .orderByAsc("sort", "id");
        List<DepartmentEntity> departments = list(query); // 使用 MyBatis-Plus 查询
        List<io.quarkcloud.quarkadmin.component.form.fields.Tree.TreeData> list = new ArrayList<>();
        if (departments.isEmpty()) return list; // 如果没有菜单则返回空列表

        for (DepartmentEntity department : departments) {
            io.quarkcloud.quarkadmin.component.form.fields.Tree.TreeData item = new io.quarkcloud.quarkadmin.component.form.fields.Tree.TreeData(department.getName(), department.getId());
            List<io.quarkcloud.quarkadmin.component.form.fields.Tree.TreeData> children = findTreeNode(department.getId()); // 查找子节点
            if (!children.isEmpty()) {
                item.setChildren(children); // 如果有子节点，设置子节点
            }
            list.add(item);
        }
        return list;
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
