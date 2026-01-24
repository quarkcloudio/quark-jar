package io.quarkcloud.quarkadmin.component.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.quarkcloud.quarkadmin.component.Component;
import io.quarkcloud.quarkcore.util.Reflect;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Form extends Component {

    // 表单标题
    private String title;

    // 表单宽度
    private String width;

    // 是否显示冒号
    private boolean colon;

    // 表单的值
    private Object values;

    // 表单的初始值
    private Object initialValues;

    // 标签的对齐方式
    private String labelAlign;

    // 表单名称
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private String name;

    // 是否保留字段值
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean preserve;

    // 是否显示必填标记
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean requiredMark;

    // 提交失败是否滚动到第一个错误字段
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean scrollToFirstError;

    // 字段组件的尺寸
    private String size;

    // 日期格式化器
    private String dateFormatter;

    // 表单布局
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private String layout;

    // 是否开启栅格化模式
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
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
    private Object body;

    // 表单行为
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Object actions;

    // 样式
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
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
    public Form setStyle(Map<String, Object> style) {
        this.style = style;
        return this;
    }

    // 解析初始值
    public Object parseInitialValue(Object field, Map<String, Object> initialValues) {
        if (field instanceof List<?>) return null; // 数组直接跳过
    
        Reflect reflect = new Reflect(field);
    
        if (!reflect.checkMethodExist("getName")) return null;
        String name = (String) reflect.invoke("getName");
        if (name == null || name.isEmpty()) return null;
    
        Object value = null;
    
        // defaultValue
        if (reflect.checkMethodExist("getDefaultValue")) {
            Object defaultValue = reflect.invoke("getDefaultValue");
            if (defaultValue != null) value = defaultValue;
        }
    
        // value 覆盖
        if (reflect.checkMethodExist("getValue")) {
            Object v = reflect.invoke("getValue");
            if (v != null) value = v;
        }
    
        // initialValues 覆盖
        if (initialValues != null && initialValues.containsKey(name)) {
            Object initV = initialValues.get(name);
            if (initV != null) value = initV;
        }
    
        // 空字符串视为 null
        if (value instanceof String && ((String) value).trim().isEmpty()) {
            value = null;
        }
    
        // 解析字符串类型的 List/Map
        if (value instanceof String) {
            String str = ((String) value).trim();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            try {
                if (str.startsWith("[")) value = objectMapper.readValue(str, List.class);
                else if (str.startsWith("{")) value = objectMapper.readValue(str, Map.class);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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
        // 数组直接返回
        if (v instanceof List<?>) {
            return items;
        }

        boolean isHasGetBody = new Reflect(v).checkMethodExist("getBody");
        if (isHasGetBody) {
            Object body = new Reflect(v).invoke("getBody");
            List<Object> getItems = findFields(body, true);
            if (!getItems.isEmpty()) {
                items.addAll(getItems);
            }
            return items;
        }

        boolean isHasGetTabPanes = new Reflect(v).checkMethodExist("getTabPanes");
        if (isHasGetTabPanes) {
            body = new Reflect(v).invoke("getTabPanes");
            List<Object> getItems = findFields(body, true);
            if (!getItems.isEmpty()) {
                items.addAll(getItems);
            }
            return items;
        }

        boolean isHasGetComponent = new Reflect(v).checkMethodExist("getComponent");
        if (isHasGetComponent) {
            String component = (String) new Reflect(v).invoke("getComponent");
            if (component.contains("Field")) {
                items.add(v);
                if (when) {
                    List<Object> whenFields = getWhenFields(v);
                    if (!whenFields.isEmpty()) {
                        items.addAll(whenFields);
                    }
                }
            }
        }
        return items;
    }

    // 获取When组件中的字段
    public List<Object> getWhenFields(Object item) {
        List<Object> items = new ArrayList<>();

        boolean whenIsValid = new Reflect(item).checkMethodExist("getWhen");
        if (!whenIsValid) {
            return items;
        }
        Object getWhen = new Reflect(item).invoke("getWhen");
        if (getWhen == null) {
            return items;
        }

        boolean itemsIsValid = new Reflect(getWhen).checkMethodExist("getItems");
        if (!itemsIsValid) {
            return items;
        }
        Object whenItems = new Reflect(getWhen).invoke("getItems");
        if (whenItems == null) {
            return items;
        }
        if (!(whenItems instanceof List<?>)) {
            return items;
        }

        for (Object v : (List<?>) whenItems) {
            Object body = new Reflect(v).invoke("getBody");
            if (body instanceof List<?>) {
                items.addAll((List<?>) body);
            } else {
                items.add(body);
            }
        }

        return items;
    }

    // 表单默认值，只有初始化以及重置时生效
    @SuppressWarnings("unchecked")
    public Form setInitialValues(Object initialValuesObj) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
    
        Map<String, Object> initialValuesMap = objectMapper.convertValue(initialValuesObj, Map.class);
        Map<String, Object> data = new HashMap<>();
    
        List<Object> fields = findFields(this.body, true);
    
        for (Object field : fields) {
            Object value = parseInitialValue(field, initialValuesMap);
            if (value != null) {
                Reflect reflect = new Reflect(field);
                if (reflect.checkMethodExist("getName")) {
                    String name = (String) reflect.invoke("getName");
                    data.put(name, value);
                }
            }
        }
    
        this.initialValues = data;
        return this;
    }
    
    // 表单布局，horizontal | vertical
    public Form setLayout(String layout) {
        if (layout.equals("vertical")) {
            this.labelCol = null;
            this.wrapperCol = null;
            this.buttonWrapperCol = null;
        }
        this.layout = layout;
        return this;
    }

    // 需要为输入控件设置布局样式时，使用该属性，用法同 labelCol
    public Form setWrapperCol(Map<String, Object> wrapperCol) {
        if (this.layout.equals("vertical")) {
            throw new IllegalArgumentException("If layout set vertical mode, can't set wrapperCol!");
        }
        this.wrapperCol = wrapperCol;
        return this;
    }

    // 表单按钮布局样式,默认：['offset' => 2, 'span' => 22 ]
    public Form setButtonWrapperCol(Map<String, Object> buttonWrapperCol) {
        if (this.layout.equals("vertical")) {
            throw new IllegalArgumentException("If layout set vertical mode, can't set buttonWrapperCol!");
        }
        this.buttonWrapperCol = buttonWrapperCol;
        return this;
    }
}
