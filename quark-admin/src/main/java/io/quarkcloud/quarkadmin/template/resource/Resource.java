package io.quarkcloud.quarkadmin.template.resource;

import java.util.List;
import java.util.Map;
import io.quarkcloud.quarkcore.service.Context;

public interface Resource<T> {

    // 获取标题
    public String getTitle();

    // 获取子标题
    public String getSubTitle();

    // 获取是否携带返回Icon
    public boolean isBackIcon();

    // 获取列表页分页配置
    public Object getPerPage();

    // 获取表格标题后缀
    public String getTableTitleSuffix();

    // 获取表格行为列显示文字
    public String getTableActionColumnTitle();

    // 获取表格行为列的宽度
    public int getTableActionColumnWidth();

    // 获取表格是否轮询数据
    public int getTablePolling();

    // 获取全局排序规则
    public String getQueryOrder();

    // 获取列表页排序规则
    public String getIndexQueryOrder();

    // 获取导出数据排序规则
    public String getExportQueryOrder();

    // 获取是否具有导出功能
    public boolean isWithExport();

    // 设置单列字段
    public Resource<T> setField(Map<String, Object> field);

    // 字段
    public List<Object> fields(Context ctx);

    // 搜索
    public List<Object> searches(Context ctx);

    // 行为
    public List<Object> actions(Context ctx);

    // 菜单
    public Map<String, Object> menus(Context ctx);

    // 数据导出前回调
    public List<Object> beforeExporting(Context ctx, List<Map<String, Object>> list);

    // 数据导入前回调
    public List<List<Object>> beforeImporting(Context ctx, List<List<Object>> list);

    // 表格行内编辑执行完之后回调
    public Object afterEditable(Context ctx, Object id, String field, Object value);

    // 行为执行完之后回调
    public Object afterAction(Context ctx, String uriKey, Object query);

    // 页面组件渲染
    public Object pageComponentRender(Context ctx, Object body);

    // 页面容器组件渲染
    public Object pageContainerComponentRender(Context ctx, Object body);

    // 列表页表格主体
    public Object indexTableExtraRender(Context ctx);

    // 列表页工具栏
    public Object indexTableToolBar(Context ctx);

    // 列表页标题
    public String indexTableTitle(Context ctx);

    // 列表页组件渲染
    public Object indexComponentRender(Context ctx);

    // 组件渲染
    public Object indexRender(Context ctx);
}
