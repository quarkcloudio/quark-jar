package io.quarkcloud.quarkadmin.service;

import java.util.List;
import io.quarkcloud.quarkadmin.entity.PermissionEntity;
import io.quarkcloud.quarkadmin.entity.RoleEntity;
import io.quarkcloud.quarkadmin.mapper.RoleMapper;

public interface RoleService extends ResourceService<RoleMapper, RoleEntity> {

    // 根据角色id获取权限列表
    public List<PermissionEntity> getPermissionsById(Long roleId);

    // 根据角色id获取权限ID列表
    public List<Long> getPermissionIdsById(Long roleId);
}
