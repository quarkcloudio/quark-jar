package io.quarkcloud.quarkadmin.component.modal;

import java.util.List;

import io.quarkcloud.quarkadmin.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Modal extends Component {

    // 标题
    public String title;

    // 垂直居中展示 Modal
    public boolean centered;

    // 是否显示右上角的关闭按钮
    public boolean closable;

    // 关闭时销毁 Modal 里的子元素
    public boolean destroyOnClose;

    // 设置按钮形状，可选值为 circle、 round 或者不设
    public boolean focusTriggerAfterClose;

    // 是否支持键盘 esc 关闭
    public boolean keyboard;

    // 是否展示遮罩
    public boolean mask;

    // 点击蒙层是否允许关闭
    public boolean maskClosable;

    // 对话框是否可见
    public boolean open;

    // 宽度
    public int width;

    // 设置 Modal 的 z-index
    public int zIndex;

    // 弹窗行为
    public List<Object> actions;

    // 容器控件里面的内容
    public Object body;

    public Modal() {
        this.component = "modal";
        this.setComponentKey();
        this.closable = true;
        this.keyboard = true;
        this.mask = true;
        this.maskClosable = true;
        this.width = 520;
        this.zIndex = 1000;
    }
}
