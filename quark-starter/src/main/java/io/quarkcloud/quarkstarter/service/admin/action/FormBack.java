package io.quarkcloud.quarkstarter.service.admin.action;

import io.quarkcloud.quarkadmin.template.resource.impl.ActionImpl;

public class FormBack extends ActionImpl {

    // 构造函数
    public FormBack() {

        // 名称
        this("返回上一页");

        // 类型
        this.type = "default";

        // 行为类型
        this.actionType = "back";

        // 在表单页展示
        setShowOnForm();

        // 在详情页展示
        setShowOnDetail();
    }

    // 构造函数，带名称参数
    public FormBack(String name) {

        // 名称
        this.name = name;
        
        // 类型
        this.type = "default";

        // 行为类型
        this.actionType = "back";

        // 在表单页展示
        setShowOnForm();

        // 在详情页展示
        setShowOnDetail();
    }
}
