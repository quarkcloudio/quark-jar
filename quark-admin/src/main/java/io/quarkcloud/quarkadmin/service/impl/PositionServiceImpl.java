package io.quarkcloud.quarkadmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import io.quarkcloud.quarkadmin.entity.PositionEntity;
import io.quarkcloud.quarkadmin.mapper.PositionMapper;
import io.quarkcloud.quarkadmin.service.PositionService;
import io.quarkcloud.quarkadmin.component.form.fields.Checkbox.Option;

@Service
public class PositionServiceImpl extends ResourceServiceImpl<PositionMapper, PositionEntity> implements PositionService {
    // 获取复选框选项
    public List<Option> getCheckboxOptions() {
        List<Option> list = new ArrayList<>();
        List<PositionEntity> positionEntities = this.list();
        for (PositionEntity positionEntity : positionEntities) {
            list.add(new Option(positionEntity.getName(), positionEntity.getId()));
        }
        return list;
    }
}
