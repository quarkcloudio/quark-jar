package io.quarkcloud.quarkadmin.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import io.quarkcloud.quarkadmin.entity.Menu;

public interface MenuService extends IService<Menu> {

    // 获取菜单列表
    public List<Menu> getList();
}
