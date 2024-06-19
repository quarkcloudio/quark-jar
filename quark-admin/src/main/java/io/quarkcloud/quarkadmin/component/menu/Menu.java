package io.quarkcloud.quarkadmin.component.menu;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.quarkcloud.quarkadmin.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Menu extends Component {

    /**
     * 组件类型
     */
    private String component;

    /**
     * 子菜单初始展开的 key 数组
     */
    private Object defaultOpenKeys;

    /**
     * 菜单项初始选中的 key 数组
     */
    private Object defaultSelectedKeys;

    /**
     * 内联菜单是否收起状态
     */
    private boolean inlineCollapsed;

    /**
     * 内联菜单项的缩进宽度
     */
    private int inlineIndent;

    /**
     * 菜单模式，可以是 vertical、horizontal 或 inline
     */
    private String mode;

    /**
     * 是否允许多选菜单项
     */
    private boolean multiple;

    /**
     * 菜单项是否可选中
     */
    private boolean selectable;

    /**
     * 用户鼠标离开子菜单后关闭延时（秒）
     */
    private double subMenuCloseDelay;

    /**
     * 用户鼠标进入子菜单后打开延时（秒）
     */
    private double subMenuOpenDelay;

    /**
     * 菜单主题颜色，可以是 light 或 dark
     */
    private String theme;

    /**
     * 子菜单展开/关闭的触发行为，可以是 hover 或 click
     */
    private String triggerSubMenuAction;

    /**
     * 菜单项
     */
    private Object items;

    // 初始化组件
    public Menu() {
        this.component = "menu";
        this.inlineIndent = 24;
        this.mode = "vertical";
        this.selectable = true;
        this.subMenuCloseDelay = 0.1;
        this.subMenuOpenDelay = 0;
        this.theme = "light";
        this.triggerSubMenuAction = "hover";
        this.setComponentKey();
    }
}
