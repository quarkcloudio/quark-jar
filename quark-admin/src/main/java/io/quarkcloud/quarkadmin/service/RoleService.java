package io.quarkcloud.quarkadmin.service;

import java.util.List;

import io.quarkcloud.quarkadmin.component.form.fields.Checkbox.Option;
import io.quarkcloud.quarkadmin.entity.PermissionEntity;
import io.quarkcloud.quarkadmin.entity.RoleEntity;
import io.quarkcloud.quarkadmin.mapper.RoleMapper;

public interface RoleService extends ResourceService<RoleMapper, RoleEntity> {

    // 根据角色id获取权限列表
    public List<PermissionEntity> getPermissionsById(Long roleId);

    // 根据角色id获取权限ID列表
    public List<Long> getPermissionIdsById(Long roleId);

    // 根据角色id获取菜单ID列表
    public List<Long> getMenuIdsById(Long roleId);

    // 根据角色id获取部门ID列表
    public List<Long> getDepartmentIdsById(Long roleId);

    // 获取Checkbox Option列表
    public List<Option> getCheckboxOptions();

    // 添加菜单
    public boolean addMenu(Long roleId, Long menuId);

    // 删除菜单
    public boolean removeAllMenus(Long roleId);

    // 添加权限
    public boolean addPermission(Long roleId, Long permissionId);

    // 删除权限
    public boolean removeAllPermissions(Long roleId);

    // 添加部门
    public boolean addDepartment(Long roleId, Long menuId);

    // 删除部门
    public boolean removeAllDepartments(Long roleId);

    // 更新数据权限
    public boolean updateDataScope(Long roleId, Short dataScope, List<Long> departmentIds);
}
