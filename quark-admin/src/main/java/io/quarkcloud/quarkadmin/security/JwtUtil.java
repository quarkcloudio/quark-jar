package io.quarkcloud.quarkadmin.security;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import io.quarkcloud.quarkadmin.entity.UserEntity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${app.key:defaultSecretKey}")
    private String appKey;

    /** 过期时间 24 小时 */
    private static final long EXPIRE_TIME = 24 * 1000 * 60 * 60;

    /** 生成 Token */
    public String generateToken(UserEntity userInfo) {
        String token = JWT.create()
                .setPayload("id", userInfo.getId())
                .setPayload("username", userInfo.getUsername())
                .setPayload("nickname", userInfo.getNickname())
                .setPayload("sex", userInfo.getSex())
                .setPayload("email", userInfo.getEmail())
                .setPayload("phone", userInfo.getPhone())
                .setPayload("avatar", userInfo.getAvatar())
                .setPayload("guard_name", "admin")
                .setKey(appKey.getBytes())
                .setExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .sign();

        return token;
    }

    /** 校验 Token */
    public boolean validateToken(String token) {
        return JWTUtil.verify(token, appKey.getBytes());
    }

    /** 获取用户ID */
    public Long getUserId(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        Object idObj = jwt.getPayload("id");
        if (idObj == null) {
            return null;
        }
        // 安全地转换为 Long 类型
        if (idObj instanceof Number) {
            return ((Number) idObj).longValue();
        } else if (idObj instanceof String) {
            try {
                return Long.parseLong((String) idObj);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}