package io.quarkcloud.quarkstarter.service.admin.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;

@Component(value = "adminResource")
public class Admin extends ResourceImpl {

    public Admin() {
        this.title = "管理员";
    }

    // 字段
    public List<Object> fields(Context ctx) {
        return Arrays.asList(
            Field.text("username", "用户名"),
            Field.text("nickname", "昵称")
        );
    }
}
