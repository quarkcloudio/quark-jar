package io.quarkcloud.quarkadmin.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@TableName("files")
public class File extends Model<File> {

    // 主键
    private long id;

    // 对象类型
    private String objType;

    // 对象Id
    private long objId;

    // 文件分类id
    private long fileCategorId;

    // 排序
    private int sort;

    // 文件名
    private String name;

    // 状态
    private long size;

    // 文件扩展名
    private String ext;

    // 文件路径
    private String path;

    // 文件访问路径
    private String url;

    // 文件hash
    private String hash;

    // 创建时间
    private Date createdAt;

    // 更新时间
    private Date updatedAt;
}
