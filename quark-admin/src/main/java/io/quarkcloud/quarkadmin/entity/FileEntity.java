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
@TableName("files")
public class FileEntity extends Model<FileEntity> {

    // 主键
    @TableId(type = IdType.AUTO)
    private Long id;

    // 对象类型
    private String objType;

    // 对象Id
    private Long objId;

    // 文件分类id
    private Long fileCategoryId;

    // 排序
    private Integer sort;

    // 文件名
    private String name;

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

    // 创建时间
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime createdAt;

    // 更新时间
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime updatedAt;
}
