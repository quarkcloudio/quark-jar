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
@TableName("menus")
public class MenuEntity {

    // 主键
    @TableId(type = IdType.AUTO)
    private Long id;

    // 名称
    private String name;

    // GuardName
    private String guardName;

    // 图标
    private String icon;

    // 类型
    private Integer type;

    // 父ID
    private Long pid;

    // 排序
    private Short sort;

    // 访问路径
    private String path;

    // 是否显示
    @TableField(value = "`show`")
    private Boolean show;

    // 是否为引擎路由
    private Boolean isEngine;

    // 是否打开新页面
    private Boolean isLink;

    // 权限ID
    @TableField(exist = false)
    private List<Long> permissionIds;

    // 状态
    private Boolean status;

    // 组件key
    @TableField(value = "`key`")
    private String key;

    // 语言
    private String locale;

    // 在菜单中隐藏
    private Boolean hideInMenu;

    // 创建时间
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime createdAt;

    // 更新时间
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime updatedAt;
}
