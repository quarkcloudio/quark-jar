package io.quarkcloud.quarkadmin.template.resource.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.quarkcloud.quarkcore.service.Context;

public class ResolveField {

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

    // 获取字段
    public List<Object> getFields(Context ctx) {
        Resourcer template = (Resourcer) ctx.getTemplate();
        List<Object> fields = template.fields(ctx);
        return findFields(fields, true);
    }

    // 查找字段
    public List<Object> findFields(List<Object> fields, boolean when) {
        List<Object> items = new ArrayList<>();

        for (Object field : fields) {
            try {
                Field bodyField = field.getClass().getDeclaredField("body");
                bodyField.setAccessible(true);
                boolean hasBody = bodyField.isAccessible();

                if (hasBody) {
                    Object body = bodyField.get(field);
                    List<Object> bodyFields = findFields((List<Object>) body, true);
                    items.addAll(bodyFields);
                } else {
                    Field componentField = field.getClass().getDeclaredField("component");
                    componentField.setAccessible(true);
                    String component = (String) componentField.get(field);

                    if (component.contains("Field")) {
                        items.add(field);
                        if (when) {
                            List<Object> whenFields = getWhenFields(field);
                            if (!whenFields.isEmpty()) {
                                items.addAll(whenFields);
                            }
                        }
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return items;
    }

    // 获取When组件中的字段
    public List<Object> getWhenFields(Object field) {
        List<Object> items = new ArrayList<>();

        try {
            Field whenField = field.getClass().getDeclaredField("when");
            whenField.setAccessible(true);
            boolean whenIsValid = whenField.isAccessible();

            if (!whenIsValid) {
                return items;
            }

            Object getWhen = field.getClass().getMethod("getWhen").invoke(field);
            if (getWhen == null) {
                return items;
            }

            List<Object> whenItems = (List<Object>) getWhen.getClass().getMethod("getItems").invoke(getWhen);
            if (whenItems == null) {
                return items;
            }

            for (Object v : whenItems) {
                Field bodyField = v.getClass().getDeclaredField("body");
                bodyField.setAccessible(true);
                Object body = bodyField.get(v);

                if (body instanceof List) {
                    items.addAll((List<Object>) body);
                } else {
                    items.add(body);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    // 获取表格行为列标题
    private String getTableActionColumnTitle() {
        return "Action"; // 示例值
    }

    // 获取表格行为列宽度
    private int getTableActionColumnWidth() {
        return 200; // 示例值
    }
}
