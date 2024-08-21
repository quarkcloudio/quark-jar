package io.quarkcloud.quarkadmin.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@TableName("configs")
public class ConfigEntity extends Model<ConfigEntity> {

    // 主键
    @TableId(type = IdType.AUTO)
    private Long id;

    // 标题
    private String title;

    // 类型
    private String type;

    // 配置标识
    private String name;

    // 排序
    private int sort;

    // 分组名称
    private String groupName;

    // 配置值
    private String value;

    // 备注信息
    private String remark;

    // 状态
    private int status;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;
}
