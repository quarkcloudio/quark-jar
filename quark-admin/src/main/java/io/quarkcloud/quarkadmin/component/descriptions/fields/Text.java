package io.quarkcloud.quarkadmin.component.descriptions.fields;

import java.util.HashMap;

import io.quarkcloud.quarkadmin.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Text extends Component {

    // 内容的描述
    public String label;

    // 内容的补充描述，hover 后显示
    public String tooltip;

    // 是否自动缩略
    public boolean ellipsis;

    // 是否支持复制
    public boolean copyable;

    // 列数
    public int span;

    // 值类型
    public String valueType;

    // 值枚举
    public String valueEnum;

    // 索引
    public String dataIndex;

    // 保存值
    public Object value;

    public Text() {
        this.component = "text";
        this.setComponentKey();
        this.style = new HashMap<>();
        this.valueType = "text";
    }

}
