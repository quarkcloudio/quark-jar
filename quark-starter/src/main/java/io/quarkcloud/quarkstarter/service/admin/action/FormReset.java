package io.quarkcloud.quarkstarter.service.admin.action;

import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.action.ResetImpl;

public class FormReset<M, T> extends ResetImpl<ResourceMapper<T>, T> {

    // 构造函数
    public FormReset() {
        this.name = "重置";

        // 类型
        this.type = "default";

        // 设置展示位置
        this.setShowOnForm();
    }

    // 构造函数，带名称参数
    public FormReset(String name) {
        this.name = name;
        
        // 类型
        this.type = "default";

        // 设置展示位置
        this.setShowOnForm();
    }
}
