package io.quarkcloud.quarkadmin.service;

import java.util.List;

import com.fasterxml.jackson.databind.node.ArrayNode;

import io.quarkcloud.quarkadmin.component.form.fields.TreeSelect.TreeData;
import io.quarkcloud.quarkadmin.entity.MenuEntity;
import io.quarkcloud.quarkadmin.mapper.MenuMapper;

public interface MenuService extends ResourceService<MenuMapper, MenuEntity> {

    // 获取菜单列表
    public List<MenuEntity> getList();

    // 获取树结构数据，根节点可选
    public List<TreeData> treeSelect(boolean root);

    // 获取树结构数据
    public List<io.quarkcloud.quarkadmin.component.form.fields.Tree.TreeData> tree();

    // 根据管理员 ID 获取菜单列表
    public ArrayNode getListByAdminId(Long adminId);

    // 给菜单绑定权限
    public boolean addPermission(Long menuId, Long permissionId);

    // 清理菜单所有权限
    public boolean removeAllPermissions(Long menuId);
}
