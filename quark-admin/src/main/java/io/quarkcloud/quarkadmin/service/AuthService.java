package io.quarkcloud.quarkadmin.service;

import io.quarkcloud.quarkadmin.entity.UserEntity;

public interface AuthService {

    /**
     * 登录
     */
    public String login(Object username, Object password);

    /**
     * 获取用户信息
     */
    public UserEntity getUserInfo();

    /**
     * 获取用户ID
     */
    public Long getUserId();
}
