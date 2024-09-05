package io.quarkcloud.quarkadmin.template.resource.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkcore.util.Reflect;
import io.quarkcloud.quarkadmin.annotation.AdminResource;
import io.quarkcloud.quarkadmin.component.card.Card;
import io.quarkcloud.quarkadmin.component.form.Closure;
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
import io.quarkcloud.quarkadmin.template.resource.core.PerformQuery;
import io.quarkcloud.quarkadmin.template.resource.core.PerformValidation;
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
    
    // 全局排序规则
    public Map<String, String> queryOrder;

    // 列表页排序规则
    public Map<String, String> indexQueryOrder;

    // 导出数据排序规则
    public Map<String, String> exportQueryOrder;

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
        this.indexQueryOrder = Map.of("id", "desc");
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

    // 全局查询
    public MPJLambdaWrapper<T> queryWrapper(Context context, MPJLambdaWrapper<T> queryWrapper) {
        return queryWrapper;
    }

    // 全局查询
    public LambdaUpdateWrapper<T> updateWrapper(Context context, LambdaUpdateWrapper<T> updateWrapper) {
        return updateWrapper;
    }

    // 列表查询
    public MPJLambdaWrapper<T> indexQueryWrapper(Context context, MPJLambdaWrapper<T> queryWrapper) {
        return queryWrapper;
    }

    // 列表行内编辑查询
    public LambdaUpdateWrapper<T> editableQueryWrapper(Context context, LambdaUpdateWrapper<T> updateWrapper) {
        return updateWrapper;
    }

    // 执行行为查询
    public LambdaUpdateWrapper<T> actionQueryWrapper(Context context, LambdaUpdateWrapper<T> updateWrapper) {
        return updateWrapper;
    }

    // 编辑页面查询
    public MPJLambdaWrapper<T> editQueryWrapper(Context context, MPJLambdaWrapper<T> queryWrapper) {
        return queryWrapper;
    }

    // 详情页面查询
    public MPJLambdaWrapper<T> detailQueryWrapper(Context context, MPJLambdaWrapper<T> queryWrapper) {
        return queryWrapper;
    }

    // 导出数据查询
    public MPJLambdaWrapper<T> exportQueryWrapper(Context context, MPJLambdaWrapper<T> queryWrapper) {
        return queryWrapper;
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

    // 解析列表页表格数据
    public List<T> performsIndexList(Context context, List<T> lists) {
        List<Object> getFields = fields(context);
        List<Object> indexFields = new ResolveField(getFields, context).indexFields(context);
        T tempEntity = this.entity;
        for (T item : lists) {
            this.entity = item;
            Reflect itemReflect = new Reflect(item);
            for (Object fieldObj : indexFields) {
                Reflect fieldReflect = new Reflect(fieldObj);
                String fieldName = (String) fieldReflect.getFieldValue("name");
                boolean hasItemName = itemReflect.checkFieldExist(fieldName);
                if (hasItemName) {
                    Closure callback = (Closure) fieldReflect.getFieldValue("callback");
                    // 解析回调函数值
                    if (callback != null) {
                        Object callbackValue = callback.callback();
                        itemReflect.setFieldValue(fieldName, callbackValue);
                    } else {
                        Object itemValue = itemReflect.getFieldValue(fieldName);
                        if (itemValue instanceof String) {
                            String getItemValue = (String) itemValue;
                            ObjectMapper mapper = new ObjectMapper();
                            try {
                                if (getItemValue.startsWith("[") && getItemValue.endsWith("]")) {
                                    List<?> list = mapper.readValue(getItemValue, new TypeReference<List<Object>>() {});
                                    itemReflect.setFieldValue(fieldName, list);
                                } else if (getItemValue.startsWith("{") && getItemValue.endsWith("}")) {
                                    Map<?, ?> map = mapper.readValue(getItemValue, new TypeReference<Map<String, Object>>() {});
                                    itemReflect.setFieldValue(fieldName, map);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            // 归还原始对象
            this.entity = tempEntity;
        }

        // 显示前回调
        return this.beforeIndexShowing(context, lists);
    }

    // 列表页面显示前回调
    public List<T> beforeIndexShowing(Context context, List<T> list) {
        return list;
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

        // 获取全局查询条件
        queryWrapper = this.queryWrapper(context, queryWrapper);

        // 获取列表查询条件
        queryWrapper = this.indexQueryWrapper(context, queryWrapper);

        Map<String, String> defaultQueryOrder = this.queryOrder;
        if (defaultQueryOrder == null) {
            defaultQueryOrder = this.indexQueryOrder;
        }

        queryWrapper = new PerformQuery<T>(context, queryWrapper).
            setSearches(this.searches(context)).
            setDefaultOrder(defaultQueryOrder).
            buildIndexQuery();

        // 设置查询条件
        resourceService.
            setContext(context).
            setQueryWrapper(queryWrapper);

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
        Object items = this.performsIndexList(context, data.getRecords());
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
    @SuppressWarnings("unchecked")
    public Object storeRender(Context context) {

        // 获取实体数据
        T getEntity = (T) context.getRequestBody(entity.getClass());

        // 获取资源服务
        ResourceService<ResourceMapper<T>, T> getResourceService = (ResourceService<ResourceMapper<T>, T>) this.resourceService;

        // 创建数据前验证数据
        Object validationResult = new PerformValidation<T>(context, this.fields(context), getResourceService).validatorForCreation(getEntity);
        if (validationResult!= null) {
            return Message.error(validationResult);
        }

        // 保存数据
        this.resourceService.save(getEntity);

        // 保存后回调
        return this.afterSaved(context, getEntity);
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

        // 查询条件
        MPJLambdaWrapper<T> queryWrapper = new MPJLambdaWrapper<>();

        // 获取全局查询条件
        queryWrapper = this.queryWrapper(context, queryWrapper);

        // 获取编辑页查询条件
        queryWrapper = this.editQueryWrapper(context, queryWrapper);

        // 获取编辑数据
        T data = this.resourceService.setContext(context).setQueryWrapper(queryWrapper).getOneByContext();

        // 编辑数据前回调
        data = beforeEditing(context, data);

        // 渲染编辑页组件
        return this.pageComponentRender(context, editComponentRender(context, data));
    }

    // 获取编辑表单值
    public Object editValuesRender(Context context) {
        
        // 查询条件
        MPJLambdaWrapper<T> queryWrapper = new MPJLambdaWrapper<>();

        // 获取全局查询条件
        queryWrapper = this.queryWrapper(context, queryWrapper);

        // 获取编辑页查询条件
        queryWrapper = this.editQueryWrapper(context, queryWrapper);

        // 获取编辑数据
        T data = this.resourceService.setContext(context).setQueryWrapper(queryWrapper).getOneByContext();

        // 编辑数据前回调
        data = beforeEditing(context, data);

        // 渲染编辑页数据
        return Message.success("获取成功！", null, data);
    }

    // 保存编辑数据
    @SuppressWarnings("unchecked")
    public Object saveRender(Context context) {

        // 获取实体数据
        T getEntity = (T) context.getRequestBody(entity.getClass());

        // 获取资源服务
        ResourceService<ResourceMapper<T>, T> getResourceService = (ResourceService<ResourceMapper<T>, T>) this.resourceService;

        // 创建数据前验证数据
        Object validationResult = new PerformValidation<T>(context, this.fields(context), getResourceService).validatorForUpdate(getEntity);
        if (validationResult!= null) {
            return Message.error(validationResult);
        }

        // 保存数据
        this.resourceService.updateById(getEntity);

        // 保存后回调
        return this.afterSaved(context, getEntity);
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

        // 查询条件
        LambdaUpdateWrapper<T> updateWrapper = new LambdaUpdateWrapper<T>();

        // 获取全局查询条件
        updateWrapper = this.updateWrapper(context, updateWrapper);

        // 获取行内编辑查询条件
        updateWrapper = this.editableQueryWrapper(context, updateWrapper);

        // 更新数据
        T result = this.resourceService.setContext(context).editableUpdate(this.entity);
        if (result == null) {
            return Message.error("操作失败！");
        }

        // 返回成功
        return Message.success("操作成功！");
    }

    // 行为解析
    @SuppressWarnings("unchecked")
    public Object actionRender(Context context) {

        // 行为结果
        Object result = null;

        // 查询条件
        LambdaUpdateWrapper<T> queryWrapper = new LambdaUpdateWrapper<>();

        // 获取全局查询条件
        queryWrapper = this.updateWrapper(context, queryWrapper);

        // 获取行为查询条件
        queryWrapper = this.actionQueryWrapper(context, queryWrapper);

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
                        ResourceService<ResourceMapper<T>, T> getResourceService = (ResourceService<ResourceMapper<T>, T>) resourceService;
                        result = dropdownActionInstance.handle(context, queryWrapper, getResourceService);
                        Object err = this.afterAction(context, dropdownUriKey, getResourceService);
                        if (err!=null) {
                            return err;
                        }
                    }
                }
            } else {
                if (context.getPathVariable("uriKey").equals(uriKey)) {
                    ResourceService<ResourceMapper<T>, T> getResourceService = (ResourceService<ResourceMapper<T>, T>) resourceService;
                    result = actionInstance.handle(context, queryWrapper, getResourceService);
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
    @SuppressWarnings("unchecked")
    public Object actionValuesRender(Context context) {
        Map<String, Object> data = new HashMap<>();
        List<Object> actions = this.actions(context);
        for (Object item : actions) {
            Action<T> actionInstance = (Action<T>) item;
            // uri唯一标识
            String uriKey = actionInstance.getUriKey(item);
            // 获取行为类型
            String actionType = actionInstance.getActionType();
            if ("dropdown".equals(actionType)) {
                Dropdown<M,T> dropdownActioner = (Dropdown<M,T>) actionInstance;
                for (Object dropdownAction : dropdownActioner.getActions()) {
                    String dropdownUriKey = dropdownActioner.getUriKey(dropdownAction);
                    if (context.getPathVariable("uriKey").equals(dropdownUriKey)) {
                        Reflect dropdownActionReflect = new Reflect(dropdownAction);
                        boolean hasDataMethod = dropdownActionReflect.checkMethodExist("data");
                        if (hasDataMethod) {
                            data = ( Map<String,Object>) dropdownActionReflect.invoke("data",context.getClass(), context);
                        }
                    }
                }
            } else {
                if (context.getPathVariable("uriKey").equals(uriKey)) {
                    Reflect actionInstanceReflect = new Reflect(actionInstance);
                    boolean hasDataMethod = actionInstanceReflect.checkMethodExist("data");
                    if (hasDataMethod) {
                        data = ( Map<String,Object>) actionInstanceReflect.invoke("data",context.getClass(), context);
                    }
                }
            }
        }

        return Message.success("操作成功！", null, data);
    }

    // 详情页面
    public Object detailRender(Context context) {
        return Message.success("操作成功！");
    }

    // 表单渲染
    public Object formRender(Context context) {
        return Message.success("操作成功！");
    }

    // 导入模板
    public Object importTemplateRender(Context context) {
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
}
