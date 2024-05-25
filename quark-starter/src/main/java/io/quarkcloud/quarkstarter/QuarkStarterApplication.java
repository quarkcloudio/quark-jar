package io.quarkcloud.quarkstarter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import io.quarkcloud.quarkcore.QuarkApplication;
import io.quarkcloud.quarkcore.autoconfigure.QuarkAdminApplication;

@SpringBootApplication(scanBasePackages = {"io.quarkcloud.quarkstarter","io.quarkcloud.quarkadmin"})
@QuarkAdminApplication(scanBasePackages = {"io.quarkcloud.quarkstarter.service.admin"})
@MapperScan(basePackages = {"io.quarkcloud.quarkstarter.mapper","io.quarkcloud.quarkadmin.mapper"})
@ComponentScan(basePackages = {"io.quarkcloud.quarkstarter","io.quarkcloud.quarkcore.service","io.quarkcloud.quarkadmin"})
public class QuarkStarterApplication {

    public static void main(String[] args) {

        // Quark服务
        QuarkApplication.run(QuarkStarterApplication.class, args);

        // SpringBoot服务
        SpringApplication.run(QuarkStarterApplication.class, args);
    }
}
