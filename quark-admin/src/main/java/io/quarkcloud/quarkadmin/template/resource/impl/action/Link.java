package io.quarkcloud.quarkadmin.template.resource.impl.action;

import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.ActionImpl;
import io.quarkcloud.quarkcore.service.Context;

public class Link<M, T> extends ActionImpl<ResourceMapper<T>, T> {

    // 获取跳转链接
    public String href;

    // 相当于 a 链接的 target 属性，href 存在时生效，_blank | _self | _parent | _top
    public String target;

    // 初始化
    public Link() {
        this.setActionType("link");
        this.target = "_self";
    }

    // 获取跳转链接
    public String getHref(Context context) {
        return this.href;
    }

    // 相当于 a 链接的 target 属性，href 存在时生效
    public String getTarget(Context context) {
        return this.target;
    }
}
