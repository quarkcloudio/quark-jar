package io.quarkcloud.quarkadmin.service.impl;

import org.springframework.stereotype.Service;

import com.github.yulichang.wrapper.MPJLambdaWrapper;

import io.quarkcloud.quarkadmin.entity.ConfigEntity;
import io.quarkcloud.quarkadmin.mapper.ConfigMapper;
import io.quarkcloud.quarkadmin.service.ConfigService;

@Service
public class ConfigServiceImpl extends ResourceServiceImpl<ConfigMapper, ConfigEntity> implements ConfigService {

    public String getValue(String key) {
        MPJLambdaWrapper<ConfigEntity> queryWrapper = new MPJLambdaWrapper<ConfigEntity>().eq("name", key);
        ConfigEntity configEntity = this.getOne(queryWrapper);
        return configEntity == null ? null : configEntity.getValue();
    }
}
