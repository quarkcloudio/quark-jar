package io.quarkcloud.quarkadmin.template.resource.core;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkadmin.template.resource.Search;

public class PerformQuery<T> {

    // 上下文
    public Context context;

    // 查询条件
    public MPJLambdaWrapper<T> queryWrapper;

    // 更新条件
    public UpdateWrapper<T> updateWrapper;

    // 搜索组件
    public List<Object> searches;

    // 排序规则
    public Map<String, String> defaultQueryOrder;

    // 构造函数
    public PerformQuery(Context context) {
        this.context = context;
    }

    // 设置搜索组件
    public PerformQuery<T> setQueryWrapper(MPJLambdaWrapper<T> queryWrapper) {
        this.queryWrapper = queryWrapper;
        return this;
    }

    // 设置搜索组件
    public PerformQuery<T> setUpdateWrapper(UpdateWrapper<T> updateWrapper) {
        this.updateWrapper = updateWrapper;
        return this;
    }

    // 设置搜索组件
    public PerformQuery<T> setSearches(List<Object> searches) {
        this.searches = searches;
        return this;
    }

    // 设置默认排序
    public PerformQuery<T> setDefaultOrder(Map<String, String> defaultOrder) {
        this.defaultQueryOrder = defaultOrder;
        return this;
    }

    // 构建行为查询
    public UpdateWrapper<T> buildActionQuery() {
        String id = context.getParameter("id");
        if (id != null && !id.isEmpty()) {
            if (id.contains(",")) {
                String[] ids = id.split(",");
                List<String> idList = Arrays.asList(ids);
                this.updateWrapper.in("id", idList);
            } else {
                this.updateWrapper.eq("id", id);
            }
        }
        return this.updateWrapper;
    }

    // 构建详情查询
    public MPJLambdaWrapper<T> buildDetailQuery() {
        String id = context.getParameter("id");
        if (id != null && !id.isEmpty()) {
            this.queryWrapper.eq("id", id);
        }
        return this.queryWrapper;
    }

    // 构建详情查询
    public MPJLambdaWrapper<T> buildEditQuery() {
        String id = context.getParameter("id");
        if (id != null && !id.isEmpty()) {
            this.queryWrapper.eq("id", id);
        }
        return this.queryWrapper;
    }

    // 创建导出查询
    public MPJLambdaWrapper<T> buildExportQuery() {
        MPJLambdaWrapper<T> getQueryWrapper = this.applySearch(this.queryWrapper);
        getQueryWrapper = this.applyColumnFilters(getQueryWrapper);
        getQueryWrapper = this.applyOrderings(getQueryWrapper);
        return getQueryWrapper;
    }

    // 构建列表查询
    public MPJLambdaWrapper<T> buildIndexQuery() {
        MPJLambdaWrapper<T> getQueryWrapper = this.applySearch(this.queryWrapper);
        getQueryWrapper = this.applyColumnFilters(getQueryWrapper);
        getQueryWrapper = this.applyOrderings(getQueryWrapper);
        return getQueryWrapper;
    }

    // 构建行内编辑查询
    public UpdateWrapper<T> buildEditableQuery() {
        String id = context.getParameter("id");
        if (id != null && !id.isEmpty()) {
            this.updateWrapper.eq("id", id);
        }
        return this.updateWrapper;
    }
    
    // 创建更新查询
    public UpdateWrapper<T> buildUpdateQuery() {
        String id = (String) context.getRequestParam("id");
        if (id != null && !id.isEmpty()) {
            if (id.contains(",")) {
                String[] ids = id.split(",");
                List<String> idList = Arrays.asList(ids);
                this.updateWrapper.in("id", idList);
            } else {
                this.updateWrapper.eq("id", id);
            }
        }
        return this.updateWrapper;
    }

    // 构建查询条件
    @SuppressWarnings("unchecked")
    public MPJLambdaWrapper<T> applySearch(MPJLambdaWrapper<T> queryWrapper) {
        String searchParam = context.getParameter("search");
        if (searchParam == null || searchParam == "") {
            return queryWrapper;
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = null;
        try {
            map = mapper.readValue(searchParam, Map.class);
        } catch (JsonProcessingException e) {
            return queryWrapper;
        }
        if (map==null) {
            return queryWrapper;
        }
        for (Object item : searches) {
            Search<T> search = (Search<T>) item;
            String column = search.getColumn(item);
            if (map.get(column) != null && map.get(column) != "") {
                queryWrapper = search.apply(context, queryWrapper, map.get(column));
            }
        }
        return queryWrapper;
    }

    // 构建查询条件
    @SuppressWarnings("unchecked")
    public MPJLambdaWrapper<T> applyColumnFilters(MPJLambdaWrapper<T> queryWrapper) {
        String filterParam = context.getParameter("filter");
        if (filterParam == null || filterParam == "") {
            return queryWrapper;
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<Object>> map = null;
        try {
            map = mapper.readValue(filterParam, Map.class);
        } catch (JsonProcessingException e) {
            return queryWrapper;
        }
        if (map==null) {
            return queryWrapper;
        }
        for (Map.Entry<String, List<Object>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<Object> value = entry.getValue();
            if (key!=null&&value!=null) {
                queryWrapper.in(key, value);
            }
        }
        return queryWrapper;
    }

    // 构建查询条件
    @SuppressWarnings("unchecked")
    public MPJLambdaWrapper<T> applyOrderings(MPJLambdaWrapper<T> queryWrapper) {
        String sorterParam = context.getParameter("sorter");
        if (sorterParam == null || sorterParam.equals("{}") || sorterParam.isEmpty()) {
            for (Map.Entry<String, String> entry : defaultQueryOrder.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key!=null&&value!=null) {
                    if (value.equals("asc") || value.equals("ascend")) {
                        queryWrapper.orderByAsc(key);
                    } else if (value.equals("desc") || value.equals("descend")) {
                        queryWrapper.orderByDesc(key);
                    }
                }
            }
            return queryWrapper;
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = null;
        try {
            map = mapper.readValue(sorterParam, Map.class);
        } catch (JsonProcessingException e) {
            return queryWrapper;
        }
        if (map==null) {
            return queryWrapper;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key!=null&&value!=null) {
                if (value.equals("asc") || value.equals("ascend")) {
                    queryWrapper.orderByAsc(key);
                } else if (value.equals("desc") || value.equals("descend")) {
                    queryWrapper.orderByDesc(key);
                }
            }
        }
        return queryWrapper;
    }
}
