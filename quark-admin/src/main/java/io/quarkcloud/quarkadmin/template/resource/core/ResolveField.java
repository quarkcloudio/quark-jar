package io.quarkcloud.quarkadmin.template.resource.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.quarkcloud.quarkadmin.template.resource.Action;
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

        // 获取字段
        fields = this.getFields(ctx);

        // 判断是否为空
        if (fields == null) {
            return items;
        }

        // 遍历
        for (Object field : fields) {
            Object isShown = new Object();
            boolean hasMethod = false;
            try {
                isShown = field.getClass().getMethod("IsShownOnExport").invoke(field);
                hasMethod = true;
            } catch (Exception e) {
                hasMethod = false;
            }

            if (hasMethod) {
                if (isShown instanceof Boolean) {
                    if ((Boolean) isShown) {
                        items.add(field);
                    }
                }
            }
        }

        return items;
    }

    // 导入字段
    public List<Object> importFields(Context ctx) {
        List<Object> items = new ArrayList<>();

        // 获取字段
        fields = this.getFields(ctx);

        // 判断是否为空
        if (fields == null) {
            return items;
        }

        // 遍历
        for (Object field : fields) {
            Object isShown = new Object();
            boolean hasMethod = false;
            try {
                isShown = field.getClass().getMethod("isShownOnImport").invoke(field);
                hasMethod = true;
            } catch (Exception e) {
                hasMethod = false;
            }

            if (hasMethod) {
                if (isShown instanceof Boolean) {
                    if ((Boolean) isShown) {
                        items.add(field);
                    }
                }
            }
        }

        return items;
    }

    // 不包含When组件内字段的导入字段
    public List<Object> importFieldsWithoutWhen(Context ctx) {
        List<Object> items = new ArrayList<>();

        // 获取字段
        fields = this.getFieldsWithoutWhen(ctx);

        // 判断是否为空
        if (fields == null) {
            return items;
        }

        // 遍历
        for (Object field : fields) {
            Object isShown = new Object();
            boolean hasMethod = false;
            try {
                isShown = field.getClass().getMethod("isShownOnImport").invoke(field);
                hasMethod = true;
            } catch (Exception e) {
                hasMethod = false;
            }

            if (hasMethod) {
                if (isShown instanceof Boolean) {
                    if ((Boolean) isShown) {
                        items.add(field);
                    }
                }
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
                boolean hasBody = false;
                Object body = new Object();
                try {
                    body = v.getClass().getField("body").get(v);
                    hasBody = true;
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    hasBody = false;
                }
                if (hasBody) {
                    List<Object> getItems = findFields(body, true);
                    if (getItems != null && !getItems.isEmpty()) {
                        items.addAll(getItems);
                    }
                } else {
                    Object component;
                    try {
                        component = v.getClass().getSuperclass().getDeclaredField("component").get(v);
                        String getComponent = (String) component;
                        if (getComponent != null && getComponent.contains("field")) {
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
        try {
            item.getClass().getDeclaredField("when");
        } catch (NoSuchFieldException e) {
            return items;
        }

        Object getWhen = new Object();
        try {
            getWhen = item.getClass().getMethod("getWhen").invoke(item);
        } catch (Exception e) {
            return items;
        }

        Object whenItems = new Object();
        boolean hasWhenItems = false;
        try {
            whenItems = getWhen.getClass().getField("items").get(getWhen);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        if (!hasWhenItems) {
            return items;
        }

        if (whenItems instanceof List<?>) {
            List<?> whenItemsList = (List<?>) whenItems;
            for (Object v : whenItemsList) {
                Object body = new Object();
                boolean hasBody = false;
                try {
                    body = v.getClass().getField("body").get(v);
                    hasBody = true;
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    hasBody = false;
                }
                if (hasBody) {
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
