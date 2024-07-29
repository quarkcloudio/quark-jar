package io.quarkcloud.quarkadmin.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Select;
import com.github.yulichang.base.MPJBaseMapper;

public interface ResourceMapper<T> extends MPJBaseMapper<T> {

    @Select("select * from admins WHERE id = #{id}")
    public List<T> test(Long id);
}
