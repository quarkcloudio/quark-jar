package io.quarkcloud.quarkstarter.service.admin.dashboard;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.template.Dashboard;
import io.quarkcloud.quarkadmin.template.metrics.Metrics;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkstarter.service.admin.metric.SystemInfo;
import io.quarkcloud.quarkstarter.service.admin.metric.TeamInfo;

@Component(value = "dashboardIndex")
public class Index extends Dashboard {

    // 构造函数
    public Index() {

    }

    // 内容
    public List<Metrics> cards(Context context) {
        return Arrays.asList(
            new SystemInfo(),
            new TeamInfo()
        );
    }
}