package io.quarkcloud.quarkadmin.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@TableName("attachments")
public class AttachmentEntity extends Model<AttachmentEntity> {

    // 主键
    @TableId(type = IdType.AUTO)
    private Long id;

    // 上传用户Id
    private Long uid;

    // 上传来源
    private String source;

    // 分类id
    private Long categoryId;

    // 文件名
    private String name;

    // 文件类型
    private String type;

    // 排序
    private Integer sort;

    // 状态
    private Long size;

    // 文件扩展名
    private String ext;

    // 文件路径
    private String path;

    // 文件访问路径
    private String url;

    // 文件hash
    private String hash;

    // 文件扩展属性
    private String extra;

    // 创建时间
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime createdAt;

    // 更新时间
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime updatedAt;
}
