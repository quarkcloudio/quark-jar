package io.quarkcloud.quarkstarter.admin.auth;

import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.annotation.AdminAuth;
import io.quarkcloud.quarkadmin.template.auth.impl.AuthImpl;

@AdminAuth(title="QuarkJar")
@Component(value = "authIndex")
public class Index extends AuthImpl {

    // 构造函数
    public Index() {
        loginApi = "/api/admin/auth/index/login";
    }
}