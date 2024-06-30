package io.quarkcloud.quarkadmin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.service.ResourceService;
import io.quarkcloud.quarkcore.service.Context;

public class ResourceServiceImpl<M extends ResourceMapper<T>, T> implements ResourceService<T> {
    
    // 获取mapper
    @Autowired
    protected M resourceMapper;

    // 查询条件
    protected Wrapper<T> queryWrapper;

    // 获取列表
    public List<T> list(Context ctx) {
        return this.resourceMapper.selectList(queryWrapper);
    }
}