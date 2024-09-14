package io.quarkcloud.quarkadmin.component.form.fields;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

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
public class SelectField extends Component {

    @Data
    public static class Option {
        
        // 选项的标签
        public Option(String label, Object value) {
            this.label = label;
            this.value = value;
        }

        // 选项的文本
        String label;

        // 选项的 value
        Object value;

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        boolean disabled;
    }

    @Data
    public static class FieldNames {

        String label;

        String value;

        String children;
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

    // 是否支持清除，默认true
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean allowClear;

    // 是否在选中项后清空搜索框，只在 mode 为 multiple 或 tags 时有效
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean autoClearSearchValue;

    // 自动获取焦点，默认false
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean autoFocus;

    // 是否有边框，默认true
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean bordered;

    // 自定义的选择框清空图标
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object clearIcon;

    // 是否默认高亮第一个选项
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean defaultActiveFirstOption;

    // 是否默认展开下拉菜单
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean defaultOpen;

    // 默认的选中项
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

    // 加载中状态
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean loading;

    // 最多显示多少个 tag，响应式模式会对性能产生损耗
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    int maxTagCount;

    // 隐藏 tag 时显示的内容
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String maxTagPlaceholder;

    // 最大显示的 tag 文本长度
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    int maxTagTextLength;

    // 自定义多选时当前选中的条目图标
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object menuItemSelectedIcon;

    // 设置 Select 的模式为多选或标签 multiple | tags
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String mode;

    // 当下拉列表为空时显示的内容
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String notFoundContent;

    // 控制浮层显隐
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean open;

    // 可选项数据源
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    List<Option> options;

    // 输入框占位文本
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String placeholder;

    // 浮层预设位置，bottomLeft bottomRight topLeft topRight
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String placement;

    // 自定义的多选框清除图标
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object removeIcon;

    // 设置搜索的值，需要与 showSearch 配合使用
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String searchValue;

    // 是否显示下拉小箭头
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean showArrow;

    // 在选择框中显示搜索框
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean showSearch;

    // 输入框大小，large | middle | small
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String size;

    // 设置校验状态，'error' | 'warning'
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String status;

    // 自定义的选择框后缀图标
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object suffixIcon;

    // 自动分词的分隔符，仅在 mode="tags" 时生效
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object tokenSeparators;

    // 指定选中项，string[] | number[]
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object value;

    // 设置 false 时关闭虚拟滚动
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean virtual;

    // 单向联动
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Map<String, String> load;

    // 自定义样式
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Map<String, Object> style;

    public SelectField() {
        this.component = "selectField";
        this.colon = true;
        this.labelAlign = "right";
        this.showOnIndex = true;
        this.showOnDetail = true;
        this.showOnCreation = true;
        this.showOnUpdate = true;
        this.showOnExport = true;
        this.showOnImport = true;
        this.column = new Column();
        this.allowClear = true;
        this.setComponentKey();
        this.style = new HashMap<>();
        this.rules = new ArrayList<>();
        this.setWidth(200);
        this.whenItem = new ArrayList<>();
        this.when = new When();
    }

    // Field 的长度，我们归纳了常用的 Field 长度以及适合的场景，支持了一些枚举 "xs" , "s" , "m" , "l" , "x"
    public SelectField setWidth(Object width) {
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
    // new SelectField().
    // setRules(Arrays.asList(
    // rule.required(true, "用户名必须填写"), // 需要用户名字段不能为空
    // rule.min(6, "用户名不能少于6个字符"), // 用户名最少需要6个字符
    // rule.max(20, "用户名不能超过20个字符") // 用户名最多只能包含20个字符
    // ));
    public SelectField setRules(List<Rule> rules) {

        rules.forEach(rule -> rule.setName(name));
        this.rules = rules;

        return this;
    }

    // 校验规则，只在创建表单提交时生效
    //
    // new SelectField().
    // setCreationRules(Arrays.asList(
    // rule.unique("admins", "username", "用户名已存在"),
    // ));
    public SelectField setCreationRules(List<Rule> rules) {

        rules.forEach(rule -> rule.setName(name));
        this.creationRules = rules;

        return this;
    }

    // 校验规则，只在更新表单提交时生效
    //
    // new SelectField().
    // setUpdateRules(Arrays.asList(
    // rule.unique("admins", "username", "用户名已存在"),
    // ));
    public SelectField setUpdateRules(List<Rule> rules) {

        rules.forEach(rule -> rule.setName(name));
        this.updateRules = rules;

        return this;
    }

    // 生成前端验证规则
    public SelectField buildFrontendRules(String path) {
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
    public SelectField setFilters(boolean filters) {
        this.filters = filters;

        return this;
    }

    // 表头的筛选菜单项，当值为 true 时，自动使用 valueEnum 生成，只在列表页中有效
    public SelectField setFilters(Map<String, String> filters) {
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
    // new SelectField().setWhen(option, callback)
    public SelectField setWhen(Object option, Closure callback) {
        this.setWhen("=", option, callback);
        return this;
    }

    // 设置When组件数据
    //
    // new SelectField().setWhen(">", option, callback)
    public SelectField setWhen(String operator, Object option, Closure callback) {

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
    public SelectField hideFromIndex(boolean callback) {
        this.showOnIndex = !callback;

        return this;
    }

    // Specify that the element should be hidden from the detail view.
    public SelectField hideFromDetail(boolean callback) {
        this.showOnDetail = !callback;

        return this;
    }

    // Specify that the element should be hidden from the creation view.
    public SelectField hideWhenCreating(boolean callback) {
        this.showOnCreation = !callback;

        return this;
    }

    // Specify that the element should be hidden from the update view.
    public SelectField hideWhenUpdating(boolean callback) {
        this.showOnUpdate = !callback;

        return this;
    }

    // Specify that the element should be hidden from the export file.
    public SelectField hideWhenExporting(boolean callback) {
        this.showOnExport = !callback;

        return this;
    }

    // Specify that the element should be hidden from the import file.
    public SelectField hideWhenImporting(boolean callback) {
        this.showOnImport = !callback;

        return this;
    }

    // Specify that the element should be hidden from the index view.
    public SelectField onIndexShowing(boolean callback) {
        this.showOnIndex = callback;

        return this;
    }

    // Specify that the element should be hidden from the detail view.
    public SelectField onDetailShowing(boolean callback) {
        this.showOnDetail = callback;

        return this;
    }

    // Specify that the element should be hidden from the creation view.
    public SelectField showOnCreating(boolean callback) {
        this.showOnCreation = callback;

        return this;
    }

    // Specify that the element should be hidden from the update view.
    public SelectField showOnUpdating(boolean callback) {
        this.showOnUpdate = callback;

        return this;
    }

    // Specify that the element should be hidden from the export file.
    public SelectField showOnExporting(boolean callback) {
        this.showOnExport = callback;

        return this;
    }

    // Specify that the element should be hidden from the import file.
    public SelectField showOnImporting(boolean callback) {
        this.showOnImport = callback;

        return this;
    }

    // Specify that the element should only be shown on the index view.
    public SelectField onlyOnIndex() {
        this.showOnIndex = true;
        this.showOnDetail = false;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on the detail view.
    public SelectField onlyOnDetail() {
        this.showOnIndex = false;
        this.showOnDetail = true;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on forms.
    public SelectField onlyOnForms() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = true;
        this.showOnUpdate = true;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on export file.
    public SelectField onlyOnExport() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = true;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on import file.
    public SelectField onlyOnImport() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = false;
        this.showOnImport = true;

        return this;
    }

    // Specify that the element should be hidden from forms.
    public SelectField exceptOnForms() {
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
        Map<Object, String> data = new HashMap<>();
        for (Option option : options) {
            data.put(option.getValue(), option.getLabel());
        }
        return data;
    }

    public SelectField setLoad(String field, String api) {

        this.load.put("field", field);
        this.load.put("api", api);

        return this;
    }

    // 根据value值获取Option的Label
    public String getOptionLabel(Object value) {

        List<?> values = new ArrayList<>();

        // Check if value is a string and contains JSON structure
        if (value instanceof String) {
            String stringValue = (String) value;
            if (stringValue.contains("[") || stringValue.contains("{")) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    values = objectMapper.readValue(stringValue, List.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Collect values if not already done
        if (values.isEmpty()) {
            values = List.of(value);
        }

        // Find matching labels
        List<String> labels = values.stream()
                .flatMap(val -> options.stream().filter(option -> Objects.equals(val, option.getValue())))
                .map(Option::getLabel)
                .collect(Collectors.toList());

        // Join labels into a single string
        return String.join(",", labels);
    }

    // 根据label值获取Option的Value
    public Object getOptionValue(String label) {

        List<Object> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        // Split labels by commas and add to list
        if (label.contains(",")) {
            labels.addAll(Arrays.asList(label.split(",")));
        } else if (label.contains("，")) {
            labels.addAll(Arrays.asList(label.split("，")));
        } else {
            labels.add(label);
        }

        // Find matching values
        if (labels.size() > 1) {
            values = labels.stream()
                    .flatMap(lbl -> options.stream().filter(option -> Objects.equals(option.getLabel(), lbl)))
                    .map(Option::getValue)
                    .collect(Collectors.toList());
        } else {
            values = options.stream()
                    .filter(option -> Objects.equals(option.getLabel(), label))
                    .map(Option::getValue)
                    .collect(Collectors.toList());
        }

        // Return values if more than one, otherwise single value
        return values.size() > 1 ? values : values.isEmpty() ? null : values.get(0);
    }

    // 获取Option的Labels
    public String getOptionLabels() {
        return options.stream()
                .map(Option::getLabel)
                .collect(Collectors.joining(","));
    }
}
