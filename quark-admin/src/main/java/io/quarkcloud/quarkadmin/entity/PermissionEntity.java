package io.quarkcloud.quarkadmin.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("permissions")
public class PermissionEntity {

    // 主键
    @TableId(type = IdType.AUTO)
    private Long id;

    // 权限名称
    private String name;

    // 守卫名称
    private String guardName;

    // 权限路径
    private String path;

    // 访问方法
    private String method;

    // 备注
    private String remark;

    // 创建时间
    private Date createdAt;

    // 更新时间
    private Date updatedAt;
}
