package io.quarkcloud.quarkadmin.template.resource.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.quarkcloud.quarkadmin.component.form.fields.SelectField;
import io.quarkcloud.quarkadmin.component.form.fields.SwitchField;
import io.quarkcloud.quarkadmin.component.form.fields.TreeSelect;
import io.quarkcloud.quarkadmin.component.form.fields.Cascader;
import io.quarkcloud.quarkadmin.component.form.fields.Checkbox;
import io.quarkcloud.quarkadmin.component.form.fields.Radio;
import io.quarkcloud.quarkadmin.component.table.Column;
import io.quarkcloud.quarkadmin.component.descriptions.Descriptions;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkcore.util.Reflect;

public class ResolveField {

    // fields
    public List<Object> fields;

    // context
    public Context context;

    // 表格行为列标题
    public String tableActionColumnTitle;

    // 表格行为列宽度
    public int tableActionColumnWidth;

    // 列表行为
    public List<Object> tableRowActions;

    // 详情页行为
    public List<Object> detailActions;

    // 构造函数
    public ResolveField() {}

    // 构造函数
    public ResolveField(List<Object> fields, Context context) {
        this.fields = fields;
        this.context = context;
    }

    // 设置表格行为列标题
    public ResolveField setTableActionColumnTitle(String tableActionColumnTitle) {
        this.tableActionColumnTitle = tableActionColumnTitle;
        return this;
    }

    // 设置表格行为列宽度
    public ResolveField setTableActionColumnWidth(int tableActionColumnWidth) {
        this.tableActionColumnWidth = tableActionColumnWidth;
        return this;
    }

    // 列表行为
    public ResolveField setTableRowActions(List<Object> tableRowActions) {
        this.tableRowActions = tableRowActions;
        return this;
    }

    // 列表行为
    public ResolveField setDetailActions(List<Object> detailActions) {
        this.detailActions = detailActions;
        return this;
    }

    // 列表字段
    public List<Object> indexFields(Context ctx) {
        List<Object> items = new ArrayList<>();
        fields = this.getFields(ctx);
        if (fields == null) {
            return items;
        }
        for (Object field : fields) {
            Object isShown = new Reflect(field).invoke("isShownOnIndex");
            if (isShown!=null && (Boolean) isShown) {
                items.add(field);
            }
        }
        return items;
    }

    // 表格列
    public List<Object> indexTableColumns(Context ctx) {
        List<Object> columns = new ArrayList<>();

        // 获取索引表字段
        List<Object> fields = indexFields(ctx);
        for (Object v : fields) {
            Object getColumn = fieldToColumn(ctx, v);
            if (getColumn != null) {
                columns.add(getColumn);
            }
        }

        // 获取索引表行动作
        if (tableRowActions.size() > 0) {
            // 构建行动作列
            String columnTitle = tableActionColumnTitle;
            int columnWidth = tableActionColumnWidth;

            Column column = new Column()
                .setTitle(columnTitle)
                .setWidth(columnWidth)
                .setAttribute("action")
                .setValueType("option")
                .setActions(tableRowActions)
                .setFixed("right");
            
            columns.add(column);
        }

        return columns;
    }

    // 将表单项转换为表格列
    public Object fieldToColumn(Context ctx, Object field) {
        Column column = null;
        Reflect reflect = new Reflect(field);
        
        // 获取字段属性
        String name = (String) reflect.getFieldValue("name");
        String label = (String) reflect.getFieldValue("label");
        String component = (String) reflect.getFieldValue("component");
        String align = (String) reflect.getFieldValue("align");
        Object fixed = reflect.getFieldValue("getField");
        boolean editable = (boolean) reflect.getFieldValue("editable");
        boolean ellipsis = (boolean) reflect.getFieldValue("ellipsis");
        boolean copyable = (boolean) reflect.getFieldValue("copyable");
        Object filters =  reflect.getFieldValue("filters");
        int order = (int) reflect.getFieldValue("order");
        Object sorter = reflect.getFieldValue("sorter");
        int span = (int) reflect.getFieldValue("span");
        int columnWidth = (int) reflect.getFieldValue("columnWidth");

        // 创建一个通用列对象
        column = new Column()
            .setTitle(label)
            .setAttribute(name)
            .setAlign(align)
            .setFixed((String) fixed)
            .setEllipsis(ellipsis)
            .setCopyable(copyable)
            .setFilters(filters)
            .setOrder(order)
            .setSorter(sorter)
            .setSpan(span)
            .setWidth(columnWidth);

        // 根据组件类型设置特定的值类型和字段属性
        switch (component) {
            case "idField":
                boolean onIndexDisplayed = (boolean) reflect.getFieldValue("onIndexDisplayed");
                if (!onIndexDisplayed) {
                    return null;
                }
                column.setValueType("text");
                break;
            case "hiddenField":
                return null;
            case "textField":
                column.setValueType("text");
            case "textAreaField":
                column.setValueType("text");
                break;
            case "treeSelectField":
                List<?> treeOptions = ((TreeSelect) field).getOptions();
                column.setValueType("treeSelect")
                    .setFieldProps(Map.of("options", treeOptions));
                break;
            case "cascaderField":
                List<?> cascaderOptions = ((Cascader) field).getOptions();
                column.setValueType("cascader")
                    .setFieldProps(Map.of("options", cascaderOptions));
                break;
            case "selectField":
                List<?> selectOptions = ((SelectField) field).getOptions();
                column.setValueType("select")
                    .setFieldProps(Map.of("options", selectOptions));
                
                // 如果设置了过滤项，设置值的枚举
                if (filters instanceof Boolean && (boolean) filters) {
                    Object valueEnum = ((SelectField) field).getValueEnum();
                    column.setValueEnum(valueEnum);
                }
                break;
            case "checkboxField":
                List<?> checkboxOptions = ((Checkbox) field).getOptions();
                column.setValueType("checkbox")
                    .setFieldProps(Map.of("options", checkboxOptions));
                
                // 如果设置了过滤项，设置值的枚举
                if (filters instanceof Boolean && (boolean) filters) {
                    Object valueEnum = ((Checkbox) field).getValueEnum();
                    column.setValueEnum(valueEnum);
                }
                break;
            case "radioField":
                List<?> radioOptions = ((Radio) field).getOptions();
                column.setValueType("radio")
                    .setFieldProps(Map.of("options", radioOptions));
                
                // 如果设置了过滤项，设置值的枚举
                if (filters instanceof Boolean && (boolean) filters) {
                    Object valueEnum = ((Radio) field).getValueEnum();
                    column.setValueEnum(valueEnum);
                }
                break;
            case "switchField":
                Object switchOptions = ((SwitchField) field).getOptions();
                column.setValueType("select")
                    .setValueEnum(switchOptions);
                
                // 如果设置了过滤项，设置值的枚举
                if (filters instanceof Boolean && (boolean) filters) {
                    Object valueEnum = ((SwitchField) field).getValueEnum();
                    column.setValueEnum(valueEnum);
                }
                break;
            case "imageField":
                column.setValueType("image");
                break;
            default:
                column.setValueType(component);
                break;
        }

        // 如果可编辑，设置编辑项
        if (editable) {
            String editableApi = ctx.getRequest().getRequestURI().replace("/index", "/editable");
            column.setEditable(component, null, editableApi);
        }

        return column;
    }

    // 创建页字段
    public List<Object> creationFields(Context ctx) {
        List<Object> items = new ArrayList<>();
        fields = this.getFields(ctx);
        if (fields == null) {
            return items;
        }
        for (Object field : fields) {
            Object isShown = new Reflect(field).invoke("isShownOnCreation");
            if (isShown!=null && (Boolean) isShown) {
                items.add(field);
            }
        }
        return items;
    }

    // 不包含When组件内字段的创建页字段
    public List<Object> creationFieldsWithoutWhen(Context ctx) {
        List<Object> items = new ArrayList<>();
        fields = this.getFieldsWithoutWhen(ctx);
        if (fields == null) {
            return items;
        }
        for (Object field : fields) {
            Object isShown = new Reflect(field).invoke("isShownOnCreation");
            if (isShown!=null && (Boolean) isShown) {
                items.add(field);
            }
        }
        return items;
    }

    // 包裹在组件内的创建页字段
    public List<Object> creationFieldsWithinComponents(Context ctx) {
        List<Object> items = creationFormFieldsParser(ctx, fields);
        return items;
    }

    // 解析创建页表单组件内的字段
    public List<Object> creationFormFieldsParser(Context ctx, Object fields) {
        List<Object> items = new ArrayList<>();

        // 解析字段
        if (fields instanceof List<?>) {
            List<?> fieldList = (List<?>) fields;
            for (Object obj : fieldList) {
                try {
                    // 检查是否有Body字段
                    boolean hasBody = obj.getClass().getField("body") != null;

                    if (hasBody) {
                        // 获取Body字段的内容值
                        Object body = obj.getClass().getField("body").get(obj);

                        // 递归解析值
                        Object parsedFields = creationFormFieldsParser(ctx, body);

                        // 更新Body字段的值
                        obj.getClass().getField("body").set(obj, parsedFields);

                        items.add(obj);
                    } else {
                        // 获取Component字段的值
                        String component = (String) obj.getClass().getSuperclass().getDeclaredField("component").get(obj);

                        // 如果Component包含"Field"，则进行进一步处理
                        if (component.contains("Field")) {

                            // 判断是否在创建页面显示
                            Object isShown = new Object();
                            boolean hasMethod = false;
                            try {
                                isShown = obj.getClass().getMethod("isShownOnCreation").invoke(obj);
                                hasMethod = true;
                            } catch (Exception e) {
                                hasMethod = false;
                            }
                
                            if (hasMethod) {
                                if (isShown instanceof Boolean) {
                                    if ((Boolean) isShown) {
                                        try {
                                            Method method = obj.getClass().getMethod("buildFrontendRules", String.class);
                                            method.invoke(obj, context.request.getQueryString());
                                        } catch (NoSuchMethodException | SecurityException | IllegalAccessException
                                                | InvocationTargetException e) {
                                            e.printStackTrace();
                                        }
                                        items.add(obj);
                                    }
                                }
                            }
                        } else {
                            items.add(obj);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return items;
    }

    // 编辑页字段
    public List<Object> updateFields(Context ctx) {
        List<Object> items = new ArrayList<>();
        fields = this.getFields(ctx);
        if (fields == null) {
            return items;
        }
        for (Object field : fields) {
            Object isShown = new Reflect(field).invoke("isShownOnUpdate");
            if (isShown!=null && (Boolean) isShown) {
                items.add(field);
            }
        }
        return items;
    }

    // 不包含When组件内字段的编辑页字段
    public List<Object> updateFieldsWithoutWhen(Context ctx) {
        List<Object> items = new ArrayList<>();
        fields = this.getFieldsWithoutWhen(ctx);
        if (fields == null) {
            return items;
        }
        for (Object field : fields) {
            Object isShown = new Reflect(field).invoke("isShownOnUpdate");
            if (isShown!=null && (Boolean) isShown) {
                items.add(field);
            }
        }
        return items;
    }

    // 包裹在组件内的编辑页字段
    public List<Object> updateFieldsWithinComponents(Context ctx) {
        List<Object> items = updateFormFieldsParser(ctx, fields);
        return items;
    }

    // 解析编辑页表单组件内的字段
    public List<Object> updateFormFieldsParser(Context ctx, Object fields) {
        List<Object> items = new ArrayList<>();

        // 解析字段
        if (fields instanceof List<?>) {
            List<?> fieldList = (List<?>) fields;
            for (Object obj : fieldList) {
                try {
                    // 检查是否有Body字段
                    boolean hasBody = obj.getClass().getField("body") != null;

                    if (hasBody) {
                        // 获取Body字段的内容值
                        Object body = obj.getClass().getField("body").get(obj);

                        // 递归解析值
                        Object parsedFields = updateFormFieldsParser(ctx, body);

                        // 更新Body字段的值
                        obj.getClass().getField("body").set(obj, parsedFields);

                        items.add(obj);
                    } else {
                        // 获取Component字段的值
                        String component = (String) obj.getClass().getSuperclass().getDeclaredField("component").get(obj);

                        // 如果Component包含"Field"，则进行进一步处理
                        if (component.contains("Field")) {

                            // 判断是否在创建页面显示
                            Object isShown = new Object();
                            boolean hasMethod = false;
                            try {
                                isShown = obj.getClass().getMethod("isShownOnUpdate").invoke(obj);
                                hasMethod = true;
                            } catch (Exception e) {
                                hasMethod = false;
                            }
                
                            if (hasMethod) {
                                if (isShown instanceof Boolean) {
                                    if ((Boolean) isShown) {
                                        try {
                                            Method method = obj.getClass().getMethod("buildFrontendRules", String.class);
                                            method.invoke(obj, context.request.getQueryString());
                                        } catch (NoSuchMethodException | SecurityException | IllegalAccessException
                                                | InvocationTargetException e) {
                                            e.printStackTrace();
                                        }
                                        items.add(obj);
                                    }
                                }
                            }
                        } else {
                            items.add(obj);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

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
        fields = this.getFields(ctx);
        if (fields == null) {
            return items;
        }
        for (Object field : fields) {
            Object isShown = new Reflect(field).invoke("isShownOnDetail");
            if (isShown!=null && (Boolean) isShown) {
                items.add(field);
            }
        }
        return items;
    }

    // 包裹在组件内的详情页字段
    public Object detailFieldsWithinComponents(Context ctx, Map<String, Object> data) {
        List<Object> items = new ArrayList<>();
        String componentType = "description";

        for (Object v : fields) {
            try {
                // 检查是否有Body字段
                Reflect bodyReflect = new Reflect(v);
                boolean bodyFieldExist = bodyReflect.checkFieldExist("body");

                // 解析body数据
                if (bodyFieldExist) {
                    Object body = bodyReflect.getFieldValue();
                    List<Object> subItems = new ArrayList<>();
                    for (Object sv : (List<?>) body) {
                        Object isShown = new Reflect(sv).invoke("isShownOnDetail");
                        if (isShown!=null && (Boolean) isShown) {
                            Object getColumn = fieldToColumn(ctx, sv);
                            items.add(getColumn);
                        }
                    }

                    Object descriptions = new Descriptions()
                        .setStyle(Map.of("padding", "24px"))
                        .setTitle("")
                        .setColumn(2)
                        .setColumns(subItems)
                        .setDataSource(data)
                        .setActions(detailActions);

                    v.getClass().getMethod("setBody", Object.class).invoke(v, descriptions);
                    items.add(v);
                } else {
                    Object isShown = new Reflect(v).invoke("isShownOnDetail");
                    if (isShown!=null && (Boolean) isShown) {
                        Object getColumn = fieldToColumn(ctx, v);
                        items.add(getColumn);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if ("description".equals(componentType)) {
            return new Descriptions()
                .setStyle(Map.of("padding", "24px"))
                .setTitle("")
                .setColumn(2)
                .setColumns(items)
                .setDataSource(data)
                .setActions(detailActions);
        }

        return items;
    }

    // 导出字段
    public List<Object> exportFields(Context ctx) {
        List<Object> items = new ArrayList<>();
        fields = this.getFields(ctx);
        if (fields == null) {
            return items;
        }
        for (Object field : fields) {
            Object isShown = new Reflect(field).invoke("isShownOnExport");
            if (isShown!=null && (Boolean) isShown) {
                items.add(field);
            }
        }
        return items;
    }

    // 导入字段
    public List<Object> importFields(Context ctx) {
        List<Object> items = new ArrayList<>();
        fields = this.getFields(ctx);
        if (fields == null) {
            return items;
        }
        for (Object field : fields) {
            Object isShown = new Reflect(field).invoke("isShownOnImport");
            if (isShown!=null && (Boolean) isShown) {
                items.add(field);
            }
        }
        return items;
    }

    // 不包含When组件内字段的导入字段
    public List<Object> importFieldsWithoutWhen(Context ctx) {
        List<Object> items = new ArrayList<>();
        fields = this.getFieldsWithoutWhen(ctx);
        if (fields == null) {
            return items;
        }
        for (Object field : fields) {
            Object isShown = new Reflect(field).invoke("isShownOnImport");
            if (isShown!=null && (Boolean) isShown) {
                items.add(field);
            }
        }
        return items;
    }

    // 获取字段
    public List<Object> getFields(Context ctx) {
        return this.findFields(fields, true);
    }

    // 获取不包含When组件的字段
    public List<Object> getFieldsWithoutWhen(Context ctx) {
        return this.findFields(fields, false);
    }

    public List<Object> findFields(Object fields, boolean when) {
        List<Object> items = new ArrayList<>();
        if (fields instanceof List<?>) {
            List<?> fieldsList = (List<?>) fields;
            for (Object v : fieldsList) {
                Reflect bodyReflect = new Reflect(v);
                boolean bodyFieldExist = bodyReflect.checkFieldExist("body");
                if (bodyFieldExist) {
                    Object body = bodyReflect.getFieldValue();
                    List<Object> getItems = findFields(body, true);
                    if (getItems != null && !getItems.isEmpty()) {
                        items.addAll(getItems);
                    }
                } else {
                    try {
                        String component = (String) v.getClass().getSuperclass().getDeclaredField("component").get(v);
                        if (component != null && component.contains("Field")) {
                            items.add(v);
                            if (when) {
                                List<Object> whenFields = getWhenFields(v);
                                if (!whenFields.isEmpty()) {
                                    items.addAll(whenFields);
                                }
                            }
                        }
                    } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
                            | SecurityException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return items;
    }

    // 获取When组件中的字段
    public List<Object> getWhenFields(Object item) {
        List<Object> items = new ArrayList<>();

        // 反射
        Reflect itemReflect = new Reflect(item);

        // 是否存在getWhen方法
        boolean getWhenMethodExist = itemReflect.checkMethodExist("getWhen");
        if (!getWhenMethodExist) {
            return items;
        }

        // 获取When组件
        Object getWhen = itemReflect.invoke("getWhen");

        // 判断是否存在items字段
        Reflect whenReflect = new Reflect(getWhen);
        boolean itemsFieldExist = whenReflect.checkFieldExist("items");
        if (!itemsFieldExist) {
            return items;
        }
        Object whenItems = whenReflect.getFieldValue("items");
        if (whenItems instanceof List<?>) {
            List<?> whenItemsList = (List<?>) whenItems;
            for (Object v : whenItemsList) {
                Reflect bodyReflect = new Reflect(v);
                boolean bodyFieldExist = bodyReflect.checkFieldExist("body");
                if (bodyFieldExist) {
                    Object body = bodyReflect.getFieldValue();
                    if (body instanceof List<?>) {
                        items.addAll((List<?>) body);
                    } else {
                        items.add(body);
                    }
                }
            }
        }

        return items;
    }
}
