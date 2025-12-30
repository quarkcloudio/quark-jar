package io.quarkcloud.quarkadmin.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import io.quarkcloud.quarkadmin.entity.UserEntity;
import io.quarkcloud.quarkadmin.service.AuthService;
import io.quarkcloud.quarkadmin.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import io.quarkcloud.quarkcore.service.Env;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserService userService;

    @Override
    public String login(Object username, Object password) {
        UserEntity adminInfo = userService.getByUsername((String) username);
        if (adminInfo == null) {
            throw new IllegalArgumentException("用户名或密码错误！");
        }

        // 创建 BCryptPasswordEncoder 实例
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches((String) password, adminInfo.getPassword())) {
            throw new IllegalArgumentException("用户名或密码错误！");
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

        return token;
    }

    @Override
    public UserEntity getUserInfo() {
        return userService.getById(getUserId());
    }

    @Override
    public Long getUserId() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }

        HttpServletRequest request = attributes.getRequest();
        String auth = request.getHeader("Authorization");
        String token = null;

        if (auth != null && auth.startsWith("Bearer ")) {
            token = auth.substring(7);
        }
        String appKey = Env.getProperty("app.key");
        if (!JWT.of(token).setKey(appKey.getBytes()).verify()) {
            return null;
        }
        try {
            JWTValidator.of(token).validateDate(DateUtil.date());
        } catch (Exception e) {
            return null;
        }
        final JWT jwt = JWTUtil.parseToken(token);
        return (Long) jwt.getPayload("id");
    }
}
