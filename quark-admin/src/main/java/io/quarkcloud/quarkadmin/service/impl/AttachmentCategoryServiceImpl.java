package io.quarkcloud.quarkadmin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.quarkcloud.quarkadmin.entity.AttachmentCategoryEntity;
import io.quarkcloud.quarkadmin.mapper.AttachmentCategoryMapper;
import io.quarkcloud.quarkadmin.service.AttachmentCategoryService;

@Service
public class AttachmentCategoryServiceImpl extends ResourceServiceImpl<AttachmentCategoryMapper, AttachmentCategoryEntity>  implements AttachmentCategoryService {

    // 根据条件获取列表数据
    public List<AttachmentCategoryEntity> getList(Object adminId) {
        QueryWrapper<AttachmentCategoryEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("source", "ADMIN").eq("uid", adminId);
        return this.list(queryWrapper);
    }
}
