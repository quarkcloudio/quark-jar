package io.quarkcloud.quarkadmin.template.resource.impl;

import java.util.Map;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

import io.quarkcloud.quarkadmin.template.resource.Search;
import io.quarkcloud.quarkcore.service.Context;

public class SearchImpl<T> implements Search<T> {
    public String column;
    public String name;
    public String component;
    public String api;

    public SearchImpl() {
        this.component = "textField";
    }

    // 获取字段名
    public String getColumn(Object search) {
        if (column == null || column.isEmpty()) {
            String columnType = search.getClass().getSimpleName();
            columnType = columnType.replace("Search", "");
            return columnType.toLowerCase();
        }
        return column;
    }

    // 获取名称
    public String getName() {
        return name;
    }

    // 获取组件名称
    public String getComponent() {
        return component;
    }

    // 获取接口
    public String getApi() {
        return api;
    }

    // 默认值
    public Object getDefault() {
        return true;
    }

    // 执行查询
    public MPJLambdaWrapper<T> apply(Context context, MPJLambdaWrapper<T> query, Object value) {
        return query;
    }

    // 属性
    public Object options(Context context) {
        return null;
    }

    // 单向联动
    public Map<String, String> load(Context context) {
        return new java.util.HashMap<>();
    }
}
