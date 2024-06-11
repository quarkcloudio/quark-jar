package io.quarkcloud.quarkadmin.component.grid;

import java.util.HashMap;
import java.util.Map;

import io.quarkcloud.quarkadmin.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Col extends Component {

    // 布局属性
    public String flex;

    // 栅格左侧的间隔格数，间隔内不可以有栅格
    public int offset;

    // 栅格顺序
    public int order;

    // 栅格向左移动格数
    public int pull;

    // 栅格向右移动格数
    public int push;

    // 栅格占位格数，为 0 时相当于 display: none
    public int span;

    // 屏幕 < 576px 响应式栅格，可为栅格数或一个包含其他属性的对象
    public Object xs;

    // 屏幕 ≥ 576px 响应式栅格，可为栅格数或一个包含其他属性的对象
    public Object sm;

    // 屏幕 ≥ 768px 响应式栅格，可为栅格数或一个包含其他属性的对象
    public Object md;

    // 屏幕 ≥ 992px 响应式栅格，可为栅格数或一个包含其他属性的对象
    public Object lg;

    // 屏幕 ≥ 1200px 响应式栅格，可为栅格数或一个包含其他属性的对象
    public Object xl;

    // 屏幕 ≥ 1600px 响应式栅格，可为栅格数或一个包含其他属性的对象
    public Object xxl;

    // 卡牌内容
    public Object body;

    public Col() {
        this.component = "col";
        this.setComponentKey();
        this.style = new HashMap<>();
        this.offset = 0;
        this.order = 0;
        this.pull = 0;
        this.push = 0;
    }

    // 组件Style
    public Col setStyle(Map<String, Object> style) {
        this.style = style;

        return this;
    }
}
