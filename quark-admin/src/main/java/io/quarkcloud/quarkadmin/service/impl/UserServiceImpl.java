package io.quarkcloud.quarkadmin.service.impl;

import org.springframework.stereotype.Service;
import io.quarkcloud.quarkadmin.entity.UserEntity;
import io.quarkcloud.quarkadmin.mapper.UserMapper;
import io.quarkcloud.quarkadmin.service.UserService;

@Service
public class UserServiceImpl extends ResourceServiceImpl<UserMapper, UserEntity> implements UserService {

}
