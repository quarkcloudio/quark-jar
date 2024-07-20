package io.quarkcloud.quarkstarter.service.admin.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.entity.AdminEntity;
import io.quarkcloud.quarkadmin.mapper.AdminMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkstarter.service.admin.search.Input;

@Component(value = "adminResource")
public class Admin extends ResourceImpl<AdminMapper,AdminEntity> {

    // 构造函数
    public Admin() {
        this.title = "管理员";
        this.perPage = 10;
    }

    // 字段
    public List<Object> fields(Context ctx) {
        return Arrays.asList(
            Field.text("username", "用户名"),
            Field.text("nickname", "昵称")
        );
    }

    // 搜索表单
    public List<Object> searches(Context ctx) {
        return Arrays.asList(
            new Input<AdminEntity>("username", "用户名")
        );
    }
}
