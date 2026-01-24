package io.quarkcloud.quarkadmin.component.form.fields;

import java.util.ArrayList;
import java.util.Arrays;
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
public class Icon extends Component {

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

    // 默认选中的选项
    private Object defaultValue;

    // 整组失效
    private Boolean disabled;

    // 指定选中项
    private Object value;

    // 占位符
    private String placeholder;

    // 大小
    private String size;

    // 是否支持清除
    private Boolean allowClear;

    // 是否支持搜索
    private Boolean allowSearch;

    // 可选项数据源
    private List<String> options;

    // 自定义样式
    Map<String, Object> style;

    public Icon() {
        this.component = "iconField";
        this.style = new HashMap<>();
        this.rules = new ArrayList<>();
        this.colon = true;
        this.labelAlign = "right";
        this.showOnIndex = true;
        this.showOnDetail = true;
        this.showOnCreation = true;
        this.showOnUpdate = true;
        this.showOnExport = true;
        this.showOnImport = true;
        this.allowSearch = true;
        this.allowClear = true;
        this.column = new Column();
        this.options = Arrays.asList(
            
		"ant-design:database-outlined", "ant-design:mobile-outlined", "ant-design:tablet-outlined", "ant-design:red-envelope-outlined",
		"ant-design:book-outlined", "ant-design:file-done-outlined", "ant-design:reconciliation-outlined",
		"ant-design:file-sync-outlined", "ant-design:file-search-outlined", "ant-design:solution-outlined", "ant-design:file-protect-outlined",
		"ant-design:file-add-outlined", "ant-design:file-excel-outlined", "ant-design:file-exclamation-outlined", "ant-design:file-pdf-outlined",
		"ant-design:file-image-outlined", "ant-design:file-markdown-outlined", "ant-design:file-unknown-outlined", "ant-design:file-ppt-outlined",
		"ant-design:file-word-outlined", "ant-design:file-outlined", "ant-design:file-zip-outlined", "ant-design:file-text-outlined", "ant-design:copy-outlined",
		"ant-design:snippets-outlined", "ant-design:audit-outlined", "ant-design:diff-outlined", "ant-design:security-scan-outlined",
		"ant-design:property-safety-outlined", "ant-design:insurance-outlined", "ant-design:alert-outlined", "ant-design:delete-outlined", "ant-design:hourglass-outlined",
		"ant-design:bulb-outlined", "ant-design:experiment-outlined", "ant-design:bell-outlined", "ant-design:trophy-outlined", "ant-design:rest-outlined", "ant-design:usb-outlined",
		"ant-design:skin-outlined", "ant-design:home-outlined", "ant-design:bank-outlined", "ant-design:filter-outlined", "ant-design:funnel-plot-outlined", "ant-design:like-outlined",
		"ant-design:dislike-outlined", "ant-design:unlock-outlined", "ant-design:lock-outlined", "ant-design:customer-service-outlined", "ant-design:flag-outlined",
		"ant-design:money-collect-outlined", "ant-design:medicine-box-outlined", "ant-design:shop-outlined", "ant-design:rocket-outlined", "ant-design:shopping-outlined",
		"ant-design:folder-outlined", "ant-design:folder-open-outlined", "ant-design:folder-add-outlined", "ant-design:deployment-unit-outlined",
		"ant-design:account-book-outlined", "ant-design:contacts-outlined", "ant-design:carry-out-outlined",
		"ant-design:calendar-outlined", "ant-design:scan-outlined", "ant-design:select-outlined", "ant-design:box-plot-outlined", "ant-design:build-outlined", "ant-design:sliders-outlined",
		"ant-design:laptop-outlined", "ant-design:barcode-outlined", "ant-design:camera-outlined", "ant-design:cluster-outlined", "ant-design:gateway-outlined", "ant-design:car-outlined",
		"ant-design:printer-outlined", "ant-design:read-outlined", "ant-design:cloud-server-outlined", "ant-design:cloud-upload-outlined", "ant-design:cloud-outlined",
		"ant-design:cloud-download-outlined", "ant-design:cloud-sync-outlined", "ant-design:notification-outlined", "ant-design:sound-outlined",
		"ant-design:radar-chart-outlined", "ant-design:qrcode-outlined", "ant-design:fund-outlined", "ant-design:mail-outlined", "ant-design:table-outlined",
		"ant-design:idcard-outlined", "ant-design:credit-card-outlined", "ant-design:heart-outlined", "ant-design:block-outlined", "ant-design:star-outlined",
		"ant-design:gold-outlined", "ant-design:heat-map-outlined", "ant-design:wifi-outlined", "ant-design:edit-outlined", "ant-design:key-outlined",
		"ant-design:api-outlined", "ant-design:disconnect-outlined", "ant-design:highlight-outlined", "ant-design:monitor-outlined", "ant-design:link-outlined", "ant-design:man-outlined",
		"ant-design:percentage-outlined", "ant-design:pushpin-outlined", "ant-design:phone-outlined", "ant-design:shake-outlined", "ant-design:tag-outlined",
		"ant-design:tags-outlined", "ant-design:scissor-outlined", "ant-design:share-alt-outlined", "ant-design:branches-outlined", "ant-design:fork-outlined", "ant-design:shrink-outlined",
		"ant-design:vertical-right-outlined", "ant-design:vertical-left-outlined", "ant-design:right-outlined", "ant-design:left-outlined",
		"ant-design:up-outlined", "ant-design:down-outlined", "ant-design:fullscreen-outlined", "ant-design:fullscreen-exit-outlined", "ant-design:double-left-outlined",
		"ant-design:double-right-outlined", "ant-design:arrow-right-outlined", "ant-design:arrow-up-outlined", "ant-design:arrow-left-outlined", "ant-design:arrow-down-outlined",
		"ant-design:upload-outlined", "ant-design:column-height-outlined", "ant-design:vertical-align-bottom-outlined", "ant-design:vertical-align-middle-outlined",
		"ant-design:to-top-outlined", "ant-design:vertical-align-top-outlined", "ant-design:download-outlined", "ant-design:sort-descending-outlined",
		"ant-design:sort-ascending-outlined", "ant-design:fall-outlined", "ant-design:swap-outlined", "ant-design:stock-outlined", "ant-design:rise-outlined",
		"ant-design:menu-outlined", "ant-design:unordered-list-outlined", "ant-design:ordered-list-outlined", "ant-design:align-right-outlined",
		"ant-design:align-center-outlined", "ant-design:align-left-outlined", "ant-design:pic-center-outlined", "ant-design:pic-right-outlined", "ant-design:pic-left-outlined",
		"ant-design:bold-outlined", "ant-design:font-colors-outlined", "ant-design:exclamation-outlined", "ant-design:font-size-outlined", "ant-design:check-circle-outlined",
		"ant-design:info-outlined", "ant-design:ci-outlined", "ant-design:line-height-outlined", "ant-design:dollar-outlined", "ant-design:strikethrough-outlined", "ant-design:compass-outlined",
		"ant-design:underline-outlined", "ant-design:close-circle-outlined", "ant-design:number-outlined", "ant-design:frown-outlined", "ant-design:italic-outlined", "ant-design:info-circle-outlined",
		"ant-design:code-outlined", "ant-design:left-circle-outlined", "ant-design:column-width-outlined", "ant-design:down-circle-outlined", "ant-design:check-outlined",
		"ant-design:ellipsis-outlined", "ant-design:copyright-outlined", "ant-design:dash-outlined", "ant-design:minus-circle-outlined", "ant-design:close-outlined", "ant-design:meh-outlined",
		"ant-design:enter-outlined", "ant-design:plus-circle-outlined", "ant-design:line-outlined", "ant-design:play-circle-outlined", "ant-design:minus-outlined", "ant-design:question-circle-outlined",
		"ant-design:question-outlined", "ant-design:pound-outlined", "ant-design:rollback-outlined", "ant-design:right-circle-outlined", "ant-design:small-dash-outlined", "ant-design:smile-outlined",
		"ant-design:pause-outlined", "ant-design:trademark-outlined", "ant-design:bg-colors-outlined", "ant-design:field-time-outlined", "ant-design:crown-outlined",
		"ant-design:drag-outlined", "ant-design:desktop-outlined", "ant-design:gift-outlined", "ant-design:up-circle-outlined", "ant-design:stop-outlined",
		"ant-design:warning-outlined", "ant-design:fire-outlined", "ant-design:sync-outlined", "ant-design:thunderbolt-outlined", "ant-design:transaction-outlined",
		"ant-design:alipay-outlined", "ant-design:undo-outlined", "ant-design:taobao-outlined", "ant-design:redo-outlined", "ant-design:wechat-outlined", "ant-design:reload-outlined",
		"ant-design:comment-outlined", "ant-design:login-outlined", "ant-design:message-outlined", "ant-design:clear-outlined", "ant-design:dashboard-outlined",
		"ant-design:issues-close-outlined", "ant-design:poweroff-outlined", "ant-design:logout-outlined", "ant-design:pie-chart-outlined", "ant-design:setting-outlined",
		"ant-design:eye-outlined", "ant-design:export-outlined", "ant-design:save-outlined", "ant-design:import-outlined",
		"ant-design:appstore-outlined", "ant-design:close-square-outlined", "ant-design:down-square-outlined", "ant-design:layout-outlined", "ant-design:left-square-outlined",
		"ant-design:play-square-outlined", "ant-design:control-outlined", "ant-design:minus-square-outlined",
		"ant-design:plus-square-outlined", "ant-design:right-square-outlined", "ant-design:project-outlined", "ant-design:wallet-outlined", "ant-design:up-square-outlined",
		"ant-design:calculator-outlined", "ant-design:interaction-outlined", "ant-design:check-square-outlined", "ant-design:border-outlined", "ant-design:border-outer-outlined",
		"ant-design:border-top-outlined", "ant-design:border-bottom-outlined", "ant-design:border-left-outlined", "ant-design:border-right-outlined", "ant-design:border-inner-outlined",
		"ant-design:border-verticle-outlined", "ant-design:border-horizontal-outlined", "ant-design:radius-bottomleft-outlined", "ant-design:radius-bottomright-outlined",
		"ant-design:radius-upleft-outlined", "ant-design:radius-upright-outlined", "ant-design:radius-setting-outlined", "ant-design:user-add-outlined", "ant-design:usergroup-delete-outlined",
		"ant-design:user-delete-outlined", "ant-design:usergroup-add-outlined", "ant-design:user-outlined", "ant-design:area-chart-outlined", "ant-design:line-chart-outlined",
		"ant-design:bar-chart-outlined", "ant-design:container-outlined", "ant-design:safety-certificate-outlined"
        );
        this.setWidth("200px");
        this.setComponentKey();
        this.whenItem = new ArrayList<>();
        this.when = new When();
    }

    // Field 的长度，我们归纳了常用的 Field 长度以及适合的场景，支持了一些枚举 "xs" , "s" , "m" , "l" , "x"
    public Icon setWidth(Object width) {
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
    // new Icon().
    // setRules(Arrays.asList(
    // rule.required(true, "用户名必须填写"), // 需要用户名字段不能为空
    // rule.min(6, "用户名不能少于6个字符"), // 用户名最少需要6个字符
    // rule.max(20, "用户名不能超过20个字符") // 用户名最多只能包含20个字符
    // ));
    public Icon setRules(List<Rule> rules) {

        rules.forEach(rule -> rule.setName(name));
        this.rules = rules;

        return this;
    }

    // 校验规则，只在创建表单提交时生效
    //
    // new Icon().
    // setCreationRules(Arrays.asList(
    // rule.unique("admins", "username", "用户名已存在"),
    // ));
    public Icon setCreationRules(List<Rule> rules) {

        rules.forEach(rule -> rule.setName(name));
        this.creationRules = rules;

        return this;
    }

    // 校验规则，只在更新表单提交时生效
    //
    // new Icon().
    // setUpdateRules(Arrays.asList(
    // rule.unique("admins", "username", "用户名已存在"),
    // ));
    public Icon setUpdateRules(List<Rule> rules) {

        rules.forEach(rule -> rule.setName(name));
        this.updateRules = rules;

        return this;
    }

    // 生成前端验证规则
    public Icon buildFrontendRules(String path) {
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
    public Icon setFilters(boolean filters) {
        this.filters = filters;

        return this;
    }

    // 表头的筛选菜单项，当值为 true 时，自动使用 valueEnum 生成，只在列表页中有效
    public Icon setFilters(Map<String, String> filters) {
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
    // new Icon().setWhen(option, callback)
    public Icon setWhen(Object option, Closure callback) {
        this.setWhen("=", option, callback);
        return this;
    }

    // 设置When组件数据
    //
    // new Icon().setWhen(">", option, callback)
    public Icon setWhen(String operator, Object option, Closure callback) {

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
    public Icon hideFromIndex(boolean callback) {
        this.showOnIndex = !callback;

        return this;
    }

    // Specify that the element should be hidden from the detail view.
    public Icon hideFromDetail(boolean callback) {
        this.showOnDetail = !callback;

        return this;
    }

    // Specify that the element should be hidden from the creation view.
    public Icon hideWhenCreating(boolean callback) {
        this.showOnCreation = !callback;

        return this;
    }

    // Specify that the element should be hidden from the update view.
    public Icon hideWhenUpdating(boolean callback) {
        this.showOnUpdate = !callback;

        return this;
    }

    // Specify that the element should be hidden from the export file.
    public Icon hideWhenExporting(boolean callback) {
        this.showOnExport = !callback;

        return this;
    }

    // Specify that the element should be hidden from the import file.
    public Icon hideWhenImporting(boolean callback) {
        this.showOnImport = !callback;

        return this;
    }

    // Specify that the element should be hidden from the index view.
    public Icon onIndexShowing(boolean callback) {
        this.showOnIndex = callback;

        return this;
    }

    // Specify that the element should be hidden from the detail view.
    public Icon onDetailShowing(boolean callback) {
        this.showOnDetail = callback;

        return this;
    }

    // Specify that the element should be hidden from the creation view.
    public Icon showOnCreating(boolean callback) {
        this.showOnCreation = callback;

        return this;
    }

    // Specify that the element should be hidden from the update view.
    public Icon showOnUpdating(boolean callback) {
        this.showOnUpdate = callback;

        return this;
    }

    // Specify that the element should be hidden from the export file.
    public Icon showOnExporting(boolean callback) {
        this.showOnExport = callback;

        return this;
    }

    // Specify that the element should be hidden from the import file.
    public Icon showOnImporting(boolean callback) {
        this.showOnImport = callback;

        return this;
    }

    // Specify that the element should only be shown on the index view.
    public Icon onlyOnIndex() {
        this.showOnIndex = true;
        this.showOnDetail = false;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on the detail view.
    public Icon onlyOnDetail() {
        this.showOnIndex = false;
        this.showOnDetail = true;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on forms.
    public Icon onlyOnForms() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = true;
        this.showOnUpdate = true;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on creation.
    public Icon onlyOnCreating() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = true;
        this.showOnUpdate = false;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on update.
    public Icon onlyOnUpdating() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = false;
        this.showOnUpdate = true;
        this.showOnExport = false;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on export file.
    public Icon onlyOnExport() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = true;
        this.showOnImport = false;

        return this;
    }

    // Specify that the element should only be shown on import file.
    public Icon onlyOnImport() {
        this.showOnIndex = false;
        this.showOnDetail = false;
        this.showOnCreation = false;
        this.showOnUpdate = false;
        this.showOnExport = false;
        this.showOnImport = true;

        return this;
    }

    // Specify that the element should be hidden from forms.
    public Icon exceptOnForms() {
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
