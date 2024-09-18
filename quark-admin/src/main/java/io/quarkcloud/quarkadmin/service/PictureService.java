package io.quarkcloud.quarkadmin.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;

import io.quarkcloud.quarkadmin.entity.PictureEntity;
import io.quarkcloud.quarkadmin.mapper.PictureMapper;

public interface PictureService extends ResourceService<PictureMapper, PictureEntity> {

    // 根据给定的ID获取路径列表
    public String getPath(Object id);

    // 根据给定的ID获取路径列表
    public List<String> getPaths(Object id);

    // 根据搜索条件获取列表数据
    public IPage<PictureEntity> getListBySearch(String objType, Object objId, Object categoryId, String name, String startDate, String endDate, Integer page);
}
