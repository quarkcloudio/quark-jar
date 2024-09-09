package io.quarkcloud.quarkadmin.template.resource.impl.search;

import io.quarkcloud.quarkadmin.template.resource.impl.SearchImpl;

public class InputImpl<T> extends SearchImpl<T> {

    // 构造方法
    public InputImpl() {
        this.component = "textField";
    }
}
