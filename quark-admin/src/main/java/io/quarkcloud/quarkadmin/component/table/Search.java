package io.quarkcloud.quarkadmin.component.table;

import java.util.List;

import io.quarkcloud.quarkadmin.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Search extends Component {

    /**
     * 过滤表单类型，'query' | 'light'
     */
    public String filterType;

    /**
     * 查询输入框的文本
     */
    public String searchText;

    /**
     * 重置按钮的文本
     */
    public String resetText;

    /**
     * 提交按钮的文本
     */
    public String submitText;

    /**
     * label 宽度，number | 'auto'
     */
    public int labelWidth;

    /**
     * 表单项宽度，number[0 - 24]
     */
    public int span;

    /**
     * 封装的搜索 Form 的 className
     */
    public String className;

    /**
     * 默认状态下是否折叠超出的表单项
     */
    public boolean defaultCollapsed;

    /**
     * 是否显示收起之后显示隐藏个数
     */
    public boolean showHiddenNum;

    /**
     * 导出按钮文字
     */
    public String exportText;

    /**
     * 导出数据接口
     */
    public String exportApi;

    /**
     * 搜索表单项
     */
    public List<Object> items;

    /**
     * 默认构造函数，初始化一些默认值。
     */
    public Search() {
        this.setComponentKey();
        this.component = "search";
        this.defaultCollapsed = true;
        this.resetText = "重置";
        this.searchText = "查询";
    }

    /**
     * 设置搜索表单项。
     * @param item 搜索表单项
     * @return 当前 Search 对象
     */
    public Search setItems(Object item) {
        this.items.add(item);
        return this;
    }
}
