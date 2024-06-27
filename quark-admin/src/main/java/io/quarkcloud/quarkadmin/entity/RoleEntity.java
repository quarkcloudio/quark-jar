package io.quarkcloud.quarkadmin.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("roles")
public class RoleEntity {

    // 主键
    private Long id;

    // 角色名称
    private String name;

    // 守卫名称
    private String guardName;

    // 创建时间
    private Date createdAt;

    // 更新时间
    private Date updatedAt;
}
