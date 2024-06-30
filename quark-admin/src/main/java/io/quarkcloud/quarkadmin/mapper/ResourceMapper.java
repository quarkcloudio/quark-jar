package io.quarkcloud.quarkadmin.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Select;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface ResourceMapper<T> extends BaseMapper<T> {

    @Select("select * from admins WHERE id = #{id}")
    public List<T> test(Long id);
}
