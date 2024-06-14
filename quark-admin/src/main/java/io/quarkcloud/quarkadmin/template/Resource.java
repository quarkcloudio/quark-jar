package io.quarkcloud.quarkadmin.template;

import java.util.Map;

import io.quarkcloud.quarkadmin.annotation.AdminResource;
import io.quarkcloud.quarkadmin.component.pagecontainer.PageContainer;
import io.quarkcloud.quarkadmin.component.pagecontainer.PageHeader;
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

        // 检查是否存在注解
        if (annotationClass == null) {
            return title;
        }

        // 注解值为空返回默认值
        if (annotationClass.title().isEmpty()) {
            return title;
        }

        // 获取注解值
        return annotationClass.title();
    }

    // 获取页面子标题
    public String getSubTitle() {

        // 检查是否存在注解
        if (annotationClass == null) {
            return subTitle;
        }

        // 注解值为空返回默认值
        if (annotationClass.subTitle().isEmpty()) {
            return subTitle;
        }

        // 获取注解值
        return annotationClass.subTitle();
    }

    // 获取页面是否携带返回Icon
    public boolean isBackIcon() {

        // 检查是否存在注解
        if (annotationClass == null) {
            return backIcon;
        }

        // 获取注解值
        return annotationClass.backIcon();
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

    // 组件渲染
    public Object indexRender(Context context) {
        return this.pageComponentRender(context, "abcd");
    }
}
