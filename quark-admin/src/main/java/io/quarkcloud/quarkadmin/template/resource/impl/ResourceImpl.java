package io.quarkcloud.quarkadmin.template.resource.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkcore.util.Reflect;
import jakarta.servlet.ServletOutputStream;
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
import io.quarkcloud.quarkadmin.service.FileService;
import io.quarkcloud.quarkadmin.service.ResourceService;
import io.quarkcloud.quarkadmin.template.resource.Action;
import io.quarkcloud.quarkadmin.template.resource.Resource;
import io.quarkcloud.quarkadmin.template.resource.core.PerformQuery;
import io.quarkcloud.quarkadmin.template.resource.core.PerformValidation;
import io.quarkcloud.quarkadmin.template.resource.core.ResolveAction;
import io.quarkcloud.quarkadmin.template.resource.core.ResolveExport;
import io.quarkcloud.quarkadmin.template.resource.core.ResolveField;
import io.quarkcloud.quarkadmin.template.resource.core.ResolveImport;
import io.quarkcloud.quarkadmin.template.resource.core.ResolveSearch;
import io.quarkcloud.quarkadmin.template.resource.impl.action.DropdownImpl;

public class ResourceImpl<M extends ResourceMapper<T>, T> implements Resource<T> {

    @Autowired
    ResourceService<M, T> resourceService;

    @Autowired
    private FileService fileService;

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
    public Form form;
    
    // 列表页Table实例
    public Table table;
    
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
        this.form = new Form();
        this.table = new Table();
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

    // 全局更新
    public UpdateWrapper<T> updateWrapper(Context context, UpdateWrapper<T> updateWrapper) {
        return updateWrapper;
    }

    // 列表查询
    public MPJLambdaWrapper<T> indexQueryWrapper(Context context, MPJLambdaWrapper<T> queryWrapper) {
        return queryWrapper;
    }

    // 列表行内编辑查询
    public UpdateWrapper<T> editableQueryWrapper(Context context, UpdateWrapper<T> updateWrapper) {
        return updateWrapper;
    }

    // 执行行为查询
    public UpdateWrapper<T> actionQueryWrapper(Context context, UpdateWrapper<T> updateWrapper) {
        return updateWrapper;
    }

    // 编辑页面查询
    public MPJLambdaWrapper<T> editQueryWrapper(Context context, MPJLambdaWrapper<T> queryWrapper) {
        return queryWrapper;
    }

    // 保存编辑查询
    public UpdateWrapper<T> saveWrapper(Context context, UpdateWrapper<T> updateWrapper) {
        return updateWrapper;
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
        List<Object> getActions = actions(context);
        return new ResolveAction<ResourceMapper<T>, T>(getActions, context).getIndexTableAlertActions();
    }

    // 列表页标题
    public String indexTableTitle(Context context) {
        return this.getTitle() + this.getTableTitleSuffix();
    }

    // 解析列表页表格数据
    public Object performsIndexList(Context context, List<T> lists) {
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

    // 解析导出表格数据
    public List<T> performsExportList(Context context, List<T> lists) {
        List<Object> getFields = fields(context);
        List<Object> indexFields = new ResolveField(getFields, context).exportFields(context);
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
        return this.beforeExportshowing(context, lists);
    }

    // 导出数据显示前回调
    public List<T> beforeExportshowing(Context context, List<T> list) {
        return list;
    }

    // 列表页面显示前回调
    public Object beforeIndexShowing(Context context, List<T> list) {
        return list;
    }

    // 列表页组件渲染
    public Object indexComponentRender(Context context) {

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

        queryWrapper = new PerformQuery<T>(context).
            setSearches(this.searches(context)).
            setQueryWrapper(queryWrapper).
            setDefaultOrder(defaultQueryOrder).
            buildIndexQuery();

        // 获取分页
        Object perPage = this.getPerPage();
        if (perPage == null || !((perPage instanceof Integer) || (perPage instanceof Long))) {
            List<T> data = resourceService.list(queryWrapper);
            Object items = this.performsIndexList(context, data);
            return table.setDatasource(items);
        }

        // 默认分页数量
        long pageSize = ((Number) perPage).longValue();

        // 获取分页参数
        IPage<T> page = new Page<T>(context.getPageFromSearch(), context.getPageSizeFromSearch(pageSize));

        // 获取分页数据
        IPage<T> data = resourceService.page(page, queryWrapper);
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
        Object data) {

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
        Object data) {
        Tabs tabsComponent = new Tabs().setTabPanes(fields).setTabBarExtraContent(extra);
        return this.form.setStyle(Map.of("backgroundColor", "#fff", "paddingBottom", "20px"))
            .setApi(api)
            .setActions(actions)
            .setBody(tabsComponent)
            .setInitialValues(data);
    }

    public T beforeSaving(Context context, T submitData) {
        return submitData;
    }

    public Object formComponentRender (
        Context context,
        String title,
        Object extra,
        String api,
        Object fields,
        Object actions,
        Object data) {
        if (fields instanceof List && !((List<?>) fields).isEmpty()) {
            Reflect fieldReflect = new Reflect(((List<?>) fields).get(0));
            String component = (String) fieldReflect.getFieldValue("component");
            if (component.equals("tabPane")) {
                return this.formWithinTabs(context, title, extra, api, fields, actions, data);
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

    // 创建表单的字段
    public List<Object> creationFields(Context context) {
        return new ResolveField(this.fields(context), context).creationFieldsWithinComponents(context);
    }

    // 创建表单的数据
    public Object creationData(Context context) {
        return this.beforeCreating(context);
    }

    // 创建页面显示前回调
    public Object beforeCreating(Context context) {
        return this.entity;
    }

    // 渲染创建页组件
    public Object creationComponentRender(Context context, Object data) {
        String title = formTitle(context);
        List<Object> getActions = actions(context);
        Object formExtraActions = new ResolveAction<ResourceMapper<T>, T>(getActions, context).getFormExtraActions();
        String api = creationApi(context);
        Object fields = creationFields(context);
        Object formActions = new ResolveAction<ResourceMapper<T>, T>(getActions, context).getFormActions();
        return this.formComponentRender(context, title, formExtraActions, api, fields, formActions, data);

    }

    // 创建页组件渲染
    public Object creationRender(Context context) {
        Object data = this.creationData(context);
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

        // 保存前
        getEntity = this.beforeSaving(context, getEntity);

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

    // 编辑页面获取表单数据接口
    public String editValueApi(Context context) {
        String formApi = this.formApi(context);
        if (!formApi.isEmpty()) {
            return formApi;
        }
        String[] uri = context.getRequest().getRequestURI().split("/");
        if (uri[uri.length - 1].equals("index")) {
            return context.getRequest().getRequestURI().replace("/index", "/edit/values?id=${id}");
        }
        return context.getRequest().getRequestURI().replace("/edit", "/edit/values?id=${id}");
    }

    // 编辑表单的字段
    public List<Object> editFields(Context context) {
        return new ResolveField(this.fields(context), context).updateFieldsWithinComponents(context);
    }

    // 编辑表单的数据
    public T editData(Context context) {
        // 查询条件
        MPJLambdaWrapper<T> queryWrapper = new MPJLambdaWrapper<>();

        // 获取全局查询条件
        queryWrapper = this.queryWrapper(context, queryWrapper);

        // 获取编辑页查询条件
        queryWrapper = this.editQueryWrapper(context, queryWrapper);

        // 构建查询条件
        queryWrapper = new PerformQuery<T>(context).
            setQueryWrapper(queryWrapper).
            buildEditQuery();

        // 获取编辑数据
        T data = resourceService.getOne(queryWrapper);

        // 编辑数据前回调
        return beforeEditing(context, data);
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
        Object fields = editFields(context);
        Object formActions = new ResolveAction<ResourceMapper<T>, T>(getActions, context).getFormActions();
        return this.formComponentRender(context, title, formExtraActions, api, fields, formActions, data);
    }

    // 编辑页组件渲染
    public Object editRender(Context context) {

        // 获取编辑数据
        T data = this.editData(context);

        // 渲染编辑页组件
        return this.pageComponentRender(context, editComponentRender(context, data));
    }

    // 获取编辑表单值
    public Object editValuesRender(Context context) {
        
        // 获取编辑数据
        T data = this.editData(context);

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

        // 查询条件
        UpdateWrapper<T> queryWrapper = new UpdateWrapper<T>();

        // 获取全局查询条件
        queryWrapper = this.updateWrapper(context, queryWrapper);

        // 获取查询条件
        queryWrapper = this.saveWrapper(context, queryWrapper);

        // 构建查询条件
        queryWrapper = new PerformQuery<T>(context).
            setUpdateWrapper(queryWrapper).
            buildUpdateQuery();

        // 保存前回调
        getEntity = this.beforeSaving(context, getEntity);

        // 保存数据
        this.resourceService.update(getEntity, queryWrapper);

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
    @SuppressWarnings("unchecked")
    public Object editableRender(Context context) {

        // 查询条件
        UpdateWrapper<T> updateWrapper = new UpdateWrapper<T>();

        // 获取全局查询条件
        updateWrapper = this.updateWrapper(context, updateWrapper);

        // 获取行内编辑查询条件
        updateWrapper = this.editableQueryWrapper(context, updateWrapper);

        // 构建查询条件
        updateWrapper = new PerformQuery<T>(context).
            setUpdateWrapper(updateWrapper).
            buildEditableQuery();

        // 获取实体数据
        T getEntity = (T) context.getEditableBody(entity.getClass());

        // 更新数据
        boolean result = this.resourceService.update(getEntity, updateWrapper);
        if (!result) {
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
        UpdateWrapper<T> queryWrapper = new UpdateWrapper<>();

        // 获取全局查询条件
        queryWrapper = this.updateWrapper(context, queryWrapper);

        // 获取行为查询条件
        queryWrapper = this.actionQueryWrapper(context, queryWrapper);

        // 构建查询条件
        queryWrapper = new PerformQuery<T>(context).
            setUpdateWrapper(queryWrapper).
            buildActionQuery();

        List<Object> actions = this.actions(context);
        for (Object item : actions) {
            Action<T> actionInstance = (Action<T>)item;
            String uriKey = actionInstance.getUriKey(item);
            String actionType = actionInstance.getActionType();
            if ("dropdown".equals(actionType)) {
                DropdownImpl<M,T> dropdownActioner = (DropdownImpl<M,T>) actionInstance;
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
                DropdownImpl<M,T> dropdownActioner = (DropdownImpl<M,T>) actionInstance;
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

    // 详情标题
    public String detailTitle(Context context) {
        String title = this.getTitle();

        return title + "详情";
    }

    // 详情页页面显示前回调
    public T beforeDetailShowing(Context context, T data) {
        return data;
    }

    // 渲染详情页组件
    public Object detailComponentRender(Context context, T data) {
        String title = detailTitle(context);
        List<Object> getActions = actions(context);
        List<Object> getFields = fields(context);

        // 详情页右上角自定义区域行为
        Object formExtraActions = new ResolveAction<ResourceMapper<T>, T>(getActions, context).getDetailExtraActions();

        // 包裹在组件内的详情页字段
        Object fields = new ResolveField(getFields, context).detailFieldsWithinComponents(context, data);

        // 包裹在组件内的详情页字段
        Object formActions = new ResolveAction<ResourceMapper<T>, T>(getActions, context).getDetailActions();

        return detailWithinCard(
            context,
            title,
            formExtraActions,
            fields,
            formActions,
            data
        );
    }

    // 在卡片内的详情页组件
    public Object detailWithinCard(
            Context context,
            String title,
            Object extra,
            Object fields,
            Object actions,
            T data) {

        Card cardComponent = new Card();
        cardComponent.setTitle(title)
                .setHeaderBordered(true)
                .setExtra(extra)
                .setBody(fields);

        return cardComponent;
    }

    // 在标签页内的详情页组件
    public Object detailWithinTabs(
            Context context,
            String title,
            Object extra,
            Object fields,
            List<Object> actions,
            T data) {

        Tabs tabsComponent = new Tabs();
        tabsComponent.setTabPanes(fields).setTabBarExtraContent(extra);

        return tabsComponent;
    }

    // 详情页面
    public Object detailRender(Context context) {

        // 查询条件
        MPJLambdaWrapper<T> queryWrapper = new MPJLambdaWrapper<>();

        // 获取全局查询条件
        queryWrapper = this.queryWrapper(context, queryWrapper);

        // 获取查询条件
        queryWrapper = this.detailQueryWrapper(context, queryWrapper);

        // 构建查询条件
        queryWrapper = new PerformQuery<T>(context).
            setQueryWrapper(queryWrapper).
            buildDetailQuery();

        // 获取数据
        T data = this.resourceService.getOne(queryWrapper);

        // 数据前回调
        data = beforeDetailShowing(context, data);

        // 渲染组件
        return this.pageComponentRender(context, detailComponentRender(context, data));
    }

    // 表单渲染
    public Object formRender(Context context) {
        Object data = beforeCreating(context);
        return this.pageComponentRender(context, creationComponentRender(context, data));
    }

    // 导入模板
    public Object importTemplateRender(Context context) throws IOException {
        List<Object> getFields = fields(context);
        List<Object> fields = new ResolveField(getFields, context).importFields(context);
        ResolveImport resolveImport = new ResolveImport();
        List<String> exportTitles = new ArrayList<>();

        for (Object v : fields) {
            String label = resolveImport.getFieldLabel(v);
            exportTitles.add(label + resolveImport.getFieldRemark(v));
        }

        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter(true);

        //创建xlsx格式的
        writer.write(exportTitles, true);
        context.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"); 
        context.setHeader("Content-Disposition","attachment;filename=data_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xlsx"); 
        ServletOutputStream out=context.getOutputStream(); 

        //out为OutputStream，需要写出到的目标流
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);

        return null;
    }

    // 导入数据
    @SuppressWarnings("unchecked")
    public Object importRender(Context context) {
        Object fileId = context.getRequestParam("fileId");
        if (fileId == null) {
            return Message.error("参数错误！");
        }

        List<Object> getFileId= (List<Object>) fileId;
        if (getFileId.size()==0) {
            return Message.error("参数错误！");
        }

        Reflect fieldReflect = new Reflect(getFileId.get(0));
        boolean idFieldExist = fieldReflect.checkFieldExist("id");
        if (!idFieldExist) {
            return Message.error("参数错误！");
        }

        Object id = fieldReflect.getFieldValue("id");
        if (id == null) {
            return Message.error("参数错误！");
        }

        String excelFilePath = fileService.getFilePath(id);
        if (excelFilePath == null || excelFilePath.contains("")) {
            return Message.error("未找到上传的文件！");
        }

        ExcelReader reader = ExcelUtil.getReader(excelFilePath);
        List<List<Object>> importData = reader.read();
        if (importData.size() == 0) {
            return Message.error("导入数据为空！");
        }

        // 表格头部
        List<Object> importHead = importData.get(0);
        importData.remove(0); // 去除表格头部

        // 导入前回调
        List<List<Object>> lists = this.beforeImporting(context, importData);

        boolean importResult = true;
        int importTotalNum = lists.size();
        int importSuccessedNum = 0;
        int importFailedNum = 0;
        List<List<Object>> importFailedData = new ArrayList<>();

        // 获取字段
        List<Object> getFields = fields(context);
        List<Object> fields = new ResolveField(getFields, context).importFields(context);
        ResolveImport resolveImport = new ResolveImport();
        ResourceService<ResourceMapper<T>, T> getResourceService = (ResourceService<ResourceMapper<T>, T>) this.resourceService;

        // 解析字段
        for (List<Object> item : lists) {
            // 获取表单数据
            Map<String, Object> formValues = resolveImport.transformFormValues(fields, item);

            // 验证表单条件
            Object validationResult = new PerformValidation<T>(context, this.fields(context), getResourceService).validatorForImport(formValues);
            if (validationResult != null) {
                importResult = false;
                importFailedNum++;
                item.add(validationResult);
                importFailedData.add(item);
                continue;
            }

            // 插入数据库
            Map<String, Object> data = resolveImport.getSubmitData(fields, formValues);
            ObjectMapper mapper = new ObjectMapper();
            T resourceEntity = (T) mapper.convertValue(data, this.entity.getClass());

            // 保存前回调
            resourceEntity = this.beforeSaving(context, resourceEntity);

            // 保存数据
            boolean result = this.resourceService.save(resourceEntity);
            if (!result) {
                importResult = false;
                importFailedNum++;
                item.add("操作失败");
                importFailedData.add(item);
                continue;
            }

            // 保存后回调
            try {
                this.afterSaved(context, resourceEntity);
            } catch (Exception e) {
                importResult = false;
                importFailedNum++;
                item.add(e.getMessage());
                importFailedData.add(item);
                continue;
            }

            importSuccessedNum++;
        }

        // 返回导入失败错误数据
        if (!importResult) {
            String filePath = "public/storage/failImports/";
            String fileName = IdUtil.simpleUUID() + ".xlsx";
            String fileUrl = "//" + context.getRemoteHost() + "/storage/failImports/" + fileName;

            // 不存在路径，则创建
            File dir = FileUtil.file(filePath);
            if (!FileUtil.exist(dir)) {
                FileUtil.mkdir(dir);
            }

            List<List<Object>> rows = new ArrayList<>();
            importHead.add("错误信息");
            rows.add(importHead);
            rows.addAll(importFailedData);

            // 通过工具类创建writer
            ExcelWriter writer = ExcelUtil.getWriter(filePath + fileName);
            writer.write(rows, true);
            writer.close();

            // 创建组件
            String tpl1 = "导入总量: " + importTotalNum;
            String tpl2 = "成功数量: " + importSuccessedNum;
            String tpl3 = "失败数量: <span style='color:#ff4d4f'>" + importFailedNum + "</span> <a href='" + fileUrl + "' target='_blank'>下载失败数据</a>";

            String component = "<div style='margin-left: 50px; margin-bottom: 20px;'>"
                    + "<div>" + tpl1 + "</div>"
                    + "<div>" + tpl2 + "</div>"
                    + "<div>" + tpl3 + "</div>"
                    + "</div>";

            return component;
        }

        // 重定向
        String redirectUrl = "/layout/index?api=/api/admin/{resource}/index".replace("{resource}", context.getPathVariable("resource"));
        
        // 返回重定向
        return Message.success("操作成功！", redirectUrl);
    }

    // 导出数据
    public Object exportRender(Context context) throws IOException {

        // 查询条件
        MPJLambdaWrapper<T> queryWrapper = new MPJLambdaWrapper<>();

        // 获取全局查询条件
        queryWrapper = this.queryWrapper(context, queryWrapper);

        // 获取列表查询条件
        queryWrapper = this.exportQueryWrapper(context, queryWrapper);

        Map<String, String> defaultQueryOrder = this.queryOrder;
        if (defaultQueryOrder == null) {
            defaultQueryOrder = this.exportQueryOrder;
        }

        queryWrapper = new PerformQuery<T>(context).
            setSearches(this.searches(context)).
            setQueryWrapper(queryWrapper).
            setDefaultOrder(defaultQueryOrder).
            buildExportQuery();

        List<T> data = this.performsExportList(context, resourceService.list(queryWrapper));
        List<Object> getFields = fields(context);
        List<Object> fields = new ResolveField(getFields, context).exportFields(context);

        // 创建 ExcelWriter
        ExcelWriter writer = ExcelUtil.getWriter();
        ResolveExport resolveExport = new ResolveExport();
        List<Object[]> rows = new ArrayList<>();

        // 创建表头
        Object[] headers = new Object[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            String label = resolveExport.getFieldLabel(fields.get(i));
            if (!label.equals("")) {
                headers[i] = label;
            }
        }
        rows.add(headers);

        // 填充数据
        for (Object rowData : data) {
            Object[] rowValues = new Object[fields.size()];
            for (int j = 0; j < fields.size(); j++) {
                Object field = fields.get(j);
                String name = resolveExport.getFieldName(field);
                String component = resolveExport.getFieldComponent(field);
                Object cellValue = resolveExport.getCellValue(component, field, rowData, name);
                rowValues[j] = cellValue;
            }
            rows.add(rowValues);
        }

        // 设置响应
        context.setHeader("Content-Disposition", "attachment; filename=data_" + DateUtil.formatDateTime(new Date()) + ".xlsx");
        context.setContentType("application/octet-stream");
        writer.write(rows, true);
        ServletOutputStream out = context.getOutputStream(); 
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);

        return null;
    }
}
