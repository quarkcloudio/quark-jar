package io.quarkcloud.quarkadmin.service.impl;

import org.springframework.stereotype.Service;
import io.quarkcloud.quarkadmin.entity.ActionLogEntity;
import io.quarkcloud.quarkadmin.mapper.ActionLogMapper;
import io.quarkcloud.quarkadmin.service.ActionLogService;

@Service
public class ActionLogServiceImpl extends ResourceServiceImpl<ActionLogMapper, ActionLogEntity> implements ActionLogService {

}
