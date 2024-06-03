package io.quarkcloud.quarkadmin.component.lists;

import java.util.HashMap;
import java.util.Map;

import io.quarkcloud.quarkadmin.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Meta extends Component {

    // 列头显示文字，既字段的列名
    public String title;

    // 字段名称|字段的列名
    public String attribute;

    // 字段名称|字段的列名
    public String dataIndex;

    // 是否自动缩略
    public boolean ellipsis;

    // 是否支持复制
    public boolean copyable;

    // 值的枚举，会自动转化把值当成 key 来取出要显示的内容
    public Object valueEnum;

    // 值的类型,"money" | "option" | "date" | "dateTime" | "time" | "text"| "index" |
    // "indexBorder"
    public String valueType;

    // 在查询表单中不展示此项
    public boolean search;

    public boolean actions;

    public Meta() {
        this.component = "meta";
        this.setComponentKey();
        this.style = new HashMap<>();
    }

    // 字段名称|字段的列名
    public Meta setAttribute(String attribute) {

        this.dataIndex = attribute;
        this.attribute = attribute;

        return this;
    }

    // 值的枚举，会自动转化把值当成 key 来取出要显示的内容
    public Meta setValueEnum(Map<Object, Object> valueEnum) {

        Map<String, Object> valueEnumStr = new HashMap<>();
        Map<Integer, Object> valueEnumInt = new HashMap<>();

        valueEnum.forEach((key, value) -> {
            if (key instanceof String) {
                valueEnumStr.put((String) key, value);
            } else if (key instanceof Integer) {
                valueEnumInt.put((Integer) key, value);
            }
        });

        if (!valueEnumStr.isEmpty()) {
            this.valueEnum = valueEnumStr;
        }

        if (!valueEnumInt.isEmpty()) {
            this.valueEnum = valueEnumInt;
        }

        return this;
    }
}
