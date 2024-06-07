package io.quarkcloud.quarkstarter.service.admin.login;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import cn.hutool.jwt.JWT;
import io.quarkcloud.quarkadmin.annotation.AdminLogin;
import io.quarkcloud.quarkadmin.component.message.Message;
import io.quarkcloud.quarkadmin.entity.Admin;
import io.quarkcloud.quarkadmin.service.AdminService;
import io.quarkcloud.quarkadmin.template.Login;
import io.quarkcloud.quarkcore.service.Cache;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkcore.service.Env;

@AdminLogin(title="QuarkJar")
@Component
public class Index extends Login {

    @Autowired
    AdminService adminService;

    // 构造函数
    public Index() {
        api = "/api/admin/login/index/handle";
    }

    // 执行登录
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

        Object cacheCaptchaValue = Cache.getInstance().get(id, false);
        if (cacheCaptchaValue == null) {
            return Message.error("验证码错误！");
        }

        String getCacheCaptchaValue = (String) cacheCaptchaValue;
        if (!getCacheCaptchaValue.equalsIgnoreCase(captchaValue)) {
            return Message.error("验证码错误！");
        }

        Admin adminInfo = adminService.getByUsername((String) username);
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
                .setExpiresAt(new Date(System.currentTimeMillis() + 3600000))
                .sign();

        Map<String, String> result = new HashMap<>();
        result.put("token", token);

        return Message.success("登录成功！", result);
    }
}