package io.quarkcloud.quarkadmin.component.statistic;

import java.util.HashMap;
import java.util.Map;

import io.quarkcloud.quarkadmin.component.Component;
import io.quarkcloud.quarkadmin.component.grid.Col;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Statistic extends Component {

    /**
     * 设置小数点
     */
    private String decimalSeparator;

    /**
     * 设置千分位标识符
     */
    private String groupSeparator;

    /**
     * 数值精度
     */
    private int precision;

    /**
     * 设置数值的前缀
     */
    private String prefix;

    /**
     * 设置数值的后缀
     */
    private String suffix;

    /**
     * 设置标题
     */
    private String title;

    /**
     * 数值内容
     */
    private Object value;

    /**
     * 设置数值的样式
     */
    private Map<String, String> valueStyle;

    /**
     * 构造方法
     */
    public Statistic() {
        this.component = "statistic";
        this.setComponentKey();
        this.style = new HashMap<>();
        this.decimalSeparator = ".";
        this.groupSeparator = ",";
    }

    // 组件Style
    public Statistic setStyle(Map<String, Object> style) {
        this.style = style;

        return this;
    }
}
