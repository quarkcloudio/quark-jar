package io.quarkcloud.quarkadmin.component.grid;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.quarkcloud.quarkadmin.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Row extends Component {

    // 垂直对齐方式
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String align;

    // 栅格间隔，可以写成像素值或支持响应式的对象写法来设置水平间隔 { xs: 8, sm: 16, md: 24}。
    // 或者使用数组形式同时设置 [水平间距, 垂直间距]
    public Object gutter;

    // 水平排列方式，start | end | center | space-around | space-between
     @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String justify;

    // 是否自动换行
    public boolean wrap;

    // 设置列
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Col col;

    // 内容
    public Object body;

    public Row() {
        this.component = "row";
        this.setComponentKey();
        this.style = new HashMap<>();
    }

    // 组件Style
    public Row setStyle(Map<String, Object> style) {
        this.style = style;

        return this;
    }
}
