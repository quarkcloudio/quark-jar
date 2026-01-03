package io.quarkcloud.quarkadmin.security;

import cn.hutool.core.map.MapUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录用户信息
 *
 * @author tangtanglove
 */
@Data
public class AuthUser {

    /**
     * 用户编号
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private Object avatar;

    /**
     * Email
     */
    private String email;

    /**
     * 手机
     */
    private String phone;

    /**
     * 额外的用户信息
     */
    private Map<String, String> info;

    /**
     * 按钮权限
     */
    private List<String> buttons;

    /**
     * 角色权限
     */
    private List<String> roles;

    // ========== 上下文 ==========
    /**
     * 上下文字段，不进行持久化
     *
     * 1. 用于基于 AuthUser 维度的临时缓存
     */
    @JsonIgnore
    private Map<String, Object> context;

    public void setContext(String key, Object value) {
        if (context == null) {
            context = new HashMap<>();
        }
        context.put(key, value);
    }

    public <T> T getContext(String key, Class<T> type) {
        return MapUtil.get(context, key, type);
    }
}