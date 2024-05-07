package io.quarkcloud.quarkstarter.service.admin.login;

import io.quarkcloud.quarkadmin.annotation.AdminLogin;
import io.quarkcloud.quarkadmin.service.Login;

@AdminLogin(title="QuarkJar")
public class Index extends Login {

    // 构造函数
    public Index() {
        api = "/api/admin/login/index/handle";
    }
}