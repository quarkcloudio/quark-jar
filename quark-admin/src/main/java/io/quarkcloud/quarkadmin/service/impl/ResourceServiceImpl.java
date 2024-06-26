package io.quarkcloud.quarkadmin.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.quarkcloud.quarkadmin.service.ResourceService;
import io.quarkcloud.quarkcore.service.Context;

public class ResourceServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M,T> implements ResourceService<T> {
    
    // 获取列表
    public List<T> getList(Context ctx) {
        return null;
    }
}
