package io.quarkcloud.quarkstarter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"io.quarkcloud.quarkstarter","io.quarkcloud.quarkadmin.engine"})
@MapperScan(basePackages = {"io.quarkcloud.quarkadmin.mapper"})
public class QuarkStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuarkStarterApplication.class, args);
    }

}
