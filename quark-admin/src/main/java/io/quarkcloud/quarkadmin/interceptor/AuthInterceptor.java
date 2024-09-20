package io.quarkcloud.quarkadmin.interceptor;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import io.quarkcloud.quarkadmin.entity.ActionLogEntity;
import io.quarkcloud.quarkadmin.service.ActionLogService;
import io.quarkcloud.quarkadmin.service.AdminService;
import io.quarkcloud.quarkcore.service.Env;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    
    // 注入AdminService
    @Autowired
    private AdminService adminService;

    // 注入ActionLogService
    @Autowired
    private ActionLogService actionLogService;

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
            boolean checkResult = adminService.checkPermission(adminId, request.getRequestURI(), request.getMethod());
            if (!checkResult) {
                errorResponse(response, HttpStatus.UNAUTHORIZED, "403 Forbidden");
                return false;
            }
        }

        // 记录日志
        ActionLogEntity actionLogEntity = new ActionLogEntity();
        actionLogEntity.setObjectId(adminId);
        actionLogEntity.setUsername(jwt.getPayload("username").toString());
        actionLogEntity.setUrl(request.getRequestURI());
        actionLogEntity.setType("admin");
        actionLogEntity.setCreatedAt(LocalDateTime.now().toString());
        actionLogEntity.setUpdatedAt(LocalDateTime.now().toString());
        String remoteAddr = request.getHeader("X-FORWARDED-FOR");
        if (remoteAddr == null || "".equals(remoteAddr)) {
            remoteAddr = request.getRemoteAddr();
        }
        actionLogEntity.setIp(remoteAddr);
        actionLogService.save(actionLogEntity);

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
