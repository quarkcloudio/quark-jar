package io.quarkcloud.quarkadmin.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_has_roles")
public class UserHasRoleEntity {

    // 主键
    @TableId(type = IdType.AUTO)
    private Long id;

    // 用户id
    private Long uid;

    // 角色id
    private Long roleId;

    // 守卫名称
    private String guardName;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;
}
