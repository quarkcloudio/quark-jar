package io.quarkcloud.quarkadmin.entity;

import java.util.Date;

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
    private short sort;

    // 访问路径
    private String path;

    // 是否显示
    @TableField(value = "`show`")
    private boolean show;

    // 是否为引擎路由
    private boolean isEngine;

    // 是否打开新页面
    private boolean isLink;

    // 状态
    private boolean status;

    // 组件key
    @TableField(value = "`key`")
    private String key;

    // 语言
    private String locale;

    // 在菜单中隐藏
    private boolean hideInMenu;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;
}
