package io.quarkcloud.quarkadmin.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.service.impl.ResourceServiceImpl;
import io.quarkcloud.quarkcore.service.Context;

public interface ResourceService<M extends ResourceMapper<T>, T> {

    /**
     * 设置上下文
     * 
     * @param context 要设置的上下文对象，类型为Context，提供了操作所需环境信息。
     * @return 返回当前实例，类型为ResourceServiceImpl&lt;M, T&gt;，支持链式调用。
     */
    public ResourceServiceImpl<M, T> setContext(Context context);

    /**
     * 设置查询条件
     * 
     * @param queryWrapper 查询条件的Lambda封装，使用MPJLambdaWrapper泛型来构建查询逻辑
     * @return 返回设置查询条件后的ResourceServiceImpl实例，支持链式调用
     */
    public ResourceServiceImpl<M, T> setQueryWrapper(MPJLambdaWrapper<T> queryWrapper);

    /**
     * 通过上下文保存实体
     * 
     * 此方法利用当前的上下文环境来保存传入的实体对象它提供了一种在特定上下文中持久化数据的方法，
     * 具体的上下文依赖于实现本方法的类和使用场景例如，它可以用作在特定用户会话中保存数据，
     * 或者在特定的事务上下文中保存实体
     * 
     * @param resourceEntity 要保存的实体对象这个参数是泛型的，允许保存不同类型的实体
     * @return 返回保存后的实体对象，以便于链式调用和进一步操作
     */
    public T saveByContext(T resourceEntity);

    /**
     * 根据上下文更新实体
     * 
     * 此方法主要用于在特定上下文中更新实体的状态它通过传入一个实体对象，
     * 并根据这个实体的标识符和其他属性的值来更新数据库中的对应记录更新操作
     * 是在上下文环境中进行的，这意味着更新过程可能涉及到与数据库的交互，
     * 以及其他与更新操作相关的逻辑这个方法提供了一种灵活的方式来处理实体
     * 的更新，使得开发者不需要手动管理更新过程中的细节
     * 
     * @param entity 要更新的实体对象这个对象应该包含所有需要更新到数据库的属性值，
     *               包括标识符和其他需要更改的字段
     * @return 更新后的实体对象这个对象可能包含从数据库返回的最新信息，如更新时间等
     */
    public T updateByContext(T entity);

    /**
     * 根据上下文获取单个实体对象
     * 
     * 此方法通过上下文信息来检索并返回一个实体对象它不接受任何参数，
     * 因为所需的实体信息已经通过上下文隐式提供
     * 
     * @return T 实体类的单个实例
     */
    public T getOneByContext();

    /**
     * 根据上下文移除数据.
     * 
     * 此方法的目的是提供一种基于上下文的数据移除方式，以实现更灵活的数据管理策略.
     * 它相较于直接通过具体数据项进行移除，更加注重于上下文环境，从而决定是否移除数据.
     * 
     * @return 如果移除操作成功，返回true；否则返回false. 
     */
    public boolean removeByContext();

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
}
