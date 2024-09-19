package io.quarkcloud.quarkadmin.template.login.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.IdUtil;
import cn.hutool.jwt.JWT;
import io.quarkcloud.quarkadmin.annotation.AdminLogin;
import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.component.form.Rule;
import io.quarkcloud.quarkadmin.component.icon.Icon;
import io.quarkcloud.quarkadmin.component.message.Message;
import io.quarkcloud.quarkadmin.entity.AdminEntity;
import io.quarkcloud.quarkadmin.service.AdminService;
import io.quarkcloud.quarkadmin.template.login.Login;
import io.quarkcloud.quarkcore.service.Cache;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkcore.service.Env;
import io.quarkcloud.quarkcore.service.Redis;

public class LoginImpl implements Login {

    @Autowired
    AdminService adminService;

    @Autowired
    Redis redisClient;

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
    public LoginImpl() {

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
    public List<Object> fields(Context context) {
        return Arrays.asList(
            Field.text("username").
            setRules(Arrays.asList(
                Rule.required(true, "请输入用户名"
            ))).
            setPlaceholder("用户名").
            setWidth("100%").
            setSize("large").
            setPrefix(new Icon().setType("icon-user")),

            Field.password("password").
            setRules(Arrays.asList(
                Rule.required(true, "请输入密码")
            )).
            setPlaceholder("密码").
            setWidth("100%").
            setSize("large").
            setPrefix(new Icon().setType("icon-lock")),

            Field.imageCaptcha("captcha").
            setRules(Arrays.asList(
                Rule.required(true, "请输入验证码")
            )).
            setPlaceholder("验证码").
            setWidth("100%").
            setSize("large").
            setCaptchaIdUrl("/api/admin/login/index/captchaId").
            setCaptchaUrl("/api/admin/login/index/captcha/{id}").
            setPrefix(new Icon().setType("icon-safetycertificate"))
        );
    }

    // 获取验证码ID
    public Object captchaId(Context context) {
        Map<String, String> map = new HashMap<String, String>();

        // 生成验证码ID
        String simpleUUID = IdUtil.simpleUUID();

        String redisHost = Env.getProperty("spring.redis.host");
        if (redisHost !=null && !redisHost.isEmpty()) {
            redisClient.setValue(simpleUUID, "uninitialized");
        } else {
            // 放入缓存
            Cache.getInstance().put(simpleUUID, "uninitialized");
        }

        // 返回验证码ID
        map.put("captchaId", simpleUUID);

        return Message.success("获取成功！", null, map);
    }

    // 获取验证码
    public void captcha(Context context) {
        String id = context.getPathVariable("id");
        if (id.isEmpty()) {
            return;
        }

        Object cacheValue = null;
        String redisHost = Env.getProperty("spring.redis.host");
        if (redisHost !=null && !redisHost.isEmpty()) {
            cacheValue = redisClient.getValueAndDelete(id);
        } else {
            // 获取缓存
            cacheValue = Cache.getInstance().get(id);
        }
        if (cacheValue == null) {
            return;
        }
        if (!cacheValue.equals("uninitialized")) {
            return;
        }

        // 定义图形验证码的长、宽、验证码字符数、干扰线宽度
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(150, 40, 5, 4);
        if (redisHost !=null && !redisHost.isEmpty()) {
            redisClient.setValue(id, lineCaptcha.getCode());
        } else {
            // 将验证码放到缓存
            Cache.getInstance().put(id, lineCaptcha.getCode());
        }

        try {
            lineCaptcha.write(context.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                            method.invoke(field, context.getQueryString());
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
    public Object handle(Context context) {
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

        AdminEntity adminInfo = adminService.getByUsername((String) username);
        if (adminInfo == null) {
            return Message.error("用户名或密码错误！");
        }

        // 创建 BCryptPasswordEncoder 实例
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
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

        return Message.success("登录成功！", null, result);
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

        // 获取子标题
        subTitle = this.getSubTitle();

        // 获取组件内的字段
        Object body = this.fieldsWithinComponents(context);

        // 设置组件属性
        login.setApi(api).setRedirect(redirect).setLogo(logo).setTitle(title).setSubTitle(subTitle).setBody(body);

        return login;
    }
}
