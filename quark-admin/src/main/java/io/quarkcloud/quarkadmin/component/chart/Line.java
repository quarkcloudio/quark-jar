package io.quarkcloud.quarkadmin.component.chart;

import java.util.HashMap;
import java.util.Map;

import io.quarkcloud.quarkadmin.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Line extends Component {
    
    // 数据接口
    public String api;

    // 设置图表宽度
    public int width;

    // 设置图表高度
    public int height;

    // 图表是否自适应容器宽高。当 autoFit 设置为 true 时，width 和 height 的设置将失效。
    public boolean autoFit;

    // 画布的 padding 值，代表图表在上右下左的间距，可以为单个数字 16，或者数组 [16, 8, 16, 8] 代表四个方向，或者开启 auto，由底层自动计算间距。
    public Object padding;

    // 额外增加的 appendPadding 值，在 padding 的基础上，设置额外的 padding 数值，可以是单个数字 16，或者数组 [16, 8, 16, 8] 代表四个方向。
    public Object appendPadding;

    // 设置图表渲染方式为 canvas 或 svg。
    public String renderer;

    // 是否对超出坐标系范围的 Geometry 进行剪切。
    public boolean limitInPlot;

    // 指定具体语言，目前内置 'zh-CN' and 'en-US' 两个语言，你也可以使用 G2Plot.registerLocale 方法注册新的语言。语言包格式参考：src/locales/en_US.ts
    public String locale;

    // 数据
    public Object data;

    // X轴字段
    public String xField;

    // y轴字段
    public String yField;

    // 通过 meta 可以全局化配置图表数据元信息，以字段为单位进行配置。在 meta 上的配置将同时影响所有组件的文本信息。传入以字段名为 key，MetaOption 为 value 的配置，同时设置多个字段的元信息。
    public Object meta;

    // 是否平滑
    public boolean smooth;

    public Line() {
        this.component = "line";
        this.setComponentKey();
        this.style = new HashMap<>();
    }

    // 折线图表
    public Line newLine(Object data) {
        this.data = data;
        return this;
    }

    // 组件Style
    public Line setStyle(Map<String, Object> style) {
        this.style = style;
        return this;
    }
}
