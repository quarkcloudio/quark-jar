package io.quarkcloud.quarkstarter.service.admin.login;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import cn.hutool.jwt.JWT;
import io.quarkcloud.quarkadmin.annotation.AdminLogin;
import io.quarkcloud.quarkadmin.component.message.Message;
import io.quarkcloud.quarkadmin.entity.Admin;
import io.quarkcloud.quarkadmin.service.AdminService;
import io.quarkcloud.quarkadmin.template.Login;
import io.quarkcloud.quarkcore.service.Cache;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkcore.service.Env;

@AdminLogin(title="QuarkJar")
@Component
public class Index extends Login {

    // 构造函数
    public Index() {
        api = "/api/admin/login/index/handle";
    }
}