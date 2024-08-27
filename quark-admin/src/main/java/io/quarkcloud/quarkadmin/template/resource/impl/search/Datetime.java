package io.quarkcloud.quarkadmin.template.resource.impl.search;

import io.quarkcloud.quarkadmin.template.resource.impl.SearchImpl;
import io.quarkcloud.quarkcore.service.Context;

public class Datetime<T> extends SearchImpl<T> {

    // 构造方法
    public Datetime(Context context) {
        this.component = "datetimeField";
    }
}
