package io.quarkcloud.quarkadmin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.service.ResourceService;
import io.quarkcloud.quarkcore.service.Context;

public class ResourceServiceImpl<M extends ResourceMapper<T>, T> implements ResourceService<T> {
    
    // 资源mapper
    @Autowired
    protected M resourceMapper;

    // 上下文
    protected Context context;

    // 查询条件
    protected Wrapper<T> queryWrapper;

    // 设置上下文
    public ResourceServiceImpl<M, T> setContext(Context context) {
        this.context = context;
        return this;
    }

    // 设置查询条件
    public ResourceServiceImpl<M, T> setQueryWrapper(Wrapper<T> queryWrapper) {
        this.queryWrapper = queryWrapper;
        return this;
    }

    // 获取列表
    public List<T> list() {
        return this.resourceMapper.selectList(queryWrapper);
    }

    // 获取分页数据
    public IPage<T> page(IPage<T> page) {
        return this.resourceMapper.selectPage(page, queryWrapper);
    }
}