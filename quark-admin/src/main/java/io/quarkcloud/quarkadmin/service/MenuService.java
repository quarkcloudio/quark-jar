package io.quarkcloud.quarkadmin.service;

import java.util.List;
import io.quarkcloud.quarkadmin.entity.MenuEntity;
import io.quarkcloud.quarkadmin.mapper.MenuMapper;

public interface MenuService extends ResourceService<MenuMapper, MenuEntity> {

    // 获取菜单列表
    public List<MenuEntity> getList();
}
