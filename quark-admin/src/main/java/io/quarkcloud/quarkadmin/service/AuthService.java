package io.quarkcloud.quarkadmin.service;

import io.quarkcloud.quarkadmin.dto.AdminAuthLoginRespVo;
import io.quarkcloud.quarkadmin.security.AuthUser;

import org.springframework.security.core.Authentication;

public interface AuthService {

    /**
     * 登录
     */
    public AdminAuthLoginRespVo login(Object username, Object password);

    /**
     * 获得当前认证信息
     *
     * @return 认证信息
     */
    public Authentication getAuthentication();

    /**
     * 获取用户信息
     */
    public AuthUser getUserInfo();

    /**
     * 获取用户ID
     */
    public Long getUserId();
}
