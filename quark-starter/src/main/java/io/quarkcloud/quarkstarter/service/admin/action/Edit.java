package io.quarkcloud.quarkstarter.service.admin.action;

import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.action.Link;
import io.quarkcloud.quarkcore.service.Context;

public class Edit<M, T> extends Link<ResourceMapper<T>, T> {

    // 构造函数
    public Edit() {

        // 文字
        this.name = "编辑";

        // 设置按钮类型,primary | ghost | dashed | link | text | default
        this.type = "link";

        // 设置按钮大小,large | middle | small | default
        this.size = "small";

        // 设置展示位置
        this.setOnlyOnIndexTableRow(true);
    }

    // 构造函数
    public Edit(String title) {

        // 文字
        this.name = title;

        // 设置按钮类型,primary | ghost | dashed | link | text | default
        this.type = "link";

        // 设置按钮大小,large | middle | small | default
        this.size = "small";

        // 设置展示位置
        this.setOnlyOnIndexTableRow(true);
    }

    public String getHref(Context context) {

        // 从上下文中获取当前路径
        String path = context.getRequest().getRequestURI();

        // 将路径中的 "/index" 替换为 "/edit"
        String modifiedPath = path.replace("/index", "/edit&id=${id}");
        
        // 构建最终的 URL
        return "#/layout/index?api=" + modifiedPath;
    }
}
