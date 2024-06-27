package io.quarkcloud.quarkadmin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.quarkcloud.quarkadmin.entity.MenuEntity;
import io.quarkcloud.quarkadmin.mapper.MenuHasPermissionMapper;
import io.quarkcloud.quarkadmin.mapper.MenuMapper;
import io.quarkcloud.quarkadmin.service.MenuService;
import jakarta.annotation.Resource;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements MenuService {
    
    // 菜单
    @Resource
    private MenuMapper menuMapper;

    // 菜单权限关联
    @Resource
    private MenuHasPermissionMapper menuHasPermissionMapper;

    // 获取菜单列表
    public List<MenuEntity> getList() {
        QueryWrapper<MenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("guard_name", "admin");
        queryWrapper.eq("status", 1);

        List<MenuEntity> list = menuMapper.selectList(queryWrapper);

        return list;
    }
}
