package io.quarkcloud.quarkadmin.template.resource.impl.search;

import io.quarkcloud.quarkadmin.template.resource.impl.SearchImpl;
import io.quarkcloud.quarkcore.service.Context;

public class Date extends SearchImpl {

    // 构造方法
    public Date(Context ctx) {
        this.component = "dateField";
    }
}
