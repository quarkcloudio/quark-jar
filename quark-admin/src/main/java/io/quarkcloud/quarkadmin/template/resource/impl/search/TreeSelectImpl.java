package io.quarkcloud.quarkadmin.template.resource.impl.search;

import java.util.ArrayList;
import java.util.List;

import io.quarkcloud.quarkadmin.component.form.fields.TreeSelect.TreeData;
import io.quarkcloud.quarkadmin.template.resource.impl.SearchImpl;

public class TreeSelectImpl<T> extends SearchImpl<T> {

    // 属性值
    public List<TreeData> options;

    // 构造方法
    public TreeSelectImpl() {
        this.component = "treeSelectField";
        this.options = new ArrayList<>();
    }

    // 设置Option
    public TreeData option(String title, Object value) {
        return new TreeData(title, value);
    }
}
