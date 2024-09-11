package io.quarkcloud.quarkadmin.component.form.fields;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkcloud.quarkadmin.component.Component;
import io.quarkcloud.quarkadmin.component.form.Closure;
import io.quarkcloud.quarkadmin.component.form.Rule;
import io.quarkcloud.quarkadmin.component.table.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TreeSelect extends Component {

    @Data
    public static class FieldNames {

        String title;

        String key;

        String children;
    }

    @Data
    public static class TreeData {

        // 选项的标签
        public TreeData(String title, Object value) {
            this.title = title;
            this.value = value;
        }

        // 选项的文本
        String title;

        // 选项的 value
        Object value;

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        List<TreeData> children;

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        boolean disabled;

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        boolean disableCheckbox;

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        boolean selectable;

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        boolean checkable;
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
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean required;

    // 会在 label 旁增加一个 icon，悬浮后展示配置的信息
    String tooltip;

    // 子节点的值的属性，如 Switch 的是 'checked'。该属性为 getValueProps 的封装，自定义 getValueProps 后会失效
    String valuePropName;

    // 需要为输入控件设置布局样式时，使用该属性，用法同 labelCol。你可以通过 Form 的 wrapperCol 进行统一设置，不会作用于嵌套
    // Item。当和 Form 同时设置时，以 Item 为准
    Object wrapperCol;

    // 列表页、详情页中列属性
    @JsonIgnore
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
    @JsonIgnore
    List<Rule> rules;

    // 创建页校验规则
    @JsonIgnore
    List<Rule> creationRules;

    // 编辑页校验规则
    @JsonIgnore
    List<Rule> updateRules;

    // 前端校验规则，设置字段的校验逻辑
    List<Rule> frontendRules;

    // When组件
    When when;

    // When组件里的字段
    List<WhenItem> whenItem;

    // 在列表页展示
    @JsonIgnore
    boolean showOnIndex;

    // 在详情页展示
    @JsonIgnore
    boolean showOnDetail;

    // 在创建页面展示
    @JsonIgnore
    boolean showOnCreation;

    // 在编辑页面展示
    @JsonIgnore
    boolean showOnUpdate;

    // 在导出的Excel上展示
    @JsonIgnore
    boolean showOnExport;

    // 在导入Excel上展示
    @JsonIgnore
    boolean showOnImport;

    // 回调函数
    @JsonIgnore
    Closure callback;

    // 可以点击清除图标删除内容
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean allowClear;

    // 是否在选中项后清空搜索框，只在 mode 为 multiple 或 tags 时有效
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean autoClearSearchValue;

    // 是否有边框
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean bordered;

    // 默认选中的选项
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object defaultValue;

    // 整组失效
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean disabled;

    // 下拉菜单的 className 属性
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String popupClassName;

    // 下拉菜单和选择器同宽。默认将设置 min-width，当值小于选择框宽度时会被忽略。false 时会关闭虚拟滚动
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object dropdownMatchSelectWidth;

    // 下拉菜单的 style 属性
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object dropdownStyle;

    // 自定义 options 中 label value children 的字段
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    FieldNames fieldNames;

    // 是否把每个选项的 label 包装到 value 中，会把 Select 的 value 类型从 string 变为 { value: string,
    // label: ReactNode } 的格式
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean labelInValue;

    // 设置弹窗滚动高度 256
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    int listHeight;

    // 最多显示多少个 tag，响应式模式会对性能产生损耗
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    int maxTagCount;

    // 隐藏 tag 时显示的内容
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String maxTagPlaceholder;

    // 最大显示的 tag 文本长度
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    int maxTagTextLength;

    // 支持多选（当设置 treeCheckable 时自动变为 true）
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean multiple;

    // 当下拉列表为空时显示的内容
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String notFoundContent;

    // 选择框默认文本
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String placeholder;

    // 选择框弹出的位置 bottomLeft bottomRight topLeft topRight
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String placement;

    // 控制搜索文本
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String searchValue;

    // 是否显示下拉小箭头
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean showArrow;

    // 配置是否可搜索
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean showSearch;

    // 选择框大小
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String size;

    // 设置校验状态 'error' | 'warning'
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String status;

    // 自定义的选择框后缀图标
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object suffixIcon;

    // 自定义树节点的展开/折叠图标
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object switcherIcon;

    // 显示 Checkbox
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean treeCheckable;

    // checkable 状态下节点选择完全受控（父子节点选中状态不再关联），会使得 labelInValue 强制为 true
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean treeCheckStrictly;

    // treeNodes 数据，如果设置则不需要手动构造 TreeNode 节点（value 在整个树范围内唯一）
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    List<TreeData> treeData;

    // 使用简单格式的 treeData，具体设置参考可设置的类型
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object treeDataSimpleMode;

    // 默认展开所有树节点
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean treeDefaultExpandAll;

    // 默认展开的树节点
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    List<Object> treeDefaultExpandedKeys;

    // 点击节点 title 时的展开逻辑，可选：false | click | doubleClick
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object treeExpandAction;

    // 设置展开的树节点
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    List<Object> treeExpandedKeys;

    // 是否展示 TreeNode title 前的图标，没有默认样式，如设置为 true，需要自行定义图标相关样式
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean treeIcon;

    // 是否展示线条样式
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean treeLine;

    // 输入项过滤对应的 treeNode 属性
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String treeNodeFilterProp;

    // 作为显示的 prop 设置
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String treeNodeLabelProp;

    // 指定当前选中的条目，多选时为一个数组。（value 数组引用未变化时，Select 不会更新）
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object value;

    // 设置 false 时关闭虚拟滚动
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean virtual;

    // 自定义样式
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Map<String, Object> style;

    public TreeSelect() {
        this.component = "treeSelectField";
        this.colon = true;
        this.labelAlign = "right";
        this.showOnIndex = true;
        this.showOnDetail = true;
        this.showOnCreation = true;
        this.showOnUpdate = true;
        this.showOnExport = true;
        this.showOnImport = true;
        this.allowClear = true;
        this.column = new Column();
        this.treeDefaultExpandAll = true;
        this.treeLine = true;
        this.setComponentKey();
        this.style = new HashMap<>();
        this.rules = new ArrayList<>();
        this.setWidth(200); // Set default width
    }

    // Field 的长度，我们归纳了常用的 Field 长度以及适合的场景，支持了一些枚举 "xs" , "s" , "m" , "l" , "x"
    public TreeSelect setWidth(Object width) {
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
    // new TreeSelect().
    // setRules(Arrays.asList(
    // rule.required(true, "用户名必须填写"), // 需要用户名字段不能为空
    // rule.min(6, "用户名不能少于6个字符"), // 用户名最少需要6个字符
    // rule.max(20, "用户名不能超过20个字符") // 用户名最多只能包含20个字符
    // ));
    public TreeSelect setRules(List<Rule> rules) {

        rules.forEach(rule -> rule.setName(name));
        this.rules = rules;

        return this;
    }

    // 校验规则，只在创建表单提交时生效
    //
    // new TreeSelect().
    // setCreationRules(Arrays.asList(
    // rule.unique("admins", "username", "用户名已存在"),
    // ));
    public TreeSelect setCreationRules(List<Rule> rules) {

        rules.forEach(rule -> rule.setName(name));
        this.creationRules = rules;

        return this;
    }

    // 校验规则，只在更新表单提交时生效
    //
    // new TreeSelect().
    // setUpdateRules(Arrays.asList(
    // rule.unique("admins", "username", "用户名已存在"),
    // ));
    public TreeSelect setUpdateRules(List<Rule> rules) {

        rules.forEach(rule -> rule.setName(name));
        this.updateRules = rules;

        return this;
    }

    // 生成前端验证规则
    public TreeSelect buildFrontendRules(String path) {
        List<Rule> frontendRules = new ArrayList<>();
        String[] uri = path.split("/");
        boolean isCreating = (uri[uri.length - 1].equals("create")) || (uri[uri.length - 1].equals("store"));
        boolean isEditing = (uri[uri.length - 1].equals("edit")) || (uri[uri.length - 1].equals("update"));

        Function<List<Rule>, List<Rule>> convertToFrontendRules = Rule::convertToFrontendRules;
        frontendRules.addAll(convertToFrontendRules.apply(this.rules));

        if (isCreating) {
            frontendRules.addAll(convertToFrontendRules.apply(this.creationRules));
        }

        if (isEditing) {
            frontendRules.addAll(convertToFrontendRules.apply(this.updateRules));
        }
        
        this.frontendRules = frontendRules;

        return this;
    }

    // 表头的筛选菜单项，当值为 true 时，自动使用 valueEnum 生成，只在列表页中有效
    public TreeSelect setFilters(boolean filters) {
        this.filters = filters;

        return this;
    }

    // 表头的筛选菜单项，当值为 true 时，自动使用 valueEnum 生成，只在列表页中有效
    public TreeSelect setFilters(Map<String, String> filters) {
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
    // new TreeSelect().setWhen(option, callback)
    public TreeSelect setWhen(Object option, Closure callback) {
        this.setWhen("=", option, callback);
        return this;
    }

    // 设置When组件数据
    //
    // new TreeSelect().setWhen(">", option, callback)
    public TreeSelect setWhen(String operator, Object option, Closure callback) {

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
    public TreeSelect hideFromIndex(boolean callback) {
        this.showOnIndex = !callback;

        return this;
    }

    // Specify that the element should be hidden from the detail view.
    public TreeSelect hideFromDetail(boolean callback) {
        this.showOnDetail = !callback;

        return this;
    }

    // Specify that the element should be hidden from the creation view.
    public TreeSelect hideWhenCreating(boolean callback) {
        this.showOnCreation = !callback;

        return this;
    }

    // Specify that the element should be hidden from the update view.
    public TreeSelect hideWhenUpdating(boolean callback) {
        this.showOnUpdate = !callback;

        return this;
    }

    // Specify that the element should be hidden from the export file.
    public TreeSelect hideWhenExporting(boolean callback) {
        this.showOnExport = !callback;

        return this;
    }

    // Specify that the element should be hidden from the import file.
    public TreeSelect hideWhenImporting(boolean callback) {
        this.showOnImport = !callback;

        return this;
    }

    // Specify that the element should be hidden from the index view.
    public TreeSelect onIndexShowing(boolean callback) {
        this.showOnIndex = callback;

        return this;
    }

    // Specify that the element should be hidden from the detail view.
    public TreeSelect onDetailShowing(boolean callback) {
        this.showOnDetail = callback;

        return this;
    }

    // Specify that the element should be hidden from the creation view.
    public TreeSelect showOnCreating(boolean callback) {
        this.showOnCreation = callback;

        return this;
    }

    // Specify that the element should be hidden from the update view.
    public TreeSelect showOnUpdating(boolean callback) {
        this.showOnUpdate = callback;

        return this;
    }

    // Specify that the element should be hidden from the export file.
    public TreeSelect showOnExporting(boolean callback) {
        this.showOnExport = callback;

        return this;
    }

    // Specify that the element should be hidden from the import file.
    public TreeSelect showOnImporting(boolean callback) {
        this.showOnImport = callback;

        return this;
    }

    // Specify that the element should only be shown on the index view.
    public TreeSelect onlyOnIndex() {
        this.showOnIndex = true;
        this.showOnDetail = false;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on the detail view.
    public TreeSelect onlyOnDetail() {
        this.showOnIndex = false;
        this.showOnDetail = true;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on forms.
    public TreeSelect onlyOnForms() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = true;
        this.showOnUpdate = true;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on export file.
    public TreeSelect onlyOnExport() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = true;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on import file.
    public TreeSelect onlyOnImport() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = false;
        this.showOnImport = true;

        return this;
    }

    // Specify that the element should be hidden from forms.
    public TreeSelect exceptOnForms() {
        this.showOnIndex = true;
        this.showOnDetail = true;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = true;
        this.showOnImport = true;

        return this;
    }

    // Check for showing when updating.
    @JsonIgnore
    public boolean isShownOnUpdate() {
        return this.showOnUpdate;
    }

    // Check showing on index.
    @JsonIgnore
    public boolean isShownOnIndex() {
        return this.showOnIndex;
    }

    // Check showing on detail.
    @JsonIgnore
    public boolean isShownOnDetail() {
        return this.showOnDetail;
    }

    // Check for showing when creating.
    @JsonIgnore
    public boolean isShownOnCreation() {
        return this.showOnCreation;
    }

    // Check for showing when exporting.
    @JsonIgnore
    public boolean isShownOnExport() {
        return this.showOnExport;
    }

    // Check for showing when importing.
    @JsonIgnore
    public boolean isShownOnImport() {
        return this.showOnImport;
    }

    // 当前列值的枚举 valueEnum
    public Map<?, ?> getValueEnum() {
        return null;
    }

    // 当前可选项
    public List<TreeData> getOptions() {
        return this.treeData;
    }
}
