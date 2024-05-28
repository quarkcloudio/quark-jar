package io.quarkcloud.quarkadmin.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
@TableName("menu_has_permissions")
public class MenuHasPermission extends Model<MenuHasPermission> {

    // 主键
    private Long id;

    // 菜单ID
    private Long menuId;

    // 权限ID
    private Long permissionId;

    // 守卫名称
    private String guardName;

    // 创建时间
    private Date createdAt;

    // 更新时间
    private Date updatedAt;
}
