package io.quarkcloud.quarkadmin.component.descriptions;

import java.util.HashMap;

import io.quarkcloud.quarkadmin.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Descriptions extends Component {

    // 标题
    public String title;

    // 内容的补充描述，hover 后显示
    public String tooltip;

    // 描述列表的操作区域，显示在右上方
    public Object extra;

    // 是否展示边框
    public boolean bordered;

    // 一行的 ProDescriptionsItems 数量，可以写成像素值或支持响应式的对象写法 { xs: 8, sm: 16, md: 24}
    public Object column;

    // 设置尺寸
    public String size;

    // 布局，horizontal|vertical
    public String layout;

    // 配置 ProDescriptions.Item 的 colon 的默认值
    public boolean colon;

    // 列
    public Object columns;

    // 数据
    public Object dataSource;

    // 数据项
    public Object items;

    // 行为
    public Object actions;

    public Descriptions() {
        this.component = "descriptions";
        this.setComponentKey();
        this.style = new HashMap<>();
        this.column = 1;
        this.layout = "horizontal";
        this.colon = true;
        this.size = "default";
    }

}
