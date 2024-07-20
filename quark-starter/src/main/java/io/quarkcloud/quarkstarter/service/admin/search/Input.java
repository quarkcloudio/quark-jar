package io.quarkcloud.quarkstarter.service.admin.search;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.quarkcloud.quarkadmin.template.resource.impl.SearchImpl;
import io.quarkcloud.quarkcore.service.Context;

public class Input<T> extends SearchImpl<T> {

    // 构造方法
    public Input(String column, String name) {
        this.column = column;
        this.name = name;
    }

    // 应用查询条件方法
    public QueryWrapper<T> apply(Context ctx, QueryWrapper<T> query, Object value) {
        return query.like(column, value);
    }
}
