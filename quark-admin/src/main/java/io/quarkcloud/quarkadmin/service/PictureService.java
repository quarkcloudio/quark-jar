package io.quarkcloud.quarkadmin.service;

import io.quarkcloud.quarkadmin.entity.PictureEntity;
import io.quarkcloud.quarkadmin.mapper.PictureMapper;

public interface PictureService extends ResourceService<PictureMapper, PictureEntity> {

    /**
     * 插入文件记录并获取生成的ID
     * 
     * @param file 要插入的文件实体
     * @return 插入后生成的ID
     */
    public Long insertGetId(PictureEntity file);
}
