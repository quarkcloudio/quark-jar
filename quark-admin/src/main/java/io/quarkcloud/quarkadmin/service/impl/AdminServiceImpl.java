package io.quarkcloud.quarkadmin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import io.quarkcloud.quarkadmin.entity.AdminEntity;
import io.quarkcloud.quarkadmin.entity.MenuEntity;
import io.quarkcloud.quarkadmin.entity.PermissionEntity;
import io.quarkcloud.quarkadmin.entity.RoleEntity;
import io.quarkcloud.quarkadmin.entity.UserHasRoleEntity;
import io.quarkcloud.quarkadmin.mapper.AdminMapper;
import io.quarkcloud.quarkadmin.mapper.RoleMapper;
import io.quarkcloud.quarkadmin.mapper.UserHasRoleMapper;
import io.quarkcloud.quarkadmin.service.AdminService;
import io.quarkcloud.quarkadmin.service.MenuService;
import io.quarkcloud.quarkadmin.service.RoleService;
import io.quarkcloud.quarkcore.util.Lister;
import jakarta.annotation.Resource;

@Service
public class AdminServiceImpl extends ResourceServiceImpl<AdminMapper, AdminEntity> implements AdminService {
    
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
        List<PermissionEntity> permissions = this.getPermissionsById(adminId);
        for (PermissionEntity permission : permissions) {
            if (permission.getPath().equals(urlPath) && permission.getMethod().equals(method)) {
                hasPermission = true;
            }
        }

        return hasPermission;
    }

    // 根据用户id获取权限列表
    public List<PermissionEntity> getPermissionsById(Long adminId) {
        List<PermissionEntity> list = new ArrayList<>();
        List<Long> roleIds= this.getRoleIdsById(adminId);
        for (Long roleId : roleIds) {
            list.addAll(roleService.getPermissionsById(roleId));
        }

        return list;
    }

    // 根据用户id获取角色列表
    public List<RoleEntity> getRolesById(Long adminId) {
        QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>();
        List<Long> roleIds= this.getRoleIdsById(adminId);

        queryWrapper.in("id", roleIds);

        return roleMapper.selectList(queryWrapper);
    }

    // 根据用户id获取角色Id列表
    public List<Long> getRoleIdsById(Long adminId) {
        List<Long> list = new ArrayList<>();

        QueryWrapper<UserHasRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", adminId);
        queryWrapper.eq("guard_name", "admin");

        List<UserHasRoleEntity> userHasRoles = userHasRoleMapper.selectList(queryWrapper);
        for (UserHasRoleEntity userHasRole : userHasRoles) {
            list.add(userHasRole.getRoleId());
        }

        return list;
    }

    // 根据用户id获取菜单列表
    public List<MenuEntity> getMenusById(Long adminId) {
        List<MenuEntity> list = new ArrayList<>();
        if (adminId == 1) {
            list = menuService.getList();
        }

        return list;
    }

    public boolean hasMenu(List<MenuEntity> menus, long id) {
        return menus.stream().anyMatch(menu -> menu.getId() == id);
    }

    // 根据用户id获取菜单Tree
    public ArrayNode getMenuTreeById(Long adminId) {
        List<MenuEntity> list = getMenusById(adminId);
        ArrayNode menuTree = new ObjectMapper().createArrayNode();

        List<MenuEntity> newMenus = new ArrayList<>();

        for (MenuEntity v : list) {
            v.setKey(UUID.randomUUID().toString());
            v.setLocale("menu" + v.getPath().replace("/", "."));

            v.setHideInMenu(!v.getShow());

            if (v.getType() == 2 && v.getIsEngine()) {
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
    public AdminEntity getByUsername(String username) {
        QueryWrapper<AdminEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        AdminEntity adminInfo = adminMapper.selectOne(queryWrapper);

        return adminInfo;
    }
}
