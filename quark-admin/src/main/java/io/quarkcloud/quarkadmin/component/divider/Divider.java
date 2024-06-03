package io.quarkcloud.quarkadmin.component.divider;

import java.util.Arrays;
import java.util.HashMap;

import io.quarkcloud.quarkadmin.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Divider extends Component {

    // 是否虚线
    public boolean dashed;

    // 间距方向,'left', 'right', 'center'
    public String orientation;

    // 文字是否显示为普通正文样式
    public boolean plain;

    // 水平还是垂直类型,horizontal | vertical
    public String type;

    // 容器控件里面的内容
    public Object body;

    public Divider() {
        this.component = "divider";
        this.setComponentKey();
        this.style = new HashMap<>();
        this.type = "horizontal";
        this.orientation = "center";
    }

    // 间距方向,'left', 'right', 'center'
    public Divider setOrientation(String orientation) {

        String[] limits = { "left", "right", "center" };

        boolean inArray = Arrays.stream(limits)
                .anyMatch(limit -> limit.equals(orientation));

        if (!inArray) {
            throw new IllegalArgumentException("Argument must be in 'left', 'right', 'center'!");
        }

        this.orientation = orientation;

        return this;
    }

    // 水平还是垂直类型,horizontal | vertical
    public Divider setType(String dividerType) {

        String[] limits = { "vertical", "horizontal" };

        boolean inArray = Arrays.stream(limits)
                .anyMatch(limit -> limit.equals(dividerType));

        if (!inArray) {
            throw new IllegalArgumentException("Argument must be 'vertical' or 'horizontal'!");
        }

        this.type = dividerType;

        return this;
    }
}
