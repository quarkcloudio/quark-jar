package io.quarkcloud.quarkstarter.service.admin.login;

import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.annotation.AdminLogin;
import io.quarkcloud.quarkadmin.template.login.impl.LoginImpl;

@AdminLogin(title="QuarkJar")
@Component(value = "loginIndex")
public class Index extends LoginImpl {

    // 构造函数
    public Index() {
        api = "/api/admin/login/index/handle";
    }
}