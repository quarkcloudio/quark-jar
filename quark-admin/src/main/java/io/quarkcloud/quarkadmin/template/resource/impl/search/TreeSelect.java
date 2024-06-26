package io.quarkcloud.quarkadmin.template.resource.impl.search;

import java.util.ArrayList;
import java.util.List;

import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkadmin.component.form.fields.TreeSelect.TreeData;

public class TreeSelect extends SearchImpl {

    // 属性值
    public List<TreeData> options;

    // 构造方法
    public TreeSelect(Context ctx) {
        this.component = "treeSelectField";
        this.options = new ArrayList<>();
    }

    // 设置Option
    public TreeData option(Object value, String title) {
        return new TreeData().setTitle(title).setValue(value);
    }
}
