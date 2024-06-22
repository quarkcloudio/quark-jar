package io.quarkcloud.quarkadmin.component.space;

import java.util.Map;

import io.quarkcloud.quarkadmin.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)

public class Space extends Component {

    // 对齐方式
    private String align;
    
    // 间距方向
    private String direction;
    
    // 间距大小
    private String size;
    
    // 拆分卡片的方向, vertical | horizontal
    private String split;
    
    // 是否自动换行，仅在 horizontal 时有效
    private boolean wrap;
    
    // 容器控件里面的内容
    private Object body;
    
    // 样式
    private Map<String, Object> style;
    
    // 组件类型
    private String component;
    
    // 组件的键
    private String key;

    // 构造函数
    public Space() {
        this.component = "space";
        this.size = "small";
        this.setComponentKey("space");
    }

    // 设置样式
    public Space setStyle(Map<String, Object> style) {
        this.style = style;
        return this;
    }
}
