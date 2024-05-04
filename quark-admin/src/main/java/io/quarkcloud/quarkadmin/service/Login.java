package io.quarkcloud.quarkadmin.service;

import io.quarkcloud.quarkadmin.commponent.LoginCommponent;
import io.quarkcloud.quarkadmin.annotation.AdminLogin;

public class Login {

    // 注解实例
    protected AdminLogin annotationClass = null;

    // 登录接口
    public String api;

    // 登录成功后跳转地址
    public String redirect;

    // 登录页面Logo
    public String logo;

    // 标题
    public String title;

    // 副标题
    public String subTitle;

    // 构造函数
    public Login() {

        // 获取注解对象
        if (getClass().isAnnotationPresent(AdminLogin.class)) {
            annotationClass = getClass().getAnnotation(AdminLogin.class);
        }

        // 登录接口
        api = "/api/admin/login/{resource}/handle";

        // 登录成功后跳转地址
        redirect = "/layout/index?api=/api/admin/dashboard/index/index";

        // 标题
        title = "QuarkJar";

        // 副标题
        subTitle = "信息丰富的世界里，唯一稀缺的就是人类的注意力";
    }

    // 获取接口
    public String getApi() {

        // 检查是否存在注解
        if (annotationClass == null) {
            return api;
        }

        // 注解值为空返回默认值
        if (annotationClass.api().isEmpty()) {
            return api;
        }

        // 获取注解值
        return annotationClass.api();
    }

    // 登录成功后跳转地址
    public String getRedirect() {

        // 检查是否存在注解
        if (annotationClass == null) {
            return redirect;
        }
 
        // 注解值为空返回默认值
        if (annotationClass.redirect().isEmpty()) {
            return redirect;
        }

        // 获取注解值
        return annotationClass.redirect();
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

    // 获取子标题
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
 
    // 获取表单内容
    public Object render() {

        // 登录表单组件
        LoginCommponent login = new LoginCommponent();
        
        // 获取接口
        api = this.getApi();

        // 获取重定向
        redirect = this.getRedirect();

        // 获取标题
        title = this.getTitle();

        // 获取子标题
        subTitle = this.getSubTitle();

        login.setApi(api);
        login.setRedirect(redirect);
        login.setLogo(logo);
        login.setTitle(title);
        login.setSubTitle(subTitle);

        return login;
    }
}
