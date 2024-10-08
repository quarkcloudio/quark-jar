package io.quarkcloud.quarkstarter.service.admin.dashboard;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.template.dashboard.impl.DashboardImpl;
import io.quarkcloud.quarkadmin.template.metrics.Metrics;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkstarter.service.admin.metric.SystemInfo;
import io.quarkcloud.quarkstarter.service.admin.metric.TeamInfo;
import io.quarkcloud.quarkstarter.service.admin.metric.TotalUser;
import io.quarkcloud.quarkstarter.service.admin.metric.TotalFile;
import io.quarkcloud.quarkstarter.service.admin.metric.TotalLog;
import io.quarkcloud.quarkstarter.service.admin.metric.TotalPicture;

@Component(value = "dashboardIndex")
public class Index extends DashboardImpl {

    // 构造函数
    public Index() {}

    // 内容
    public List<Metrics> cards(Context context) {
        return Arrays.asList(
            new TotalUser(),
            new TotalLog(),
            new TotalFile(),
            new TotalPicture(),
            new SystemInfo(),
            new TeamInfo()
        );
    }
}