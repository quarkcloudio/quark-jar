package io.quarkcloud.quarkstarter.service.admin.action;

import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.action.BackImpl;

public class FormExtraBack<M, T> extends BackImpl<ResourceMapper<T>, T> {
    // 构造函数
    public FormExtraBack() {
        this.name = "返回上一页";

        // 类型
        this.type = "link";

        // 行为类型
        this.actionType = "back";

        // 在表单页右上角自定义区域展示
        setShowOnFormExtra();

        // 在详情页右上角自定义区域展示
        setShowOnDetailExtra();
    }

    // 构造函数，带名称参数
    public FormExtraBack(String name) {
        this.name = name;

        // 类型
        this.type = "link";

        // 行为类型
        this.actionType = "back";

        // 在表单页右上角自定义区域展示
        setShowOnFormExtra();

        // 在详情页右上角自定义区域展示
        setShowOnDetailExtra();
    }
}
