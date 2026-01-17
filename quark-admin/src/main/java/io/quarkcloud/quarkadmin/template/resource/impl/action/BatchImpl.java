package io.quarkcloud.quarkadmin.template.resource.impl.action;

import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.ActionImpl;

public class BatchImpl<M, T> extends ActionImpl<ResourceMapper<T>, T> {

    // 构造函数
    public BatchImpl() {
        this.setActionType("ajax");
        this.setBatch(true);
        this.setSize("small");
        this.setType("default");
    }
}