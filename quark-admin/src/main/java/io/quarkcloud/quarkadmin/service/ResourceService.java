package io.quarkcloud.quarkadmin.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.service.impl.ResourceServiceImpl;
import io.quarkcloud.quarkcore.service.Context;

public interface ResourceService<M extends ResourceMapper<T>, T> {

    // 设置上下文
    public ResourceServiceImpl<M, T> setContext(Context context);

    // 设置查询条件
    public ResourceServiceImpl<M, T> setQueryWrapper(Wrapper<T> queryWrapper);

    // 使用列表查询条件
    public ResourceServiceImpl<M, T> indexQueryWrapper();

    // 获取列表
    public List<T> list();

    // 获取分页数据
    public IPage<T> page(IPage<T> page);
}
