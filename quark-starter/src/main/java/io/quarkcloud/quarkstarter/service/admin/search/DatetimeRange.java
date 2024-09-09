package io.quarkcloud.quarkstarter.service.admin.search;

import java.util.List;

import com.github.yulichang.wrapper.MPJLambdaWrapper;

import io.quarkcloud.quarkadmin.template.resource.impl.search.DatetimeRangeImpl;
import io.quarkcloud.quarkcore.service.Context;

public class DatetimeRange<T> extends DatetimeRangeImpl<T> {

    // 构造方法
    public DatetimeRange(String column, String name) {
        this.column = column;
        this.name = name;
    }

    // 应用查询条件方法
    public MPJLambdaWrapper<T> apply(Context context, MPJLambdaWrapper<T> query, Object value) {
        if (!(value instanceof List)) {
            return query;
        }
        List<?> getValue = (List<?>) value;
        return query.between(column, getValue.get(0), getValue.get(1));
    }
}
