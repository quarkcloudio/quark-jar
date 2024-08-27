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
    protected MPJLambdaWrapper<T> queryWrapper;

    // 搜索组件
    public List<Object> searches;

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

    // 构建查询条件
    @SuppressWarnings("unchecked")
    public MPJLambdaWrapper<T> buildIndexQuery() {
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

    // 创建导出查询
    public MPJLambdaWrapper<T> buildExportQuery() {
        return queryWrapper;
    }
}
