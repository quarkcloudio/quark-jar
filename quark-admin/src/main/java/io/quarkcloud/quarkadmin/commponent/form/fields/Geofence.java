package io.quarkcloud.quarkadmin.commponent.form.fields;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.ArrayUtils;

import io.quarkcloud.quarkadmin.commponent.Commponent;
import io.quarkcloud.quarkadmin.commponent.form.Rule;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Geofence extends Commponent {
    
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
    Rule[] rules;

    // 创建页校验规则
    Rule[] creationRules;

    // 编辑页校验规则
    Rule[] updateRules;

    // 前端校验规则，设置字段的校验逻辑
    Rule[] frontendRules;

    // When组件
    When when;

    // When组件里的字段
    WhenItem[] whenItem;

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

    // 默认选中的选项
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Object defaultValue;

    // 整组失效
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    boolean disabled;

    // 自定义样式
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Map<String, Object> style;

    // 指定选中项,string[] | number[]
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Map<String, Object> value;

    // 缩放级别
    int zoom;

    // 地图Key
    String mapKey;

    // 地图安全密钥
    String mapSecurityJsCode;

    public Geofence() {
        this.component = "geofenceField";
        this.setComponentKey();
    }

    // 校验规则，设置字段的校验逻辑
    //
    // new Geofence().
    // setRules(new Rule[]{
    // rule.required(true, "用户名必须填写"), // 需要用户名字段不能为空
    // rule.min(6, "用户名不能少于6个字符"), // 用户名最少需要6个字符
    // rule.max(20, "用户名不能超过20个字符") // 用户名最多只能包含20个字符
    // });
    public Geofence setRules(Rule[] rules) {
        for (int i = 0; i < rules.length; i++) {
            rules[i] = rules[i].setName(name);
        }
        this.rules = rules;

        return this;
    }

    // 校验规则，只在创建表单提交时生效
    //
    // new Geofence().
    // setCreationRules(new Rule[]{
    // rule.unique("admins", "username", "用户名已存在"),
    // });
    public Geofence setCreationRules(Rule[] rules) {
        for (int i = 0; i < rules.length; i++) {
            rules[i] = rules[i].setName(name);
        }
        this.creationRules = rules;

        return this;
    }

    // 校验规则，只在更新表单提交时生效
    //
    // new Geofence().
    // setUpdateRules(new Rule[]{
    // rule.unique("admins", "username", "用户名已存在"),
    // });
    public Geofence setUpdateRules(Rule[] rules) {
        for (int i = 0; i < rules.length; i++) {
            rules[i] = rules[i].setName(name);
        }
        this.updateRules = rules;

        return this;
    }

    // 生成前端验证规则
    public Geofence buildFrontendRules(String path) {
        Rule[] rules = new Rule[] {};
        Rule[] creationRules = new Rule[] {};
        Rule[] updateRules = new Rule[] {};
        Rule[] frontendRules = new Rule[] {};

        String[] uri = path.split("/");
        boolean isCreating = (uri[uri.length - 1] == "create") || (uri[uri.length - 1] == "store");
        boolean isEditing = (uri[uri.length - 1] == "edit") || (uri[uri.length - 1] == "update");

        if (this.rules.length > 0) {
            rules = Rule.convertToFrontendRules(this.rules);
        }

        if (isCreating && this.creationRules.length > 0) {
            creationRules = Rule.convertToFrontendRules(this.creationRules);
        }

        if (isEditing && this.updateRules.length > 0) {
            updateRules = Rule.convertToFrontendRules(this.updateRules);
        }

        if (rules.length > 0) {
            frontendRules = (Rule[]) ArrayUtils.addAll(frontendRules, rules);
        }

        if (creationRules.length > 0) {
            frontendRules = (Rule[]) ArrayUtils.addAll(frontendRules, creationRules);
        }

        if (updateRules.length > 0) {
            frontendRules = (Rule[]) ArrayUtils.addAll(frontendRules, updateRules);
        }

        this.frontendRules = frontendRules;

        return this;
    }

    // 表头的筛选菜单项，当值为 true 时，自动使用 valueEnum 生成，只在列表页中有效
    public Geofence setFilters(boolean filters) {
        this.filters = filters;

        return this;
    }

    // 表头的筛选菜单项，当值为 true 时，自动使用 valueEnum 生成，只在列表页中有效
    public Geofence setFilters(Map<String, String> filters) {
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
    // new Geofence().setWhen(option, callback)
    public Geofence setWhen(Object option, Closure callback) {
        this.setWhen("=", option, callback);
        return this;
    }

    // 设置When组件数据
    //
    // new Geofence().setWhen(">", option, callback)
    public Geofence setWhen(String operator, Object option, Closure callback) {
        When w = new When();
        WhenItem i = new WhenItem();

        i.body = callback.callback();
        switch (operator) {
            case "=":
                i.condition = "<%=String(" + this.name + ") === '" + option + "' %>";
                break;
            case ">":
                i.condition = "<%=String(" + this.name + ") > '" + option + "' %>";
                break;
            case "<":
                i.condition = "<%=String(" + this.name + ") < '" + option + "' %>";
                break;
            case "<=":
                i.condition = "<%=String(" + this.name + ") <= '" + option + "' %>";
                break;
            case ">=":
                i.condition = "<%=String(" + this.name + ") => '" + option + "' %>";
                break;
            case "has":
                i.condition = "<%=(String(" + this.name + ").indexOf('" + option + "') !=-1) %>";
                break;
            case "in":
                ObjectMapper mapper = new ObjectMapper();
                String jsonStr;
                try {
                    jsonStr = mapper.writeValueAsString(option);
                    i.condition = "<%=(" + jsonStr + ".indexOf(" + this.name + ") !=-1) %>";
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                break;
            default:
                i.condition = "<%=String(" + this.name + ") === '" + option + "' %>";
                break;
        }

        i.conditionName = this.name;
        i.conditionOperator = operator;
        i.option = option;

        this.whenItem = (WhenItem[]) ArrayUtils.addAll(this.whenItem, i);
        this.when = w.setItems(this.whenItem);

        return this;
    }

    // Specify that the element should be hidden from the index view.
    public Geofence hideFromIndex(boolean callback) {
        this.showOnIndex = !callback;

        return this;
    }

    // Specify that the element should be hidden from the detail view.
    public Geofence hideFromDetail(boolean callback) {
        this.showOnDetail = !callback;

        return this;
    }

    // Specify that the element should be hidden from the creation view.
    public Geofence hideWhenCreating(boolean callback) {
        this.showOnCreation = !callback;

        return this;
    }

    // Specify that the element should be hidden from the update view.
    public Geofence hideWhenUpdating(boolean callback) {
        this.showOnUpdate = !callback;

        return this;
    }

    // Specify that the element should be hidden from the export file.
    public Geofence hideWhenExporting(boolean callback) {
        this.showOnExport = !callback;

        return this;
    }

    // Specify that the element should be hidden from the import file.
    public Geofence hideWhenImporting(boolean callback) {
        this.showOnImport = !callback;

        return this;
    }

    // Specify that the element should be hidden from the index view.
    public Geofence onIndexShowing(boolean callback) {
        this.showOnIndex = callback;

        return this;
    }

    // Specify that the element should be hidden from the detail view.
    public Geofence onDetailShowing(boolean callback) {
        this.showOnDetail = callback;

        return this;
    }

    // Specify that the element should be hidden from the creation view.
    public Geofence showOnCreating(boolean callback) {
        this.showOnCreation = callback;

        return this;
    }

    // Specify that the element should be hidden from the update view.
    public Geofence showOnUpdating(boolean callback) {
        this.showOnUpdate = callback;

        return this;
    }

    // Specify that the element should be hidden from the export file.
    public Geofence showOnExporting(boolean callback) {
        this.showOnExport = callback;

        return this;
    }

    // Specify that the element should be hidden from the import file.
    public Geofence showOnImporting(boolean callback) {
        this.showOnImport = callback;

        return this;
    }

    // Specify that the element should only be shown on the index view.
    public Geofence onlyOnIndex() {
        this.showOnIndex = true;
        this.showOnDetail = false;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on the detail view.
    public Geofence onlyOnDetail() {
        this.showOnIndex = false;
        this.showOnDetail = true;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on forms.
    public Geofence onlyOnForms() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = true;
        this.showOnUpdate = true;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on export file.
    public Geofence onlyOnExport() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = true;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on import file.
    public Geofence onlyOnImport() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = false;
        this.showOnImport = true;

        return this;
    }

    // Specify that the element should be hidden from forms.
    public Geofence exceptOnForms() {
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

    // 地图宽度
    public Geofence setWidth(Object width) {
        Map<String, Object> style = new HashMap<>();

        this.style.forEach((key, value) -> {
            style.put(key, value);
        });
        style.put("width", width);
        this.style = style;

        return this;
    }

    // 地图高度
    public Geofence setHeight(Object height) {
        Map<String, Object> style = new HashMap<>();

        this.style.forEach((key, value) -> {
            style.put(key, value);
        });
        style.put("height", height);
        this.style = style;

        return this;
    }

    // 中心点
    public Geofence SetCenter(String longitude, String latitude) {

        Map<String, Object> center = new HashMap<>();

        center.put("longitude", longitude);
        center.put("latitude", latitude);

        this.value.put("center", center);

        return this;
    }

    // 多边形围栏坐标点
    public Geofence SetPoints(Object[] points) {

        this.value.put("points", points);

        return this;
    }
}
