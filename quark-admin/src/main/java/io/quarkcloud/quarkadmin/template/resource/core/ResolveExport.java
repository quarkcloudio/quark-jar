package io.quarkcloud.quarkadmin.template.resource.core;

import io.quarkcloud.quarkadmin.component.form.fields.Checkbox;
import io.quarkcloud.quarkadmin.component.form.fields.Radio;
import io.quarkcloud.quarkadmin.component.form.fields.SelectField;
import io.quarkcloud.quarkadmin.component.form.fields.SwitchField;
import io.quarkcloud.quarkcore.util.Reflect;

public class ResolveExport {

    // 获取字段Label
    public String getFieldLabel(Object field) {
        Reflect fieldReflect = new Reflect(field);
        boolean fieldExist = fieldReflect.checkFieldExist("label");
        if (!fieldExist) {
            return "";
        }
        return (String) fieldReflect.getFieldValue("label");
    }

    // 获取字段Name
    public String getFieldName(Object field) {
        Reflect fieldReflect = new Reflect(field);
        boolean fieldExist = fieldReflect.checkFieldExist("name");
        if (!fieldExist) {
            return "";
        }
        return (String) fieldReflect.getFieldValue("name");
    }

    // 获取字段Component
    public String getFieldComponent(Object field) {
        Reflect fieldReflect = new Reflect(field);
        boolean fieldExist = fieldReflect.checkFieldExist("component");
        if (!fieldExist) {
            return "";
        }
        return (String) fieldReflect.getFieldValue("component");
    }

    public Object getCellValue(String component, Object field, Object rowData, String name) {
        Reflect fieldReflect = new Reflect(rowData);
        boolean fieldExist = fieldReflect.checkFieldExist(name);
        if (!fieldExist) {
            return null;
        }
        switch (component) {
            case "inputNumberField":
                return fieldReflect.getFieldValue(name);
            case "textField":
                return fieldReflect.getFieldValue(name);
            case "selectField":
                SelectField selectField = (SelectField) field;
                return selectField.getOptionLabel(fieldReflect.getFieldValue(name));
            case "checkboxField":
                Checkbox checkboxField = (Checkbox) field;
                return checkboxField.getOptionLabel(fieldReflect.getFieldValue(name));
            case "radioField":
                Radio radioField = (Radio) field;
                return radioField.getOptionLabel(fieldReflect.getFieldValue(name));
            case "switchField":
                SwitchField switchField = (SwitchField) field;
                return switchField.getOptionLabel(fieldReflect.getFieldValue(name));
            default:
                return fieldReflect.getFieldValue(name);
        }
    }
}
