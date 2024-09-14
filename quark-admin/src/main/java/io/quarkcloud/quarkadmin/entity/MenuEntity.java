package io.quarkcloud.quarkadmin.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

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
    private Long type;

    // 父ID
    private Long pid;

    // 排序
    private Short sort;

    // 访问路径
    private String path;

    // 是否显示
    @TableField(value = "`show`")
    private Short show;

    // 是否为引擎路由
    private Short isEngine;

    // 是否打开新页面
    private Short isLink;

    // 状态
    private Short status;

    // 组件key
    @TableField(value = "`key`")
    private String key;

    // 语言
    private String locale;

    // 在菜单中隐藏
    private Boolean hideInMenu;

    // 创建时间
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private String createdAt;

    // 更新时间
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private String updatedAt;
}
