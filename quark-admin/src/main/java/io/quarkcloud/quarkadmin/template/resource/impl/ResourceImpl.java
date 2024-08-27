package io.quarkcloud.quarkadmin.template.resource.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.transaction.Transaction;
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
import io.quarkcloud.quarkadmin.template.resource.Action;
import io.quarkcloud.quarkadmin.template.resource.Resource;
import io.quarkcloud.quarkadmin.template.resource.core.ResolveAction;
import io.quarkcloud.quarkadmin.template.resource.core.ResolveField;
import io.quarkcloud.quarkadmin.template.resource.core.ResolveSearch;
import io.quarkcloud.quarkadmin.template.resource.impl.action.Dropdown;

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
    public MPJLambdaWrapper<T> query(Context context, MPJLambdaWrapper<T> queryWrapper) {
        return queryWrapper;
    }

    // 列表查询
    public MPJLambdaWrapper<T> indexQuery(Context context, MPJLambdaWrapper<T> queryWrapper) {
        return queryWrapper.orderByDesc("id");
    }

    // 字段
    public List<Object> fields(Context context) {
        return null;
    }

    // 搜索
    public List<Object> searches(Context context) {
        return null;
    }

    // 行为
    public List<Object> actions(Context context) {
        return null;
    }

    // 菜单
    public Map<String, Object> menus(Context context) {
        return null;
    }

    // 数据导出前回调
    public List<Object> beforeExporting(Context context, List<Map<String, Object>> list) {
        List<Object> result = new ArrayList<>();
        for (Map<String, Object> v : list) {
            result.add(v);
        }
        return result;
    }

    // 数据导入前回调
    public List<List<Object>> beforeImporting(Context context, List<List<Object>> list) {
        return list;
    }

    // 表格行内编辑执行完之后回调
    public Object afterEditable(Context context, Object id, String field, Object value) {
        return null;
    }

    // 行为执行完之后回调
    public Object afterAction(Context context, String uriKey, ResourceService<ResourceMapper<T>, T> resourceService) {
        return null;
    }

    // 页面组件渲染
    public Object pageComponentRender(Context context, Object body) {
        return this.pageContainerComponentRender(context, body);
    }

    // 页面容器组件渲染
    public Object pageContainerComponentRender(Context context, Object body) {

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
    public Object indexTableExtraRender(Context context) {
        return null;
    }

    // 列表页工具栏
    public Object indexTableToolBar(Context context) {
        Object tableActions = new ResolveAction<ResourceMapper<T>, T>(actions(context), context).getIndexTableActions();
        return new ToolBar().
            setTitle(indexTableTitle(context)).
            setActions(tableActions);
    }

    // 列表页表格列
    public Object indexTableColumns(Context context) {
        List<Object> getActions = actions(context);
        List<Object> getFields = fields(context);
        List<Object> tableActions = new ResolveAction<ResourceMapper<T>, T>(getActions, context).getIndexTableRowActions();
        List<Object> tableColumns = new ResolveField(getFields, context).
            setTableActionColumnTitle(tableActionColumnTitle).
            setTableActionColumnWidth(tableActionColumnWidth).
            setTableRowActions(tableActions).
            indexTableColumns(context);
        return tableColumns;
    }

    // 列表页批量操作
    public Object indexTableAlertActions(Context context) {
        return null;
    }

    // 列表页标题
    public String indexTableTitle(Context context) {
        return this.getTitle() + this.getTableTitleSuffix();
    }

    // 列表页组件渲染
    public Object indexComponentRender(Context context) {

        // 表格组件
        Table table = new Table();

        // 列表标题
        String tableTitle = indexTableTitle(context);

        // 列表页轮询数据
        int tablePolling = getTablePolling();

        // 列表页表格主体
        Object tableExtraRender = indexTableExtraRender(context);

        // 列表页工具栏
        Object tableToolBar = indexTableToolBar(context);

        // 列表页表格列
        Object tableColumns = indexTableColumns(context);

        // 列表页批量操作
        Object indexTableAlertActions = indexTableAlertActions(context);

        // 列表页搜索栏
        Object indexSearches = new ResolveSearch<T>(this.searches(context), context).
            setWithExport(this.isWithExport()).
            indexSearches(context);

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
        queryWrapper = this.query(context, queryWrapper);

        // 获取查询条件
        queryWrapper = this.indexQuery(context, queryWrapper);

        // 设置查询条件
        resourceService.
            setContext(context).
            setQueryWrapper(queryWrapper).
            setSearches(this.searches(context));

        // 获取分页
        Object perPage = this.getPerPage();
        if (perPage == null || !((perPage instanceof Integer) || (perPage instanceof Long))) {
            List<T> data = resourceService.getListByContext();
            return table.setDatasource(data);
        }

        // 解析分页数据
        long pageSize = ((Number) perPage).longValue();
        IPage<T> data = resourceService.getPageByContext(pageSize);
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
    public String formApi(Context context) {
        return "";
    }

    // 创建表单标题
    public String formTitle(Context context) {
        String title = this.getTitle();
        if (context.isCreating()) {
            return "创建" + title;
        } else if (context.isEditing()) {
            return "编辑" + title;
        }
        return title;
    }

    // 创建表单组件渲染
    public Object formWithinCard(
        Context context,
        String title,
        Object extra,
        String api,
        Object fields,
        Object actions,
        T data) {

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
        Context context,
        String title,
        Object extra,
        String api,
        Object fields,
        Object actions,
        T data) {
        Tabs tabsComponent = new Tabs().setTabPanes(fields).setTabBarExtraContent(extra);
        return this.form.setStyle(Map.of("backgroundColor", "#fff", "paddingBottom", "20px"))
            .setApi(api)
            .setActions(actions)
            .setBody(tabsComponent)
            .setInitialValues(data);
    }

    public Map<String, Object> beforeSaving(Context context, Map<String, Object> submitData) {
        return submitData;
    }

    public Object formComponentRender (
        Context context,
        String title,
        Object extra,
        String api,
        Object fields,
        Object actions,
        T data) {
        if (fields instanceof List && !((List<?>) fields).isEmpty()) {
            String component;
            try {
                component = ((List<?>) fields).get(0).getClass().getField("component").toString();
                if (component.equals("tabPane")) {
                    return this.formWithinTabs(context, title, extra, api, fields, actions, data);
                }
            } catch (NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }
        }
        return this.formWithinCard(context, title, extra, api, fields, actions, data);
    }

    // 创建表单的接口
    public String creationApi(Context context) {
        String formApi = this.formApi(context);
        if (!formApi.isEmpty()) {
            return formApi;
        }
        String[] uri = context.getRequest().getRequestURI().split("/");
        if (uri[uri.length - 1].equals("index")) {
            return context.getRequest().getRequestURI().replace("/index", "/store");
        }
        return context.getRequest().getRequestURI().replace("/create", "/store");
    }

    // 创建页面显示前回调
    public T beforeCreating(Context context) {
        return this.entity;
    }

    // 渲染创建页组件
    public Object creationComponentRender(Context context, T data) {
        String title = formTitle(context);
        List<Object> getActions = actions(context);
        Object formExtraActions = new ResolveAction<ResourceMapper<T>, T>(getActions, context).getFormExtraActions();
        String api = creationApi(context);
        List<Object> getFields = fields(context);
        Object fields = new ResolveField(getFields, context).creationFieldsWithinComponents(context);
        Object formActions = new ResolveAction<ResourceMapper<T>, T>(getActions, context).getFormActions();
        return this.formComponentRender(context, title, formExtraActions, api, fields, formActions, data);

    }

    // 创建页组件渲染
    public Object creationRender(Context context) {
        T data = beforeCreating(context);
        return this.pageComponentRender(context, creationComponentRender(context, data));
    }

    // 保存创建数据
    public Object storeRender(Context context) {
        T result = this.resourceService.setContext(context).saveByContext(this.entity);
        return this.afterSaved(context, result);
    }

    // 编辑表单的接口
    public String editApi(Context context) {
        String formApi = this.formApi(context);
        if (!formApi.isEmpty()) {
            return formApi;
        }
        String[] uri = context.getRequest().getRequestURI().split("/");
        if (uri[uri.length - 1].equals("index")) {
            return context.getRequest().getRequestURI().replace("/index", "/save");
        }
        return context.getRequest().getRequestURI().replace("/edit", "/save");
    }

    // 编辑页面显示前回调
    public T beforeEditing(Context context,T data) {
        return data;
    }

    // 渲染编辑页组件
    public Object editComponentRender(Context context, T data) {
        String title = formTitle(context);
        List<Object> getActions = actions(context);
        Object formExtraActions = new ResolveAction<ResourceMapper<T>, T>(getActions, context).getFormExtraActions();
        String api = editApi(context);
        List<Object> getFields = fields(context);
        Object fields = new ResolveField(getFields, context).updateFieldsWithinComponents(context);
        Object formActions = new ResolveAction<ResourceMapper<T>, T>(getActions, context).getFormActions();
        return this.formComponentRender(context, title, formExtraActions, api, fields, formActions, data);
    }

    // 编辑页组件渲染
    public Object editRender(Context context) {
        T data = this.resourceService.setContext(context).getOneByContext();
        data = beforeEditing(context, data);
        return this.pageComponentRender(context, editComponentRender(context, data));
    }

    // 获取编辑表单值
    public Object editValuesRender(Context context) {
        return Message.success("操作成功！");
    }

    // 保存编辑数据
    public Object saveRender(Context context) {
        T result = this.resourceService.setContext(context).updateByContext(this.entity);
        return this.afterSaved(context, result);
    }

    // 保存数据后回调
    public Object afterSaved(Context context,T result) {
        if (context.isImport()) {
            return result;
        }
        if (result == null) {
            return Message.error("操作失败！");
        }
        String redirectUrl = "/layout/index?api=/api/admin/{resource}/index".replace("{resource}", context.getPathVariable("resource"));
        return Message.success("操作成功！", redirectUrl);
    }

    // 表格行内编辑
    public Object editableRender(Context context) {
        return Message.success("操作成功！");
    }

    // 行为解析
    @SuppressWarnings("unchecked")
    public Object actionRender(Context context) {
        Object result = null;
        MPJLambdaWrapper<T> queryWrapper = new MPJLambdaWrapper<>();
        queryWrapper = this.query(context, queryWrapper);
        List<Object> actions = this.actions(context);
        for (Object item : actions) {
            Action<T> actionInstance = (Action<T>)item;
            String uriKey = actionInstance.getUriKey(item);
            String actionType = actionInstance.getActionType();
            if ("dropdown".equals(actionType)) {
                Dropdown<M,T> dropdownActioner = (Dropdown<M,T>) actionInstance;
                for (Object dropdownAction : dropdownActioner.getActions()) {
                    String dropdownUriKey = dropdownActioner.getUriKey(dropdownAction);
                    if (context.getPathVariable("uriKey").equals(dropdownUriKey)) {
                        Action<T> dropdownActionInstance = (Action<T>) dropdownAction;
                        ResourceService<ResourceMapper<T>, T> getResourceService = (ResourceService<ResourceMapper<T>, T>) resourceService.setQueryWrapper(queryWrapper);
                        result = dropdownActionInstance.handle(context, getResourceService);
                        Object err = this.afterAction(context, dropdownUriKey, getResourceService);
                        if (err!=null) {
                            return err;
                        }
                    }
                }
            } else {
                if (context.getPathVariable("uriKey").equals(uriKey)) {
                    ResourceService<ResourceMapper<T>, T> getResourceService = (ResourceService<ResourceMapper<T>, T>) resourceService.setQueryWrapper(queryWrapper);
                    result = actionInstance.handle(context, getResourceService);
                    Object err = this.afterAction(context, uriKey, getResourceService);
                    if (err!=null) {
                        return err;
                    }
                }
            }
        }

        return result;
    }

    // 行为表单值
    public Object actionValuesRender(Context context) {
        return Message.success("操作成功！");
    }

    // 导入数据
    public Object importRender(Context context) {
        return Message.success("操作成功！");
    }

    // 导出数据
    public Object exportRender(Context context) {
        return Message.success("操作成功！");
    }

    // 详情页面
    public Object detailRender(Context context) {
        return Message.success("操作成功！");
    }

    // 导入模板
    public Object importTemplateRender(Context context) {
        return Message.success("操作成功！");
    }

    // 表单渲染
    public Object formRender(Context context) {
        return Message.success("操作成功！");
    }
}
