package io.quarkcloud.quarkadmin.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;

import io.quarkcloud.quarkcore.service.Context;

public interface ResourceService<T> extends IService<T> {

    // 获取列表
    public List<T> getList(Context ctx);
}
