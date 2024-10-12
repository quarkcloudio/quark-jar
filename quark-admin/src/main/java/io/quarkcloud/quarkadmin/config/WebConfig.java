package io.quarkcloud.quarkadmin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        // "/admin"重定向到"/admin/"
        registry.addViewController("/admin").setViewName("redirect:/admin/");

        // 添加二级目录的index.html映射
        registry.addViewController("/admin/").setViewName("forward:/admin/index.html");
    }
}