package io.quarkcloud.quarkstarter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.quarkcloud.quarkadmin.autoconfigure.QuarkAdminApplication;

@SpringBootApplication(scanBasePackages = {"io.quarkcloud.quarkstarter","io.quarkcloud.quarkadmin"})
@QuarkAdminApplication({"abcd"})
@MapperScan(basePackages = {"io.quarkcloud.quarkadmin.mapper"})
public class QuarkStarterApplication {

    public static void main(String[] args) {

        // Quark服务
        QuarkApplication.run(QuarkStarterApplication.class, args);

        // SpringBoot服务
        SpringApplication.run(QuarkStarterApplication.class, args);
    }
}
