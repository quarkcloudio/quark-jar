package io.quarkcloud.quarkadmin.service;

import io.quarkcloud.quarkadmin.commponent.LoginCommponent;

public class Login {

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

        // 登录接口
        this.api = "/api/admin/login/{resource}/handle";

        // 登录成功后跳转地址
        this.redirect = "/layout/index?api=/api/admin/dashboard/index/index";

        // 标题
        this.title = "QuarkJar";

        // 副标题
        this.subTitle = "信息丰富的世界里，唯一稀缺的就是人类的注意力";
    }

    // 获取登录接口
    public String getApi() {
        return api;
    }

    // 获取登录成功后跳转地址
    public String getRedirect() {
        return redirect;
    }

    
    // 获取登录页面Logo
    public String getLogo() {
        return logo;
    }

    // 获取标题
    public String getTitle() {
        return title;
    }

    // 获取副标题
    public String getSubTitle() {
        return subTitle;
    }

    // 获取表单内容
    public Object render() {
        LoginCommponent login = new LoginCommponent();

        login.setApi(api);
        login.setRedirect(redirect);
        login.setLogo(logo);
        login.setTitle(title);
        login.setSubTitle(subTitle);

        return login;
    }
}
