package io.quarkcloud.quarkadmin.commponent.form.fields;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

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
public class Radio extends Commponent {

    @Data
    public static class Option {

        String label;

        Object value;

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        boolean disabled;
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

    // RadioButton 的风格样式，目前有描边和填色两种风格 outline | solid
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object buttonStyle;

    // 默认选中的选项
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object defaultValue;

    // 整组失效
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean disabled;

    // 可选项数据源
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    List<Option> options;

    // 用于设置 Radio options 类型 default | button
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String optionType;

    // 大小，只对按钮样式生效, large | middle | small
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String size;

    // 指定选中项,string[] | number[]
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object value;

    public Radio() {
        this.component = "radioField";
        this.setComponentKey();
        this.style = new HashMap<>();
    }

    // Field 的长度，我们归纳了常用的 Field 长度以及适合的场景，支持了一些枚举 "xs" , "s" , "m" , "l" , "x"
    public Radio setWidth(Object width) {
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
    // new Radio().
    // setRules(Arrays.asList(
    // rule.required(true, "用户名必须填写"), // 需要用户名字段不能为空
    // rule.min(6, "用户名不能少于6个字符"), // 用户名最少需要6个字符
    // rule.max(20, "用户名不能超过20个字符") // 用户名最多只能包含20个字符
    // ));
    public Radio setRules(List<Rule> rules) {

        rules.forEach(rule -> rule.setName(name));
        this.rules = rules;

        return this;
    }

    // 校验规则，只在创建表单提交时生效
    //
    // new Radio().
    // setCreationRules(Arrays.asList(
    // rule.unique("admins", "username", "用户名已存在"),
    // ));
    public Radio setCreationRules(List<Rule> rules) {

        rules.forEach(rule -> rule.setName(name));
        this.creationRules = rules;

        return this;
    }

    // 校验规则，只在更新表单提交时生效
    //
    // new Radio().
    // setUpdateRules(Arrays.asList(
    // rule.unique("admins", "username", "用户名已存在"),
    // ));
    public Radio setUpdateRules(List<Rule> rules) {

        rules.forEach(rule -> rule.setName(name));
        this.updateRules = rules;

        return this;
    }

    // 生成前端验证规则
    public Radio buildFrontendRules(String path) {

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
    public Radio setFilters(boolean filters) {
        this.filters = filters;

        return this;
    }

    // 表头的筛选菜单项，当值为 true 时，自动使用 valueEnum 生成，只在列表页中有效
    public Radio setFilters(Map<String, String> filters) {
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
    // new Radio().setWhen(option, callback)
    public Radio setWhen(Object option, Closure callback) {
        this.setWhen("=", option, callback);
        return this;
    }

    // 设置When组件数据
    //
    // new Radio().setWhen(">", option, callback)
    public Radio setWhen(String operator, Object option, Closure callback) {

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
    public Radio hideFromIndex(boolean callback) {
        this.showOnIndex = !callback;

        return this;
    }

    // Specify that the element should be hidden from the detail view.
    public Radio hideFromDetail(boolean callback) {
        this.showOnDetail = !callback;

        return this;
    }

    // Specify that the element should be hidden from the creation view.
    public Radio hideWhenCreating(boolean callback) {
        this.showOnCreation = !callback;

        return this;
    }

    // Specify that the element should be hidden from the update view.
    public Radio hideWhenUpdating(boolean callback) {
        this.showOnUpdate = !callback;

        return this;
    }

    // Specify that the element should be hidden from the export file.
    public Radio hideWhenExporting(boolean callback) {
        this.showOnExport = !callback;

        return this;
    }

    // Specify that the element should be hidden from the import file.
    public Radio hideWhenImporting(boolean callback) {
        this.showOnImport = !callback;

        return this;
    }

    // Specify that the element should be hidden from the index view.
    public Radio onIndexShowing(boolean callback) {
        this.showOnIndex = callback;

        return this;
    }

    // Specify that the element should be hidden from the detail view.
    public Radio onDetailShowing(boolean callback) {
        this.showOnDetail = callback;

        return this;
    }

    // Specify that the element should be hidden from the creation view.
    public Radio showOnCreating(boolean callback) {
        this.showOnCreation = callback;

        return this;
    }

    // Specify that the element should be hidden from the update view.
    public Radio showOnUpdating(boolean callback) {
        this.showOnUpdate = callback;

        return this;
    }

    // Specify that the element should be hidden from the export file.
    public Radio showOnExporting(boolean callback) {
        this.showOnExport = callback;

        return this;
    }

    // Specify that the element should be hidden from the import file.
    public Radio showOnImporting(boolean callback) {
        this.showOnImport = callback;

        return this;
    }

    // Specify that the element should only be shown on the index view.
    public Radio onlyOnIndex() {
        this.showOnIndex = true;
        this.showOnDetail = false;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on the detail view.
    public Radio onlyOnDetail() {
        this.showOnIndex = false;
        this.showOnDetail = true;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on forms.
    public Radio onlyOnForms() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = true;
        this.showOnUpdate = true;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on export file.
    public Radio onlyOnExport() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = true;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on import file.
    public Radio onlyOnImport() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = false;
        this.showOnImport = true;

        return this;
    }

    // Specify that the element should be hidden from forms.
    public Radio exceptOnForms() {
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
