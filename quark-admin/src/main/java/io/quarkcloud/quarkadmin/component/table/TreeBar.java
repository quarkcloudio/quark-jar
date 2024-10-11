package io.quarkcloud.quarkadmin.component.table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.quarkcloud.quarkadmin.component.Component;

/**
 * TreeBar 表示树形控件的配置项
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class TreeBar extends Component {

    /**
     * FieldNames 表示自定义字段名的映射
     */
    @Data
    public static class FieldNames {
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public String title;     // 标题字段的名称

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public String key;       // 键字段的名称

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public String children;  // 子节点字段的名称
    }

    /**
     * TreeData 表示树形结构的数据项
     */
    @Data
    public static class TreeData {

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public boolean checkable;        // 是否显示复选框

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public boolean disableCheckbox;  // 是否禁用复选框

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public boolean disabled;         // 是否禁用节点

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public Object icon;              // 节点的图标

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public boolean isLeaf;           // 是否是叶子节点

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public Object key;               // 节点的唯一标识符

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public boolean selectable;       // 是否可选中节点

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public String title;             // 节点的标题

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public List<TreeData> children;  // 子节点列表

        // 选项的标签
        public TreeData(String title, Object key) {
            this.title = title;
            this.key = key;
        }        
    }

    public String component;                              // 组件名称
    public String name;                                   // 字段名，支持数组

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean autoExpandParent;                      // 是否自动展开父节点

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean blockNode;                             // 节点是否占据一行

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean checkable;                             // 节点前添加 Checkbox 复选框

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public List<Object> checkedKeys;                      // 选中的复选框的树节点

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean checkStrictly;                         // 是否完全控制父子节点的选中状态

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public List<Object> defaultCheckedKeys;               // 默认选中的复选框节点

    public boolean defaultExpandAll;                      // 是否默认展开所有节点

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public List<Object> defaultExpandedKeys;              // 默认展开的树节点

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean defaultExpandParent;                   // 默认展开父节点

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public List<Object> defaultSelectedKeys;              // 默认选中的树节点

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Object defaultValue;                           // 默认选中的值

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean disabled;                              // 整体禁用

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean draggable;                             // 节点是否可拖拽

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public List<Object> expandedKeys;                     // 展开的树节点

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public FieldNames fieldNames;                         // 自定义字段名称

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int height;                                    // 虚拟滚动容器的高度

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Object icon;                                   // 自定义图标

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean multiple;                              // 支持选择多个节点

    public String placeholder;                            // 占位文本

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String rootClassName;                          // Tree 最外层的类名

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Object rootStyle;                              // Tree 最外层的样式

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean selectable;                            // 是否可选中

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public List<Object> selectedKeys;                     // 选中的树节点

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean showIcon;                              // 是否显示图标

    public boolean showLine;                              // 是否显示连接线

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Object switcherIcon;                           // 自定义展开/折叠图标

    public List<TreeData> treeData;                       // 树形数据

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Object value;                                  // 选中的值

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean virtual;                               // 是否启用虚拟滚动

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Map<String, Object> style;                     // 自定义样式

    // 初始化方法，设置一些默认值
    public TreeBar() {
        this.component = "treeBar";
        this.name="treeBarSelectedKeys";
        this.placeholder = "请输入搜索内容";
        this.defaultExpandAll = true;
        this.showLine = true;
        this.setComponentKey();
    }

    // 设置宽度
    public TreeBar setWidth(Object width) {
        if (style == null) {
            style = new java.util.HashMap<>();
        }
        style.put("width", width);
        return this;
    }

    // 设置树形数据
    public TreeBar setTreeData(List<TreeData> treeData) {
        this.treeData = treeData;
        return this;
    }

    // 设置树形数据
    public TreeBar setData(List<TreeData> treeData) {
        this.treeData = treeData;
        return this;
    }

    // 设置自定义样式
    public TreeBar setStyle(Map<String, Object> style) {
        this.style = style;
        return this;
    }
}