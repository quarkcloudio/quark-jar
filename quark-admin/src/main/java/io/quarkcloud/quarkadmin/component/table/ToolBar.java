package io.quarkcloud.quarkadmin.component.table;

import java.util.Map;

import io.quarkcloud.quarkadmin.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ToolBar extends Component {

    /**
     * 标题
     */
    public String title;

    /**
     * 子标题
     */
    public String subTitle;

    /**
     * 描述
     */
    public String description;

    /**
     * 查询区配置
     */
    public Object search;

    /**
     * 操作区配置
     */
    public Object actions;

    /**
     * 过滤区配置，通常配合 LightFilter 使用
     */
    public Object filter;

    /**
     * 是否多行展示
     */
    public boolean multipleLine;

    /**
     * 菜单配置
     */
    public Object menu;

    /**
     * 标签页配置，仅当 multipleLine 为 true 时有效
     */
    public Object tabs;

    /**
     * 默认构造函数，初始化一些默认值。
     */
    public ToolBar() {
        this.component = "toolBar";
        this.setComponentKey("toolBar");
    }

    /**
     * 设置样式
     */
    public ToolBar setStyle(Map<String, Object> style) {
        this.style = style;
        return this;
    }

    /**
     * 设置标题
     */
    public ToolBar setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 设置子标题
     */
    public ToolBar setSubTitle(String subTitle) {
        this.subTitle = subTitle;
        return this;
    }

    /**
     * 设置描述
     */
    public ToolBar setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * 设置查询区配置
     */
    public ToolBar setSearch(Object search) {
        this.search = search;
        return this;
    }

    /**
     * 设置操作区配置
     */
    public ToolBar setActions(Object actions) {
        this.actions = actions;
        return this;
    }

    /**
     * 设置过滤区配置
     */
    public ToolBar setFilter(Object filter) {
        this.filter = filter;
        return this;
    }

    /**
     * 设置是否多行展示
     */
    public ToolBar setMultipleLine(boolean multipleLine) {
        this.multipleLine = multipleLine;
        return this;
    }

    /**
     * 设置菜单配置
     */
    public ToolBar setMenu(Object menu) {
        this.menu = menu;
        return this;
    }

    /**
     * 设置标签页配置
     */
    public ToolBar setTabs(Object tabs) {
        this.tabs = tabs;
        return this;
    }
}
