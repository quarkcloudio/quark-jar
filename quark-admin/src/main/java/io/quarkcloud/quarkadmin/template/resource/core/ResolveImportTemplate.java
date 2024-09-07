package io.quarkcloud.quarkadmin.template.resource.core;

import java.util.ArrayList;
import java.util.List;

import io.quarkcloud.quarkadmin.component.form.Rule;
import io.quarkcloud.quarkadmin.component.form.fields.Checkbox;
import io.quarkcloud.quarkadmin.component.form.fields.Radio;
import io.quarkcloud.quarkadmin.component.form.fields.SelectField;
import io.quarkcloud.quarkadmin.component.form.fields.SwitchField;
import io.quarkcloud.quarkcore.util.Reflect;

public class ResolveImportTemplate {

    // 获取导入模板字段Label
    public String getFieldLabel(Object field) {
        Reflect fieldReflect = new Reflect(field);
        boolean labelFieldExist = fieldReflect.checkFieldExist("label");
        if (!labelFieldExist) {
            return "";
        }
        return (String) fieldReflect.getFieldValue("label");
    }

    // 获取导入模板字段备注
    @SuppressWarnings("unchecked")
    public String getFieldRemark(Object field) {
        String remark = "";
        String optionLabels = "";
        Reflect fieldReflect = new Reflect(field);
        boolean componentFieldExist = fieldReflect.checkFieldExist("component");
        if (!componentFieldExist) {
            return remark;
        }
        String component = (String) fieldReflect.getFieldValue("component");
        switch (component) {
            case "inputNumberField":
                remark = "数字格式";
                break;
            case "textField":
                remark = "";
                break;
            case "selectField":
                SelectField selectField = (SelectField) field;
                String mode = selectField.getMode();
                optionLabels = selectField.getOptionLabels();
                if (!mode.isEmpty()) {
                    remark = "可多选：" + optionLabels + "；多值请用“,”分割";
                } else {
                    remark = "可选：" + optionLabels;
                }
                break;
            case "cascaderField":
                remark = "级联格式，例如：省，市，县";
                break;
            case "checkboxField":
                Checkbox checkboxField = (Checkbox) field;
                optionLabels = checkboxField.getOptionLabels();
                remark = "可多选项：" + optionLabels + "；多值请用“,”分割";
                break;
            case "radioField":
                Radio radioField = (Radio) field;
                optionLabels = radioField.getOptionLabels();
                remark = "可选项：" + optionLabels;
                break;
            case "switchField":
                SwitchField switchField = (SwitchField) field;
                optionLabels = switchField.getOptionLabels();
                remark = "可选项：" + optionLabels;
                break;
            case "dateField":
                remark = "日期格式，例如：1987-02-15";
                break;
            case "datetimeField":
                remark = "日期时间格式，例如：1987-02-15 20:00:00";
                break;
        }

        List<Rule> rules = new ArrayList<>();
        boolean getRulesMethodExist = fieldReflect.checkMethodExist("getRules");
        if (getRulesMethodExist) {
            List<Rule> getRules = (List<Rule>) fieldReflect.invoke("getRules");
            rules.addAll(getRules);
        }

        boolean getCreationRulesMethodExist = fieldReflect.checkMethodExist("getCreationRules");
        if (getCreationRulesMethodExist) {
            List<Rule> getCreationRules = (List<Rule>) fieldReflect.invoke("getCreationRules");
            rules.addAll(getCreationRules);
        }

        String ruleMessage = getFieldRuleMessage(rules);
        if (!ruleMessage.isEmpty()) {
            remark = remark + " 条件：" + ruleMessage;
        }

        if (!remark.isEmpty()) {
            remark = "（" + remark + "）";
        }

        return remark;
    }

    // 获取字段验证规则提示
    private String getFieldRuleMessage(List<Rule> rules) {
        List<String> messages = new ArrayList<>();
        for (Rule rule : rules) {
            switch (rule.getRuleType()) {
                case "required":
                    messages.add("必填");
                    break;
                case "min":
                    messages.add("大于某个字符");
                    break;
                case "max":
                    messages.add("小于某个字符");
                    break;
                case "email":
                    messages.add("必须为邮箱格式");
                    break;
                case "numeric":
                    messages.add("必须为数字格式");
                    break;
                case "url":
                    messages.add("必须为链接格式");
                    break;
                case "integer":
                    messages.add("必须为整数格式");
                    break;
                case "date":
                    messages.add("必须为日期格式");
                    break;
                case "boolean":
                    messages.add("必须为布尔格式");
                    break;
                case "unique":
                    messages.add("不可重复");
                    break;
            }
        }

        return String.join("，", messages);
    }
}
