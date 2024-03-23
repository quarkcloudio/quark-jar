package io.quarkcloud.quarkstarter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.quarkcloud.quarkadmin.annotation.QuarkAdmin;

@SpringBootApplication(scanBasePackages = {"io.quarkcloud.quarkstarter","io.quarkcloud.quarkadmin"})
@MapperScan(basePackages = {"io.quarkcloud.quarkadmin.mapper"})
public class QuarkStarterApplication {

    @QuarkAdmin({"abcd"})
    public static void main(String[] args) {
        SpringApplication.run(QuarkStarterApplication.class, args);
    }
}
