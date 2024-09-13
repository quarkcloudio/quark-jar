package io.quarkcloud.quarkadmin.service;

import io.quarkcloud.quarkadmin.entity.ConfigEntity;
import io.quarkcloud.quarkadmin.mapper.ConfigMapper;

public interface ConfigService extends ResourceService<ConfigMapper, ConfigEntity> {

    /**
     * 根据给定的键获取相应的值
     * 
     * @param key 要获取值的键
     * @return 与键对应的值
     */
    public Object getValue(String key);
}
