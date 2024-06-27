package io.quarkcloud.quarkstarter.service.admin.metric;

import java.util.Map;
import io.quarkcloud.quarkadmin.entity.AdminEntity;
import io.quarkcloud.quarkadmin.template.metrics.impl.Value;

public class TotalAdmin extends Value {

    // 构造方法
    public TotalAdmin() {
        this.title = "管理员数量";
        this.col = 6;
    }

    // 计算
    public Object calculate() {
        long count = new AdminEntity().selectCount(null);

        return this.value(count).setValueStyle(Map.of("color", "#3f8600"));
    }
}
