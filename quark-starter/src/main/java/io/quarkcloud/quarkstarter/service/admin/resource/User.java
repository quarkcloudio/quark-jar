package io.quarkcloud.quarkstarter.service.admin.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.component.form.Rule;
import io.quarkcloud.quarkadmin.component.message.Message;
import io.quarkcloud.quarkadmin.entity.UserEntity;
import io.quarkcloud.quarkadmin.entity.RoleEntity;
import io.quarkcloud.quarkadmin.mapper.UserMapper;
import io.quarkcloud.quarkadmin.service.UserService;
import io.quarkcloud.quarkadmin.service.RoleService;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkstarter.service.admin.action.BatchDelete;
import io.quarkcloud.quarkstarter.service.admin.action.BatchDisable;
import io.quarkcloud.quarkstarter.service.admin.action.BatchEnable;
import io.quarkcloud.quarkstarter.service.admin.action.CreateLink;
import io.quarkcloud.quarkstarter.service.admin.action.Delete;
import io.quarkcloud.quarkstarter.service.admin.action.DetailLink;
import io.quarkcloud.quarkstarter.service.admin.action.EditLink;
import io.quarkcloud.quarkstarter.service.admin.action.FormBack;
import io.quarkcloud.quarkstarter.service.admin.action.FormExtraBack;
import io.quarkcloud.quarkstarter.service.admin.action.FormReset;
import io.quarkcloud.quarkstarter.service.admin.action.FormSubmit;
import io.quarkcloud.quarkstarter.service.admin.action.More;
import io.quarkcloud.quarkstarter.service.admin.search.DatetimeRange;
import io.quarkcloud.quarkstarter.service.admin.search.Input;
import io.quarkcloud.quarkstarter.service.admin.search.Status;

@Component
public class User extends ResourceImpl<UserMapper, UserEntity> {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService adminService;

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
            )).setCreationRules(Arrays.asList(
                Rule.unique("users", "username", "用户名已存在")
            )).setUpdateRules(Arrays.asList(
                    Rule.unique("users", "username", "{id}", "用户名已存在")
            )),
            Field.checkbox("roleIds", "角色", () -> {
                List<RoleEntity> roleEntities = adminService.getRolesById(this.entity.getId());
                return roleEntities.stream().map(roleEntity -> {
                    return String.format("<span class='label label-primary'>%s</span>", roleEntity.getName());
                }).reduce((a, b) -> {
                    return a + " " + b;
                }).orElse("");
            }).setOptions(roleService.getCheckboxOptions()),
            Field.text("nickname", "昵称").setEditable(true).setRules(Arrays.asList(
                Rule.required(true, "昵称必须填写")
            )),
            Field.text("email", "邮箱").setRules(Arrays.asList(
                Rule.required(true, "邮箱必须填写")
            )),
            Field.text("phone", "手机号").setRules(Arrays.asList(
                Rule.required(true, "手机号必须填写")
            )),
            Field.radio("sex", "性别").setOptions(Arrays.asList(
                    Field.radioOption("男",1),
                    Field.radioOption("女",2)
                ))
                .setFilters(true)
                .setDefaultValue(1),
            Field.password("password", "密码").setCreationRules(Arrays.asList(
                Rule.required(true, "密码必须填写")
            )).onlyOnForms(),
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
            new CreateLink<UserMapper, UserEntity>(this.getTitle()),
            new DetailLink<UserMapper, UserEntity>(),
            new More<UserMapper, UserEntity>().setActions(Arrays.asList(
                new EditLink<UserMapper, UserEntity>(),
                new Delete<UserMapper, UserEntity>()
            )),
            new BatchDelete<UserMapper, UserEntity>(),
            new BatchDisable<UserMapper, UserEntity>(),
            new BatchEnable<UserMapper, UserEntity>(),
            new FormExtraBack<UserMapper, UserEntity>(),
            new FormSubmit<UserMapper, UserEntity>(),
            new FormReset<UserMapper, UserEntity>(),
            new FormBack<UserMapper, UserEntity>()
        );
    }

    // 编辑页面显示前回调
    public UserEntity beforeEditing(Context context,UserEntity data) {
        data.setPassword(null);
        List<Long> roleIds = adminService.getRoleIdsById(data.getId());
        data.setRoleIds(roleIds);
        return data;
    }

    // 保存数据前回调
    public UserEntity beforeSaving(Context context, UserEntity submitData) {
        Object password = submitData.getPassword();
        if (password != null) {
            // 密码加密
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            submitData.setPassword(encoder.encode((String) password));;
        }
        return submitData;
    }

    // 保存数据后回调
    public Object afterSaved(Context context, UserEntity result) {
        if (context.isImport()) {
            return result;
        }
        if (result == null) {
            return Message.error("操作失败！");
        }

        // 保存角色关联
        Long adminId = result.getId();
        List<Long> roleIds = result.getRoleIds();
        boolean insertRoleResult = true;
        if (roleIds.size() > 0) {
            adminService.removeAllRoles(adminId);
            for (Long roleId : roleIds) {
                boolean insertRole = adminService.addRole(adminId, roleId);
                if (insertRole == false) {
                    insertRoleResult = false;
                }
            }
        }

        if (!insertRoleResult) {
            return Message.error("操作失败！");
        }

        String redirectUrl = "/layout/index?api=/api/admin/{resource}/index".replace("{resource}", context.getPathVariable("resource"));
        return Message.success("操作成功！", redirectUrl);
    }
}
