package io.quarkcloud.quarkadmin.template.resource.impl.search;

import java.util.ArrayList;
import java.util.List;

import io.quarkcloud.quarkadmin.component.form.fields.Cascader.Option;
import io.quarkcloud.quarkadmin.template.resource.impl.SearchImpl;

public class CascaderImpl<T> extends SearchImpl<T> {

    // 属性值
    public List<Option> options;

    // 构造方法
    public CascaderImpl() {
        this.component = "cascaderField";
        this.options = new ArrayList<>();
    }

    // 设置Option
    public Option option(String label, Object value) {
        return new Option(label, value);
    }
}
