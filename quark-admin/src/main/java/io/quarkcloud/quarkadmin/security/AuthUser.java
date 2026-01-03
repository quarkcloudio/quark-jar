package io.quarkcloud.quarkadmin.security;

import cn.hutool.core.map.MapUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
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

    public static final String INFO_KEY_NICKNAME = "nickname";
    public static final String INFO_KEY_DEPT_ID = "deptId";

    /**
     * 用户编号
     */
    private Long id;

    /**
     * 额外的用户信息
     */
    private Map<String, String> info;

    /**
     * 授权范围
     */
    private List<String> scopes;

    /**
     * 过期时间
     */
    private LocalDateTime expiresTime;


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