package io.quarkcloud.quarkadmin.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@TableName("role_has_menus")
public class RoleHasMenuEntity extends Model<RoleHasMenuEntity> {

    // 主键
    @TableId(type = IdType.AUTO)
    private Long id;

    // 角色ID
    private Long roleId;

    // 菜单ID
    private Long menuId;

    // 守卫名称
    private String guardName;

    // 创建时间
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime createdAt;

    // 更新时间
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime updatedAt;
}
