package io.quarkcloud.quarkstarter.service.admin.action;

import io.quarkcloud.quarkadmin.template.resource.impl.action.Link;

public class Create extends Link {

    // 构造函数
    public Create(String title) {

        // 文字
        this.name = "创建" + title;

        // 类型
        this.type = "primary";

        // 图标
        this.icon = "plus-circle";

        // 设置展示位置
        this.setOnlyOnIndex(true);
    }
}
