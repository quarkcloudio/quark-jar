package io.quarkcloud.quarkadmin.service;

import java.util.List;

import io.quarkcloud.quarkcore.service.Context;

public interface ResourceService<T> {

    // 获取列表
    public List<T> list(Context ctx);
}
