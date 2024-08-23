package io.quarkcloud.quarkadmin.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("roles")
public class RoleEntity {

    // 主键
    @TableId(type = IdType.AUTO)
    private Long id;

    // 角色名称
    private String name;

    // 守卫名称
    private String guardName;

    // 创建时间
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private String createdAt;

    // 更新时间
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private String updatedAt;
}
