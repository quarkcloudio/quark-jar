package io.quarkcloud.quarkadmin.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("role_has_permissions")
public class RoleHasPermissionEntity {

    // 主键
    private Long id;

    // 角色ID
    private Long roleId;

    // 权限ID
    private Long permissionId;

    // 守卫名称
    private String guardName;

    // 创建时间
    private Date createdAt;

    // 更新时间
    private Date updatedAt;
}
