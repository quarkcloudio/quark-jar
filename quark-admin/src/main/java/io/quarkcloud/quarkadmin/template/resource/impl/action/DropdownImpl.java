package io.quarkcloud.quarkadmin.template.resource.impl.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.template.resource.Action;
import io.quarkcloud.quarkadmin.template.resource.core.ResolveAction;
import io.quarkcloud.quarkadmin.template.resource.impl.ActionImpl;
import io.quarkcloud.quarkcore.service.Context;

public class DropdownImpl<M, T> extends ActionImpl<ResourceMapper<T>, T> {

    /**
     * 下拉框箭头是否显示
     */
    public boolean arrow;

    /**
     * 菜单弹出位置：bottomLeft bottomCenter bottomRight topLeft topCenter topRight
     */
    public String placement;

    /**
     * 触发下拉的行为，移动端不支持 hover，Array<click|hover|contextMenu>
     */
    public List<String> trigger;

    /**
     * 下拉根元素的样式
     */
    public Map<String, Object> overlayStyle;

    /**
     * 下拉菜单行为
     */
    public List<Action<T>> actions;

    // 初始化
    public DropdownImpl() {
        this.actionType = "dropdown";
        this.placement = "bottomLeft";
        this.trigger = new ArrayList<String>();
        this.overlayStyle = new java.util.HashMap<String, Object>();
    }

    /**
     * 获取下拉框箭头是否显示
     * @return 是否显示箭头
     */
    public boolean getArrow() {
        return arrow;
    }

    /**
     * 获取菜单弹出位置
     * @return 菜单弹出位置
     */
    public String getPlacement() {
        return placement;
    }

    /**
     * 获取触发下拉的行为
     * @return 触发下拉的行为列表
     */
    public List<String> getTrigger() {
        return trigger;
    }

    /**
     * 获取下拉根元素的样式
     * @return 下拉根元素的样式
     */
    public Map<String, Object> getOverlayStyle() {
        return overlayStyle;
    }

    /**
     * 设置下拉菜单行为
     * @param actions 下拉菜单行为列表
     * @return Dropdown 实例
     */
    public DropdownImpl<M, T> setActions(List<Action<T>> actions) {
        this.actions = actions;
        return this;
    }

    /**
     * 获取下拉菜单行为
     * @return 下拉菜单行为列表
     */
    public List<Action<T>> getActions() {
        return actions;
    }

    /**
     * 获取菜单
     * @param context 上下文对象
     * @return 菜单组件
     */
    public Object getMenu(Context context) {
        List<Object> items = new ArrayList<>();
        ResolveAction<ResourceMapper<T>, T> resolveAction = new ResolveAction<ResourceMapper<T>, T>();

        // 获取行为
        List<Action<T>> actions = getActions();

        // 解析行为
        for (Action<T> v : actions) {
            items.add(resolveAction.buildAction(context, v));
        }

        return new io.quarkcloud.quarkadmin.component.menu.Menu().setItems(items);
    }
}
