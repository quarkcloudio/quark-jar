package io.quarkcloud.quarkadmin.interceptor;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.servlet.HandlerInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {
    
    @SuppressWarnings("null")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 创建一个Map对象，用于存储响应信息
        Map<String, Object> map = new HashMap<>();

        // 从请求头中获取令牌
        String token = request.getHeader("Authorization");

        map.put("state", false); // 设置状态为false

        // 将Map转化为JSON字符串（使用Jackson库）
        String json = new ObjectMapper().writeValueAsString(map);

        response.setContentType("application/json;charset=UTF-8"); // 设置响应的Content-Type
        response.getWriter().println(json); // 将JSON字符串写入响应中

        return false; // 不放行请求
    }
}
