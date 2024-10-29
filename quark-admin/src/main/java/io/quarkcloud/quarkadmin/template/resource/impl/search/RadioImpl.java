package io.quarkcloud.quarkadmin.template.resource.impl.search;

import java.util.ArrayList;
import java.util.List;

import io.quarkcloud.quarkadmin.component.form.fields.Radio;
import io.quarkcloud.quarkadmin.component.form.fields.Radio.Option;
import io.quarkcloud.quarkadmin.template.resource.impl.SearchImpl;

public class RadioImpl<T> extends SearchImpl<T> {

    // 属性值
    public List<Option> options;

    // 构造方法
    public RadioImpl() {
        this.component = "radioField";
        this.options = new ArrayList<>();
    }

    // 设置Option
    public Option option(String label, Object value) {
        return new Option(label, value);
    }

    // 第一种情况：直接传入 List<Option>
    public RadioImpl<T> setOptions(List<Option> options) {
        this.options = options;
        return this;
    }

    // 第二种情况：传入对象和字段名称
    public RadioImpl<T> setOptions(List<?> options, String labelName, String valueName) {
        this.options = new Radio().listToOptions(options, labelName, valueName);
        return this;
    }
}
