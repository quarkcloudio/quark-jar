package io.quarkcloud.quarkstarter.service.admin.search;

import com.github.yulichang.wrapper.MPJLambdaWrapper;

import io.quarkcloud.quarkadmin.template.resource.impl.SearchImpl;
import io.quarkcloud.quarkcore.service.Context;

public class Input<T> extends SearchImpl<T> {

    // 构造方法
    public Input(String column, String name) {
        this.column = column;
        this.name = name;
    }

    // 应用查询条件方法
    public MPJLambdaWrapper<T> apply(Context context, MPJLambdaWrapper<T> query, Object value) {
        return query.like(column, value);
    }
}
