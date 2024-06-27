package io.quarkcloud.quarkadmin.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_has_roles")
public class UserHasRoleEntity {

    // 主键
    private Long id;

    // 用户id
    private Long uid;

    // 角色id
    private Long roleId;

    // 守卫名称
    private String guardName;

    // 创建时间
    private Date createdAt;

    // 更新时间
    private Date updatedAt;
}
