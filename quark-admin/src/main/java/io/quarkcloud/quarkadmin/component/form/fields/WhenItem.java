package io.quarkcloud.quarkadmin.component.form.fields;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WhenItem {

    // 组件名称
	String component;

    // 条件：js表达式语句
	String condition;
    
    // 需要对比的字段名称
	String conditionName;
    
    // 操作符，= <>
	String conditionOperator;

    // 条件符合的属性值
	Object option;

    // 内容
	Object body;

    public WhenItem() {
        this.component = "whenItem";
    }
}
