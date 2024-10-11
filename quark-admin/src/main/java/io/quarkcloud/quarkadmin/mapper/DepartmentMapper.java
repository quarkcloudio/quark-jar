package io.quarkcloud.quarkadmin.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import io.quarkcloud.quarkadmin.entity.DepartmentEntity;

@Mapper
public interface DepartmentMapper extends BaseMapper<DepartmentEntity> {

}
