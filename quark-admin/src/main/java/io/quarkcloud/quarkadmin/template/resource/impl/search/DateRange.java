package io.quarkcloud.quarkadmin.template.resource.impl.search;

import io.quarkcloud.quarkadmin.template.resource.impl.SearchImpl;

public class DateRange<T> extends SearchImpl<T> {

    // 构造方法
    public DateRange() {
        this.component = "dateRangeField";
    }
}
