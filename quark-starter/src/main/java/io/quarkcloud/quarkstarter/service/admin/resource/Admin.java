package io.quarkcloud.quarkstarter.service.admin.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.component.form.Rule;
import io.quarkcloud.quarkadmin.entity.AdminEntity;
import io.quarkcloud.quarkadmin.mapper.AdminMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkstarter.service.admin.action.Create;
import io.quarkcloud.quarkstarter.service.admin.action.Delete;
import io.quarkcloud.quarkstarter.service.admin.action.Edit;
import io.quarkcloud.quarkstarter.service.admin.action.FormBack;
import io.quarkcloud.quarkstarter.service.admin.action.FormExtraBack;
import io.quarkcloud.quarkstarter.service.admin.action.FormReset;
import io.quarkcloud.quarkstarter.service.admin.action.FormSubmit;
import io.quarkcloud.quarkstarter.service.admin.search.Input;

@Component(value = "adminResource")
public class Admin extends ResourceImpl<AdminMapper,AdminEntity> {

    // 构造函数
    public Admin() {
        this.entity = new AdminEntity();
        this.title = "管理员";
        this.perPage = 10;
    }

    // 字段
    public List<Object> fields(Context context) {
        return Arrays.asList(
            Field.id("id", "ID"),
            Field.image("avatar", "头像").onlyOnForms(),
            Field.text("username", "用户名").setRules(Arrays.asList(
                Rule.required(true, "用户名必须填写"),
                Rule.min(6, "用户名不能少于6个字符"),
                Rule.max(20, "用户名不能超过20个字符")
            )),
            Field.text("nickname", "昵称"),
            Field.text("email", "邮箱"),
            Field.text("phone", "手机号"),
            Field.password("password", "密码").onlyOnForms()
        );
    }

    // 搜索表单
    public List<Object> searches(Context context) {
        return Arrays.asList(
            new Input<AdminEntity>("username", "用户名")
        );
    }
    
    // 行为
    public List<Object> actions(Context context) {
        return Arrays.asList(
            new Create<AdminMapper, AdminEntity>(this.title),
            new Edit<AdminMapper, AdminEntity>(),
            new Delete<AdminMapper, AdminEntity>(),
            new FormExtraBack<AdminMapper, AdminEntity>(),
            new FormSubmit<AdminMapper, AdminEntity>(),
            new FormReset<AdminMapper, AdminEntity>(),
            new FormBack<AdminMapper, AdminEntity>()
        );
    }

    // 编辑页面显示前回调
    public AdminEntity beforeEditing(Context context,AdminEntity data) {
        data.setPassword(null);
        return data;
    }
}
