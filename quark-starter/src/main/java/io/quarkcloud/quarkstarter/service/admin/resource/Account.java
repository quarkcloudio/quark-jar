package io.quarkcloud.quarkstarter.service.admin.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.hutool.jwt.JWT;
import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.component.form.Rule;
import io.quarkcloud.quarkadmin.entity.AdminEntity;
import io.quarkcloud.quarkadmin.mapper.AdminMapper;
import io.quarkcloud.quarkadmin.service.AdminService;
import io.quarkcloud.quarkadmin.template.resource.core.ResolveAction;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkstarter.service.admin.action.ChangeAccount;
import io.quarkcloud.quarkstarter.service.admin.action.FormBack;
import io.quarkcloud.quarkstarter.service.admin.action.FormExtraBack;
import io.quarkcloud.quarkstarter.service.admin.action.FormReset;
import io.quarkcloud.quarkstarter.service.admin.action.FormSubmit;

@Component(value = "accountResource")
public class Account extends ResourceImpl<AdminMapper, AdminEntity> {

    @Autowired
    private AdminService adminService;

    // 构造函数
    public Account() {
        this.entity = new AdminEntity();
        this.title = "个人设置";
    }

    public String formApi(Context context) {
        ChangeAccount<AdminMapper, AdminEntity> changeAccount= new ChangeAccount<AdminMapper, AdminEntity>();
        List<String> params = changeAccount.getApiParams();
        String uriKey = changeAccount.getUriKey(changeAccount);
        return new ResolveAction<AdminMapper, AdminEntity>().buildActionApi(context, params, uriKey);
    }

    // 字段
    public List<Object> fields(Context context) {
        return Arrays.asList(
            Field.image("avatar", "头像"),

            Field.text("nickname", "昵称")
                .setEditable(true)
                .setRules(Arrays.asList(
                    new Rule().setRequired().setMessage("昵称必须填写")
                )),

            Field.text("email", "邮箱")
                .setRules(Arrays.asList(
                    new Rule().setRequired().setMessage("邮箱必须填写")
                ))
                .setCreationRules(Arrays.asList(
                    new Rule().setUnique("admins", "email").setMessage("邮箱已存在")
                )),

            Field.text("phone", "手机号")
                .setRules(Arrays.asList(
                    new Rule().setRequired().setMessage("手机号必须填写")
                ))
                .setCreationRules(Arrays.asList(
                    new Rule().setUnique("admins", "phone").setMessage("手机号已存在")
                )),

            Field.radio("sex", "性别")
                .setOptions(Arrays.asList(
                    Field.radioOption("男", 1),
                    Field.radioOption("女", 2)
                ))
                .setDefaultValue(1),

            Field.password("password", "密码")
        );
    }

    // 行为
    public List<Object> actions(Context context) {
        return Arrays.asList(
            new ChangeAccount<AdminMapper, AdminEntity>(),
            new FormExtraBack<AdminMapper, AdminEntity>(),
            new FormSubmit<AdminMapper, AdminEntity>(),
            new FormReset<AdminMapper, AdminEntity>(),
            new FormBack<AdminMapper, AdminEntity>()
        );
    }

    // 创建前回调
    public Object beforeCreating(Context context) {
        JWT jwt = context.parseToken();
        Long adminId = Long.parseLong(jwt.getPayload("id").toString());

        // 获取登录管理员信息
        AdminEntity adminInfo = adminService.getById(adminId);
        if (adminInfo == null) {
            return adminInfo; // 可以返回一个特定的错误信息
        }

        // 删除密码字段
        adminInfo.setPassword(null);;

        return adminInfo;
    }
}
