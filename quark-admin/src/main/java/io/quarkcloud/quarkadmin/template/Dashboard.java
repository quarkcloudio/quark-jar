package io.quarkcloud.quarkadmin.template;

import io.quarkcloud.quarkadmin.annotation.AdminDashboard;
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
        title = "QuarkJar";
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
    public boolean getBackIcon() {

        // 检查是否存在注解
        if (annotationClass == null) {
            return backIcon;
        }

        // 获取注解值
        return annotationClass.backIcon();
    }

    // 组件渲染
    public Object render(Context context) {

        return "admin/dashboard/index";
    }
}
