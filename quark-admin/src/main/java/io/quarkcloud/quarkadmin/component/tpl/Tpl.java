package io.quarkcloud.quarkadmin.component.tpl;

import java.util.HashMap;
import java.util.Map;

import io.quarkcloud.quarkadmin.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Tpl extends Component {

    public Object body;

    public Tpl() {
        this.component = "tpl";
        this.setComponentKey(componentKey);
        this.style = new HashMap<>();
    }

    // 组件Style
    public Tpl setStyle(Map<String, Object> style) {
        this.style = style;
        return this;
    }
}
