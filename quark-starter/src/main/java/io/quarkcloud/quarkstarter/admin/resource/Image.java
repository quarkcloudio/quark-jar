package io.quarkcloud.quarkstarter.admin.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.entity.AttachmentEntity;
import io.quarkcloud.quarkadmin.mapper.AttachmentMapper;
import io.quarkcloud.quarkadmin.service.AttachmentService;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkstarter.admin.action.BatchDelete;
import io.quarkcloud.quarkstarter.admin.action.Delete;
import io.quarkcloud.quarkstarter.admin.search.Input;

@Component
public class Image extends ResourceImpl<AttachmentMapper, AttachmentEntity> {

    @Autowired
    AttachmentService attachmentService;

    // 构造函数
    public Image() {
        this.entity = new AttachmentEntity();
        this.title = "图片";
        this.pageSize = 10;
    }

    // 字段
    public List<Object> fields(Context context) {
        return Arrays.asList(
            Field.id("id", "ID"),
            Field.text("path", "显示", () -> {
                return String.format("<img src='%s' width=50 height=50 />", attachmentService.getUrl(this.entity.getId()));
            }),
            Field.text("name", "名称").setEllipsis(true),
            Field.text("size", "大小").setSorter(true),
            Field.text("width", "宽度"),
            Field.text("height", "高度"),
            Field.text("ext", "扩展名"),
            Field.datetime("createdAt", "上传时间")
        );
    }

    // 搜索表单
    public List<Object> searches(Context context) {
        return Arrays.asList(
            new Input<AttachmentEntity>("name", "名称"),
            new Input<AttachmentEntity>("created_at", "上传时间")
        );
    }
    
    // 行为
    public List<Object> actions(Context context) {
        return Arrays.asList(
            new Delete<AttachmentMapper, AttachmentEntity>(),
            new BatchDelete<AttachmentMapper, AttachmentEntity>()
        );
    }
}
