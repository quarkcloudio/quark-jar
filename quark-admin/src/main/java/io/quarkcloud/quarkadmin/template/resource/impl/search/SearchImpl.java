package io.quarkcloud.quarkadmin.template.resource.impl.search;

import java.util.Map;

import io.quarkcloud.quarkadmin.template.resource.Search;
import io.quarkcloud.quarkcore.service.Context;

public class SearchImpl implements Search {
    private String column;
    private String name;
    private String component;
    private String api;

    // 初始化
    public SearchImpl(Context ctx) {
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
    public Object apply(Context ctx, Object query, Object value) {
        return query; // Placeholder implementation, adjust as needed
    }

    // 属性
    public Object options(Context ctx) {
        return null; // Placeholder implementation, adjust as needed
    }

    // 单向联动
    public Map<String, String> load(Context ctx) {
        return null; // Placeholder implementation, adjust as needed
    }
}
