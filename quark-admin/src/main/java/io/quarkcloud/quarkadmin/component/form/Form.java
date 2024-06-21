package io.quarkcloud.quarkadmin.component.form;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.quarkcloud.quarkadmin.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Form extends Component {

    // 组件类型
    private String component;

    // 表单标题
    private String title;

    // 表单宽度
    private String width;

    // 是否显示冒号
    private boolean colon;

    // 表单的值
    private Map<String, Object> values;

    // 表单的初始值
    private Map<String, Object> initialValues;

    // 标签的对齐方式
    private String labelAlign;

    // 表单名称
    private String name;

    // 是否保留字段值
    private boolean preserve;

    // 是否显示必填标记
    private boolean requiredMark;

    // 提交失败是否滚动到第一个错误字段
    private boolean scrollToFirstError;

    // 字段组件的尺寸
    private String size;

    // 日期格式化器
    private String dateFormatter;

    // 表单布局
    private String layout;

    // 是否开启栅格化模式
    private boolean grid;

    // 行属性
    private Map<String, Object> rowProps;

    // 标签列属性
    private Map<String, Object> labelCol;

    // 包装列属性
    private Map<String, Object> wrapperCol;

    // 按钮包装列属性
    private Map<String, Object> buttonWrapperCol;

    // 表单提交的API
    private String api;

    // 表单提交API的类型
    private String apiType;

    // 是否新页面打开提交结果
    private boolean targetBlank;

    // 初始化API
    private String initApi;

    // 表单项
    private List<Object> body;

    // 表单行为
    private List<Object> actions;

    // 组件Key
    private String componentKey;

    // 样式
    private Map<String, Object> style;

    // 构造函数
    public Form() {
        this.component = "form";
        this.colon = true;
        this.labelAlign = "right";
        this.preserve = true;
        this.requiredMark = true;
        this.size = "default";
        this.dateFormatter = "string";
        this.layout = "horizontal";
        this.labelCol = new HashMap<String, Object>() {{
            put("span", 4);
        }};
        this.wrapperCol = new HashMap<String, Object>() {{
            put("span", 20);
        }};
        this.buttonWrapperCol = new HashMap<String, Object>() {{
            put("offset", 4);
            put("span", 20);
        }};
        this.apiType = "POST";
    }

    // 设置样式
    public Component setStyle(Map<String, Object> style) {
        this.style = style;
        return this;
    }

    // 解析initialValue
    public Object parseInitialValue(Object item, Map<String, Object> initialValues) {
        Object value = null;

        if (item instanceof List) {
            return null;
        }

        return value;
    }

    // 查找字段
    public List<Object> findFields(Object fields, boolean when) {
        List<Object> items = new ArrayList<>();

        if (fields instanceof List) {
            for (Object v : (List<?>) fields) {
                items.addAll(fieldParser(v, when));
            }
        } else {
            items.addAll(fieldParser(fields, when));
        }

        return items;
    }

    // 解析字段
    public List<Object> fieldParser(Object v, boolean when) {
        List<Object> items = new ArrayList<>();

        if (v instanceof List) {
            return items;
        }

        return items;
    }

    // 获取When组件中的字段
    public List<Object> getWhenFields(Object item) {
        List<Object> items = new ArrayList<>();
        return items;
    }

    // 表单默认值，只有初始化以及重置时生效
    public Component setInitialValues(Map<String, Object> initialValues) {
        Map<String, Object> data = initialValues;
        return this;
    }

    // 表单布局，horizontal | vertical
    public Component setLayout(String layout) {
        if (layout.equals("vertical")) {
            this.labelCol = null;
            this.wrapperCol = null;
            this.buttonWrapperCol = null;
        }
        this.layout = layout;
        return this;
    }

    // 需要为输入控件设置布局样式时，使用该属性，用法同 labelCol
    public Component setWrapperCol(Map<String, Object> wrapperCol) {
        if (this.layout.equals("vertical")) {
            throw new IllegalArgumentException("If layout set vertical mode, can't set wrapperCol!");
        }
        this.wrapperCol = wrapperCol;
        return this;
    }

    // 表单按钮布局样式,默认：['offset' => 2, 'span' => 22 ]
    public Component setButtonWrapperCol(Map<String, Object> buttonWrapperCol) {
        if (this.layout.equals("vertical")) {
            throw new IllegalArgumentException("If layout set vertical mode, can't set buttonWrapperCol!");
        }
        this.buttonWrapperCol = buttonWrapperCol;
        return this;
    }
}
