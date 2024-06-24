package io.quarkcloud.quarkadmin.template.resource.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkadmin.annotation.AdminResource;
import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.component.form.fields.Cascader;
import io.quarkcloud.quarkadmin.component.form.fields.Radio;
import io.quarkcloud.quarkadmin.component.form.fields.SelectField;
import io.quarkcloud.quarkadmin.component.form.fields.TreeSelect;
import io.quarkcloud.quarkadmin.component.pagecontainer.PageContainer;
import io.quarkcloud.quarkadmin.component.pagecontainer.PageHeader;
import io.quarkcloud.quarkadmin.component.table.Search;
import io.quarkcloud.quarkadmin.component.table.Table;
import io.quarkcloud.quarkadmin.component.table.ToolBar;
import io.quarkcloud.quarkadmin.template.resource.Action;
import io.quarkcloud.quarkadmin.template.resource.Resource;
import io.quarkcloud.quarkadmin.template.resource.core.ResolveAction;

public class ResourceImpl implements Resource {

    // 注解实例
    protected AdminResource annotationClass = null;

    // 页面标题
    public String title;
    
    // 页面子标题
    public String subTitle;
    
    // 页面是否携带返回Icon
    public boolean backIcon;
    
    // 列表页分页配置
    public Object perPage;
    
    // 表单页Form实例
    public Object form;
    
    // 列表页Table实例
    public Object table;
    
    // 列表页表格标题后缀
    public String tableTitleSuffix;
    
    // 列表页表格行为列显示文字，既字段的列名
    public String tableActionColumnTitle;
    
    // 列表页表格行为列的宽度
    public int tableActionColumnWidth;
    
    // 列表页表格是否轮询数据
    public int tablePolling;
    
    // 全局排序规则
    public String queryOrder;
    
    // 列表页排序规则
    public String indexQueryOrder;
    
    // 导出数据排序规则
    public String exportQueryOrder;
    
    // 挂载模型
    public Object model;
    
    // 注入的字段数据
    public Map<String, Object> field;
    
    // 是否具有导出功能
    public boolean withExport;

    // 构造函数
    public ResourceImpl() {

        // 获取注解对象
        if (getClass().isAnnotationPresent(AdminResource.class)) {
            annotationClass = getClass().getAnnotation(AdminResource.class);
        }
    }

    // 获取标题
    public String getTitle() {
        if (annotationClass == null || annotationClass.title().isEmpty()) {
            return title;
        }
        return annotationClass.title();
    }

    // 获取子标题
    public String getSubTitle() {
        if (annotationClass == null || annotationClass.subTitle().isEmpty()) {
            return subTitle;
        }
        return annotationClass.subTitle();
    }

    // 获取是否携带返回Icon
    public boolean isBackIcon() {
        if (annotationClass == null) {
            return backIcon;
        }
        return annotationClass.backIcon();
    }

    // 获取列表页分页配置
    public Object getPerPage() {
        if (annotationClass == null || annotationClass.perPage() == 0) {
            return perPage;
        }
        return annotationClass.perPage();
    }

    // 获取表格标题后缀
    public String getTableTitleSuffix() {
        if (annotationClass == null || annotationClass.tableTitleSuffix().isEmpty()) {
            return tableTitleSuffix;
        }
        return annotationClass.tableTitleSuffix();
    }

    // 获取表格行为列显示文字
    public String getTableActionColumnTitle() {
        if (annotationClass == null || annotationClass.tableActionColumnTitle().isEmpty()) {
            return tableActionColumnTitle;
        }
        return annotationClass.tableActionColumnTitle();
    }

    // 获取表格行为列的宽度
    public int getTableActionColumnWidth() {
        if (annotationClass == null || annotationClass.tableActionColumnWidth() == 0) {
            return tableActionColumnWidth;
        }
        return annotationClass.tableActionColumnWidth();
    }

    // 获取表格是否轮询数据
    public int getTablePolling() {
        if (annotationClass == null || annotationClass.tablePolling() == 0) {
            return tablePolling;
        }
        return annotationClass.tablePolling();
    }

    // 获取全局排序规则
    public String getQueryOrder() {
        if (annotationClass == null || annotationClass.queryOrder().isEmpty()) {
            return queryOrder;
        }
        return annotationClass.queryOrder();
    }

    // 获取列表页排序规则
    public String getIndexQueryOrder() {
        if (annotationClass == null || annotationClass.indexQueryOrder().isEmpty()) {
            return indexQueryOrder;
        }
        return annotationClass.indexQueryOrder();
    }

    // 获取导出数据排序规则
    public String getExportQueryOrder() {
        if (annotationClass == null || annotationClass.exportQueryOrder().isEmpty()) {
            return exportQueryOrder;
        }
        return annotationClass.exportQueryOrder();
    }

    // 获取是否具有导出功能
    public boolean isWithExport() {
        if (annotationClass == null) {
            return withExport;
        }
        return annotationClass.withExport();
    }

    // 设置单列字段
    public ResourceImpl setField(Map<String, Object> field) {
        this.field = field;
        return this;
    }

    // 字段
    public List<Object> fields(Context ctx) {
        return null;
    }

    // 搜索
    public List<Object> searches(Context ctx) {
        return null;
    }

    // 行为
    public List<Action> actions(Context ctx) {
        return null;
    }

    // 菜单
    public Map<String, Object> menus(Context ctx) {
        return null;
    }

    // 数据导出前回调
    public List<Object> beforeExporting(Context ctx, List<Map<String, Object>> list) {
        List<Object> result = new ArrayList<>();
        for (Map<String, Object> v : list) {
            result.add(v);
        }
        return result;
    }

    // 数据导入前回调
    public List<List<Object>> beforeImporting(Context ctx, List<List<Object>> list) {
        return list;
    }

    // 表格行内编辑执行完之后回调
    public Object afterEditable(Context ctx, Object id, String field, Object value) {
        return null;
    }

    // 行为执行完之后回调
    public Object afterAction(Context ctx, String uriKey, Object query) {
        return null;
    }

    // 页面组件渲染
    public Object pageComponentRender(Context ctx, Object body) {

        // 页面容器组件渲染
        return this.pageContainerComponentRender(ctx, body);
    }

    // 页面容器组件渲染
    public Object pageContainerComponentRender(Context ctx, Object body) {

        // 页面标题
        String title = this.getTitle();

        // 页面子标题
        String subTitle = this.getSubTitle();

        // 页面是否携带返回Icon
        boolean backIcon = this.isBackIcon();

        // 设置头部
        PageHeader header = new PageHeader()
                .setTitle(title)
                .setSubTitle(subTitle);

        if (!backIcon) {
            header.setBackIcon(false);
        }

        return new PageContainer()
                .setHeader(header)
                .setBody(body);
    }

    // 列表页表格主体
    public Object indexTableExtraRender(Context ctx) {
        return null;
    }

    // 列表页工具栏
    public Object indexTableToolBar(Context ctx) {
        Object tableActions = new ResolveAction(actions(ctx), ctx).getIndexTableActions();

        return new ToolBar().
            setTitle(indexTableTitle(ctx)).
            setActions(tableActions);
    }

    // 列表页表格列
    public Object indexTableColumns(Context ctx) {
        return null;
    }

    // 列表页批量操作
    public Object indexTableAlertActions(Context ctx) {
        return null;
    }

    // 列表页搜索栏
    public Object indexSearches(Context ctx) {

        // 获取搜索项
        List<Object> searches = this.searches(ctx);

        // 初始化搜索组件
        Search search = new Search();

        // 是否包含导出功能
        boolean withExport = this.isWithExport();
        if (withExport) {
            String resource = ctx.getPathVariable("resource");
            String[] paths = ctx.getRequestMapping();
            for (String path : paths) {
                search.setExportText("导出").setExportApi(path.replace(":resource", resource));
            }
        }

        if (searches == null) {
            return search;
        }

        // 解析搜索项
        for (Object v : searches) {

            // 搜索项实例
            Object field = new Object();

            // 获取搜索实例
            io.quarkcloud.quarkadmin.template.resource.impl.search.SearchImpl searcher = (io.quarkcloud.quarkadmin.template.resource.impl.search.SearchImpl) v;

            // 获取组件名称
            String component = searcher.getComponent();

            // 获取label标签文本
            String label = searcher.getName();

            // 获取字段名，支持数组
            String name = searcher.getColumn(v);

            // 获取接口
            String api = searcher.getApi();

            // 获取属性
            Object options = searcher.options(ctx);

            // 根据组件类型构建组件
            switch (component) {
                case "textField":
                    field = Field.
                        text(name, label).
                        setWidth(null);
                    break;
                case "selectField":
                    field = Field.
                        select(name, label).
                        setWidth(null).
                        setOptions((List<SelectField.Option>) options);
                    break;
                case "radioField":
                    field = Field.
                        radio(name, label).
                        setOptions((List<Radio.Option>) options).
                        setOptionType("button").
                        setButtonStyle("solid");
                    break;
                case "multipleSelectField":
                    field = Field.
                        select(name, label).
                        setMode("multiple").
                        setWidth(null).
                        setOptions((List<SelectField.Option>) options);
                    break;
                case "dateField":
                    field = Field.
                        date(name, label).
                        setWidth(null);
                    break;
                case "datetimeField":
                    field = Field.
                        datetime(name, label).
                        setWidth(null);
                    break;
                case "dateRangeField":
                    field = Field.
                        dateRange(name, label).
                        setWidth(null);
                    break;
                case "datetimeRangeField":
                    field = Field.
                        datetimeRange(name, label).
                        setWidth(null);
                    break;
                case "cascaderField":
                    field = Field.
                        cascader(name, label).
                        setApi(api).
                        setWidth(null).
                        setOptions((List<Cascader.Option>) options);
                    break;
                case "treeSelectField":
                    field = Field.
                        treeSelect(name, label).
                        setWidth(null).
                        setTreeData((List<TreeSelect.TreeData>) options);
                    break;
            }

            if (field != null) {
                search.setItems(field);
            }
        }

        return search;
    }

    // 列表页标题
    public String indexTableTitle(Context ctx) {

        // 返回拼接后的标题
        return this.getTitle() + this.getTableTitleSuffix();
    }

    // 列表页组件渲染
    public Object indexComponentRender(Context ctx, Object data) {
        Object component;

        // 表格组件
        Table table = new Table();

        // 列表标题
        String tableTitle = indexTableTitle(ctx);

        // 列表页轮询数据
        int tablePolling = getTablePolling();

        // 列表页表格主体
        Object tableExtraRender = indexTableExtraRender(ctx);

        // 列表页工具栏
        Object tableToolBar = indexTableToolBar(ctx);

        // 列表页表格列
        Object tableColumns = indexTableColumns(ctx);

        // 列表页批量操作
        Object indexTableAlertActions = indexTableAlertActions(ctx);

        // 列表页搜索栏
        Object indexSearches = indexSearches(ctx);

        // 表格组件
        table.setPolling(tablePolling)
             .setTitle(tableTitle)
             .setTableExtraRender(tableExtraRender)
             .setToolBar(tableToolBar)
             .setColumns(tableColumns)
             .setBatchActions(indexTableAlertActions)
             .setSearches(indexSearches);

        // 获取分页
        Object perPage = this.getPerPage();
        if (perPage == null) {
            return table.setDatasource(data);
        }

        // 不分页，直接返回数据
        if (!(perPage instanceof Integer)) {
            return table.setDatasource(data);
        } else {
            Map<String, Object> dataMap = (Map<String, Object>) data;
            int current = (int) dataMap.get("currentPage");
            int perPageValue = (int) dataMap.get("perPage");
            long total = (long) dataMap.get("total");
            Object items = dataMap.get("items");

            component = table.setPagination(current, perPageValue, (int) total, 1).setDatasource(items);
        }

        return component;
    }

    // 组件渲染
    public Object indexRender(Context context) {
        Object body = indexComponentRender(context, null);
        return this.pageComponentRender(context, body);
    }
}
