package io.quarkcloud.quarkcore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class Env {

    // 注入属性
    @Autowired
    private Environment autowiredEnv;

    // 静态属性
    private static Environment env;

    // 初始化
    @PostConstruct
    private void beforeInit() {
        env = this.autowiredEnv;
    }

    public static String getProperty(String envKey) {
        return env.getProperty(envKey);
    }
}
