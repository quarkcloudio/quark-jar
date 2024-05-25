package io.quarkcloud.quarkadmin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import io.quarkcloud.quarkadmin.interceptor.AuthInterceptor;
 
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    /**
     * 添加拦截器配置
     */
    @SuppressWarnings("null")
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/api/admin/**") // 对"/api/admin/"进行拦截，需token验证
                .excludePathPatterns("/api/admin/login/**"); // 放行接口
    }
}