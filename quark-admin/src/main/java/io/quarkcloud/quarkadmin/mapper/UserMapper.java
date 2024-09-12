package io.quarkcloud.quarkadmin.mapper;

import org.apache.ibatis.annotations.Mapper;
import io.quarkcloud.quarkadmin.entity.UserEntity;

@Mapper
public interface UserMapper extends ResourceMapper<UserEntity> {

}
