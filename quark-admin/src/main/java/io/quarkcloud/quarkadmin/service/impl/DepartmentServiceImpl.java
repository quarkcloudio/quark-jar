package io.quarkcloud.quarkadmin.service.impl;

import org.springframework.stereotype.Service;
import io.quarkcloud.quarkadmin.entity.DepartmentEntity;
import io.quarkcloud.quarkadmin.mapper.DepartmentMapper;
import io.quarkcloud.quarkadmin.service.DepartmentService;

@Service
public class DepartmentServiceImpl extends ResourceServiceImpl<DepartmentMapper, DepartmentEntity> implements DepartmentService {

}
