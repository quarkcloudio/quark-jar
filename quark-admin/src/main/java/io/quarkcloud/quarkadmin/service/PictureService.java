package io.quarkcloud.quarkadmin.service;

import java.util.List;

import io.quarkcloud.quarkadmin.entity.PictureEntity;
import io.quarkcloud.quarkadmin.mapper.PictureMapper;

public interface PictureService extends ResourceService<PictureMapper, PictureEntity> {

    /*
     * 获取路径
     * 
     * 根据提供的标识符获取对应的路径字符串
     * 
     * @param id 标识符，用于定位路径
     * @return 返回与标识符对应的路径字符串
     */
    public String getPath(Object id);

    /**
     * 根据给定的ID获取路径列表
     * 此方法解释了为什么需要根据ID获取路径，以及如何使用这个方法
     * 
     * @param id 一个标识符，用于确定要获取路径的对象
     * @return 返回一个字符串列表，包含所有与给定ID相关联的路径
     */
    public List<String> getPaths(Object id);
}
