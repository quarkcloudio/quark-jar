package io.quarkcloud.quarkadmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@TableName("attachment_categories")
public class AttachmentCategoryEntity extends Model<AttachmentCategoryEntity> {

    // 主键
    @TableId(type = IdType.AUTO)
    private Long id;

    // 上传来源
    private String source;

    // 创建用户
    private Long uid;

    // 分类名称
    private String title;

    // 排序
    private Short sort;

    // 描述
    private String description;
}
