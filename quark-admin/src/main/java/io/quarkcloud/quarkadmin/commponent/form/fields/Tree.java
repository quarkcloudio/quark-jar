package io.quarkcloud.quarkadmin.commponent.form.fields;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkcloud.quarkadmin.commponent.Commponent;
import io.quarkcloud.quarkadmin.commponent.form.Rule;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Tree extends Commponent {

    @Data
    public static class FieldNames {

        String title;

        String key;

        String children;
    }

    @Data
    public static class TreeData {

        // 当树为 checkable 时，设置独立节点是否展示 Checkbox
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        boolean checkable;

        // 禁掉 checkbox
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        boolean disableCheckbox;

        // 禁掉响应
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        boolean disabled;

        // 自定义图标。可接收组件，props 为当前节点 props
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        Object icon;

        // 设置为叶子节点 (设置了 loadData 时有效)。为 false 时会强制将其作为父节点
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        boolean isLeaf;

        // 被树的 (default)ExpandedKeys / (default)CheckedKeys / (default)SelectedKeys
        // 属性所用。注意：整个树范围内的所有节点的 key 值不能重复！
        Object key;

        // 设置节点是否可被选中
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        boolean selectable;

        // 标题
        String title;

        // 子节点
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        TreeData[] children;
    }

    // 开启 grid 模式时传递给 Row, 仅在ProFormGroup, ProFormList, ProFormFieldSet 中有效，默认：{
    // gutter: 8 }
    Map<String, Object> rowProps;

    // 开启 grid 模式时传递给 Col，默认：{ xs: 24 }
    Map<String, Object> colProps;

    // 是否是次要控件，只针对 LightFilter 下有效
    boolean secondary;

    // 配合 label 属性使用，表示是否显示 label 后面的冒号
    boolean colon;

    // 额外的提示信息，和 help 类似，当需要错误信息和提示文案同时出现时，可以使用这个。
    String extra;

    // 配合 valiTextStatus 属性使用，展示校验状态图标，建议只配合 Input 组件使用
    boolean hasFeedback;

    // 提示信息，如不设置，则会根据校验规则自动生成
    String help;

    // 是否隐藏字段（依然会收集和校验字段）
    boolean hidden;

    // 设置子元素默认值，如果与 Form 的 initialValues 冲突则以 Form 为准
    Object initialValue;

    // label 标签的文本
    String label;

    // 标签文本对齐方式
    String labelAlign;

    // label 标签布局，同 <Col> 组件，设置 span offset 值，如 {span: 3, offset: 12} 或 sm: {span:
    // 3, offset: 12}。你可以通过 Form 的 labelCol 进行统一设置，不会作用于嵌套 Item。当和 Form 同时设置时，以 Item
    // 为准
    Object labelCol;

    // 字段名，支持数组
    String name;

    // 为 true 时不带样式，作为纯字段控件使用
    boolean noStyle;

    // 必填样式设置。如不设置，则会根据校验规则自动生成
    boolean required;

    // 会在 label 旁增加一个 icon，悬浮后展示配置的信息
    String tooltip;

    // 子节点的值的属性，如 Switch 的是 'checked'。该属性为 getValueProps 的封装，自定义 getValueProps 后会失效
    String valuePropName;

    // 需要为输入控件设置布局样式时，使用该属性，用法同 labelCol。你可以通过 Form 的 wrapperCol 进行统一设置，不会作用于嵌套
    // Item。当和 Form 同时设置时，以 Item 为准
    Object wrapperCol;

    // 列表页、详情页中列属性
    Object column;

    // 设置列的对齐方式,left | right | center，只在列表页、详情页中有效
    String align;

    // （IE 下无效）列是否固定，可选 true (等效于 left) left rightr，只在列表页中有效
    Object fixed;

    // 表格列是否可编辑，只在列表页中有效
    boolean editable;

    // 是否自动缩略，只在列表页、详情页中有效
    boolean ellipsis;

    // 是否支持复制，只在列表页、详情页中有效
    boolean copyable;

    // 表头的筛选菜单项，当值为 true 时，自动使用 valueEnum 生成，只在列表页中有效
    Object filters;

    // 查询表单中的权重，权重大排序靠前，只在列表页中有效
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    int order;

    // 可排序列，只在列表页中有效
    Object sorter;

    // 包含列的数量，只在详情页中有效
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    int span;

    // 设置列宽，只在列表页中有效
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    int columnWidth;

    // 获取数据接口
    String api;

    // 是否忽略保存到数据库，默认为 false
    boolean ignore;

    // 全局校验规则
    List<Rule> rules;

    // 创建页校验规则
    List<Rule> creationRules;

    // 编辑页校验规则
    List<Rule> updateRules;

    // 前端校验规则，设置字段的校验逻辑
    List<Rule> frontendRules;

    // When组件
    When when;

    // When组件里的字段
    List<WhenItem> whenItem;

    // 在列表页展示
    boolean showOnIndex;

    // 在详情页展示
    boolean showOnDetail;

    // 在创建页面展示
    boolean showOnCreation;

    // 在编辑页面展示
    boolean showOnUpdate;

    // 在导出的Excel上展示
    boolean showOnExport;

    // 在导入Excel上展示
    boolean showOnImport;

    // 回调函数
    @FunctionalInterface
    interface Closure {
        Object callback();
    }

    // 是否自动展开父节点、
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean autoExpandParent;

    // 是否节点占据一行
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean blockNode;

    // 节点前添加 Checkbox 复选框
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean checkable;

    // （受控）选中复选框的树节点
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    List<Object> checkedKeys;

    // checkable 状态下节点选择完全受控（父子节点选中状态不再关联）
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean checkStrictly;

    // 默认选中复选框的树节点
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    List<Object> defaultCheckedKeys;

    // 默认展开所有树节点
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean defaultExpandAll;

    // 默认展开指定的树节点
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    List<Object> defaultExpandedKeys;

    // 默认展开父节点
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean defaultExpandParent;

    // 默认选中的树节点
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    List<Object> defaultSelectedKeys;

    // 默认选中的选项
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object defaultValue;

    // 整组失效
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean disabled;

    // 设置节点可拖拽
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean draggable;

    // （受控）展开指定的树节点
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    List<Object> expandedKeys;

    // 自定义 options 中 label value children 的字段
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    FieldNames fieldNames;

    // 设置虚拟滚动容器高度
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    int height;

    // 自定义树节点图标
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object icon;

    // 支持点选多个节点（节点本身）
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean multiple;

    // 占位文本
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String placeholder;

    // 添加在 Tree 最外层的 className
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String rootClassName;

    // 添加在 Tree 最外层的 style
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object rootStyle;

    // 是否可选中
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean selectable;

    // （受控）设置选中的树节点
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    List<Object> selectedKeys;

    // 是否展示 TreeNode title 前的图标
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean showIcon;

    // 是否展示连接线
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean showLine;

    // 自定义树节点的展开/折叠图标
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object switcherIcon;

    // treeNodes 数据，如果设置则不需要手动构造 TreeNode 节点
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    List<TreeData> treeData;

    // 指定当前选中的条目，多选时为一个数组。（value 数组引用未变化时，Select 不会更新）
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object value;

    // 设置 false 时关闭虚拟滚动
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean virtual;

    // 自定义样式
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Map<String, Object> style;

    public Tree() {
        this.component = "treeField";
        this.setComponentKey();
        this.style = new HashMap<>();
    }

    // Field 的长度，我们归纳了常用的 Field 长度以及适合的场景，支持了一些枚举 "xs" , "s" , "m" , "l" , "x"
    public Tree setWidth(Object width) {
        Map<String, Object> style = new HashMap<>();

        this.style.forEach((key, value) -> {
            style.put(key, value);
        });
        style.put("width", width);
        this.style = style;

        return this;
    }

    // 校验规则，设置字段的校验逻辑
    //
    // new Tree().
    // setRules(Arrays.asList(
    // rule.required(true, "用户名必须填写"), // 需要用户名字段不能为空
    // rule.min(6, "用户名不能少于6个字符"), // 用户名最少需要6个字符
    // rule.max(20, "用户名不能超过20个字符") // 用户名最多只能包含20个字符
    // ));
    public Tree setRules(List<Rule> rules) {

        rules.forEach(rule -> rule.setName(name));
        this.rules = rules;

        return this;
    }

    // 校验规则，只在创建表单提交时生效
    //
    // new Tree().
    // setCreationRules(Arrays.asList(
    // rule.unique("admins", "username", "用户名已存在"),
    // ));
    public Tree setCreationRules(List<Rule> rules) {

        rules.forEach(rule -> rule.setName(name));
        this.creationRules = rules;

        return this;
    }

    // 校验规则，只在更新表单提交时生效
    //
    // new Tree().
    // setUpdateRules(Arrays.asList(
    // rule.unique("admins", "username", "用户名已存在"),
    // ));
    public Tree setUpdateRules(List<Rule> rules) {

        rules.forEach(rule -> rule.setName(name));
        this.updateRules = rules;

        return this;
    }

    // 生成前端验证规则
    public Tree buildFrontendRules(String path) {

        List<Rule> rules = new ArrayList<>();
        List<Rule> creationRules = new ArrayList<>();
        List<Rule> updateRules = new ArrayList<>();
        List<Rule> frontendRules = new ArrayList<>();

        String[] uri = path.split("/");
        boolean isCreating = (uri[uri.length - 1].equals("create")) || (uri[uri.length - 1].equals("store"));
        boolean isEditing = (uri[uri.length - 1].equals("edit")) || (uri[uri.length - 1].equals("update"));

        Function<List<Rule>, List<Rule>> convertToFrontendRules = Rule::convertToFrontendRules;

        frontendRules.addAll(convertToFrontendRules.apply(rules));

        if (isCreating) {
            frontendRules.addAll(convertToFrontendRules.apply(creationRules));
        }

        if (isEditing) {
            frontendRules.addAll(convertToFrontendRules.apply(updateRules));
        }

        return this;
    }

    // 表头的筛选菜单项，当值为 true 时，自动使用 valueEnum 生成，只在列表页中有效
    public Tree setFilters(boolean filters) {
        this.filters = filters;

        return this;
    }

    // 表头的筛选菜单项，当值为 true 时，自动使用 valueEnum 生成，只在列表页中有效
    public Tree setFilters(Map<String, String> filters) {
        List<Map<String, String>> tmpFilters = new ArrayList<>();
        filters.forEach((k, v) -> {
            Map<String, String> map = new HashMap<String, String>();
            map.put("text", v);
            map.put("value", k);
            tmpFilters.add(map);
        });
        this.filters = tmpFilters;

        return this;
    }

    // 设置When组件数据
    //
    // new Tree().setWhen(option, callback)
    public Tree setWhen(Object option, Closure callback) {
        this.setWhen("=", option, callback);
        return this;
    }

    // 设置When组件数据
    //
    // new Tree().setWhen(">", option, callback)
    public Tree setWhen(String operator, Object option, Closure callback) {

        WhenItem item = new WhenItem();

        item.body = callback.callback();
        item.conditionName = this.name;
        item.conditionOperator = operator;
        item.option = option;

        StringBuilder conditionBuilder = new StringBuilder();
        conditionBuilder.append("<%=String(").append(this.name).append(")");

        switch (operator) {
            case "=":
                conditionBuilder.append(" === '").append(option).append("' %>");
                break;
            case ">":
                conditionBuilder.append(" > '").append(option).append("' %>");
                break;
            case "<":
                conditionBuilder.append(" < '").append(option).append("' %>");
                break;
            case "<=":
                conditionBuilder.append(" <= '").append(option).append("' %>");
                break;
            case ">=":
                conditionBuilder.append(" >= '").append(option).append("' %>");
                break;
            case "has":
                conditionBuilder.append(".indexOf('").append(option).append("') !=-1) %>");
                break;
            case "in":
                ObjectMapper mapper = new ObjectMapper();
                String jsonStr;
                try {
                    jsonStr = mapper.writeValueAsString(option);
                    conditionBuilder.append(jsonStr).append(".indexOf(").append(this.name).append(") !=-1) %>");
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                break;
            default:
                conditionBuilder.append(" === '").append(option).append("' %>");
                break;
        }

        item.condition = conditionBuilder.toString();
        whenItem.add(item);
        when.setItems(whenItem);

        return this;
    }

    // Specify that the element should be hidden from the index view.
    public Tree hideFromIndex(boolean callback) {
        this.showOnIndex = !callback;

        return this;
    }

    // Specify that the element should be hidden from the detail view.
    public Tree hideFromDetail(boolean callback) {
        this.showOnDetail = !callback;

        return this;
    }

    // Specify that the element should be hidden from the creation view.
    public Tree hideWhenCreating(boolean callback) {
        this.showOnCreation = !callback;

        return this;
    }

    // Specify that the element should be hidden from the update view.
    public Tree hideWhenUpdating(boolean callback) {
        this.showOnUpdate = !callback;

        return this;
    }

    // Specify that the element should be hidden from the export file.
    public Tree hideWhenExporting(boolean callback) {
        this.showOnExport = !callback;

        return this;
    }

    // Specify that the element should be hidden from the import file.
    public Tree hideWhenImporting(boolean callback) {
        this.showOnImport = !callback;

        return this;
    }

    // Specify that the element should be hidden from the index view.
    public Tree onIndexShowing(boolean callback) {
        this.showOnIndex = callback;

        return this;
    }

    // Specify that the element should be hidden from the detail view.
    public Tree onDetailShowing(boolean callback) {
        this.showOnDetail = callback;

        return this;
    }

    // Specify that the element should be hidden from the creation view.
    public Tree showOnCreating(boolean callback) {
        this.showOnCreation = callback;

        return this;
    }

    // Specify that the element should be hidden from the update view.
    public Tree showOnUpdating(boolean callback) {
        this.showOnUpdate = callback;

        return this;
    }

    // Specify that the element should be hidden from the export file.
    public Tree showOnExporting(boolean callback) {
        this.showOnExport = callback;

        return this;
    }

    // Specify that the element should be hidden from the import file.
    public Tree showOnImporting(boolean callback) {
        this.showOnImport = callback;

        return this;
    }

    // Specify that the element should only be shown on the index view.
    public Tree onlyOnIndex() {
        this.showOnIndex = true;
        this.showOnDetail = false;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on the detail view.
    public Tree onlyOnDetail() {
        this.showOnIndex = false;
        this.showOnDetail = true;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on forms.
    public Tree onlyOnForms() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = true;
        this.showOnUpdate = true;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on export file.
    public Tree onlyOnExport() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = true;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on import file.
    public Tree onlyOnImport() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = false;
        this.showOnImport = true;

        return this;
    }

    // Specify that the element should be hidden from forms.
    public Tree exceptOnForms() {
        this.showOnIndex = true;
        this.showOnDetail = true;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = true;
        this.showOnImport = true;

        return this;
    }

    // Check for showing when updating.
    public boolean isShownOnUpdate() {
        return this.showOnUpdate;
    }

    // Check showing on index.
    public boolean isShownOnIndex() {
        return this.showOnIndex;
    }

    // Check showing on detail.
    public boolean isShownOnDetail() {
        return this.showOnDetail;
    }

    // Check for showing when creating.
    public boolean isShownOnCreation() {
        return this.showOnCreation;
    }

    // Check for showing when exporting.
    public boolean isShownOnExport() {
        return this.showOnExport;
    }

    // Check for showing when importing.
    public boolean isShownOnImport() {
        return this.showOnImport;
    }

    // 当前列值的枚举 valueEnum
    public Map<?, ?> getValueEnum() {
        return null;
    }
}
