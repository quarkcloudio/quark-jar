package io.quarkcloud.quarkadmin.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import io.quarkcloud.quarkcore.service.Env;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {
    
    @SuppressWarnings("null")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取令牌
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            errorResponse(response, HttpStatus.UNAUTHORIZED, "401 Unauthozied");
            return false;
        }

        String token = authHeader.substring("Bearer ".length());

        // JWT密钥
        String appKey = Env.getProperty("app.key");

        // 验证Token是否合法
        if (!JWT.of(token).setKey(appKey.getBytes()).verify()) {
            errorResponse(response, HttpStatus.UNAUTHORIZED, "token is not valid yet");
            return false;
        }

        // 验证Token是否过期
        try {
            JWTValidator.of(token).validateDate(DateUtil.date());
        } catch (Exception e) {
            errorResponse(response, HttpStatus.UNAUTHORIZED, "token is expired");
            return false;
        }

        final JWT jwt = JWTUtil.parseToken(token);
        String guardName = (String) jwt.getPayload("guard_name");
        if (!guardName.equals("admin")) {
            errorResponse(response, HttpStatus.UNAUTHORIZED, "401 Unauthozied");
            return false;
        }

        Long adminId = Long.parseLong(jwt.getPayload("id").toString());
        if (adminId != 1) {
            
        }

        return true; // 放行请求
    }

    private void errorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        // 设置响应状态码和内容类型
        response.setStatus(status.value());
        response.setContentType("application/json");

        // 创建错误信息对象
        Map<String, Object> map = new HashMap<>();
        map.put("code", 10001);
        map.put("msg", message);

        // 将错误信息对象序列化为 JSON 字符串并写入响应
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), map);
    }
}
