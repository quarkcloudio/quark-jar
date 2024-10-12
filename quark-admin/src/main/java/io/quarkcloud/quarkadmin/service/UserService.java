package io.quarkcloud.quarkadmin.service;

import java.util.List;

import io.quarkcloud.quarkadmin.entity.UserEntity;
import io.quarkcloud.quarkadmin.entity.PermissionEntity;
import io.quarkcloud.quarkadmin.entity.RoleEntity;
import io.quarkcloud.quarkadmin.entity.DepartmentEntity;
import io.quarkcloud.quarkadmin.mapper.UserMapper;

public interface UserService extends ResourceService<UserMapper, UserEntity> {

    public boolean checkPermission(Long adminId, String urlPath, String method);

    // 根据用户id获取权限列表
    public List<PermissionEntity> getPermissionsById(Long adminId);

    // 根据用户id获取角色列表
    public List<RoleEntity> getRolesById(Long adminId);

    // 根据用户id获取角色Id列表
    public List<Long> getRoleIdsById(Long adminId);

    // 根据用户id获取菜单Id列表
    public List<Long> getMenuIdsById(Long adminId);

    // 根据用户id获取部门Id列表
    public List<DepartmentEntity> getDepartmentsById(Long adminId);

    // 根据账号查询
    public UserEntity getByUsername(String username);

    // 给管理员添加角色
    public boolean addRole(Long adminId, Long roleId);

    // 清理管理员所有角色
    public boolean removeAllRoles(Long adminId);
}
