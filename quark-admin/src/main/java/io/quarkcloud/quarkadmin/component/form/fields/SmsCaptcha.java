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
public class SmsCaptcha extends Component {

    @Data
    public static class Captcha {

        // 验证码按钮大小
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        String size;

        // 验证码按钮文案
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        String text;

        // 获取验证码请求地址
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        String url;
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
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    When when;

    // When组件里的字段
    @JsonIgnore
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

    // 带标签的 input，设置后置标签
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object addonAfter;

    // 带标签的 input，设置前置标签
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object addonBefore;

    // 可以点击清除图标删除内容
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean allowClear;

    // 是否有边框，默认true
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean bordered;

    // 默认的选中项
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object defaultValue;

    // 禁用
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object disabled;

    // 输入框的 id
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String id;

    // 最大长度
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    int maxLength;

    // 是否展示字数
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean showCount;

    // 设置校验状态,'error' | 'warning'
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String status;

    // 带有前缀图标的 input
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object prefix;

    // 控件大小。注：标准表单内的输入框大小限制为 middle，large | middle | small
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String size;

    // 带有后缀图标的 input
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object suffix;

    // 声明 input 类型，同原生 input 标签的 type 属性，见：MDN(请直接使用 Input.TextArea 代替
    // type="textarea")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String type;

    // 指定选中项,string[] | number[]
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object value;

    // 占位符
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String placeholder;

    // 自定义样式
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Map<String, Object> style;

    // 获取验证码ID的URL
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String captchaIdUrl;

    // 获取验证码URL
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String captchaUrl;

    // 依赖的字段名称，默认为phone
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    String dependency;

    // 验证码属性
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Captcha captchaProps;

    public SmsCaptcha() {
        this.component = "smsCaptchaField";
        this.colon = true;
        this.labelAlign = "right";
        this.showOnIndex = true;
        this.showOnDetail = true;
        this.showOnCreation = true;
        this.showOnUpdate = true;
        this.showOnExport = true;
        this.showOnImport = true;
        this.placeholder = "请输入";
        this.maxLength = 200;
        this.column = new Column();
        this.setComponentKey();
        this.style = new HashMap<>();
        this.rules = new ArrayList<>();
        this.whenItem = new ArrayList<>();
        this.when = new When();
    }

    // Field 的长度，我们归纳了常用的 Field 长度以及适合的场景，支持了一些枚举 "xs" , "s" , "m" , "l" , "x"
    public SmsCaptcha setWidth(Object width) {
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
    // new SmsCaptcha().
    // setRules(Arrays.asList(
    // rule.required(true, "用户名必须填写"), // 需要用户名字段不能为空
    // rule.min(6, "用户名不能少于6个字符"), // 用户名最少需要6个字符
    // rule.max(20, "用户名不能超过20个字符") // 用户名最多只能包含20个字符
    // ));
    public SmsCaptcha setRules(List<Rule> rules) {

        rules.forEach(rule -> rule.setName(name));
        this.rules = rules;

        return this;
    }

    // 校验规则，只在创建表单提交时生效
    //
    // new SmsCaptcha().
    // setCreationRules(Arrays.asList(
    // rule.unique("admins", "username", "用户名已存在"),
    // ));
    public SmsCaptcha setCreationRules(List<Rule> rules) {

        rules.forEach(rule -> rule.setName(name));
        this.creationRules = rules;

        return this;
    }

    // 校验规则，只在更新表单提交时生效
    //
    // new SmsCaptcha().
    // setUpdateRules(Arrays.asList(
    // rule.unique("admins", "username", "用户名已存在"),
    // ));
    public SmsCaptcha setUpdateRules(List<Rule> rules) {

        rules.forEach(rule -> rule.setName(name));
        this.updateRules = rules;

        return this;
    }

    // 生成前端验证规则
    public SmsCaptcha buildFrontendRules(String path) {
        List<Rule> frontendRules = new ArrayList<>();
        String[] uri = path.split("/");
        boolean isCreating = (uri[uri.length - 1].equals("create")) || (uri[uri.length - 1].equals("store"));
        boolean isEditing = (uri[uri.length - 1].equals("edit")) || (uri[uri.length - 1].equals("update"));

        Function<List<Rule>, List<Rule>> convertToFrontendRules = Rule::convertToFrontendRules;
        frontendRules.addAll(convertToFrontendRules.apply(this.rules));

        if (isCreating && this.creationRules != null) {
            frontendRules.addAll(convertToFrontendRules.apply(this.creationRules));
        }
        if (isEditing && this.updateRules != null) {
            frontendRules.addAll(convertToFrontendRules.apply(this.updateRules));
        }
        
        this.frontendRules = frontendRules;

        return this;
    }

    // 表头的筛选菜单项，当值为 true 时，自动使用 valueEnum 生成，只在列表页中有效
    public SmsCaptcha setFilters(boolean filters) {
        this.filters = filters;

        return this;
    }

    // 表头的筛选菜单项，当值为 true 时，自动使用 valueEnum 生成，只在列表页中有效
    public SmsCaptcha setFilters(Map<String, String> filters) {
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
    // new SmsCaptcha().setWhen(option, callback)
    public SmsCaptcha setWhen(Object option, Closure callback) {
        this.setWhen("=", option, callback);
        return this;
    }

    // 设置When组件数据
    //
    // new SmsCaptcha().setWhen(">", option, callback)
    public SmsCaptcha setWhen(String operator, Object option, Closure callback) {

        WhenItem item = new WhenItem();

        item.body = callback.callback();
        item.conditionName = this.name;
        item.conditionOperator = operator;
        item.option = option;

        StringBuilder conditionBuilder = new StringBuilder();
        conditionBuilder.append("<%=String(").append(this.name).append(")");

        switch (operator) {
            case "!=":
                conditionBuilder.append(" !== '").append(option).append("' %>");
                break;
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
    public SmsCaptcha hideFromIndex(boolean callback) {
        this.showOnIndex = !callback;

        return this;
    }

    // Specify that the element should be hidden from the detail view.
    public SmsCaptcha hideFromDetail(boolean callback) {
        this.showOnDetail = !callback;

        return this;
    }

    // Specify that the element should be hidden from the creation view.
    public SmsCaptcha hideWhenCreating(boolean callback) {
        this.showOnCreation = !callback;

        return this;
    }

    // Specify that the element should be hidden from the update view.
    public SmsCaptcha hideWhenUpdating(boolean callback) {
        this.showOnUpdate = !callback;

        return this;
    }

    // Specify that the element should be hidden from the export file.
    public SmsCaptcha hideWhenExporting(boolean callback) {
        this.showOnExport = !callback;

        return this;
    }

    // Specify that the element should be hidden from the import file.
    public SmsCaptcha hideWhenImporting(boolean callback) {
        this.showOnImport = !callback;

        return this;
    }

    // Specify that the element should be hidden from the index view.
    public SmsCaptcha onIndexShowing(boolean callback) {
        this.showOnIndex = callback;

        return this;
    }

    // Specify that the element should be hidden from the detail view.
    public SmsCaptcha onDetailShowing(boolean callback) {
        this.showOnDetail = callback;

        return this;
    }

    // Specify that the element should be hidden from the creation view.
    public SmsCaptcha showOnCreating(boolean callback) {
        this.showOnCreation = callback;

        return this;
    }

    // Specify that the element should be hidden from the update view.
    public SmsCaptcha showOnUpdating(boolean callback) {
        this.showOnUpdate = callback;

        return this;
    }

    // Specify that the element should be hidden from the export file.
    public SmsCaptcha showOnExporting(boolean callback) {
        this.showOnExport = callback;

        return this;
    }

    // Specify that the element should be hidden from the import file.
    public SmsCaptcha showOnImporting(boolean callback) {
        this.showOnImport = callback;

        return this;
    }

    // Specify that the element should only be shown on the index view.
    public SmsCaptcha onlyOnIndex() {
        this.showOnIndex = true;
        this.showOnDetail = false;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on the detail view.
    public SmsCaptcha onlyOnDetail() {
        this.showOnIndex = false;
        this.showOnDetail = true;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on forms.
    public SmsCaptcha onlyOnForms() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = true;
        this.showOnUpdate = true;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on creation.
    public SmsCaptcha onlyOnCreating() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = true;
        this.showOnUpdate = false;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on update.
    public SmsCaptcha onlyOnUpdating() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = false;
        this.showOnUpdate = true;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on export file.
    public SmsCaptcha onlyOnExport() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = true;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on import file.
    public SmsCaptcha onlyOnImport() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = false;
        this.showOnImport = true;

        return this;
    }

    // Specify that the element should be hidden from forms.
    public SmsCaptcha exceptOnForms() {
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
}
