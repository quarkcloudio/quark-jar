package io.quarkcloud.quarkadmin.component.tabs;

import java.util.Map;

import io.quarkcloud.quarkadmin.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class TabPane extends Component {

    /** 标签标题 */
    private String title;
    
    /** 内容 */
    private Object body;

    // 初始化
    public TabPane init() {
        this.title = "";
        this.body = null;
        this.setComponentKey();
        return this;
    }

    // 设置样式
    public TabPane setStyle(Map<String, Object> style) {
        this.style = style;
        return this;
    }
}
