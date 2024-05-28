package io.quarkcloud.quarkadmin.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.quarkcloud.quarkadmin.entity.RoleHasPermission;

@Mapper
public interface RoleHasPermissionMapper extends BaseMapper<RoleHasPermission> {

}
