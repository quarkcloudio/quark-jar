package io.quarkcloud.quarkadmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.quarkcloud.quarkadmin.entity.AdminEntity;
import io.quarkcloud.quarkadmin.entity.PermissionEntity;
import io.quarkcloud.quarkadmin.entity.RoleEntity;
import io.quarkcloud.quarkadmin.entity.UserHasRoleEntity;
import io.quarkcloud.quarkadmin.mapper.AdminMapper;
import io.quarkcloud.quarkadmin.mapper.RoleMapper;
import io.quarkcloud.quarkadmin.mapper.UserHasRoleMapper;
import io.quarkcloud.quarkadmin.service.AdminService;
import io.quarkcloud.quarkadmin.service.PermissionService;
import io.quarkcloud.quarkadmin.service.RoleService;
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

    // 用户权限
    @Autowired
    private PermissionService permissionService;

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
        if (roleIds.size() == 0) {
            return new ArrayList<RoleEntity>();
        }
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
    public List<Long> getMenuIdsById(Long adminId) {
        List<Long> menuIds= new ArrayList<>();
        List<PermissionEntity> permissions= this.getPermissionsById(adminId);
        for (PermissionEntity permission : permissions) {
            List<Long> getMenuIds = permissionService.getMenuIdsById(permission.getId());
            menuIds.addAll(getMenuIds);
        }
        return menuIds;
    }

    // 根据账号查询
    public AdminEntity getByUsername(String username) {
        QueryWrapper<AdminEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        AdminEntity adminInfo = adminMapper.selectOne(queryWrapper);

        return adminInfo;
    }

    // 给管理员添加角色
    public boolean addRole(Long adminId, Long roleId) {
        UserHasRoleEntity userHasRoleEntity = new UserHasRoleEntity();
        userHasRoleEntity.setUid(adminId);
        userHasRoleEntity.setRoleId(roleId);
        userHasRoleEntity.setGuardName("admin");
        Integer result = this.userHasRoleMapper.insert(userHasRoleEntity);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    // 清理管理员所有角色
    public boolean removeAllRoles(Long adminId) {
        QueryWrapper<UserHasRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", adminId);
        queryWrapper.eq("guard_name", "admin");
        Integer result = this.userHasRoleMapper.delete(queryWrapper);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }
}
