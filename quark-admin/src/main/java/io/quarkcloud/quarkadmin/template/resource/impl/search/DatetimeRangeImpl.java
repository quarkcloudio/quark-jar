package io.quarkcloud.quarkadmin.template.resource.impl.search;

import io.quarkcloud.quarkadmin.template.resource.impl.SearchImpl;

public class DatetimeRangeImpl<T> extends SearchImpl<T> {

    // 构造方法
    public DatetimeRangeImpl() {
        this.component = "datetimeRangeField";
    }
}
