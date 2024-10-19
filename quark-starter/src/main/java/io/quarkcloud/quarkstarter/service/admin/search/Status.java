package io.quarkcloud.quarkstarter.service.admin.search;

import java.util.Arrays;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

import io.quarkcloud.quarkadmin.template.resource.impl.search.SelectImpl;
import io.quarkcloud.quarkcore.service.Context;

public class Status<T> extends SelectImpl<T> {

    // 构造方法
    public Status() {
        this.name = "状态";
    }

    // 应用查询条件方法
    public MPJLambdaWrapper<T> apply(Context context, MPJLambdaWrapper<T> query, Object value) {
        return query.eq("status", value);
    }

    public Object options(Context context) {
        return Arrays.asList(
            this.option("禁用",0),
            this.option("启用",1)
        );
    }
}
