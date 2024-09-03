package io.quarkcloud.quarkadmin.mapper;

import org.apache.ibatis.annotations.Select;
import com.github.yulichang.base.MPJBaseMapper;

public interface ResourceMapper<T> extends MPJBaseMapper<T> {

    @Select("SELECT COUNT(*) FROM #{table} WHERE #{field} = #{fieldValue}")
    public int uniqueValidate(String table, String field, Object fieldValue);

    @Select("SELECT COUNT(*) FROM #{table} WHERE #{ignoreField} <> #{ignoreValue} AND #{field} = #{fieldValue}")
    public int uniqueValidate(String table, String field, Object fieldValue, String ignoreField, Object ignoreValue);
}
