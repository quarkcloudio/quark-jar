package io.quarkcloud.quarkadmin.service.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.service.ResourceService;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkcore.util.Reflect;

public class ResourceServiceImpl<M extends ResourceMapper<T>, T> implements ResourceService<M, T> {
    
    // 资源mapper
    @Autowired
    protected M resourceMapper;

    // 上下文
    protected Context context;

    // 查询条件
    protected MPJLambdaWrapper<T> queryWrapper;

    // 设置上下文
    public ResourceServiceImpl<M, T> setContext(Context context) {
        this.context = context;
        return this;
    }

    // 设置查询条件
    public ResourceServiceImpl<M, T> setQueryWrapper(MPJLambdaWrapper<T> queryWrapper) {
        this.queryWrapper = queryWrapper;
        return this;
    }

    // 获取列表
    public List<T> getListByContext() {
        return this.resourceMapper.selectList(queryWrapper);
    }

    // 获取分页数据
    @SuppressWarnings("unchecked")
    public IPage<T> getPageByContext(long pageSize) {
        long currentPage = 1;
        String searchParam = context.getParameter("search");
        if (searchParam != null && searchParam != "") {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = null;
            try {
                map = mapper.readValue(searchParam, Map.class);
            } catch (JsonProcessingException e) {
                currentPage = 1;
            }
            if (map!=null) {
                Object getPage = map.get("page");
                Object getPageSize = map.get("pageSize");
                if (getPage!=null && getPage instanceof String) {
                    currentPage = Long.parseLong((String) getPage);
                }
                if (getPageSize!=null && getPageSize instanceof String) {
                    pageSize = Long.parseLong((String) getPageSize);
                }
                if (getPage instanceof Number) {
                    currentPage = ((Number) getPage).longValue();
                }
                if (getPageSize instanceof Number) {
                    pageSize = ((Number) getPageSize).longValue();
                }
            }
        }

        // 构建分页
        IPage<T> page = new Page<T>(currentPage, pageSize);
        return this.resourceMapper.selectPage(page, queryWrapper);
    }

    // 保存
    @SuppressWarnings("unchecked")
    public T saveByContext(T entity) {
        entity = (T) context.getRequestBody(entity.getClass());
        this.resourceMapper.insert(entity);
        return entity;
    }

    // 解析表格列可编辑数据
    private Object resolveEditableBody(Context context, Class<? extends Object> clazz) {
        Object entity = null;
        try {
            entity = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        Map<String, String[]> map = context.getParameterMap();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            String fieldName = entry.getKey();
            String[] fieldValue = entry.getValue();
            Field field;
            try {
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                try {
                    // 根据字段类型转换值
                    Class<?> fieldType = field.getType();
                    if (fieldType == String.class) {
                        field.set(entity, fieldValue[0]);
                    } else if (fieldType == int.class || fieldType == Integer.class) {
                        if (fieldValue[0].equals("true")) {
                            field.set(entity, 1);
                        } else if (fieldValue[0].equals("false")) {
                            field.set(entity, 0);
                        } else {
                            field.set(entity, Integer.parseInt(fieldValue[0]));
                        }
                    } else if (fieldType == long.class || fieldType == Long.class) {
                        field.set(entity, Long.parseLong(fieldValue[0]));
                    } else if (fieldType == double.class || fieldType == Double.class) {
                        field.set(entity, Double.parseDouble(fieldValue[0]));
                    } else if (fieldType == float.class || fieldType == Float.class) {
                        field.set(entity, Float.parseFloat(fieldValue[0]));
                    } else if (fieldType == short.class || fieldType == Short.class) {
                        field.set(entity, Short.parseShort(fieldValue[0]));
                    } else if (fieldType == byte.class || fieldType == Byte.class) {
                        field.set(entity, Byte.parseByte(fieldValue[0]));
                    } else if (fieldType == boolean.class || fieldType == Boolean.class) {
                        field.set(entity, Boolean.parseBoolean(fieldValue[0]));
                    } else if (fieldType == char.class || fieldType == Character.class) {
                        field.set(entity, fieldValue[0].charAt(0));
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }
        }
        return entity;
    }

    // 更新
    @SuppressWarnings("unchecked")
    public T editableUpdate(T entity) {
        entity = (T) this.resolveEditableBody(context, entity.getClass());
        this.resourceMapper.updateById(entity);
        return entity;
    }

    // 更新
    @SuppressWarnings("unchecked")
    public T updateByContext(T entity) {
        entity = (T) context.getRequestBody(entity.getClass());
        this.resourceMapper.updateById(entity);
        return entity;
    }

    // 获取单个
    public T getOneByContext() {
        String id = context.getParameter("id");
        return this.resourceMapper.selectById(id);
    }

    // 删除
    public boolean removeByContext() {
        String id = context.getParameter("id");
        boolean result = false;
        if (id != null && !id.isEmpty()) {
            if (id.contains(",")) {
                String[] ids = id.split(",");
                List<String> idList = Arrays.asList(ids);
                result = this.removeByIds(idList);
            } else {
                result = this.removeById(id);
            }
        }
        return result;
    }

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
}