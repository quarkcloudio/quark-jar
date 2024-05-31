package io.quarkcloud.quarkadmin.commponent.drawer;

import java.util.HashMap;
import java.util.List;

import io.quarkcloud.quarkadmin.commponent.Commponent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Drawer extends Commponent {

    // 标题
    public String title;

    // Modal body 样式
    public Object bodyStyle;

    // 是否显示右上角的关闭按钮
    public boolean closable;

    // 可用于设置 Drawer 包裹内容部分的样式
    public Object contentWrapperStyle;

    // 关闭时销毁 Modal 里的子元素
    public boolean destroyOnClose;

    // 用于设置 Drawer 弹出层的样式
    public Object drawerStyle;

    // 抽屉页脚部件的样式
    public Object footerStyle;

    // 高度, 在 placement 为 top 或 bottom 时使用
    public int height;

    // 是否支持键盘 esc 关闭
    public boolean keyboard;

    // 是否展示遮罩
    public boolean mask;

    // 点击蒙层是否允许关闭
    public boolean maskClosable;

    // 遮罩样式
    public Object maskStyle;

    // 对话框是否可见
    public boolean open;

    // 宽度
    public int width;

    // 设置 Modal 的 z-index
    public int zIndex;

    // 弹窗行为
    public List<Object> actions;

    // 抽屉的方向,top | right | bottom | left
    public String placement;

    // 容器控件里面的内容
    public Object body;

    public Drawer() {
        this.component = "drawer";
        this.setComponentKey();
        this.closable = true;
        this.footerStyle = new HashMap<>() {
            {
                put("textAlign", "right");
            }
        };
        this.height = 256;
        this.keyboard = true;
        this.mask = true;
        this.maskClosable = true;
        this.placement = "right";
        this.width = 256;
        this.zIndex = 1000;
    }
}
