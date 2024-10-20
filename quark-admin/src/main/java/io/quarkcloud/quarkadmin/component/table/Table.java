package io.quarkcloud.quarkadmin.component.table;

import java.util.ArrayList;
import java.util.HashMap;
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
public class Table  extends Component {
    /**
     * Expandable 类表示表格行可扩展的相关属性配置
     */
    @Data
    public static class Expandable {

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public String childrenColumnName;        // 子列的名称

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public Object columnTitle;               // 自定义的展开列标题

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public Object columnWidth;               // 展开列的宽度

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public boolean defaultExpandAllRows;     // 默认是否展开所有行

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public List<Object> defaultExpandedRowKeys; // 默认展开的行的 key 值列表

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public String expandedRowClassName;      // 展开行的自定义类名

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public List<Object> expandedRowKeys;     // 当前展开的行的 key 值列表

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public Object expandIcon;                // 自定义展开图标

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public boolean expandRowByClick;         // 是否通过点击行来展开

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public Object fixed;                     // 展开列是否固定

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public int indentSize;                   // 缩进大小

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public boolean rowExpandable;            // 行是否可展开

        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public boolean showExpandColumn;         // 是否显示展开列
    }

    /**
     * 行键
     */
    public String rowKey;

    /**
     * 表格数据接口
     */
    public String api;

    /**
     * 表格数据接口类型
     */
    public String apiType;

    /**
     * 表格布局属性
     */
    public String tableLayout;

    /**
     * 表头标题
     */
    public String headerTitle;

    /**
     * 表格列配置
     */
    public Object columns;

    /**
     * 表格行选择配置
     */
    public Object rowSelection;

    /**
     * 表格选项
     */
    public Map<String, Boolean> options;

    /**
     * 搜索区配置
     */
    public Object search;

    /**
     * 批量操作配置
     */
    public Object batchActions;

    /**
     * 日期格式化字符串
     */
    public String dateFormatter;

    /**
     * 列为空时的文本
     */
    public String columnEmptyText;

    /**
     * 工具栏配置
     */
    public Object toolBar;

    /**
     * 树形栏
     */
    public Object treeBar;

    /**
     * 表格额外渲染配置
     */
    public Object tableExtraRender;

    /**
     * 配置展开行
     */
    public Expandable expandable;

    /**
     * 表格滚动配置
     */
    public Object scroll;

    /**
     * 是否显示条纹
     */
    public boolean striped;

    /**
     * 表格数据源
     */
    public Object datasource;

    /**
     * 表格分页配置
     */
    public Object pagination;

    /**
     * 表格轮询间隔
     */
    public int polling;

    /**
     * 默认构造函数，初始化一些默认值。
     */
    public Table() {
        this.component = "table";
        this.rowKey = "id";
        this.apiType = "GET";
        this.columnEmptyText = "-";
        this.dateFormatter = "string";
        this.options = new HashMap<>();
        this.options.put("fullScreen", true);
        this.options.put("reload", true);
        this.options.put("setting", true);
        this.rowSelection = new ArrayList<>();
        this.setComponentKey("table", false);
    }

    /**
     * 设置样式
     */
    public Table setStyle(Map<String, Object> style) {
        this.style = style;
        return this;
    }

    /**
     * 表头标题
     */
    public Table setTitle(String title) {
        this.headerTitle = title;
        return this;
    }

    /**
     * 设置搜索区配置
     */
    public Table setSearches(Object search) {
        this.search = search;
        return this;
    }

    /**
     * 设置表格分页配置
     */
    public Table setPagination(long current, long pageSize, long total, long defaultCurrent) {
        Map<String, Long> pagination = new HashMap<>();
        pagination.put("current", current);
        pagination.put("pageSize", pageSize);
        pagination.put("total", total);
        pagination.put("defaultCurrent", defaultCurrent);
        this.pagination = pagination;
        return this;
    }
}
