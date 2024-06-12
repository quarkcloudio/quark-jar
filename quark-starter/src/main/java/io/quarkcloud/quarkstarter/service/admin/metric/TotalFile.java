package io.quarkcloud.quarkstarter.service.admin.metric;

import java.util.Map;
import io.quarkcloud.quarkadmin.template.metrics.Value;

public class TotalFile extends Value {

    // 构造方法
    public TotalFile() {
        this.title = "文件数量";
        this.col = 6;
    }

    // 计算
    public Object calculate() {
        return this.value(12).setValueStyle(Map.of("color", "#cf1322"));
    }
}
