package io.quarkcloud.quarkadmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@TableName("picture_categories")
public class PictureCategoryEntity extends Model<PictureCategoryEntity> {

    // 主键
    @TableId(type = IdType.AUTO)
    private Long id;

    // 对象类型
    private String objType;

    // 对象Id
    private Long objId;

    // 分类名称
    private String title;

    // 排序
    private int sort;

    // 描述
    private String description;
}
