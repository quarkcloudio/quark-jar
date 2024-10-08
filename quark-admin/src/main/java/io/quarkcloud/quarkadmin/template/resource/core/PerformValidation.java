package io.quarkcloud.quarkadmin.template.resource.core;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.quarkcloud.quarkadmin.component.form.Rule;
import io.quarkcloud.quarkadmin.component.form.fields.When;
import io.quarkcloud.quarkadmin.component.form.fields.WhenItem;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkcore.util.Reflect;
import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.service.ResourceService;

public class PerformValidation<T> {

    // 字段
    public List<Object> fields;

    // 上下文
    public Context context;

    // 资源服务
    public ResourceService<ResourceMapper<T>, T> resourceService;

    // 构造函数
    public PerformValidation(Context context, List<Object> fields, ResourceService<ResourceMapper<T>, T> resourceService) {
        this.context = context;
        this.fields = fields;
        this.resourceService = resourceService;
    }

    // 创建请求的验证器
    public Object validatorForCreation(Object data) {
        List<Rule> rules = rulesForCreation(context);
        return validator(rules, data);
    }

    // 验证规则
    public Object validator(List<Rule> rules, Object data) {
        Object result = null;
        Reflect reflect = new Reflect(data);
        for (Rule rule : rules) {
            Object fieldValue = reflect.getFieldValue(rule.getName());
            switch (rule.getRuleType()) {
                case "required":
                    if (fieldValue == null) {
                        String errMsg = rule.getMessage();
                        if (!errMsg.isEmpty()) {
                            result = errMsg;
                        }
                    }
                    break;

                case "min":
                    if (fieldValue instanceof String) {
                        String fieldStr = (String) fieldValue;
                        int strNum = 0;
                        if (fieldStr != null) {
                            // 获取 UTF-8 编码的字节数组长度
                            strNum = fieldStr.getBytes(StandardCharsets.UTF_8).length;
                        }
                        if (strNum < rule.getMin()) {
                            String errMsg = rule.getMessage();
                            if (!errMsg.isEmpty()) {
                                result = errMsg;
                            }
                        }
                    }
                    break;

                case "max":
                    if (fieldValue instanceof String) {
                        String fieldStr = (String) fieldValue;
                        int strNum = 0;
                        if (fieldStr != null) {
                            // 获取 UTF-8 编码的字节数组长度
                            strNum = fieldStr.getBytes(StandardCharsets.UTF_8).length;
                        }
                        if (strNum > rule.getMax()) {
                            String errMsg = rule.getMessage();
                            if (!errMsg.isEmpty()) {
                                result = errMsg;
                            }
                        }
                    }
                    break;

                case "unique":
                    boolean checkUniqueResult = false;
                    String table = rule.getUniqueTable();
                    String field = rule.getUniqueTableField();
                    String except = rule.getUniqueIgnoreValue();
                    if (except != null && !except.isEmpty()) {
                        String ignoreField = except.replace("{", "").replace("}", "");
                        Object ignoreValue = reflect.getFieldValue(ignoreField);
                        checkUniqueResult = resourceService.checkUnique(table, field, fieldValue, ignoreField, ignoreValue);
                    } else {
                        checkUniqueResult = resourceService.checkUnique(table, field, fieldValue);
                    }
                    if (checkUniqueResult) {
                        String errMsg = rule.getMessage();
                        if (!errMsg.isEmpty()) {
                            result = errMsg;
                        }
                    }
                    break;
            }
        }
        return result;
    }

    // 创建请求的验证规则
    public List<Rule> rulesForCreation(Context context) {
        List<Rule> rules = new ArrayList<>();
        ResolveField resolveField = new ResolveField(fields, context);
        Object fields = resolveField.creationFieldsWithoutWhen(context);
        if (fields instanceof List<?>) {
            for (Object v : (List<?>) fields) {
                rules.addAll(getRulesForCreation(v));
                Reflect reflect = new Reflect(v);
                boolean hasGetWhen = reflect.checkMethodExist("getWhen");
                if (hasGetWhen) {
                    When when= (When) reflect.invoke("getWhen");
                    if (when!= null) {
                        List<WhenItem> whenItems = when.getItems();
                        if (whenItems != null && !whenItems.isEmpty()) {
                            for (WhenItem item : whenItems) {
                                if (needValidateWhenRules(context, item)) {
                                    Object body = item.getBody();
                                    if (body instanceof List<?>) {
                                        for (Object bv : (List<?>) body) {
                                            rules.addAll(getRulesForCreation(bv));
                                        }
                                    } else {
                                        rules.addAll(getRulesForCreation(body));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return rules;
    }

    // 判断是否需要验证 When 组件里的规则
    public boolean needValidateWhenRules(Context context, WhenItem whenItem) {
        String conditionName = whenItem.getConditionName();
        Object conditionOption = whenItem.getOption();
        String conditionOperator = whenItem.getConditionOperator();
        Map<String, String[]> data = context.getParameterMap();
        String[] value = data.get(conditionName);
        if (value == null) {
            return false;
        }
        String valueString = value[0];
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
                return valueString.contains(conditionOption.toString());
            case "in":
                if (conditionOption instanceof List) {
                    List<?> list = (List<?>) conditionOption;
                    if (!list.isEmpty() && list.get(0) instanceof String) {
                        return list.contains(valueString);
                    }
                }
                break;
            default:
                return valueString.equals(conditionOption.toString());
        }
        return false;
    }

    // 获取创建请求资源规则
    @SuppressWarnings("unchecked")
    public List<Rule> getRulesForCreation(Object field) {
        List<Rule> rules = new ArrayList<>();
        Reflect reflect = new Reflect(field);
        boolean hasGetRules = reflect.checkMethodExist("getRules");
        if (hasGetRules) {
            List<Rule> getRules= (List<Rule>) reflect.invoke("getRules");
            if (getRules!=null) {
                rules.addAll(getRules);
            }
        }
        boolean hasGetCreationRules = reflect.checkMethodExist("getCreationRules");
        if (hasGetCreationRules) {
            List<Rule> getCreationRules= (List<Rule>) reflect.invoke("getCreationRules");
            if (getCreationRules!=null) {
                rules.addAll(getCreationRules);
            }
        }
        return rules;
    }

    // 更新请求的验证器
    public Object validatorForUpdate(Object data) {
        List<Rule> rules = rulesForUpdate(context);
        return validator(rules, data);
    }

    // 更新请求的验证规则
    public List<Rule> rulesForUpdate(Context context) {
        List<Rule> rules = new ArrayList<>();
        ResolveField resolveField = new ResolveField(fields, context);
        Object fields = resolveField.updateFieldsWithoutWhen(context);
        if (fields instanceof List<?>) {
            for (Object v : (List<?>) fields) {
                rules.addAll(getRulesForUpdate(v));
                Reflect reflect = new Reflect(v);
                boolean hasGetWhen = reflect.checkMethodExist("getWhen");
                if (hasGetWhen) {
                    When when= (When) reflect.invoke("getWhen");
                    if (when!= null) {
                        List<WhenItem> whenItems = when.getItems();
                        if (whenItems != null && !whenItems.isEmpty()) {
                            for (WhenItem item : whenItems) {
                                if (needValidateWhenRules(context, item)) {
                                    Object body = item.getBody();
                                    if (body instanceof List<?>) {
                                        for (Object bv : (List<?>) body) {
                                            rules.addAll(getRulesForUpdate(bv));
                                        }
                                    } else {
                                        rules.addAll(getRulesForUpdate(body));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return rules;
    }

    // 获取更新请求资源规则
    @SuppressWarnings("unchecked")
    public List<Rule> getRulesForUpdate(Object field) {
        List<Rule> rules = new ArrayList<>();
        Reflect reflect = new Reflect(field);
        boolean hasGetRules = reflect.checkMethodExist("getRules");
        if (hasGetRules) {
            List<Rule> getRules= (List<Rule>) reflect.invoke("getRules");
            if (getRules!= null) {
                rules.addAll(getRules);
            }
        }
        boolean hasGetUpdateRules = reflect.checkMethodExist("getUpdateRules");
        if (hasGetUpdateRules) {
            List<Rule> getUpdateRules= (List<Rule>) reflect.invoke("getUpdateRules");
            if (getUpdateRules!= null) {
                rules.addAll(getUpdateRules);
            }
        }
        return rules;
    }

    // 导入请求的验证器
    public Object validatorForImport(Object data) {
        List<Rule> rules = rulesForImport(context);
        return validator(rules, data);
    }

    // 创建请求的验证规则
    public List<Rule> rulesForImport(Context context) {
        List<Rule> rules = new ArrayList<>();
        ResolveField resolveField = new ResolveField(fields, context);
        Object fields = resolveField.importFieldsWithoutWhen(context);
        if (fields instanceof List<?>) {
            for (Object v : (List<?>) fields) {
                rules.addAll(getRulesForCreation(v));
                Reflect reflect = new Reflect(v);
                boolean hasGetWhen = reflect.checkMethodExist("getWhen");
                if (hasGetWhen) {
                    When when= (When) reflect.invoke("getWhen");
                    if (when!= null) {
                        List<WhenItem> whenItems = when.getItems();
                        if (whenItems != null && !whenItems.isEmpty()) {
                            for (WhenItem item : whenItems) {
                                if (needValidateWhenRules(context, item)) {
                                    Object body = item.getBody();
                                    if (body instanceof List<?>) {
                                        for (Object bv : (List<?>) body) {
                                            rules.addAll(getRulesForCreation(bv));
                                        }
                                    } else {
                                        rules.addAll(getRulesForCreation(body));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return rules;
    }
}
