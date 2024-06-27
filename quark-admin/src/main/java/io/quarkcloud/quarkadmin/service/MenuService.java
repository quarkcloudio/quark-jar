package io.quarkcloud.quarkadmin.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import io.quarkcloud.quarkadmin.entity.MenuEntity;

public interface MenuService extends IService<MenuEntity> {

    // 获取菜单列表
    public List<MenuEntity> getList();
}
