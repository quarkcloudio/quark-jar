package io.quarkcloud.quarkadmin.template.resource.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import io.quarkcloud.quarkcore.service.Context;

public class ResolveField {

    // fields
    public List<Object> fields;

    // context
    public Context context;

    // 构造函数
    public ResolveField() {}

    // 构造函数
    public ResolveField(List<Object> fields, Context context) {
        this.fields = fields;
        this.context = context;
    }

    // 列表字段
    public List<Object> indexFields(Context ctx) {
        List<Object> items = new ArrayList<>();
        return items;
    }

    // 表格列
    public List<Object> indexTableColumns(Context ctx) {
        List<Object> columns = new ArrayList<>();
        return columns;
    }

    // 将表单项转换为表格列
    public Object fieldToColumn(Context ctx, Object field) {
        return null;
    }

    // 创建页字段
    public List<Object> creationFields(Context ctx) {
        List<Object> items = new ArrayList<>();
        return items;
    }

    // 不包含When组件内字段的创建页字段
    public List<Object> creationFieldsWithoutWhen(Context ctx) {
        List<Object> items = new ArrayList<>();
        return items;
    }

    // 包裹在组件内的创建页字段
    public List<Object> creationFieldsWithinComponents(Context ctx) {
        return null;
    }

    // 解析创建页表单组件内的字段
    public List<Object> creationFormFieldsParser(Context ctx, List<Object> fields) {
        List<Object> items = new ArrayList<>();
        return items;
    }

    // 编辑页字段
    public List<Object> updateFields(Context ctx) {
        List<Object> items = new ArrayList<>();
        return items;
    }

    // 不包含When组件内字段的编辑页字段
    public List<Object> updateFieldsWithoutWhen(Context ctx) {
        List<Object> items = new ArrayList<>();
        return items;
    }

    // 包裹在组件内的编辑页字段
    public List<Object> updateFieldsWithinComponents(Context ctx) {
        return null;
    }

    // 解析编辑页表单组件内的字段
    public List<Object> updateFormFieldsParser(Context ctx, List<Object> fields) {
        List<Object> items = new ArrayList<>();
        return items;
    }

    // 解析表单组件内的字段
    public Object formFieldsParser(Context ctx, Object fields) {
        if (fields instanceof List) {
            ((List<Object>) fields).stream().forEach(field -> {
                boolean hasBody = false;
                Object body = new Object();
                try {
                    body = field.getClass().getField("body").get(field);
                    hasBody = true;
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    hasBody = false;
                }
                if (hasBody) {
                    this.formFieldsParser(context, body);
                } else {
                    try {
                        Object component = field.getClass().getSuperclass().getDeclaredField("component").get(field);
                        String getComponent = (String) component;
                        if (getComponent.contains("Field")) {
                            Method method = field.getClass().getMethod("buildFrontendRules", String.class);
                            method.invoke(field, context.request.getQueryString());
                        }
                    } catch (NoSuchFieldException | NoSuchMethodException | SecurityException | IllegalAccessException
                            | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return fields;
    }

    // 详情页字段
    public List<Object> detailFields(Context ctx) {
        List<Object> items = new ArrayList<>();
        return items;
    }

    // 包裹在组件内的详情页字段
    public Object detailFieldsWithinComponents(Context ctx, Map<String, Object> data) {
        List<Object> items = new ArrayList<>();
        return items;
    }

    // 导出字段
    public List<Object> exportFields(Context ctx) {
        List<Object> items = new ArrayList<>();
        return items;
    }

    // 导入字段
    public List<Object> importFields(Context ctx) {
        List<Object> items = new ArrayList<>();
        return items;
    }

    // 不包含When组件内字段的导入字段
    public List<Object> importFieldsWithoutWhen(Context ctx) {
        List<Object> items = new ArrayList<>();
        return items;
    }

    // 获取字段
    public List<Object> getFields(Context ctx) {
        return null;
    }

    // 获取不包含When组件的字段
    public List<Object> getFieldsWithoutWhen(Context ctx) {
        return null;
    }

    // 查找字段
    public List<Object> findFields(List<Object> fields, boolean when) {
        List<Object> items = new ArrayList<>();
        return items;
    }

    // 获取When组件中的字段
    public List<Object> getWhenFields(Object item) {
        List<Object> items = new ArrayList<>();
        return items;
    }
}
