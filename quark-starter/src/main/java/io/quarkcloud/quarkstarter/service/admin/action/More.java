package io.quarkcloud.quarkstarter.service.admin.action;

import java.util.List;
import java.util.Map;

import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.action.DropdownImpl;

public class More<M, T> extends DropdownImpl<ResourceMapper<T>, T> {

    // 构造函数 
    public More() {

        this.name = "更多";

        // 下拉框箭头是否显示
        this.arrow = true;

        // 菜单弹出位置：bottomLeft bottomCenter bottomRight topLeft topCenter topRight
        this.placement = "bottomLeft";

        // 触发下拉的行为, 移动端不支持 hover,Array<click|hover|contextMenu>
        this.trigger = List.of("hover");

        // 下拉根元素的样式
        this.overlayStyle = Map.of("zIndex",999);

        // 设置按钮类型,primary | ghost | dashed | link | text | default
        this.type = "link";

        // 设置按钮大小,large | middle | small | default
        this.size = "small";

        // 设置展示位置
        this.setOnlyOnIndexTableRow(true);
    }
}
