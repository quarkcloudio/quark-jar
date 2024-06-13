package io.quarkcloud.quarkstarter.service.admin.metric;

import java.util.Map;

import io.quarkcloud.quarkadmin.entity.ActionLog;
import io.quarkcloud.quarkadmin.template.metrics.Value;

public class TotalLog extends Value {

    // 构造方法
    public TotalLog() {
        this.title = "日志数量";
        this.col = 6;
    }

    // 计算
    public Object calculate() {
        long count = new ActionLog().selectCount(null);
        return this.value(count).setValueStyle(Map.of("color", "#999999"));
    }
}
