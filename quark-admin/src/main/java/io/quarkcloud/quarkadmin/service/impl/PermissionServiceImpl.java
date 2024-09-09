package io.quarkcloud.quarkadmin.service.impl;

import org.springframework.stereotype.Service;
import io.quarkcloud.quarkadmin.entity.PermissionEntity;
import io.quarkcloud.quarkadmin.mapper.PermissionMapper;
import io.quarkcloud.quarkadmin.service.PermissionService;
import jakarta.annotation.Resource;

@Service
public class PermissionServiceImpl extends ResourceServiceImpl<PermissionMapper, PermissionEntity> implements PermissionService {
    
    // 权限
    @Resource
    private PermissionMapper permissionMapper;
}
