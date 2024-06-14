package io.quarkcloud.quarkadmin.template;

import java.util.Map;

import io.quarkcloud.quarkadmin.annotation.AdminResource;
import io.quarkcloud.quarkadmin.component.pagecontainer.PageContainer;
import io.quarkcloud.quarkadmin.component.pagecontainer.PageHeader;
import io.quarkcloud.quarkadmin.component.table.Table;
import io.quarkcloud.quarkcore.service.Context;

public class Resource {

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
    public Resource() {

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

    // 列表页组件渲染
    public Object indexComponentRender(Context ctx, Object data) {
        Object component;
        Table table = new Table();

        // 列表标题
        String tableTitle = indexTableTitle(ctx);

        // 列表页轮询数据
        int tablePolling = this.getTablePolling();

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
        Object body = indexComponentRender(context, "xxx");

        return this.pageComponentRender(context, body);
    }
}
