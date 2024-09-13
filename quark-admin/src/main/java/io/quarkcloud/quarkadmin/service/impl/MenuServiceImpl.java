package io.quarkcloud.quarkadmin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import io.quarkcloud.quarkadmin.component.form.fields.TreeSelect.TreeData;
import io.quarkcloud.quarkadmin.entity.MenuEntity;
import io.quarkcloud.quarkadmin.mapper.MenuHasPermissionMapper;
import io.quarkcloud.quarkadmin.mapper.MenuMapper;
import io.quarkcloud.quarkadmin.service.AdminService;
import io.quarkcloud.quarkadmin.service.MenuService;
import io.quarkcloud.quarkcore.util.Lister;
import jakarta.annotation.Resource;

@Service
public class MenuServiceImpl extends ResourceServiceImpl<MenuMapper, MenuEntity> implements MenuService {
    
    // 菜单
    @Resource
    private MenuMapper menuMapper;

    // 菜单权限关联
    @Resource
    private MenuHasPermissionMapper menuHasPermissionMapper;

    // 用户角色
    @Autowired
    private AdminService adminService;

    // 获取菜单列表
    public List<MenuEntity> getList() {
        QueryWrapper<MenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("guard_name", "admin");
        queryWrapper.eq("status", 1);
        List<MenuEntity> list = menuMapper.selectList(queryWrapper);
        return list;
    }

    // 获取树结构数据，根节点可选
    public List<TreeData> treeSelect(boolean root) {
        List<TreeData> list = new ArrayList<>();
        if (root) {
            list.add(new TreeData("根节点", 0)); // 如果需要根节点，则添加
        }
        list.addAll(findTreeSelectNode(0L)); // 添加子节点
        return list;
    }

    // 递归查找树选择节点
    private List<TreeData> findTreeSelectNode(Long pid) {
        QueryWrapper<MenuEntity> query = new QueryWrapper<>();
        query.eq("guard_name", "admin")
             .eq("pid", pid)
             .eq("status", 1)
             .orderByAsc("sort", "id");
        List<MenuEntity> menus = list(query); // 使用 MyBatis-Plus 查询
        List<TreeData> list = new ArrayList<>();
        if (menus.isEmpty()) return list; // 如果没有菜单则返回空列表

        for (MenuEntity menu : menus) {
            TreeData item = new TreeData(menu.getName(), menu.getId());
            List<TreeData> children = findTreeSelectNode(menu.getId()); // 查找子节点
            if (!children.isEmpty()) {
                item.setChildren(children); // 如果有子节点，设置子节点
            }
            list.add(item);
        }
        return list;
    }

    // 获取树结构数据
    public List<io.quarkcloud.quarkadmin.component.form.fields.Tree.TreeData> tree() {
        return findTreeNode(0L); // 从根节点开始查找
    }

    // 递归查找树节点
    private List<io.quarkcloud.quarkadmin.component.form.fields.Tree.TreeData> findTreeNode(Long pid) {
        QueryWrapper<MenuEntity> query = new QueryWrapper<>();
        query.eq("guard_name", "admin")
             .eq("pid", pid)
             .eq("status", 1)
             .orderByAsc("sort", "id");
        List<MenuEntity> menus = list(query); // 使用 MyBatis-Plus 查询
        List<io.quarkcloud.quarkadmin.component.form.fields.Tree.TreeData> list = new ArrayList<>();
        if (menus.isEmpty()) return list; // 如果没有菜单则返回空列表

        for (MenuEntity menu : menus) {
            io.quarkcloud.quarkadmin.component.form.fields.Tree.TreeData item = new io.quarkcloud.quarkadmin.component.form.fields.Tree.TreeData(menu.getName(), menu.getId());
            List<io.quarkcloud.quarkadmin.component.form.fields.Tree.TreeData> children = findTreeNode(menu.getId()); // 查找子节点
            if (!children.isEmpty()) {
                item.setChildren(children); // 如果有子节点，设置子节点
            }
            list.add(item);
        }
        return list;
    }

    // 递归查找父节点
    private List<MenuEntity> findParentTreeNode(Long childPid) {
        QueryWrapper<MenuEntity> query = new QueryWrapper<>();
        query.eq("guard_name", "admin")
             .eq("id", childPid)
             .eq("status", 1)
             .in("type", 1, 2, 3);
        List<MenuEntity> menus = list(query); // 使用 MyBatis-Plus 查询
        if (menus.isEmpty()) return menus; // 如果没有菜单则返回空列表

        for (MenuEntity menu : menus) {
            if (menu.getPid() != 0) {
                List<MenuEntity> parents = findParentTreeNode(menu.getPid()); // 查找父节点
                if (!parents.isEmpty()) {
                    menus.addAll(parents); // 将父节点添加到菜单列表中
                }
            }
        }
        return menus;
    }

    // 根据管理员 ID 获取菜单列表
    public ArrayNode getListByAdminId(Long adminId) {
        List<MenuEntity> menus = new ArrayList<>();
        if (adminId == 1) { // 如果是超级管理员
            QueryWrapper<MenuEntity> query = new QueryWrapper<>();
            query.eq("guard_name", "admin")
                 .eq("status", 1)
                 .in("type", 1, 2, 3)
                 .orderByAsc("sort");
            menus = list(query); // 使用 MyBatis-Plus 查询
            return menuParser(menus); // 解析菜单数据
        }

        List<Long> menuIds = adminService.getMenuIdsById(adminId);
        if (menuIds.isEmpty()) return null;

        QueryWrapper<MenuEntity> query = new QueryWrapper<>();
        query.eq("guard_name", "admin")
             .eq("status", 1)
             .in("id", menuIds)
             .in("type", 1, 2, 3)
             .ne("pid", 0);
        menus = list(query); // 使用 MyBatis-Plus 查询

        for (MenuEntity menu : menus) {
            List<MenuEntity> parents = findParentTreeNode(menu.getPid()); // 查找父节点
            for (MenuEntity parent : parents) {
                menuIds.add(parent.getId()); // 添加父节点 ID
            }
        }

        query = new QueryWrapper<>();
        query.eq("guard_name", "admin")
             .eq("status", 1)
             .in("id", menuIds)
             .orderByAsc("sort");
        menus = list(query); // 使用 MyBatis-Plus 查询

        return menuParser(menus); // 解析菜单数据
    }

    // 解析菜单数据
    private ArrayNode menuParser(List<MenuEntity> menus) {
        ArrayNode menuTree = new ObjectMapper().createArrayNode();
        List<MenuEntity> newMenus = new ArrayList<>();
        for (MenuEntity menu : menus) {
            menu.setKey(UUID.randomUUID().toString()); // 生成唯一标识
            menu.setLocale("menu" + menu.getPath().replace("/", "."));
            menu.setHideInMenu(!menu.getShow()); // 设置是否隐藏
            if (menu.getType() == 2 && menu.getIsEngine()) {
                menu.setPath("/layout/index?api=" + menu.getPath()); // 设置路径
            }
            if (!hasMenu(newMenus, menu.getId()) && menu.getType() != 3) {
                newMenus.add(menu); // 添加新菜单
            }
        }

        menuTree = Lister.listToTree(newMenus, "id", "pid", "routes", 0L);
        
        return menuTree;
    }

    // 检查菜单是否已经存在
    private boolean hasMenu(List<MenuEntity> menus, Long id) {
        return menus.stream().anyMatch(menu -> menu.getId() == id);
    }

    // 根据 ID 获取菜单信息
    public MenuEntity getInfoById(Long id) {
        QueryWrapper<MenuEntity> query = new QueryWrapper<>();
        query.eq("status", 1).eq("id", id);
        return getOne(query); // 使用 MyBatis-Plus 查询单个结果
    }
}
