package io.quarkcloud.quarkadmin.template.resource.impl.search;

import io.quarkcloud.quarkcore.service.Context;

public class DateRange extends SearchImpl {

    // 构造方法
    public DateRange(Context ctx) {
        this.component = "dateRangeField";
    }
}
