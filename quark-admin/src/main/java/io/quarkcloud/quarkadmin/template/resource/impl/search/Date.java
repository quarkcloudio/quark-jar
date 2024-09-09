package io.quarkcloud.quarkadmin.template.resource.impl.search;

import io.quarkcloud.quarkadmin.template.resource.impl.SearchImpl;

public class Date<T> extends SearchImpl<T> {

    // 构造方法
    public Date() {
        this.component = "dateField";
    }
}
