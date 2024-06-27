package io.quarkcloud.quarkadmin.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.quarkcloud.quarkadmin.entity.UserHasRoleEntity;

@Mapper
public interface UserHasRoleMapper extends BaseMapper<UserHasRoleEntity> {

}
