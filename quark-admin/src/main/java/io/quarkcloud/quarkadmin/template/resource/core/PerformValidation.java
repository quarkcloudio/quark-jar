package io.quarkcloud.quarkadmin.template.resource.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkcloud.quarkadmin.component.form.Rule;
import io.quarkcloud.quarkadmin.component.form.fields.WhenItem;
import io.quarkcloud.quarkcore.service.Context;
import net.sf.jsqlparser.util.validation.ValidationException;

public class PerformValidation<T> {
    // 创建请求的验证器
    public void validatorForCreation(Context ctx, T data) {

    }

    // 验证规则
    public Object validator(List<Rule> rules, T data) {
        for (Rule rule : rules) {
        }

        return null;
    }

    private void checkUnique(Rule rule, Object fieldValue) throws ValidationException {
        if (fieldValue == null) {
            return;
        }
        String table = rule.getUniqueTable();
        String field = rule.getUniqueTableField();
        String except = rule.getUniqueIgnoreValue();
        String sql;
        Object[] params;
    }

    // 创建请求的验证规则
    public List<Rule> rulesForCreation(Context ctx) {
        List<Rule> rules = new ArrayList<>();
        return rules;
    }

    // 判断是否需要验证 When 组件里的规则
    public boolean needValidateWhenRules(Context ctx, WhenItem whenItem) {
        ObjectMapper objectMapper = new ObjectMapper(); // 用于 JSON 序列化
        String conditionName = whenItem.getConditionName();
        Object conditionOption = whenItem.getOption();
        String conditionOperator = whenItem.getConditionOperator();
        
        Map<String, Object> data = null;
        Object value = data.get(conditionName);
        if (value == null) {
            return false;
        }

        String valueString = value.toString();
        if (valueString.isEmpty()) {
            return false;
        }

        switch (conditionOperator) {
            case "=":
                return valueString.equals(conditionOption.toString());

            case ">":
                return valueString.compareTo(conditionOption.toString()) > 0;

            case "<":
                return valueString.compareTo(conditionOption.toString()) < 0;

            case "<=":
                return valueString.compareTo(conditionOption.toString()) <= 0;

            case ">=":
                return valueString.compareTo(conditionOption.toString()) >= 0;

            case "has":
                if (value instanceof List) {
                    try {
                        String json = objectMapper.writeValueAsString(value);
                        return json.contains(conditionOption.toString());
                    } catch (JsonProcessingException e) {
                        // Handle JSON processing error
                        return false;
                    }
                } else {
                    return valueString.contains(conditionOption.toString());
                }

            case "in":
                if (conditionOption instanceof List) {
                    List<String> conditionOptionList = (List<String>) conditionOption;
                    return conditionOptionList.contains(valueString);
                }
                break;

            default:
                return valueString.equals(conditionOption.toString());
        }

        return false;
    }

    // 获取创建请求资源规则
    public List<Rule> getRulesForCreation(Object field) {
        List<Rule> rules = new ArrayList<>();
        return rules;
    }

    // 更新请求的验证器
    public Object validatorForUpdate(Context ctx, T data) {
        List<Rule> rules = rulesForUpdate(ctx);
        return validator(rules, data);
    }

    // 更新请求的验证规则
    public List<Rule> rulesForUpdate(Context ctx) {
        List<Rule> rules = new ArrayList<>();
        return rules;
    }

    // 获取更新请求资源规则
    public List<Rule> getRulesForUpdate(Object field) {
        List<Rule> rules = new ArrayList<>();
        return rules;
    }

    // 导入请求的验证器
    public Object validatorForImport(Context ctx, T data) throws ValidationException {
        List<Rule> rules = rulesForImport(ctx);
        return validator(rules, data);
    }

    // 创建请求的验证规则
    public List<Rule> rulesForImport(Context ctx) {
        List<Rule> rules = new ArrayList<>();
        return rules;
    }
}
