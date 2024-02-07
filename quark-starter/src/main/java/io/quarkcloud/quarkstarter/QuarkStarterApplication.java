package io.quarkcloud.quarkstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"io.quarkcloud.quarkstarter","io.quarkcloud.quarkadmin.engine"})
public class QuarkStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuarkStarterApplication.class, args);
    }

}
