package io.quarkcloud.quarkadmin.template.resource.impl.search;

import java.util.ArrayList;
import java.util.List;

import io.quarkcloud.quarkadmin.component.form.fields.TreeSelect;
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

    // 第一种情况：直接传入 List<TreeData>
    public TreeSelectImpl<T> setTreeData(List<TreeData> treeData) {
        this.options = treeData;
        return this;
    }

    // 第二种情况：传入对象和字段名称
    public TreeSelectImpl<T> setTreeData(List<?> options, String parentKeyName, String titleName, String valueName) {
        this.options = new TreeSelect().listToTreeData(options, 0L, parentKeyName, titleName, valueName);
        return this;
    }

    // 第三种情况：传入对象、父节点 ID 和字段名称
    public TreeSelectImpl<T> setTreeData(List<?> options, Long parentId, String parentKeyName, String titleName, String valueName) {
        this.options =  new TreeSelect().listToTreeData(options, parentId, parentKeyName, titleName, valueName);
        return this;
    }
}
