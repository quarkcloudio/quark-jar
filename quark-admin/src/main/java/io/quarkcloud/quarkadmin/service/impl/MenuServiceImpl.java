package io.quarkcloud.quarkadmin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.quarkcloud.quarkadmin.entity.Menu;
import io.quarkcloud.quarkadmin.mapper.MenuHasPermissionMapper;
import io.quarkcloud.quarkadmin.mapper.MenuMapper;
import io.quarkcloud.quarkadmin.service.MenuService;

public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    
    // 菜单
    private MenuMapper menuMapper;

    // 菜单权限关联
    private MenuHasPermissionMapper menuHasPermissionMapper;

    // 获取菜单列表
    public List<Menu> getList() {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("guard_name", "admin");
        queryWrapper.eq("status", 1);

        List<Menu> list = menuMapper.selectList(queryWrapper);

        return list;
    }
}
