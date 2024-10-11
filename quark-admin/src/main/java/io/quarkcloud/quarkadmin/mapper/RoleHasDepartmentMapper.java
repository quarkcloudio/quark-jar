package io.quarkcloud.quarkadmin.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import io.quarkcloud.quarkadmin.entity.RoleHasDepartmentEntity;

@Mapper
public interface RoleHasDepartmentMapper extends BaseMapper<RoleHasDepartmentEntity> {

}
