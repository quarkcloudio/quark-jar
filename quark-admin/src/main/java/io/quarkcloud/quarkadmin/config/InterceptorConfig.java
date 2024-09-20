package io.quarkcloud.quarkadmin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import io.quarkcloud.quarkadmin.interceptor.AuthInterceptor;
 
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    // 注入拦截器
    private final AuthInterceptor authInterceptor;

    @Autowired
    public InterceptorConfig(AuthInterceptor myInterceptor) {
        this.authInterceptor = myInterceptor;
    }

    /**
     * 添加拦截器配置
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/admin/**") // 对"/api/admin/"进行拦截，需token验证
                .excludePathPatterns("/api/admin/login/**"); // 放行接口
    }
}