package io.quarkcloud.quarkstarter.service.admin.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.entity.FileEntity;
import io.quarkcloud.quarkadmin.mapper.FileMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkstarter.service.admin.action.BatchDelete;
import io.quarkcloud.quarkstarter.service.admin.action.Delete;
import io.quarkcloud.quarkstarter.service.admin.search.Input;

@Component
public class File extends ResourceImpl<FileMapper, FileEntity> {

    // 构造函数
    public File() {
        this.entity = new FileEntity();
        this.title = "文件";
        this.perPage = 10;
    }

    // 字段
    public List<Object> fields(Context context) {
        return Arrays.asList(
            Field.id("id", "ID"),
            Field.text("name", "名称").setEllipsis(true),
            Field.text("size", "大小").setSorter(true),
            Field.text("ext", "扩展名"),
            Field.datetime("createdAt", "上传时间")
        );
    }

    // 搜索表单
    public List<Object> searches(Context context) {
        return Arrays.asList(
            new Input<FileEntity>("name", "名称"),
            new Input<FileEntity>("created_at", "上传时间")
        );
    }
    
    // 行为
    public List<Object> actions(Context context) {
        return Arrays.asList(
            new Delete<FileMapper, FileEntity>(),
            new BatchDelete<FileMapper, FileEntity>()
        );
    }
}
