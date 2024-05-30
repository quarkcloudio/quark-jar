package io.quarkcloud.quarkadmin.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.quarkcloud.quarkadmin.entity.Menu;
import io.quarkcloud.quarkadmin.mapper.MenuMapper;
import io.quarkcloud.quarkadmin.service.MenuService;

public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    
    // 根据用户id获取菜单列表
    public List<Menu> getListByUserId(String userId) {
        return null;
    }
}
