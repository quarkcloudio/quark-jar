package io.quarkcloud.quarkcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"io.quarkcloud.quarkstarter","io.quarkcloud.quarkadmin"})
public class QuarkCoreApplication {

    public static void main(String[] args) {

        // SpringBoot服务
        SpringApplication.run(QuarkCoreApplication.class, args);
    }
}

