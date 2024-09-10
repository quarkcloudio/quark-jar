package io.quarkcloud.quarkadmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.quarkcloud.quarkadmin.entity.PermissionEntity;
import io.quarkcloud.quarkadmin.entity.RoleEntity;
import io.quarkcloud.quarkadmin.entity.RoleHasPermissionEntity;
import io.quarkcloud.quarkadmin.mapper.PermissionMapper;
import io.quarkcloud.quarkadmin.mapper.RoleHasPermissionMapper;
import io.quarkcloud.quarkadmin.mapper.RoleMapper;
import io.quarkcloud.quarkadmin.service.RoleService;
import jakarta.annotation.Resource;

@Service
public class RoleServiceImpl extends ResourceServiceImpl<RoleMapper, RoleEntity> implements RoleService {
    
    // 角色权限关联表
    @Resource
    private RoleHasPermissionMapper roleHasPermissionMapper;

    // 权限表
    @Resource
    private PermissionMapper permissionMapper;

    // 根据角色id获取权限列表
    public List<PermissionEntity> getPermissionsById(Long roleId) {
        QueryWrapper<PermissionEntity> queryWrapper = new QueryWrapper<>();
        List<Long> PermissionIds= this.getPermissionIdsById(roleId);

        queryWrapper.in("id", PermissionIds);

        return permissionMapper.selectList(queryWrapper);
    }

    // 根据角色id获取权限ID列表
    public List<Long> getPermissionIdsById(Long roleId) {
        List<Long> list = new ArrayList<>();
        QueryWrapper<RoleHasPermissionEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        queryWrapper.eq("guard_name", "admin");

        List<RoleHasPermissionEntity> roleHasPermissions = roleHasPermissionMapper.selectList(queryWrapper);
        for (RoleHasPermissionEntity roleHasPermission : roleHasPermissions) {
            list.add(roleHasPermission.getPermissionId());
        }
        
        return list;
    }
}
