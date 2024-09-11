package io.quarkcloud.quarkadmin.service;

import java.util.List;

import io.quarkcloud.quarkadmin.entity.PermissionEntity;
import io.quarkcloud.quarkadmin.mapper.PermissionMapper;

public interface PermissionService extends ResourceService<PermissionMapper, PermissionEntity> {

    // 根据权限ID获取菜单ID集合
    public List<Long> getMenuIdsById (Long permissionId);
}
