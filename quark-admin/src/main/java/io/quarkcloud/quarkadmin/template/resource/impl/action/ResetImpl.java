package io.quarkcloud.quarkadmin.template.resource.impl.action;

import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.ActionImpl;

public class ResetImpl<M, T> extends ActionImpl<ResourceMapper<T>, T> {

    // 构造函数
    public ResetImpl() {
        this.setActionType("reset");
    }
}
