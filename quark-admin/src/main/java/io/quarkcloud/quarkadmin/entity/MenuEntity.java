package io.quarkcloud.quarkadmin.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    // 权限标识
    private String permission;

    // 图标
    private String icon;

    // 菜单类型：1目录，2菜单，3按钮
    private Integer type;

    // 页面类型：1默认，2引擎，3外链，
    private Integer pageType;

    // 父ID
    private Long pid;

    // 排序
    private Short sort;

    // 访问路径
    private String path;

    // 请求参数
    private String query;

    // 前端组件
    private String component;

    // 是否显示
    private Boolean visible;

    // 权限ID
    @TableField(exist = false)
    private List<Long> permissionIds;

    // 状态
    private Boolean status;

    // 在菜单中隐藏
    @TableField(exist = false)
    private Boolean hideInMenu;

    @TableField(exist = false)
    private Map<String, Object> meta;

    @TableField(exist = false)
    private String api;

    @TableField(exist = false)
    private String url;

    // 创建时间
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime createdAt;

    // 更新时间
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime updatedAt;
}
