package io.quarkcloud.quarkadmin.service;

import java.util.List;

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

    // 根据 UpdateWrapper 条件，更新记录 需要设置sqlset
    boolean update(Wrapper<T> updateWrapper);

    // 根据 whereWrapper 条件，更新记录
    boolean update(T updateEntity, Wrapper<T> whereWrapper);
    
    // 根据 ID 选择修改
    boolean updateById(T entity);

    /**
     * 根据上下文获取列表
     * 
     * 此方法旨在根据当前上下文检索和返回一个列表
     * 它不接受任何参数，因为它依赖于外部上下文来获取数据
     * 
     * @return List<T> 返回一个泛型列表，包含符合当前上下文条件的元素
     */
    public List<T> getListByContext();

    /**
     * 获取分页数据
     * 
     * @param pageSize 每页显示的数据条数，这是一个重要的分页参数，决定了每页数据的数量
     * @return 返回一个泛型的分页对象，该对象包含当前页的数据以及分页的相关信息
     */
    public IPage<T> getPageByContext(long pageSize);

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
}
