package io.quarkcloud.quarkstarter.service.admin.metric;

import java.util.Map;
import io.quarkcloud.quarkadmin.template.metrics.Value;

public class TotalAdmin extends Value {

    // 构造方法
    public TotalAdmin() {
        this.title = "管理员数量";
        this.col = 6;
    }

    // 计算
    public Object calculate() {
        return this.value(10).setValueStyle(Map.of("color", "#3f8600"));
    }
}
