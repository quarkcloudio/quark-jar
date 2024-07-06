package io.quarkcloud.quarkadmin.service;

import java.util.List;

public interface ResourceService<T> {

    // 获取列表
    public List<T> list();
}
