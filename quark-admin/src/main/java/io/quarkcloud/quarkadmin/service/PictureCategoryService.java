package io.quarkcloud.quarkadmin.service;

import java.util.List;

import io.quarkcloud.quarkadmin.entity.PictureCategoryEntity;
import io.quarkcloud.quarkadmin.mapper.PictureCategoryMapper;

public interface PictureCategoryService extends ResourceService<PictureCategoryMapper, PictureCategoryEntity> {

    // 根据条件获取列表数据
    public List<PictureCategoryEntity> getListByObj(String objType, Object objId);
}
