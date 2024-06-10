package io.quarkcloud.quarkadmin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import io.quarkcloud.quarkadmin.entity.Admin;
import io.quarkcloud.quarkadmin.entity.Menu;
import io.quarkcloud.quarkadmin.entity.Permission;
import io.quarkcloud.quarkadmin.entity.Role;
import io.quarkcloud.quarkadmin.entity.UserHasRole;
import io.quarkcloud.quarkadmin.mapper.AdminMapper;
import io.quarkcloud.quarkadmin.mapper.RoleMapper;
import io.quarkcloud.quarkadmin.mapper.UserHasRoleMapper;
import io.quarkcloud.quarkadmin.service.AdminService;
import io.quarkcloud.quarkadmin.service.MenuService;
import io.quarkcloud.quarkadmin.service.RoleService;
import io.quarkcloud.quarkcore.util.Lister;
import jakarta.annotation.Resource;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    
    // 管理员
    @Resource
    private AdminMapper adminMapper;

    // 用户角色关联表
    @Resource
    private UserHasRoleMapper userHasRoleMapper;

    // 用户角色关联表
    @Resource
    private RoleMapper roleMapper;

    // 用户角色
    @Autowired
    private RoleService roleService;

    // 菜单服务
    @Autowired
    private MenuService menuService;

    // 根据用户id，判断是否有访问权限
    public boolean checkPermission(Long adminId, String urlPath, String method) {
        boolean hasPermission = false;
        List<Permission> permissions = this.getPermissionsById(adminId);
        for (Permission permission : permissions) {
            if (permission.getPath().equals(urlPath) && permission.getMethod().equals(method)) {
                hasPermission = true;
            }
        }

        return hasPermission;
    }

    // 根据用户id获取权限列表
    public List<Permission> getPermissionsById(Long adminId) {
        List<Permission> list = new ArrayList<>();
        List<Long> roleIds= this.getRoleIdsById(adminId);
        for (Long roleId : roleIds) {
            list.addAll(roleService.getPermissionsById(roleId));
        }

        return list;
    }

    // 根据用户id获取角色列表
    public List<Role> getRolesById(Long adminId) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        List<Long> roleIds= this.getRoleIdsById(adminId);

        queryWrapper.in("id", roleIds);

        return roleMapper.selectList(queryWrapper);
    }

    // 根据用户id获取角色Id列表
    public List<Long> getRoleIdsById(Long adminId) {
        List<Long> list = new ArrayList<>();

        QueryWrapper<UserHasRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", adminId);
        queryWrapper.eq("guard_name", "admin");

        List<UserHasRole> userHasRoles = userHasRoleMapper.selectList(queryWrapper);
        for (UserHasRole userHasRole : userHasRoles) {
            list.add(userHasRole.getRoleId());
        }

        return list;
    }

    // 根据用户id获取菜单列表
    public List<Menu> getMenusById(Long adminId) {
        List<Menu> list = new ArrayList<>();
        if (adminId == 1) {
            list = menuService.getList();
        }

        return list;
    }

    public boolean hasMenu(List<Menu> menus, long id) {
        return menus.stream().anyMatch(menu -> menu.getId() == id);
    }

    // 根据用户id获取菜单Tree
    public ArrayNode getMenuTreeById(Long adminId) {
        List<Menu> list = getMenusById(adminId);
        ArrayNode menuTree = new ObjectMapper().createArrayNode();

        List<Menu> newMenus = new ArrayList<>();

        for (Menu v : list) {
            v.setKey(UUID.randomUUID().toString());
            v.setLocale("menu" + v.getPath().replace("/", "."));

            v.setHideInMenu(!v.isShow());

            if (v.getType() == 2 && v.isEngine()) {
                v.setPath("/layout/index?api=" + v.getPath());
            }

            if (!this.hasMenu(newMenus, v.getId()) && v.getType() != 3) {
                newMenus.add(v);
            }
        }

        try {
            menuTree = Lister.listToTree(list, "id", "pid", "routes", 0);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return menuTree;
    }

    // 根据账号查询
    public Admin getByUsername(String username) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        Admin adminInfo = adminMapper.selectOne(queryWrapper);

        return adminInfo;
    }
}
