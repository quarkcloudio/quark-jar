package io.quarkcloud.quarkadmin.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import io.quarkcloud.quarkadmin.annotation.AdminLogin;
import io.quarkcloud.quarkadmin.commponent.form.Field;
import io.quarkcloud.quarkadmin.commponent.form.Rule;
import io.quarkcloud.quarkadmin.commponent.icon.Icon;
import io.quarkcloud.quarkadmin.commponent.message.Message;
import io.quarkcloud.quarkadmin.entity.Admin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
 
    // 获取字段
    public Object[] fields(HttpServletRequest request) {

        return new Object[] {
            Field.
            text("username").
            setRules(new Rule[]{
				Rule.required(true, "请输入用户名"),
			}).
            setPlaceholder("用户名").
            setWidth("100%").
            setSize("large").
            setPrefix(new Icon().setType("icon-user")),

            Field.password("password").
            setRules(new Rule[]{
				Rule.required(true, "请输入密码"),
			}).
            setPlaceholder("密码").
            setWidth("100%").
            setSize("large").
            setPrefix(new Icon().setType("icon-lock")),

            Field.imageCaptcha("captcha").
            setRules(new Rule[]{
				Rule.required(true, "请输入验证码"),
			}).
            setPlaceholder("验证码").
            setWidth("100%").
            setSize("large").
            setCaptchaIdUrl("/api/admin/login/index/captchaId").
            setCaptchaUrl("/api/admin/login/index/captcha/{id}").
            setPrefix(new Icon().setType("icon-safetycertificate")),
        };
    }

    // 获取验证码ID
    public Object captchaId(HttpServletRequest request) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("captchaId", "default");

        return Message.success("获取成功！", map);
    }

    // 获取验证码
    public void captcha(HttpServletRequest request, HttpServletResponse response) {

        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(150, 40, 5, 4);

        try {
            lineCaptcha.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 包裹在组件内的创建页字段
    public Object fieldsWithinComponents(HttpServletRequest request) {

        // 获取字段
        Object[] fields = this.fields(request);

        // 解析创建页表单组件内的字段
        Object items = this.formFieldsParser(request, fields);

        return items;
    }

    // 解析创建页表单组件内的字段
    public Object formFieldsParser(HttpServletRequest request, Object fields) {
        if (fields instanceof Object[]) {
            Arrays.stream((Object[]) fields).forEach(field -> {
                boolean hasBody = false;
                Object body = new Object();
                try {
                    body = field.getClass().getField("body").get(field);
                    hasBody = true;
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    hasBody = false;
                }
                if (hasBody) {
                    this.formFieldsParser(request, body);
                } else {
                    try {
                        Object component = field.getClass().getSuperclass().getDeclaredField("component").get(field);
                        String getComponent = (String) component;
                        if(getComponent.contains("Field")) {
                            Method method = field.getClass().getMethod("buildFrontendRules",String.class);
                            method.invoke(field, request.getQueryString());
                        }
                    } catch (NoSuchFieldException | NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return fields;
    }

    // 执行登录
    public Object handle(HttpServletRequest request) {
        String username =request.getParameter("username");
        String password =request.getParameter("password");
        String captcha =request.getParameter("captcha");

        // 检查用户名
        if (username.isEmpty()) {
            return Message.error("用户名不能为空！");
        }

        // 检查密码
        if (password.isEmpty()) {
            return Message.error("密码不能为空！");
        }

        Admin admin = new Admin();
        Admin adminInfo = admin.getByUsername("administrator1");
        if (adminInfo==null) {
            return Message.error("用户名或密码错误！");
        }

        return admin.getByUsername("administrator1");
    }

    // 组件渲染
    public Object render(HttpServletRequest request) {

        // 登录表单组件
        io.quarkcloud.quarkadmin.commponent.login.Login login = new io.quarkcloud.quarkadmin.commponent.login.Login();
        
        // 获取接口
        api = this.getApi();

        // 获取重定向
        redirect = this.getRedirect();

        // 获取标题
        title = this.getTitle();

        // 获取子标题
        subTitle = this.getSubTitle();

        // 获取组件内的字段
        Object body = this.fieldsWithinComponents(request);

        // 设置组件属性
        login.
        setApi(api).
        setRedirect(redirect).
        setLogo(logo).
        setTitle(title).
        setSubTitle(subTitle).
        setBody(body);

        return login;
    }
}
