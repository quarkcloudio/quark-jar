package io.quarkcloud.quarkadmin.component.table;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.quarkcloud.quarkadmin.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Column extends Component {
    /**
     * 列头显示文字，即字段的列名
     */
    public String title;

    /**
     * 字段名称，即字段的列名
     */
    public String attribute;

    /**
     * 列的对齐方式，left | right | center
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String align;

    /**
     * 数据索引，通常与字段名称相同
     */
    public String dataIndex;

    /**
     * 是否固定列，可选 true (等效于 left) left right
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Object fixed;

    /**
     * 鼠标悬停时的提示信息
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String tooltip;

    /**
     * 是否自动缩略
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean ellipsis;

    /**
     * 是否支持复制
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean copyable;

    /**
     * 值的枚举，根据枚举值自动显示对应内容
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Object valueEnum;

    /**
     * 值的类型，如 "money" | "option" | "date" | "dateTime" 等
     */
    public String valueType;

    /**
     * 是否在查询表单中隐藏该列
     */
    public boolean hideInSearch;

    /**
     * 是否在表格中隐藏该列
     */
    public boolean hideInTable;

    /**
     * 是否在表单中隐藏该字段
     */
    public boolean hideInForm;

    /**
     * 表头的筛选菜单项
     */
    public Object filters;

    /**
     * 查询表单中的权重，权重大的列排序靠前
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int order;

    /**
     * 是否可排序
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Object sorter;

    /**
     * 合并列数
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int span;

    /**
     * 列宽度
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int width;

    /**
     * 可编辑的配置，如下拉选项等
     */
    public Object editable;

    /**
     * 数据操作列配置
     */
    public Object actions;

    /**
     * 传递给 Form.Item 的配置
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Object formItemProps;

    /**
     * 传递给渲染组件的 props 配置
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Object fieldProps;

    /**
     * 默认构造函数，初始化一些默认值。
     */
    public Column() {
        this.setComponentKey();
        this.component = "column";
        this.align = "left";
        this.editable = false;
        this.actions = false;
        this.filters = false;
        this.hideInSearch = true;
    }

    /**
     * 设置字段名称，即字段的列名，并同时设置其他相关属性。
     * @param attribute 字段名称
     * @return 当前列对象
     */
    public Column setAttribute(String attribute) {
        this.componentKey = attribute;
        this.dataIndex = attribute;
        this.attribute = attribute;
        return this;
    }

    /**
     * 设置为可编辑列，并指定编辑的类型、选项和动作。
     * @param name 编辑类型名称
     * @param options 编辑选项
     * @param action 编辑动作
     * @return 当前列对象
     */
    public Column setEditable(String name, List<Object> options, String action) {
        this.editable = Map.of(
            "name", name,
            "options", options,
            "action", action
        );
        return this;
    }
}
