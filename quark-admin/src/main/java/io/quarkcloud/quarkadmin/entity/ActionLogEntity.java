package io.quarkcloud.quarkadmin.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@TableName("action_logs")
public class ActionLogEntity extends Model<ActionLogEntity> {

    // 主键
    private Long id;

    // 操作人id
    private int objectId;

    // 操作人名
    private String username;

    // URL
    private String url;

    // 备注
    private String remark;

    // IP
    private String ip;

    // 类型
    private String type;

    // 状态
    private int status;

    // 创建时间
    private Date createdAt;

    // 更新时间
    private Date updatedAt;
}
