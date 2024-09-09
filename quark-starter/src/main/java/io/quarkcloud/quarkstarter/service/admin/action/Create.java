package io.quarkcloud.quarkstarter.service.admin.action;

import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.action.LinkImpl;
import io.quarkcloud.quarkcore.service.Context;

public class Create<M, T> extends LinkImpl<ResourceMapper<T>, T> {

    // 构造函数
    public Create(String title) {

        // 文字
        this.name = "创建" + title;

        // 类型
        this.type = "primary";

        // 图标
        this.icon = "plus-circle";

        // 设置展示位置
        this.setOnlyOnIndex(true);
    }

    public String getHref(Context context) {

        // 从上下文中获取当前路径
        String path = context.getRequest().getRequestURI();

        // 将路径中的 "/index" 替换为 "/create"
        String modifiedPath = path.replace("/index", "/create");
        
        // 构建最终的 URL
        return "#/layout/index?api=" + modifiedPath;
    }
}
