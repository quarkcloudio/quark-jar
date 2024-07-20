package io.quarkcloud.quarkadmin.template.resource;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.quarkcloud.quarkcore.service.Context;

public interface Search<T> {

    // 获取字段名
    public String getColumn(Object search);

    // 获取名称
    public String getName();

    // 获取组件名称
    public String getComponent();

    // 获取接口
    public String getApi();
    // 默认值
    public Object getDefault();

    // 执行查询
    public QueryWrapper<T> apply(Context ctx, QueryWrapper<T> query, Object value);

    // 属性
    public Object options(Context ctx);

    // 单向联动
    public Map<String, String> load(Context ctx);
}
