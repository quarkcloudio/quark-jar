package io.quarkcloud.quarkadmin.template.resource.core;

import java.util.List;
import java.util.Map;

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

    // 搜索组件
    public List<Object> searches;

    // 排序规则
    public Map<String, String> defaultQueryOrder;

    // 构造函数
    public PerformQuery(Context context, MPJLambdaWrapper<T> queryWrapper) {
        this.context = context;
        this.queryWrapper = queryWrapper;
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

    // 构建查询条件
    public MPJLambdaWrapper<T> buildIndexQuery() {

        // 搜索组件
        MPJLambdaWrapper<T> getQueryWrapper = this.applySearch(this.queryWrapper);

        // 列过滤器
        getQueryWrapper = this.applyColumnFilters(getQueryWrapper);

        // 排序
        getQueryWrapper = this.applyOrderings(getQueryWrapper);
        
        // 返回
        return getQueryWrapper;
    }

    // 创建导出查询
    public MPJLambdaWrapper<T> buildExportQuery() {
        return queryWrapper;
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
