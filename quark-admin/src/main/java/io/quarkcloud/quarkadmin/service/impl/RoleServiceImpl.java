package io.quarkcloud.quarkadmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.quarkcloud.quarkadmin.entity.Permission;
import io.quarkcloud.quarkadmin.entity.Role;
import io.quarkcloud.quarkadmin.entity.RoleHasPermission;
import io.quarkcloud.quarkadmin.mapper.PermissionMapper;
import io.quarkcloud.quarkadmin.mapper.RoleHasPermissionMapper;
import io.quarkcloud.quarkadmin.mapper.RoleMapper;
import io.quarkcloud.quarkadmin.service.RoleService;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    
    // 角色权限关联表
    private RoleHasPermissionMapper roleHasPermissionMapper;

    // 权限表
    private PermissionMapper permissionMapper;

    // 根据角色id获取权限列表
    public List<Permission> getPermissionsById(Long roleId) {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        List<Long> PermissionIds= this.getPermissionIdsById(roleId);

        queryWrapper.in("id", PermissionIds);

        return permissionMapper.selectList(queryWrapper);
    }

    // 根据角色id获取权限ID列表
    public List<Long> getPermissionIdsById(Long roleId) {
        List<Long> list = new ArrayList<>();
        QueryWrapper<RoleHasPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        queryWrapper.eq("guard_name", "admin");

        List<RoleHasPermission> roleHasPermissions = roleHasPermissionMapper.selectList(queryWrapper);
        for (RoleHasPermission roleHasPermission : roleHasPermissions) {
            list.add(roleHasPermission.getPermissionId());
        }
        
        return list;
    }
}
