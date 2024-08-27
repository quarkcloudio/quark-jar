package io.quarkcloud.quarkadmin.template.resource.impl.search;

import java.util.ArrayList;
import java.util.List;

import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkadmin.component.form.fields.Radio.Option;
import io.quarkcloud.quarkadmin.template.resource.impl.SearchImpl;

public class Radio<T> extends SearchImpl<T> {

    // 属性值
    public List<Option> options;

    // 构造方法
    public Radio(Context context) {
        this.component = "radioField";
        this.options = new ArrayList<>();
    }

    // 设置Option
    public Option option(Object value, String label) {
        return new Option().setLabel(label).setValue(value);
    }
}
