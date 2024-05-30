package io.quarkcloud.quarkadmin.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import io.quarkcloud.quarkadmin.entity.Permission;
import io.quarkcloud.quarkadmin.entity.Role;

public interface RoleService extends IService<Role> {

    // 根据角色id获取权限列表
    public List<Permission> getPremissionsById(Long roleId);

    // 根据角色id获取权限ID列表
    public List<Long> getPremissionIdsById(Long roleId);
}
