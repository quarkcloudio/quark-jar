package io.quarkcloud.quarkadmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.quarkcloud.quarkadmin.component.form.fields.Checkbox.Option;
import io.quarkcloud.quarkadmin.entity.PermissionEntity;
import io.quarkcloud.quarkadmin.entity.RoleEntity;
import io.quarkcloud.quarkadmin.entity.RoleMenuEntity;
import io.quarkcloud.quarkadmin.entity.RoleDepartmentEntity;
import io.quarkcloud.quarkadmin.entity.DepartmentEntity;
import io.quarkcloud.quarkadmin.entity.RolePermissionEntity;
import io.quarkcloud.quarkadmin.mapper.PermissionMapper;
import io.quarkcloud.quarkadmin.mapper.RoleMenuMapper;
import io.quarkcloud.quarkadmin.mapper.RoleDepartmentMapper;
import io.quarkcloud.quarkadmin.mapper.RoleHasPermissionMapper;
import io.quarkcloud.quarkadmin.mapper.RoleMapper;
import io.quarkcloud.quarkadmin.service.RoleService;
import io.quarkcloud.quarkadmin.service.DepartmentService;
import jakarta.annotation.Resource;

@Service
public class RoleServiceImpl extends ResourceServiceImpl<RoleMapper, RoleEntity> implements RoleService {
    
    // 角色表
    @Resource
    private RoleMapper roleMapper;

    // 角色权限关联表
    @Resource
    private RoleHasPermissionMapper roleHasPermissionMapper;

    // 角色菜单关联表
    @Resource
    private RoleMenuMapper roleHasMenuMapper;

    // 角色部门关联表
    @Resource
    private RoleDepartmentMapper roleHasDepartmentMapper;

    // 权限表
    @Resource
    private PermissionMapper permissionMapper;

    // 部门
    @Autowired
    private DepartmentService departmentService;

    // 根据角色id获取权限列表
    public List<PermissionEntity> getPermissionsById(Long roleId) {
        QueryWrapper<PermissionEntity> queryWrapper = new QueryWrapper<>();
        List<Long> PermissionIds= this.getPermissionIdsById(roleId);

        queryWrapper.in("id", PermissionIds);

        return permissionMapper.selectList(queryWrapper);
    }

    // 根据角色id获取权限ID列表
    public List<Long> getPermissionIdsById(Long roleId) {
        List<Long> list = new ArrayList<>();
        QueryWrapper<RolePermissionEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        queryWrapper.eq("guard_name", "admin");

        List<RolePermissionEntity> roleHasPermissions = roleHasPermissionMapper.selectList(queryWrapper);
        for (RolePermissionEntity roleHasPermission : roleHasPermissions) {
            list.add(roleHasPermission.getPermissionId());
        }
        
        return list;
    }

    // 根据角色Id获取菜单Id列表
    public List<Long> getMenuIdsById(Long roleId) {
        List<Long> list = new ArrayList<>();
        QueryWrapper<RoleMenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        queryWrapper.eq("guard_name", "admin");

        List<RoleMenuEntity> roleHasMenus = roleHasMenuMapper.selectList(queryWrapper);
        for (RoleMenuEntity roleHasMenu : roleHasMenus) {
            list.add(roleHasMenu.getMenuId());
        }
        
        return list;
    }

    // 根据角色id获取部门ID列表
    public List<Long> getDepartmentIdsById(Long roleId) {
        List<Long> list = new ArrayList<>();
        QueryWrapper<RoleDepartmentEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        queryWrapper.eq("guard_name", "admin");

        List<RoleDepartmentEntity> roleHasDepartments = roleHasDepartmentMapper.selectList(queryWrapper);
        for (RoleDepartmentEntity roleHasDepartment : roleHasDepartments) {
            list.add(roleHasDepartment.getDepartmentId());
        }
        
        return list;
    }

    // 根据角色id获取部门列表
    public List<DepartmentEntity> getDepartmentsById(Long roleId) {
        List<DepartmentEntity> list = new ArrayList<>();
        QueryWrapper<RoleDepartmentEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        queryWrapper.eq("guard_name", "admin");
        List<RoleDepartmentEntity> roleHasDepartments = roleHasDepartmentMapper.selectList(queryWrapper);
        for (RoleDepartmentEntity roleHasDepartment : roleHasDepartments) {
            list.add(departmentService.getById(roleHasDepartment.getDepartmentId()));
        }
        
        return list;
    }

    // 获取复选框选项
    public List<Option> getCheckboxOptions() {
        List<Option> list = new ArrayList<>();
        QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        List<RoleEntity> roleEntities = this.list(queryWrapper);
        for (RoleEntity roleEntity : roleEntities) {
            list.add(new Option(roleEntity.getName(), roleEntity.getId()));
        }
        return list;
    }

    // 添加菜单
    public boolean addMenu(Long roleId, Long menuId) {
        RoleMenuEntity roleHasMenuEntity = new RoleMenuEntity();
        roleHasMenuEntity.setRoleId(roleId);
        roleHasMenuEntity.setMenuId(menuId);
        roleHasMenuEntity.setGuardName("admin");
        Integer result = this.roleHasMenuMapper.insert(roleHasMenuEntity);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    // 删除菜单
    public boolean removeAllMenus(Long roleId) {
        QueryWrapper<RoleMenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        queryWrapper.eq("guard_name", "admin");
        Integer result = this.roleHasMenuMapper.delete(queryWrapper);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    // 添加权限
    public boolean addPermission(Long roleId, Long permissionId) {
        RolePermissionEntity roleHasPermissionEntity = new RolePermissionEntity();
        roleHasPermissionEntity.setRoleId(roleId);
        roleHasPermissionEntity.setPermissionId(permissionId);
        roleHasPermissionEntity.setGuardName("admin");

        QueryWrapper<RolePermissionEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        queryWrapper.eq("permission_id", permissionId);
        queryWrapper.eq("guard_name", "admin");

        // 判断是否已存在
        if (this.roleHasPermissionMapper.selectOne(queryWrapper) != null) {
            return true;
        }

        // 添加
        Integer result = this.roleHasPermissionMapper.insert(roleHasPermissionEntity);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    // 删除权限
    public boolean removeAllPermissions(Long roleId) {
        QueryWrapper<RolePermissionEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        queryWrapper.eq("guard_name", "admin");
        Integer result = this.roleHasPermissionMapper.delete(queryWrapper);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    // 添加部门
    public boolean addDepartment(Long roleId, Long departmentId) {
        RoleDepartmentEntity roleHasDepartmentEntity = new RoleDepartmentEntity();
        roleHasDepartmentEntity.setRoleId(roleId);
        roleHasDepartmentEntity.setDepartmentId(departmentId);
        roleHasDepartmentEntity.setGuardName("admin");
        Integer result = this.roleHasDepartmentMapper.insert(roleHasDepartmentEntity);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    // 删除部门
    public boolean removeAllDepartments(Long roleId) {
        QueryWrapper<RoleDepartmentEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        queryWrapper.eq("guard_name", "admin");
        Integer result = this.roleHasDepartmentMapper.delete(queryWrapper);
        if (result > 0) {
            return true;
        }
        return false;
    }

    public boolean updateDataScope(Long roleId, Short dataScope, List<Long> departmentIds) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(roleId);
        roleEntity.setDataScope(dataScope);
        boolean result = this.updateById(roleEntity);
        if (result) {
            if (dataScope==2) {
                this.removeAllDepartments(roleId);
                for (int i = 0; i < departmentIds.size(); i++) {
                    this.addDepartment(roleId, departmentIds.get(i));
                }
            } else {
                this.removeAllDepartments(roleId);
            }
        }
        return result;
    }
}
