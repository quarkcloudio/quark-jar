package io.quarkcloud.quarkadmin.template.resource.core;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.quarkcloud.quarkcore.service.Context;

public class PerformQuery<T> {

    // context
    public Context context;

    // 查询条件
    protected QueryWrapper<T> queryWrapper;

    // 构造函数
    public PerformQuery(Context context,QueryWrapper<T> queryWrapper) {
        this.context = context;
        this.queryWrapper = queryWrapper;
    }

    // 构建查询条件
    @SuppressWarnings("unchecked")
    public QueryWrapper<T> buildIndexQuery() {
        String search = context.getParameter("search");
        Map<String, Object> map = context.getRequestBody(Map.class);
        if (map == null || map.isEmpty()) {
            return queryWrapper;
        }

        return queryWrapper;
    }
}
