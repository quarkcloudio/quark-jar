package io.quarkcloud.quarkadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.service.ResourceService;
import io.quarkcloud.quarkadmin.template.resource.core.PerformQuery;
import io.quarkcloud.quarkcore.service.Context;

public class ResourceServiceImpl<M extends ResourceMapper<T>, T> implements ResourceService<M, T> {
    
    // 资源mapper
    @Autowired
    protected M resourceMapper;

    // 上下文
    protected Context context;

    // 查询条件
    protected MPJLambdaWrapper<T> queryWrapper;

    // 查询组件
    protected List<Object> searches;

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

    // 设置查询条件
    public ResourceServiceImpl<M, T> setSearches(List<Object> searches) {
        this.searches = searches;
        return this;
    }

    // 获取列表
    public List<T> list() {
        MPJLambdaWrapper<T> queryWrapper = new PerformQuery<T>(context, this.queryWrapper).setSearches(searches).buildIndexQuery();
        return this.resourceMapper.selectList(queryWrapper);
    }

    // 获取分页数据
    @SuppressWarnings("unchecked")
    public IPage<T> page(long pageSize) {
        MPJLambdaWrapper<T> queryWrapper = new PerformQuery<T>(context, this.queryWrapper).setSearches(searches).buildIndexQuery();
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
    public Boolean save() {
        T entity = (T) context.getRequestBody(this.getEntity().getClass());
        return this.resourceMapper.insert(entity) > 0;
    }

    // 获取实体
    public T getEntity() {
        return this.resourceMapper.selectOne(null);
    }
}