package io.quarkcloud.quarkadmin.commponent.form;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Rule {

    // 需要验证的字段名称
	String name;

    // 规则类型，max | min | unique | required
	String ruleType;

    // 仅在 type 为 array 类型时有效，用于指定数组元素的校验规
	Object defaultField;

    // 是否匹配枚举中的值（需要将 type 设置为 enum）
	Object[] Enum;
    
    // 仅在 type 为 array 或 object 类型时有效，用于指定子元素的校验规则
	Object[] fields;
    
    // string 类型时为字符串长度；number 类型时为确定数字； array 类型时为数组长度
	int len;
    
    // 必须设置 type：string 类型为字符串最大长度；number 类型时为最大值；array 类型时为数组最大长度
	int max;
    
    // 错误信息，不设置时会通过模板自动生成
	String message;

    // 必须设置 type：string 类型为字符串最小长度；number 类型时为最小值；array 类型时为数组最小长度
	int min;

    // 正则表达式匹配
	String pattern;

    // 是否为必选字段
	boolean required;

    // type：unique时，指定验证的表名
	String uniqueTable;

    // type：unique时，指定需验证表中的字段
	String uniqueTableField;

    // type：unique时，忽略符合条件验证的列，例如：{id}
	String uniqueIgnoreValue;

    // 字段类型，string | number | boolean | method | regexp | integer | float | array | object | enum | date | url | hex | email | any
	String type;

    // 转换前端验证规则，剔除前端不支持的unique
    public static Rule[] convertToFrontendRules(Rule[] rules) {
        Rule[] newRules = new Rule[rules.length+1];
        for (int i = 0; i < rules.length; i++) {
            if (rules[i].ruleType != "unique") {
                newRules[i] = rules[i];
            }
        }

        return newRules;
    }

    // 必须设置 type：string 类型；为字符串最大长度；number 类型时为最大值；array 类型时为数组最大长度
    public static Rule max(int max, String message) {
        Rule rule = new Rule();

        return rule.setMax(max).setMessage(message);
    }

    // 必须设置 type：string 类型为字符串最小长度；number 类型时为最小值；array 类型时为数组最小长度
    public static Rule min(int min, String message) {
        Rule rule = new Rule();

        return rule.setMin(min).setMessage(message);
    }

    // 正则表达式匹配
    public static Rule regexp(String pattern, String message) {
        Rule rule = new Rule();

        return rule.setRegexp(pattern).setMessage(message);
    }

    // 必须为字符串
    public static Rule string(String message) {
        Rule rule = new Rule();

        return rule.setString().setMessage(message);
    }

    // 必须为数字
    public static Rule number(String message) {
        Rule rule = new Rule();

        return rule.setNumber().setMessage(message);
    }

    // 必须为布尔类型
    public static Rule booleanField(String message) {
        Rule rule = new Rule();

        return rule.setBoolean().setMessage(message);
    }

    // 必须为整型
    public static Rule integer(String message) {
        Rule rule = new Rule();

        return rule.setInteger().setMessage(message);
    }

    // 必须为浮点型
    public static Rule floatField(String message) {
        Rule rule = new Rule();

        return rule.setFloat().setMessage(message);
    }

    // 必须为邮箱字段
    public static Rule email(String message) {
        Rule rule = new Rule();

        return rule.setEmail().setMessage(message);
    }

    // 必须为链接
    public static Rule url(String message) {
        Rule rule = new Rule();

        return rule.setUrl().setMessage(message);
    }

    // 必须为手机号
    public static Rule phone(String message) {
        Rule rule = new Rule();

        return rule.setPhone().setMessage(message);
    }

    // 是否为必选字段
    public static Rule required(boolean required, String message) {
        Rule rule = new Rule();

        return rule.setRequired().setMessage(message);
    }

    // 设置unique验证类型，插入数据时：Unique("admins", "username", "用户名已存在")，更新数据时：Unique("admins", "username", "{id}", "用户名已存在")
    public static Rule unique(String uniqueTable, String uniqueTableField, String message) {
        Rule rule = new Rule();
        rule.setUnique(uniqueTable, uniqueTableField);
        rule.setMessage(message);

        return rule;
    }

    // 设置unique验证类型，插入数据时：Unique("admins", "username", "用户名已存在")，更新数据时：Unique("admins", "username", "{id}", "用户名已存在")
    public static Rule unique(String uniqueTable, String uniqueTableField, String uniqueIgnoreValue, String message) {
        Rule rule = new Rule();
        rule.setUnique(uniqueTable, uniqueTableField, uniqueIgnoreValue);
        rule.setMessage(message);

        return rule;
    }

    // 需要验证的字段名称
    public Rule setName(String name) {
        this.name = name;

        return this;
    }

    // 必须设置 type：string 类型；为字符串最大长度；number 类型时为最大值；array 类型时为数组最大长度
    public Rule setMax(int max) {
        this.max = max;

        return this.setRuleType("max");
    }

    // 错误信息，不设置时会通过模板自动生成
    public Rule setMessage(String message) {
        this.message = message;

        return this;
    }

    // 必须设置 type：string 类型为字符串最小长度；number 类型时为最小值；array 类型时为数组最小长度
    public Rule setMin(int min) {
        this.min = min;

        return this.setRuleType("min");
    }

    // 正则表达式匹配
    public Rule setRegexp(String pattern) {
        this.pattern = pattern;

        return this.setRuleType("regexp");
    }

    // 是否为必选字段
    public Rule setRequired() {
        this.required = true;

        return this.setRuleType("required");
    }

    // 必须为字符串
    public Rule setString() {
        this.type = "string";

        return this.setRuleType("string");
    }

    // 必须为数字
    public Rule setNumber() {
        this.type = "number";

        return this.setRuleType("number");
    }

    // 必须为布尔类型
    public Rule setBoolean() {
        this.type = "boolean";

        return this.setRuleType("boolean");
    }

    // 必须为整型
    public Rule setInteger() {
        this.type = "integer";

        return this.setRuleType("integer");
    }

    // 必须为浮点型
    public Rule setFloat() {
        this.type = "float";

        return this.setRuleType("float");
    }

    // 必须为邮箱字段
    public Rule setEmail() {
        this.type = "email";

        return this.setRuleType("email");
    }

    // 必须为链接
    public Rule setUrl() {
        this.type = "url";

        return this.setRuleType("url");
    }

    // 必须为手机号
    public Rule setPhone() {

        return this.setRegexp("/^1[3-9]\\d{9}$/");
    }

    // 设置unique验证类型，插入数据：setUnique("admins","username")，更新数据：setUnique("admins","username","{id}")
    public Rule setUnique(String uniqueTable, String uniqueTableField) {
        this.type = "unique";
        this.uniqueTable = uniqueTable;
        this.uniqueTableField = uniqueTableField;

        return this.setRuleType("unique");
    }

    // 设置unique验证类型，插入数据：setUnique("admins","username")，更新数据：setUnique("admins","username","{id}")
    public Rule setUnique(String uniqueTable, String uniqueTableField, String uniqueIgnoreValue) {
        this.type = "unique";
        this.uniqueTable = uniqueTable;
        this.uniqueTableField = uniqueTableField;
        this.uniqueIgnoreValue = uniqueIgnoreValue;

        return this.setRuleType("unique");
    }

    // type：unique时，指定验证的表名
    public Rule setUniqueTable(String uniqueTable) {
        this.uniqueTable = uniqueTable;

        return this;
    }

    // type：unique时，指定验证的表名
    public Rule setUniqueTableField(String uniqueTableField) {
        this.uniqueTableField = uniqueTableField;

        return this;
    }

    // type：unique时，忽略符合条件验证的列，例如：{id}
    public Rule setUniqueIgnoreValue(String uniqueIgnoreValue) {
        this.uniqueIgnoreValue = uniqueIgnoreValue;

        return this;
    }

    // 字段类型，string | number | boolean | url | email
    public Rule setType(String ruleType) {
        this.type = ruleType;

        return this;
    }

    // 规则类型，max | min | unique | required
    public Rule setRuleType(String ruleType) {
        this.ruleType = ruleType;

        return this;
    }

}
