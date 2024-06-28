package io.quarkcloud.quarkadmin.template.resource.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.quarkcloud.quarkadmin.template.resource.Service;
import io.quarkcloud.quarkcore.service.Context;

public class ServiceImpl<M extends BaseMapper<T>, T> extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<M,T> implements Service<T> {
    
    // 查询条件
    private Wrapper<T> queryWrapper;

    // 获取列表
    public List<T> list(Context ctx) {
        return this.list();
    }
}
