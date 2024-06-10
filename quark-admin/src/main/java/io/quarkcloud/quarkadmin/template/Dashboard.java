package io.quarkcloud.quarkadmin.template;

import java.util.List;

import io.quarkcloud.quarkadmin.annotation.AdminDashboard;
import io.quarkcloud.quarkadmin.component.pagecontainer.PageContainer;
import io.quarkcloud.quarkadmin.component.pagecontainer.PageHeader;
import io.quarkcloud.quarkcore.service.Context;

public class Dashboard {

    // 注解实例
    protected AdminDashboard annotationClass = null;

    // 页面标题
    public String title;

    // 页面子标题
    public String subTitle;

    // 页面是否携带返回Icon
	public boolean backIcon;

    // 构造函数
    public Dashboard() {

        // 获取注解对象
        if (getClass().isAnnotationPresent(AdminDashboard.class)) {
            annotationClass = getClass().getAnnotation(AdminDashboard.class);
        }
    
        // 页面标题
        title = "仪表盘";
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

    // 内容
    public List<Object> cards(Context ctx) {
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

    // 组件渲染
    public Object render(Context context) {
        Object component = this.pageComponentRender(context, "abcd");

        return component;
    }
}
