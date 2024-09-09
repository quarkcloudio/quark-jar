package io.quarkcloud.quarkadmin.template.resource.impl.search;

import io.quarkcloud.quarkadmin.template.resource.impl.SearchImpl;

public class DateImpl<T> extends SearchImpl<T> {

    // 构造方法
    public DateImpl() {
        this.component = "dateField";
    }
}
