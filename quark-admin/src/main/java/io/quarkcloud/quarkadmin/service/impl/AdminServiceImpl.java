package io.quarkcloud.quarkadmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.quarkcloud.quarkadmin.entity.Admin;
import io.quarkcloud.quarkadmin.entity.Menu;
import io.quarkcloud.quarkadmin.entity.Permission;
import io.quarkcloud.quarkadmin.entity.Role;
import io.quarkcloud.quarkadmin.entity.UserHasRole;
import io.quarkcloud.quarkadmin.mapper.AdminMapper;
import io.quarkcloud.quarkadmin.mapper.RoleMapper;
import io.quarkcloud.quarkadmin.mapper.UserHasRoleMapper;
import io.quarkcloud.quarkadmin.service.AdminService;
import io.quarkcloud.quarkadmin.service.RoleService;

public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    
    // 用户角色关联表
    private UserHasRoleMapper userHasRoleMapper;

    // 用户角色关联表
    private RoleMapper roleMapper;

    // 用户角色
    private RoleService roleService;

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
        this.roleService = new RoleServiceImpl();
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
            
        }

        return list;
    }
}
