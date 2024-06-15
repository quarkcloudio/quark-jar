package io.quarkcloud.quarkstarter.service.admin.metric;

import java.util.Arrays;
import io.quarkcloud.quarkadmin.component.descriptions.Field;
import io.quarkcloud.quarkadmin.template.metrics.impl.Descriptions;

public class TeamInfo extends Descriptions {

    // 构造方法
    public TeamInfo() {
        this.title = "团队信息";
        this.col = 12;
    }

    // 计算
    public Object calculate() {
        Field field = new Field();

        return this.result(Arrays.asList(
            field.text("作者").setValue("tangtanglove"),
            field.text("联系方式").setValue("dai_hang_love@126.com"),
            field.text("官方网址").setValue("<a href='https://quarkcloud.io' target='_blank'>quarkcloud.io</a>"),
            field.text("文档地址").setValue("<a href='https://quarkcloud.io' target='_blank'>查看文档</a>"),
            field.text("BUG反馈").setValue("<a href='https://github.com/quarkcloudio/quark-go/v2/issues' target='_blank'>提交BUG</a>"),
            field.text("代码仓储").setValue("<a href='https://github.com/quarkcloudio/quark-go' target='_blank'>Github</a>")
        ));
    }
}
