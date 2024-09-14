package io.quarkcloud.quarkadmin.component.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

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
    private Object body;

    // 表单行为
    private Object actions;

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
    public Form setStyle(Map<String, Object> style) {
        this.style = style;
        return this;
    }

    // 解析initialValue
    public Object parseInitialValue(Object item, Map<String, Object> initialValues) {
        Object value = null;

        // 数组直接返回
        if (item instanceof List<?>) {
            return null;
        }

        boolean isHasGetName = new Reflect(item).checkMethodExist("getName");
        if (!isHasGetName) {
            return null;
        }
        String name = (String) new Reflect(item).invoke("getName");
        if (name == null || name.isEmpty()) {
            return null;
        }

        boolean issetDefaultValue = new Reflect(item).checkMethodExist("getDefaultValue");
        if (issetDefaultValue) {
            Object defaultValue = new Reflect(item).invoke("getDefaultValue");
            if (defaultValue != null) {
                value = defaultValue;
            }
        }

        boolean issetValue = new Reflect(item).checkMethodExist("getValue");
        if (issetValue) {
            Object getValue = new Reflect(item).invoke("getValue");
            if (getValue != null) {
                    value = getValue;
            }
        }

        if (initialValues.get(name) != null) {
            value = initialValues.get(name);
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
    public Form setInitialValues(Object initialValues) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> data = objectMapper.convertValue(initialValues, Map.class);
        List<Object> fields = findFields(this.body, true);
        for (Object v : fields) {
            Object value = parseInitialValue(v, data);
            if (value != null) {
                boolean isHasGetName = new Reflect(v).checkMethodExist("getName");
                if (isHasGetName) {
                    String name = (String) new Reflect(v).invoke("getName");
                    data.put(name, value);
                }
            }
        }

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String k = entry.getKey();
            Object v = entry.getValue();
            if (v instanceof String) {
                String getV = (String) v;
                if (getV.contains("[")) {
                    try {
                        List<Object> m = new ArrayList<>();
                        m = new ObjectMapper().readValue(getV, List.class);
                        data.put(k, m);
                    } catch (Exception e) {
                        if (getV.contains("{")) {
                            try {
                                Map<String, Object> m = new HashMap<>();
                                m = new ObjectMapper().readValue(getV, Map.class);
                                data.put(k, m);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                } else if (getV.contains("{")) {
                    try {
                        Map<String, Object> m = new HashMap<>();
                        m = new ObjectMapper().readValue(getV, Map.class);
                        data.put(k, m);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
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
