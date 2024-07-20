package io.quarkcloud.quarkadmin.template.resource.core;

import java.util.List;

import io.quarkcloud.quarkcore.service.Context;

public class ResolveSearch {
    
    // searches
    public List<Object> searches;

    // context
    public Context context;

    // 构造函数
    public ResolveSearch() {}

    // 构造函数
    public ResolveSearch(List<Object> searches, Context context) {
        this.searches = searches;
        this.context = context;
    }
}
