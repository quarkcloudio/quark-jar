package io.quarkcloud.quarkstarter.service.admin.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.component.form.Rule;
import io.quarkcloud.quarkadmin.component.form.fields.Radio;
import io.quarkcloud.quarkadmin.entity.UserEntity;
import io.quarkcloud.quarkadmin.mapper.UserMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkstarter.service.admin.action.Create;
import io.quarkcloud.quarkstarter.service.admin.action.Delete;
import io.quarkcloud.quarkstarter.service.admin.action.Edit;
import io.quarkcloud.quarkstarter.service.admin.action.FormBack;
import io.quarkcloud.quarkstarter.service.admin.action.FormExtraBack;
import io.quarkcloud.quarkstarter.service.admin.action.FormReset;
import io.quarkcloud.quarkstarter.service.admin.action.FormSubmit;
import io.quarkcloud.quarkstarter.service.admin.search.DatetimeRange;
import io.quarkcloud.quarkstarter.service.admin.search.Input;
import io.quarkcloud.quarkstarter.service.admin.search.Status;

@Component(value = "userResource")
public class User extends ResourceImpl<UserMapper, UserEntity> {

    // 构造函数
    public User() {
        this.entity = new UserEntity();
        this.title = "用户";
        this.perPage = 10;
    }

    // 字段
    public List<Object> fields(Context context) {
        return Arrays.asList(
            Field.id("id", "ID"),
            Field.image("avatar", "头像").onlyOnForms(),
            Field.text("username", "用户名", () -> {
                return String.format("<a href='#/layout/index?api=/api/admin/admin/edit&id=%d'>%s</a>", this.entity.getId(), this.entity.getUsername());
            }).setRules(Arrays.asList(
                Rule.required(true, "用户名必须填写"),
                Rule.min(6, "用户名不能少于6个字符"),
                Rule.max(20, "用户名不能超过20个字符")
            )),
            Field.text("nickname", "昵称").setEditable(true),
            Field.text("email", "邮箱"),
            Field.text("phone", "手机号"),
            Field.radio("sex", "性别").setOptions(Arrays.asList(
                new Radio.Option("男",1),
                new Radio.Option("女",2)
            )).setFilters(true).setDefaultValue(1),
            Field.password("password", "密码").onlyOnForms(),
            Field.datetime("lastLoginTime", "最后登录时间").onlyOnIndex(),
            Field.switchField("status", "状态").
            setTrueValue("正常").
            setFalseValue("禁用").
            setEditable(true).
            setDefaultValue(true)
        );
    }

    // 搜索表单
    public List<Object> searches(Context context) {
        return Arrays.asList(
            new Input<UserEntity>("username", "用户名"),
            new Input<UserEntity>("nickname", "昵称"),
            new Status<UserEntity>(),
            new DatetimeRange<UserEntity>("last_login_time", "登录时间")
        );
    }
    
    // 行为
    public List<Object> actions(Context context) {
        return Arrays.asList(
            new Create<UserMapper, UserEntity>(this.getTitle()),
            new Edit<UserMapper, UserEntity>(),
            new Delete<UserMapper, UserEntity>(),
            new FormExtraBack<UserMapper, UserEntity>(),
            new FormSubmit<UserMapper, UserEntity>(),
            new FormReset<UserMapper, UserEntity>(),
            new FormBack<UserMapper, UserEntity>()
        );
    }

    // 编辑页面显示前回调
    public UserEntity beforeEditing(Context context,UserEntity data) {
        data.setPassword(null);
        return data;
    }
}
