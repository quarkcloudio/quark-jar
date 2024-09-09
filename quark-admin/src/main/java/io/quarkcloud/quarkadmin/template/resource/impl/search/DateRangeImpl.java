package io.quarkcloud.quarkadmin.template.resource.impl.search;

import io.quarkcloud.quarkadmin.template.resource.impl.SearchImpl;

public class DateRangeImpl<T> extends SearchImpl<T> {

    // 构造方法
    public DateRangeImpl() {
        this.component = "dateRangeField";
    }
}
