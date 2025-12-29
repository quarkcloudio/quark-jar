package io.quarkcloud.quarkadmin.template.auth.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.IdUtil;
import cn.hutool.jwt.JWT;
import io.quarkcloud.quarkadmin.annotation.AdminAuth;
import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.component.form.Rule;
import io.quarkcloud.quarkadmin.component.icon.Icon;
import io.quarkcloud.quarkadmin.entity.UserEntity;
import io.quarkcloud.quarkadmin.service.UserService;
import io.quarkcloud.quarkadmin.template.auth.Auth;
import io.quarkcloud.quarkcore.service.Cache;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkcore.service.Env;
import io.quarkcloud.quarkcore.service.Redis;
import io.quarkcloud.quarkcore.common.Message;

public class AuthImpl implements Auth {

    @Autowired
    UserService adminService;

    @Autowired
    Redis redisClient;

    // 注解实例
    protected AdminAuth annotationClass = null;

    // 登录接口
    public String api;

    // 登录成功后跳转地址
    public String redirect;

    // 登录页面Logo
    public String logo;

    // 标题
    public String title;

    // 构造函数
    public AuthImpl() {

        // 获取注解对象
        if (getClass().isAnnotationPresent(AdminAuth.class)) {
            annotationClass = getClass().getAnnotation(AdminAuth.class);
        }

        // 登录接口
        api = "/api/admin/auth/{resource}/login";

        // 登录成功后跳转地址
        redirect = "/layout/index?api=/api/admin/dashboard/index/index";

        // 标题
        title = "QuarkJar";
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

    // 获取字段
    public List<Object> fields(Context context) {
        return Arrays.asList(
            Field.text("username").
            setRules(Arrays.asList(
                Rule.required("请输入用户名"
            ))).
            setPlaceholder("用户名").
            setWidth("100%").
            setSize("large").
            setPrefix(new Icon().setType("ant-design:user-outlined")),

            Field.password("password").
            setRules(Arrays.asList(
                Rule.required("请输入密码")
            )).
            setPlaceholder("密码").
            setWidth("100%").
            setSize("large").
            setPrefix(new Icon().setType("ant-design:lock-outlined")),

            Field.imageCaptcha("captcha").
            setRules(Arrays.asList(
                Rule.required("请输入验证码")
            )).
            setPlaceholder("验证码").
            setWidth("100%").
            setSize("large").
            setCaptchaUrl("/api/admin/auth/index/captcha").
            setPrefix(new Icon().setType("ant-design:safety-certificate-outlined"))
        );
    }

    // 获取验证码
    public Object captcha(Context context) {
        String id = IdUtil.simpleUUID();
        String redisHost = Env.getProperty("spring.redis.host");

        // 定义图形验证码的长、宽、验证码字符数、干扰线宽度
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(150, 40, 5, 4);
        if (redisHost !=null && !redisHost.isEmpty()) {
            redisClient.setValue(id, lineCaptcha.getCode(), 5, TimeUnit.MINUTES);
        } else {
            // 将验证码放到缓存
            Cache.getInstance().put(id, lineCaptcha.getCode());
        }

        Map<String, Object> data = new HashMap<>();
        data.put("captchaEnabled", true);
        data.put("img", lineCaptcha.getImageBase64());
        data.put("uuid", id);

        return Message.success("请求成功", data);
    }

    // 包裹在组件内的创建页字段
    public Object fieldsWithinComponents(Context context) {

        // 获取字段
        List<Object> fields = this.fields(context);

        // 解析创建页表单组件内的字段
        Object items = this.formFieldsParser(context, fields);

        return items;
    }

    // 解析创建页表单组件内的字段
    @SuppressWarnings("unchecked")
    public Object formFieldsParser(Context context, Object fields) {
        if (fields instanceof List) {
            ((List<Object>) fields).stream().forEach(field -> {
                boolean hasBody = false;
                Object body = new Object();
                try {
                    body = field.getClass().getField("body").get(field);
                    hasBody = true;
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    hasBody = false;
                }
                if (hasBody) {
                    this.formFieldsParser(context, body);
                } else {
                    try {
                        Object component = field.getClass().getSuperclass().getDeclaredField("component").get(field);
                        String getComponent = (String) component;
                        if (getComponent.contains("Field")) {
                            Method method = field.getClass().getMethod("buildFrontendRules", String.class);
                            method.invoke(field, context.getRequestURI());
                        }
                    } catch (NoSuchFieldException | NoSuchMethodException | SecurityException | IllegalAccessException
                            | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return fields;
    }

    // 执行登录
    @SuppressWarnings("unchecked")
    public Object login(Context context) {
        Map<String, Object> map = context.getRequestBody(Map.class);
        if (map.isEmpty()) {
            return Message.error("参数错误！");
        }

        Object username = map.get("username");
        Object password = map.get("password");
        Object captcha = map.get("captcha");

        // 检查用户名
        if (username == null) {
            return Message.error("用户名不能为空！");
        }

        // 检查密码
        if (password == null) {
            return Message.error("密码不能为空！");
        }

        // 检查验证码
        if (captcha == null) {
            return Message.error("验证码不能为空！");
        }

        Map<String, String> getCaptcha = (Map<String, String>) captcha;
        String id = getCaptcha.get("id");
        String captchaValue = getCaptcha.get("value");
        if (id.isEmpty()) {
            return Message.error("验证码ID不能为空！");
        }
        if (captchaValue.isEmpty()) {
            return Message.error("验证码不能为空！");
        }

        Object cacheCaptchaValue = null;
        String redisHost = Env.getProperty("spring.redis.host");
        if (redisHost !=null && !redisHost.isEmpty()) {
            cacheCaptchaValue = redisClient.getValueAndDelete(id);
        } else {
            cacheCaptchaValue = Cache.getInstance().get(id, false);
        }

        if (cacheCaptchaValue == null) {
            return Message.error("验证码错误！");
        }

        String getCacheCaptchaValue = (String) cacheCaptchaValue;
        if (!getCacheCaptchaValue.equalsIgnoreCase(captchaValue)) {
            return Message.error("验证码错误！");
        }

        UserEntity adminInfo = adminService.getByUsername((String) username);
        if (adminInfo == null) {
            return Message.error("用户名或密码错误！");
        }

        // 创建 BCryptPasswordEncoder 实例
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(password);
        System.out.println(adminInfo.getPassword());
        if (!encoder.matches((String) password, adminInfo.getPassword())) {
            return Message.error("用户名或密码错误！");
        }

        // JWT密钥
        String appKey = Env.getProperty("app.key");
        String token = JWT.create()
                .setPayload("id", adminInfo.getId())
                .setPayload("username", adminInfo.getUsername())
                .setPayload("nickname", adminInfo.getNickname())
                .setPayload("sex", adminInfo.getSex())
                .setPayload("email", adminInfo.getEmail())
                .setPayload("phone", adminInfo.getPhone())
                .setPayload("avatar", adminInfo.getAvatar())
                .setPayload("guard_name", "admin")
                .setKey(appKey.getBytes())
                .setExpiresAt(new Date(System.currentTimeMillis() + (3600000 * 24)))
                .sign();

        Map<String, String> result = new HashMap<>();
        result.put("token", token);

        return Message.success("登录成功！", result);
    }
    
    // 执行退出
    public Object logout(Context context) {
        return Message.success("退出成功！","/");
    }

    // 组件渲染
    public Object render(Context context) {

        // 登录表单组件
        io.quarkcloud.quarkadmin.component.login.Login login = new io.quarkcloud.quarkadmin.component.login.Login();

        // 获取接口
        api = this.getApi();

        // 获取重定向
        redirect = this.getRedirect();

        // 获取标题
        title = this.getTitle();

        // 获取组件内的字段
        Object body = this.fieldsWithinComponents(context);

        // 设置组件属性
        login.setApi(api).setRedirect(redirect).setLogo(logo).setTitle(title).setBody(body);

        return Message.success(login);
    }
}
