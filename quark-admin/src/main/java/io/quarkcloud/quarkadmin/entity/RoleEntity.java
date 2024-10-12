package io.quarkcloud.quarkadmin.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@TableName("roles")
public class RoleEntity {

    // 主键
    @TableId(type = IdType.AUTO)
    private Long id;

    // 角色名称
    private String name;

    // 权限ID
    @TableField(exist = false)
    private List<Long> menuIds;

    // 数据范围
    private Short dataScope;

    // 部门ID
    @TableField(exist = false)
    private List<Long> departmentIds;

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
