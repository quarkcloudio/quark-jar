package io.quarkcloud.quarkadmin.template.resource.impl.search;

import java.util.ArrayList;
import java.util.List;

import io.quarkcloud.quarkadmin.component.form.fields.Cascader;
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

    // 第一种情况：只传递一个 List<Option>
    public CascaderImpl<T> setOptions(List<Option> options) {
        this.options = options;
        return this;
    }

    // 第二种情况：传递四个参数
    public CascaderImpl<T> setOptions(List<?> options, String parentKeyName, String labelName, String valueName) {
        this.options = new Cascader().listToOptions(options, 0L, parentKeyName, labelName, valueName);
        return this;
    }

    // 第三种情况：传递五个参数
    public CascaderImpl<T> setOptions(List<?> options, Long parentId, String parentKeyName, String labelName, String valueName) {
        this.options = new Cascader().listToOptions(options, parentId, parentKeyName, labelName, valueName);
        return this;
    }
}
