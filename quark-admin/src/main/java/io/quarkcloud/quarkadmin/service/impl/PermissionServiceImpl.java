package io.quarkcloud.quarkadmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.quarkcloud.quarkadmin.entity.MenuHasPermissionEntity;
import io.quarkcloud.quarkadmin.entity.PermissionEntity;
import io.quarkcloud.quarkadmin.mapper.MenuHasPermissionMapper;
import io.quarkcloud.quarkadmin.mapper.PermissionMapper;
import io.quarkcloud.quarkadmin.service.PermissionService;
import jakarta.annotation.Resource;

@Service
public class PermissionServiceImpl extends ResourceServiceImpl<PermissionMapper, PermissionEntity> implements PermissionService {
    
    // 权限
    @Resource
    private PermissionMapper permissionMapper;

    // 菜单权限关联表
    @Resource
    private MenuHasPermissionMapper menuHasPermissionMapper;

    // 根据权限ID获取菜单ID集合
    public List<Long> getMenuIdsById (Long permissionId) {
        List<Long> menuIds = new ArrayList<Long>();
        QueryWrapper<MenuHasPermissionEntity> query = new QueryWrapper<>();
        query.eq("permission_id", permissionId);
        List<MenuHasPermissionEntity>  menuHasPermissions= menuHasPermissionMapper.selectList(query);
        for (MenuHasPermissionEntity menuHasPermission : menuHasPermissions) {
            menuIds.add(menuHasPermission.getMenuId());
        }
        return menuIds;
    }
}
