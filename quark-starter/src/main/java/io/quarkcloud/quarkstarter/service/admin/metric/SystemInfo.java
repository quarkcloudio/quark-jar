package io.quarkcloud.quarkstarter.service.admin.metric;

import io.quarkcloud.quarkadmin.template.metrics.Descriptions;
import java.util.Arrays;
import io.quarkcloud.quarkadmin.component.descriptions.Field;

public class SystemInfo extends Descriptions {

    // 构造方法
    public SystemInfo() {
        this.title = "系统信息";
        this.col = 12;
    }

    // 计算
    public Object calculate() {
        Field field = new Field();

        return this.result(Arrays.asList(
            field.text("应用名称").setValue("QuarkJar")
        ));
    }
}
