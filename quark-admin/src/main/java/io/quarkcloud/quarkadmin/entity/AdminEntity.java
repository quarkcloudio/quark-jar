package io.quarkcloud.quarkadmin.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@TableName("admins")
public class AdminEntity extends Model<AdminEntity> {

    // 主键
    @TableId(type = IdType.AUTO)
    private Long id;

    // 账号
    private String username;

    // 昵称
    private String nickname;

    // 性别
    private int sex;

    // 邮箱
    private String email;

    // 手机
    private String phone;

    // 密码
    private String password;

    // 头像
    private String avatar;

    // 最后登录ip
    private String lastLoginIp;

    // 最后登录时间
    private LocalDateTime lastLoginTime;

    // 状态
    private int status;

    // 创建时间
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    // 更新时间
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    // 删除时间
    private LocalDateTime deletedAt;
}
