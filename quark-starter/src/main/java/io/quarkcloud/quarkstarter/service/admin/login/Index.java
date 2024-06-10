package io.quarkcloud.quarkstarter.service.admin.login;

import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.annotation.AdminLogin;
import io.quarkcloud.quarkadmin.template.Login;

@AdminLogin(title="QuarkJar")
@Component
public class Index extends Login {

    // 构造函数
    public Index() {
        api = "/api/admin/login/index/handle";
    }
}