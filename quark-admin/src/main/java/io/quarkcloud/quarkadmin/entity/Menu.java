package io.quarkcloud.quarkadmin.entity;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
@TableName("menus")
public class Menu extends Model<Menu> {

    // 主键
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
    private boolean show;

    // 是否为引擎路由
    private boolean isEngine;

    // 是否打开新页面
    private boolean isLink;

    // 状态
    private boolean status;

    // 组件key
    private String key;

    // 语言
    private String locale;

    // 在菜单中隐藏
    private boolean hideInMenu;

    // 创建时间
    private Date createdAt;

    // 更新时间
    private Date updatedAt;

    // 根据权限查询菜单
    public List<Menu> getListByPermission(long permissionId) {

        // 创建查询器
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();

        // 条件
        queryWrapper.eq("permission_id", permissionId);

        // 查询
        List<Menu> menus = this.selectList(queryWrapper);

        // 返回数据
        return menus;
    }
}
