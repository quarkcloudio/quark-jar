package io.quarkcloud.quarkadmin.template.dashboard;

import java.util.List;
import io.quarkcloud.quarkadmin.template.metrics.Metrics;
import io.quarkcloud.quarkcore.service.Context;

public interface Dashboard {
    // 内容
    public List<Metrics> cards(Context context);

    // 组件渲染
    public Object render(Context context);
}
