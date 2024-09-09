package io.quarkcloud.quarkstarter.service.admin.action;

import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.action.SubmitImpl;

public class FormSubmit<M, T> extends SubmitImpl<ResourceMapper<T>, T> {

    // 构造函数
    public FormSubmit() {

        this.name = "提交";

        // 类型
        this.setType("primary");

        // 是否具有loading，当action 的作用类型为ajax,submit时有效
        this.setWithLoading(true);

        // 设置展示位置
        this.setOnlyOnForm(true);
    }

    // 构造函数
    public FormSubmit(String name) {

        this.name = name;

        // 类型
        this.setType("primary");

        // 是否具有loading，当action 的作用类型为ajax,submit时有效
        this.setWithLoading(true);

        // 设置展示位置
        this.setOnlyOnForm(true);
    }
}
