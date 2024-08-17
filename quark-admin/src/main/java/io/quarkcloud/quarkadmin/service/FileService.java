package io.quarkcloud.quarkadmin.service;

import io.quarkcloud.quarkadmin.entity.FileEntity;
import io.quarkcloud.quarkadmin.mapper.FileMapper;

public interface FileService extends ResourceService<FileMapper, FileEntity> {

    /**
     * 插入文件记录并获取生成的ID
     * 
     * @param file 要插入的文件实体
     * @return 插入后生成的ID
     */
    public Long insertGetId(FileEntity file);
}
