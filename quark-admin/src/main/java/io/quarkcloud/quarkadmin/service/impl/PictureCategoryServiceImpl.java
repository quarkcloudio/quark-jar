package io.quarkcloud.quarkadmin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.quarkcloud.quarkadmin.entity.PictureCategoryEntity;
import io.quarkcloud.quarkadmin.mapper.PictureCategoryMapper;
import io.quarkcloud.quarkadmin.service.PictureCategoryService;

@Service
public class PictureCategoryServiceImpl extends ResourceServiceImpl<PictureCategoryMapper, PictureCategoryEntity>  implements PictureCategoryService {

    // 根据条件获取列表数据
    public List<PictureCategoryEntity> getListByObj(String objType, Object objId) {
        QueryWrapper<PictureCategoryEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("obj_type", objType).eq("obj_id", objId);
        return this.list(queryWrapper);
    }
}
