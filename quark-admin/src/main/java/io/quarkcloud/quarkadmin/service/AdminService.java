package io.quarkcloud.quarkadmin.service;

import java.util.List;
import com.fasterxml.jackson.databind.node.ArrayNode;

import io.quarkcloud.quarkadmin.entity.AdminEntity;
import io.quarkcloud.quarkadmin.entity.MenuEntity;
import io.quarkcloud.quarkadmin.entity.PermissionEntity;
import io.quarkcloud.quarkadmin.entity.RoleEntity;
import io.quarkcloud.quarkadmin.template.resource.Service;

public interface AdminService extends Service<AdminEntity> {

    public boolean checkPermission(Long adminId, String urlPath, String method);

    // 根据用户id获取权限列表
    public List<PermissionEntity> getPermissionsById(Long adminId);

    // 根据用户id获取角色列表
    public List<RoleEntity> getRolesById(Long adminId);

    // 根据用户id获取角色Id列表
    public List<Long> getRoleIdsById(Long adminId);

    // 根据用户id获取菜单列表
    public List<MenuEntity> getMenusById(Long adminId);

    // 根据用户id获取菜单Tree
    public ArrayNode getMenuTreeById(Long adminId);

    // 根据账号查询
    public AdminEntity getByUsername(String username);
}
