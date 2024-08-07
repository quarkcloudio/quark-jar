package io.quarkcloud.quarkstarter.service.admin.action;

import io.quarkcloud.quarkadmin.template.resource.impl.ActionImpl;

public class FormReset extends ActionImpl {

    // 构造函数
    public FormReset() {
        this.name = "重置";

        // 类型
        this.type = "default";

        // 行为类型
        this.actionType = "reset";

        // 设置展示位置
        this.setShowOnForm();
    }

    // 构造函数，带名称参数
    public FormReset(String name) {
        this.name = name;
        
        // 类型
        this.type = "default";

        // 行为类型
        this.actionType = "reset";

        // 设置展示位置
        this.setShowOnForm();
    }
}
