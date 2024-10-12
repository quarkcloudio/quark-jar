package io.quarkcloud.quarkadmin.service;

import java.util.List;

import io.quarkcloud.quarkadmin.entity.PositionEntity;
import io.quarkcloud.quarkadmin.mapper.PositionMapper;
import io.quarkcloud.quarkadmin.component.form.fields.Checkbox.Option;

public interface PositionService extends ResourceService<PositionMapper,PositionEntity> {
    
    // 获取Checkbox Option列表
    public List<Option> getCheckboxOptions();
}
