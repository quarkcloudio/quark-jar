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
public class SubMenu extends Component {

    // 是否禁用
    private boolean disabled;

    // 菜单图标
    private String icon;

    // 子菜单样式，mode="inline" 时无效
    private String popupClassName;

    // 子菜单偏移量，mode="inline" 时无效
    private Object popupOffset;

    // 子菜单项值
    private String title;

    // 子菜单项
    private Object items;

    // 初始化
    public SubMenu() {
        this.component = "menuSubMenu";
        this.setComponentKey();
    }
}
