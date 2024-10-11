package io.quarkcloud.quarkadmin.service.impl;

import org.springframework.stereotype.Service;
import io.quarkcloud.quarkadmin.entity.PositionEntity;
import io.quarkcloud.quarkadmin.mapper.PositionMapper;
import io.quarkcloud.quarkadmin.service.PositionService;

@Service
public class PositionServiceImpl extends ResourceServiceImpl<PositionMapper, PositionEntity> implements PositionService {

}
