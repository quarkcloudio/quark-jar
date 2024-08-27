package io.quarkcloud.quarkadmin.template.resource.impl.search;

import io.quarkcloud.quarkadmin.template.resource.impl.SearchImpl;
import io.quarkcloud.quarkcore.service.Context;

public class Date<T> extends SearchImpl<T> {

    // 构造方法
    public Date(Context context) {
        this.component = "dateField";
    }
}
