package io.quarkcloud.quarkadmin.template.resource.impl.search;

import java.util.ArrayList;
import java.util.List;

import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkadmin.component.form.fields.SelectField.Option;

public class Select extends SearchImpl {

    // 属性值
    public List<Option> options;

    // 构造方法
    public Select(Context ctx) {
        this.component = "selectField";
        this.options = new ArrayList<>();
    }

    // 设置Option
    public Option option(Object value, String label) {
        return new Option().setLabel(label).setValue(value);
    }
}
