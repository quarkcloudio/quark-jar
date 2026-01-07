package io.quarkcloud.quarkadmin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.quarkcloud.quarkadmin.dto.AdminAuthLoginRespVo;
import io.quarkcloud.quarkadmin.entity.UserEntity;
import io.quarkcloud.quarkadmin.security.AuthUser;
import io.quarkcloud.quarkadmin.security.JwtUtil;
import io.quarkcloud.quarkadmin.service.AuthService;
import io.quarkcloud.quarkadmin.service.UserService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public AdminAuthLoginRespVo login(Object username, Object password) {
        UserEntity adminInfo = userService.getByUsername((String) username);
        if (adminInfo == null) {
            throw new IllegalArgumentException("用户名或密码错误！");
        }

        // 创建 BCryptPasswordEncoder 实例
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches((String) password, adminInfo.getPassword())) {
            throw new IllegalArgumentException("用户名或密码错误！");
        }

        String token = jwtUtil.generateToken(adminInfo);

        return  AdminAuthLoginRespVo.builder().token(token).build();
    }

    /**
     * 获得当前认证信息
     *
     * @return 认证信息
     */
    public Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }
        return context.getAuthentication();
    }

    @Override
    public AuthUser getUserInfo() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return authentication.getPrincipal() instanceof AuthUser ? (AuthUser) authentication.getPrincipal() : null;
    }

    @Override
    public Long getUserId() {
        AuthUser authUser = getUserInfo();
        return authUser != null ? authUser.getId() : null;
    }
}
