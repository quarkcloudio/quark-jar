package io.quarkcloud.quarkadmin.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import io.quarkcloud.quarkadmin.mapper.ResourceMapper;

public interface ResourceService<M extends ResourceMapper<T>, T> {

    /**
     * 插入数据
     * 
     * @param resourceEntity 要插入的实体
     * @return 插入后返回结果
     */
    public boolean save(T resourceEntity);

    /**
     * 插入数据返回Id
     * 
     * @param resourceEntity 要插入的实体
     * @return 插入后Id
     */
    public Long saveGetId(T resourceEntity);

    /**
     * 更新实体信息
     * 
     * @param updateWrapper 包装了更新操作的Wrapper对象，用于构建更新语句
     * @return 返回更新操作的执行结果，true表示更新成功，false表示更新失败
     */
    boolean update(Wrapper<T> updateWrapper);

    /**
     * 更新实体信息
     * 
     * @param updateEntity 待更新的实体对象，必须包含要更新的字段和对应的值
     * @param whereWrapper 更新条件包装器，用于指定更新的条件，通常包含where条件
     * @return 操作是否成功执行，成功返回true，失败返回false
     */
    boolean update(T updateEntity, Wrapper<T> whereWrapper);
    
    /**
     * 根据 ID 更新实体
     * 
     * @param entity 实体对象，必须包含 ID，且 ID 必须存在
     * @return 更新成功返回 true，否则返回 false
     */
    boolean updateById(T entity);

    /**
     * 根据 ID 查询数据
     * 
     * @param id 要查询的 ID
     * @return 返回查询到的实体对象，类型为T，表示泛型类型，可以是任何实体类型
     */
    T getById(Serializable id);

    /**
     * 根据 Wrapper，查询一条记录。结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")
     * 
     * @param queryWrapper 查询条件包装器，用于构建查询语句
     * @return 返回查询到的实体对象，类型为T，表示泛型类型，可以是任何实体类型
     */
    T getOne(Wrapper<T> queryWrapper);

    /**
     * 根据 queryWrapper 设置的条件，删除记录
     * 
     * @param queryWrapper 包含查询条件的包装器对象，用于定位要移除的对象
     * @return 如果成功移除了对象，返回true；否则返回false
     */
    boolean remove(Wrapper<T> queryWrapper);

    /**
     * 根据 ID 删除
     * 
     * @param id 要删除的记录的主键值
     * @return 如果删除成功，返回true；否则返回false
     */
    boolean removeById(Serializable id);

    /**
     * 根据 columnMap 条件，删除记录
     * 
     * @param idList 要删除的记录的主键值列表
     * @return 如果删除成功，返回true；否则返回false
     */
    boolean removeByMap(Map<String, Object> columnMap);

    /**
     * 删除（根据ID 批量删除）
     * 
     * @param idList 要删除的记录的主键值列表
     * @return 如果删除成功，返回true；否则返回false
     */
    boolean removeByIds(Collection<? extends Serializable> idList);

    // 查询所有
    List<T> list();

    // 查询列表
    List<T> list(Wrapper<T> queryWrapper);

    // 查询（根据ID 批量查询）
    Collection<T> listByIds(Collection<? extends Serializable> idList);

    // 查询（根据 columnMap 条件）
    Collection<T> listByMap(Map<String, Object> columnMap);

    // 查询所有列表
    List<Map<String, Object>> listMaps();

    // 查询列表
    List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper);

    // 无条件分页查询
    IPage<T> page(IPage<T> page);

    // 条件分页查询
    IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper);

    // 无条件分页查询
    IPage<Map<String, Object>> pageMaps(IPage<Map<String, Object>> page);

    // 条件分页查询
    IPage<Map<String, Object>> pageMaps(IPage<Map<String, Object>> page, Wrapper<T> queryWrapper);

    /**
     * 检查字段是否唯一
     * 
     */
    boolean uniqueValidate(String table, String field, Object fieldValue);

    /**
     * 检查字段是否唯一
     * 
     */
    boolean uniqueValidate(String table, String field, Object fieldValue, String ignoreField, Object ignoreValue);
}
