package io.quarkcloud.quarkstarter.service.admin.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.hutool.jwt.JWT;
import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.component.form.Rule;
import io.quarkcloud.quarkadmin.component.message.Message;
import io.quarkcloud.quarkadmin.entity.UserEntity;
import io.quarkcloud.quarkadmin.mapper.UserMapper;
import io.quarkcloud.quarkadmin.service.UserService;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkstarter.service.admin.action.FormBack;
import io.quarkcloud.quarkstarter.service.admin.action.FormExtraBack;
import io.quarkcloud.quarkstarter.service.admin.action.FormReset;
import io.quarkcloud.quarkstarter.service.admin.action.FormSubmit;

@Component
public class Account extends ResourceImpl<UserMapper, UserEntity> {

    @Autowired
    private UserService adminService;

    // 构造函数
    public Account() {
        this.entity = new UserEntity();
        this.title = "个人设置";
    }

    // 字段
    public List<Object> fields(Context context) {
        return Arrays.asList(
            Field.image("avatar", "头像"),

            Field.text("nickname", "昵称")
                .setRules(Arrays.asList(
                    new Rule().setRequired().setMessage("昵称必须填写")
                )),

            Field.text("email", "邮箱")
                .setRules(Arrays.asList(
                    new Rule().setRequired().setMessage("邮箱必须填写")
                )),

            Field.text("phone", "手机号")
                .setRules(Arrays.asList(
                    new Rule().setRequired().setMessage("手机号必须填写")
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
            new FormExtraBack<UserMapper, UserEntity>(),
            new FormSubmit<UserMapper, UserEntity>(),
            new FormReset<UserMapper, UserEntity>(),
            new FormBack<UserMapper, UserEntity>()
        );
    }

    // 创建前回调
    public Object beforeFormShowing(Context context) {
        JWT jwt = context.parseToken();
        Long adminId = Long.parseLong(jwt.getPayload("id").toString());

        // 获取登录管理员信息
        UserEntity adminInfo = adminService.getById(adminId);
        if (adminInfo == null) {
            return adminInfo;
        }

        adminInfo.setPassword(null);

        return adminInfo;
    }

    // 表单执行
    public Object formHandle(Context context) {
        JWT jwt = context.parseToken();
        UserEntity adminEntity = context.getRequestBody(UserEntity.class);
        Long adminId = Long.parseLong(jwt.getPayload("id").toString());
        if (adminEntity.getAvatar() != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                adminEntity.setAvatar(objectMapper.writeValueAsString(adminEntity.getAvatar()));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        if (adminEntity.getPassword() !=null && !adminEntity.getPassword().isEmpty()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            adminEntity.setPassword(encoder.encode(adminEntity.getPassword()));
        }

        // 获取登录管理员信息
        UserEntity adminInfo = adminService.getById(adminId);
        if (adminInfo == null) {
            return Message.error("管理员信息获取失败");
        }

        adminEntity.setId(adminId);

        // 更新管理员信息
        boolean result = adminService.updateById(adminEntity);
        if (!result) {
            return Message.error("操作失败！");
        }
        
        return Message.success("操作成功！");
    }
}
