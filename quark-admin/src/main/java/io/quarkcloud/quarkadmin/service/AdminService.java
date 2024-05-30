package io.quarkcloud.quarkadmin.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import io.quarkcloud.quarkadmin.entity.Admin;
import io.quarkcloud.quarkadmin.entity.Permission;
import io.quarkcloud.quarkadmin.entity.Role;

public interface AdminService extends IService<Admin> {

    // 根据用户id获取权限列表
    public List<Permission> getPremissionsById(Long adminId);

    // 根据用户id获取角色列表
    public List<Role> getRolesById(Long adminId);

    // 根据用户id获取角色Id列表
    public List<Long> getRoleIdsById(Long adminId);
}
