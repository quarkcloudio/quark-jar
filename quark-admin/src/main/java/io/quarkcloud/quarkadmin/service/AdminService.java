package io.quarkcloud.quarkadmin.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.node.ArrayNode;

import io.quarkcloud.quarkadmin.entity.Admin;
import io.quarkcloud.quarkadmin.entity.Menu;
import io.quarkcloud.quarkadmin.entity.Permission;
import io.quarkcloud.quarkadmin.entity.Role;

public interface AdminService extends IService<Admin> {

    public boolean checkPermission(Long adminId, String urlPath, String method);

    // 根据用户id获取权限列表
    public List<Permission> getPermissionsById(Long adminId);

    // 根据用户id获取角色列表
    public List<Role> getRolesById(Long adminId);

    // 根据用户id获取角色Id列表
    public List<Long> getRoleIdsById(Long adminId);

    // 根据用户id获取菜单列表
    public List<Menu> getMenusById(Long adminId);

    // 根据用户id获取菜单Tree
    public ArrayNode getMenuTreeById(Long adminId);
}
