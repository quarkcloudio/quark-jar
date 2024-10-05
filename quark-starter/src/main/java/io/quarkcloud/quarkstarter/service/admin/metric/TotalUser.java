package io.quarkcloud.quarkstarter.service.admin.metric;

import java.util.Map;
import io.quarkcloud.quarkadmin.entity.UserEntity;
import io.quarkcloud.quarkadmin.template.metrics.impl.Value;

public class TotalUser extends Value {

    // 构造方法
    public TotalUser() {
        this.title = "用户数量";
        this.col = 6;
    }

    // 计算
    public Object calculate() {
        long count = new UserEntity().selectCount(null);

        return this.value(count).setValueStyle(Map.of("color", "#3f8600"));
    }
}
