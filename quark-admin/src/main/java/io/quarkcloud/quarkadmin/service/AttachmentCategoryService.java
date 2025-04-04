package io.quarkcloud.quarkadmin.service;

import java.util.List;

import io.quarkcloud.quarkadmin.entity.AttachmentCategoryEntity;
import io.quarkcloud.quarkadmin.mapper.AttachmentCategoryMapper;

public interface AttachmentCategoryService extends ResourceService<AttachmentCategoryMapper, AttachmentCategoryEntity> {

    // 根据条件获取列表数据
    public List<AttachmentCategoryEntity> getList(Object adminId);
}
