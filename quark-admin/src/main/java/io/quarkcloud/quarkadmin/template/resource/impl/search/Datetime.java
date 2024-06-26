package io.quarkcloud.quarkadmin.template.resource.impl.search;

import io.quarkcloud.quarkcore.service.Context;

public class Datetime extends SearchImpl {

    // 构造方法
    public Datetime(Context ctx) {
        this.component = "datetimeField";
    }
}
