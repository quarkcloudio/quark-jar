package io.quarkcloud.quarkadmin.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import io.quarkcloud.quarkadmin.entity.PermissionEntity;
import io.quarkcloud.quarkadmin.entity.RoleEntity;

public interface RoleService extends IService<RoleEntity> {

    // 根据角色id获取权限列表
    public List<PermissionEntity> getPermissionsById(Long roleId);

    // 根据角色id获取权限ID列表
    public List<Long> getPermissionIdsById(Long roleId);
}
