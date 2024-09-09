package io.quarkcloud.quarkadmin.template.resource.impl.action;

import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.ActionImpl;

public class SubmitImpl<M, T> extends ActionImpl<ResourceMapper<T>, T> {

    // 构造函数
    public SubmitImpl() {
        this.setActionType("submit");
    }
}
