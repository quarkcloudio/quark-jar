package io.quarkcloud.quarkadmin.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.service.ResourceService;
import io.quarkcloud.quarkcore.util.Reflect;

public class ResourceServiceImpl<M extends ResourceMapper<T>, T> implements ResourceService<M, T> {
    
    // 资源mapper
    @Autowired
    protected M resourceMapper;

    // 唯一性校验
    public boolean uniqueValidate(String table, String field, Object fieldValue) {
        return this.resourceMapper.uniqueValidate(table, field, fieldValue) > 0;
    }

    // 唯一性校验
    public boolean uniqueValidate(String table, String field, Object fieldValue, String ignoreField, Object ignoreValue) {
        return this.resourceMapper.uniqueValidate(table, field, fieldValue, ignoreField, ignoreValue) > 0;
    }

    /**
     * 插入数据
     * 
     * @param resourceEntity 要插入的实体
     * @return 插入后返回结果
     */
    public boolean save(T resourceEntity) {
        return this.resourceMapper.insert(resourceEntity) > 0;
    };

    /**
     * 插入数据返回Id
     * 
     * @param resourceEntity 要插入的实体
     * @return 插入后Id
     */
    public Long saveGetId(T resourceEntity) {
        this.resourceMapper.insert(resourceEntity);
        Reflect reflect = new Reflect(resourceEntity);
        return (long)reflect.getFieldValue("id");
    };

    // 根据 UpdateWrapper 条件，更新记录 需要设置sqlset
    public boolean update(Wrapper<T> updateWrapper) {
        return this.resourceMapper.update(null, updateWrapper) > 0;
    };

    // 根据 whereWrapper 条件，更新记录
    public boolean update(T updateEntity, Wrapper<T> whereWrapper) {
        return this.resourceMapper.update(updateEntity, whereWrapper) > 0;
    };
    
    // 根据 ID 选择修改
    public boolean updateById(T entity) {
        return this.resourceMapper.updateById(entity) > 0;
    };

    // 根据 ID 查询
    public T getById(Serializable id) {
        return this.resourceMapper.selectById(id);
    };

    public T getOne(Wrapper<T> queryWrapper) {
        return this.resourceMapper.selectOne(queryWrapper);
    };

    /**
     * 根据 queryWrapper 设置的条件，删除记录
     * 
     * @param queryWrapper 包含查询条件的包装器对象，用于定位要移除的对象
     * @return 如果成功移除了对象，返回true；否则返回false
     */
    public boolean remove(Wrapper<T> queryWrapper) {
        return this.resourceMapper.delete(queryWrapper) > 0;
    }

    /**
     * 根据 ID 删除
     * 
     * @param id 要删除的记录的主键值
     * @return 如果删除成功，返回true；否则返回false
     */
    public boolean removeById(Serializable id) {
        return this.resourceMapper.deleteById(id) > 0;
    }

    /**
     * 根据 columnMap 条件，删除记录
     * 
     * @param idList 要删除的记录的主键值列表
     * @return 如果删除成功，返回true；否则返回false
     */
    public boolean removeByMap(Map<String, Object> columnMap) {
        return this.resourceMapper.deleteByMap(columnMap) > 0;
    }

    /**
     * 删除（根据ID 批量删除）
     * 
     * @param idList 要删除的记录的主键值列表
     * @return 如果删除成功，返回true；否则返回false
     */
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return this.resourceMapper.deleteBatchIds(idList) > 0;
    }

    // 查询所有
    public List<T> list() {
        return this.resourceMapper.selectList(null);
    }

    // 查询列表
    public List<T> list(Wrapper<T> queryWrapper) {
        return this.resourceMapper.selectList(queryWrapper);
    }

    // 查询（根据ID 批量查询）
    public Collection<T> listByIds(Collection<? extends Serializable> idList) {
        return this.resourceMapper.selectBatchIds(idList);
    }

    // 查询（根据 columnMap 条件）
    public Collection<T> listByMap(Map<String, Object> columnMap) {
        return this.resourceMapper.selectByMap(columnMap);
    }

    // 查询所有列表
    public List<Map<String, Object>> listMaps() {
        return this.resourceMapper.selectMaps(null);
    }

    // 查询列表
    public List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper) {
        return this.resourceMapper.selectMaps(queryWrapper);
    }

    // 查询全部记录
    public List<Object> listObjs() {
        return this.resourceMapper.selectObjs(null);
    }

    // 查询全部记录
    public <V> List<V> listObjs(Function<? super Object, V> mapper) {
        List<V> list = new ArrayList<>();
        List<T> objects = this.resourceMapper.selectList(null);
        for (Object object : objects) {
            list.add(mapper.apply(object));
        }
        return list;
    }

    // 根据 Wrapper 条件，查询全部记录
    public List<Object> listObjs(Wrapper<T> queryWrapper) {
        return this.resourceMapper.selectObjs(queryWrapper);
    }
    
    // 根据 Wrapper 条件，查询全部记录
    public <V> List<V> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        List<V> list = new ArrayList<>();
        List<T> objects = this.resourceMapper.selectList(queryWrapper);
        for (Object object : objects) {
            list.add(mapper.apply(object));
        }
        return list;
    }

    // 无条件分页查询
    public IPage<T> page(IPage<T> page) {
        return this.resourceMapper.selectPage(page, null);
    }

    // 条件分页查询
    public IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper) {
        return this.resourceMapper.selectPage(page, queryWrapper);
    }

    // 无条件分页查询
    public IPage<Map<String, Object>> pageMaps(IPage<Map<String, Object>> page) {
        return this.resourceMapper.selectMapsPage(page, null);
    }

    // 条件分页查询
    public IPage<Map<String, Object>> pageMaps(IPage<Map<String, Object>> page, Wrapper<T> queryWrapper) {
        return this.resourceMapper.selectMapsPage(page, queryWrapper);
    }
}