package io.quarkcloud.quarkadmin.template.resource.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkadmin.annotation.AdminResource;
import io.quarkcloud.quarkadmin.component.card.Card;
import io.quarkcloud.quarkadmin.component.form.Form;
import io.quarkcloud.quarkadmin.component.message.Message;
import io.quarkcloud.quarkadmin.component.pagecontainer.PageContainer;
import io.quarkcloud.quarkadmin.component.pagecontainer.PageHeader;
import io.quarkcloud.quarkadmin.component.table.Table;
import io.quarkcloud.quarkadmin.component.table.ToolBar;
import io.quarkcloud.quarkadmin.component.tabs.Tabs;
import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.service.ResourceService;
import io.quarkcloud.quarkadmin.template.resource.Resource;
import io.quarkcloud.quarkadmin.template.resource.core.ResolveAction;
import io.quarkcloud.quarkadmin.template.resource.core.ResolveField;
import io.quarkcloud.quarkadmin.template.resource.core.ResolveSearch;

public class ResourceImpl<M extends ResourceMapper<T>, T> implements Resource<T> {

    @Autowired
    ResourceService<M, T> resourceService;

    // 注解实例
    protected AdminResource annotationClass = null;

    // 实体类
    public T entity;

    // 页面标题
    public String title;
    
    // 页面子标题
    public String subTitle;
    
    // 页面是否携带返回Icon
    public boolean backIcon;
    
    // 列表页分页配置
    public Object perPage;
    
    // 表单页Form实例
    public Form form = new Form();
    
    // 列表页Table实例
    public Table table = new Table();
    
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
    
    // 注入的字段数据
    public Map<String, Object> field;
    
    // 是否具有导出功能
    public boolean withExport;

    // 构造函数
    public ResourceImpl() {
        if (getClass().isAnnotationPresent(AdminResource.class)) {
            annotationClass = getClass().getAnnotation(AdminResource.class);
        }
        this.tableTitleSuffix = "列表";
        this.tableActionColumnTitle = "操作";
        this.tableActionColumnWidth = 200;
        this.backIcon = true;
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
    public ResourceImpl<M,T> setField(Map<String, Object> field) {
        this.field = field;
        return this;
    }

    // 全局查询
    public MPJLambdaWrapper<T> query(Context ctx, MPJLambdaWrapper<T> queryWrapper) {
        return queryWrapper;
    }

    // 列表查询
    public MPJLambdaWrapper<T> indexQuery(Context ctx, MPJLambdaWrapper<T> queryWrapper) {
        return queryWrapper;
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
    public List<Object> actions(Context ctx) {
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

        // 获取行为
        List<Object> getActions = actions(ctx);

        // 获取字段
        List<Object> getFields = fields(ctx);

        // 获取表格行为
        List<Object> tableActions = new ResolveAction(getActions, ctx).getIndexTableRowActions();

        // 获取表格列
        List<Object> tableColumns = new ResolveField(getFields, ctx).
            setTableActionColumnTitle(tableActionColumnTitle).
            setTableActionColumnWidth(tableActionColumnWidth).
            setTableRowActions(tableActions).
            indexTableColumns(ctx);

        return tableColumns;
    }

    // 列表页批量操作
    public Object indexTableAlertActions(Context ctx) {
        return null;
    }

    // 列表页标题
    public String indexTableTitle(Context ctx) {
        return this.getTitle() + this.getTableTitleSuffix();
    }

    // 列表页组件渲染
    public Object indexComponentRender(Context ctx) {

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
        Object indexSearches = new ResolveSearch<T>(this.searches(ctx), ctx).
            setWithExport(this.isWithExport()).
            indexSearches(ctx);

        // 表格组件
        table.setPolling(tablePolling)
            .setTitle(tableTitle)
            .setTableExtraRender(tableExtraRender)
            .setToolBar(tableToolBar)
            .setColumns(tableColumns)
            .setBatchActions(indexTableAlertActions)
            .setSearches(indexSearches);

        // 查询条件
        MPJLambdaWrapper<T> queryWrapper = new MPJLambdaWrapper<>();

        // 获取查询条件
        queryWrapper = this.query(ctx, queryWrapper);

        // 获取查询条件
        queryWrapper = this.indexQuery(ctx, queryWrapper);

        // 设置查询条件
        resourceService.
            setContext(ctx).
            setQueryWrapper(queryWrapper).
            setSearches(this.searches(ctx));

        // 获取分页
        Object perPage = this.getPerPage();
        if (perPage == null || !((perPage instanceof Integer) || (perPage instanceof Long))) {
            List<T> data = resourceService.list();
            return table.setDatasource(data);
        }

        // 解析分页数据
        long pageSize = ((Number) perPage).longValue();
        IPage<T> data = resourceService.page(pageSize);
        long current = data.getCurrent();
        long total = data.getTotal();
        long defaultCurrent = 1;
        Object items = data.getRecords();

        return table.setPagination(current, pageSize, total, defaultCurrent).setDatasource(items);
    }

    // 列表页组件渲染
    public Object indexRender(Context context) {
        return this.pageComponentRender(context, indexComponentRender(context));
    }

    // 创建表单的接口
    public String formApi(Context ctx) {
        return "";
    }

    // 创建表单标题
    public String formTitle(Context ctx) {
        String title = this.getTitle();
        if (ctx.isCreating()) {
            return "创建" + title;
        } else if (ctx.isEditing()) {
            return "编辑" + title;
        }

        return title;
    }

    // 创建表单组件渲染
    public Object formWithinCard(
        Context ctx,
        String title,
        Object extra,
        String api,
        Object fields,
        Object actions,
        Map<String, Object> data) {

        Object formComponent = this.form
            .setStyle(Map.of("padding", "24px"))
            .setApi(api)
            .setActions(actions)
            .setBody(fields)
            .setInitialValues(data);

        return new Card()
            .setTitle(title)
            .setHeaderBordered(true)
            .setExtra(extra)
            .setBody(formComponent);
    }

    // 创建表单组件渲染
    public Object formWithinTabs(
        Context ctx,
        String title,
        Object extra,
        String api,
        Object fields,
        Object actions,
        Map<String, Object> data) {
        Tabs tabsComponent = new Tabs().setTabPanes(fields).setTabBarExtraContent(extra);

        return this.form.setStyle(Map.of("backgroundColor", "#fff", "paddingBottom", "20px"))
            .setApi(api)
            .setActions(actions)
            .setBody(tabsComponent)
            .setInitialValues(data);
    }

    public Map<String, Object> beforeSaving(Context ctx, Map<String, Object> submitData) {
        return submitData;
    }

    public Object formComponentRender (
        Context ctx,
        String title,
        Object extra,
        String api,
        Object fields,
        Object actions,
        Map<String, Object> data) {

        if (fields instanceof List && !((List<?>) fields).isEmpty()) {
            String component;
            try {
                component = ((List<?>) fields).get(0).getClass().getField("component").toString();
                if (component.equals("tabPane")) {
                    return this.formWithinTabs(ctx, title, extra, api, fields, actions, data);
                }
            } catch (NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }
        }

        return this.formWithinCard(ctx, title, extra, api, fields, actions, data);
    }

    // 创建表单的接口
    public String creationApi(Context ctx) {
        String formApi = this.formApi(ctx);
        if (!formApi.isEmpty()) {
            return formApi;
        }
        String[] uri = ctx.getRequest().getRequestURI().split("/");
        if (uri[uri.length - 1].equals("index")) {
            return ctx.getRequest().getRequestURI().replace("/index", "/store");
        }

        return ctx.getRequest().getRequestURI().replace("/create", "/store");
    }

    // 创建页面显示前回调
    public Map<String, Object> beforeCreating(Context ctx) {
        return Map.of();
    }

    // 渲染创建页组件
    public Object creationComponentRender(Context ctx, Map<String, Object> data) {
        String title = formTitle(ctx);
        List<Object> getActions = actions(ctx);
        Object formExtraActions = new ResolveAction(getActions, ctx).getFormExtraActions();
        String api = creationApi(ctx);

        // 获取字段
        List<Object> getFields = fields(ctx);

        // 获取表格列
        Object fields = new ResolveField(getFields, ctx).creationFieldsWithinComponents(ctx);
        Object formActions = new ResolveAction(getActions, ctx).getFormActions();

        return this.formComponentRender(ctx, title, formExtraActions, api, fields, formActions, data);

    }

    // 创建页组件渲染
    public Object creationRender(Context context) {
        Map<String, Object> data = beforeCreating(context);
        return this.pageComponentRender(context, creationComponentRender(context, data));
    }

    // 保存创建数据
    public Object storeRender(Context context) {
        T result = this.resourceService.setContext(context).save(this.entity);
        return this.afterSaved(context, result);
    }

    // 保存创建数据后回调
    public Object afterSaved(Context context,T result) {
        if (context.isImport()) {
            return result;
        }
        if (result == null) {
            return Message.error("操作失败！");
        }
        String redirectUrl = "/layout/index?api=" + "/api/admin/{resource}/index".replace("{resource}", context.getPathVariable("resource"));
        return Message.success("操作成功！", redirectUrl);
    }
}
