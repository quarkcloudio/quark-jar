package io.quarkcloud.quarkadmin.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.quarkcloud.quarkadmin.interceptor.AuthInterceptor;
 
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 注入拦截器
    private final AuthInterceptor authInterceptor;

    @Autowired
    public WebConfig(AuthInterceptor myInterceptor) {
        this.authInterceptor = myInterceptor;
    }

    /**
     * 添加拦截器配置，对"/api/admin/"进行拦截，通过URL进行权限控制
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/admin/**")
                .excludePathPatterns("/api/admin/auth/**");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        // "/admin"重定向到"/admin/"
        registry.addViewController("/admin").setViewName("redirect:/admin/");

        // 添加二级目录的index.html映射
        registry.addViewController("/admin/").setViewName("forward:/admin/index.html");
    }
}