package io.quarkcloud.quarkadmin.template.resource.core;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.quarkcloud.quarkcore.service.Context;

public class PerformQuery<T> {

    // context
    public Context context;

    // 查询条件
    protected QueryWrapper<T> queryWrapper;

    // 构造函数
    public PerformQuery(Context context) {
        this.context = context;
    }

    // 构建查询条件
    public QueryWrapper<T> buildIndexQuery() {
        return null;
    }
}
