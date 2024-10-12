package io.quarkcloud.quarkadmin.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.quarkcloud.quarkcore.util.RawJsonDeserializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@TableName("users")
public class UserEntity extends Model<UserEntity> {

    // 主键
    @TableId(type = IdType.AUTO)
    private Long id;

    // 账号
    private String username;

    // 角色ids
    @TableField(exist = false)
    private List<Long> roleIds; // 这个字段在数据库操作时会被忽略

    // 昵称
    private String nickname;

    // 性别
    private Integer sex;

    // 邮箱
    private String email;

    // 手机
    private String phone;

    // 密码
    private String password; // 密码字段在查询时将被忽略

    // 头像
    @JsonDeserialize(using = RawJsonDeserializer.class)
    private Object avatar;

    // 部门id
    private Long departmentId;

    // 职务ids
    @JsonDeserialize(using = RawJsonDeserializer.class)
    private Object positionIds;

    // 最后登录ip
    private String lastLoginIp;

    // 最后登录时间
    private String lastLoginTime;

    // 状态
    private Boolean status;

    // 创建时间
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime createdAt;

    // 更新时间
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime updatedAt;

    // 删除时间
    @TableLogic(value = "null", delval = "now()")
    @JsonIgnore
    private LocalDateTime deletedAt;
}
