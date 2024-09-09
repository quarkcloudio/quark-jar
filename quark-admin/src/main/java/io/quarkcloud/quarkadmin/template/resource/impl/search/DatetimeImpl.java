package io.quarkcloud.quarkadmin.template.resource.impl.search;

import io.quarkcloud.quarkadmin.template.resource.impl.SearchImpl;

public class DatetimeImpl<T> extends SearchImpl<T> {

    // 构造方法
    public DatetimeImpl() {
        this.component = "datetimeField";
    }
}
