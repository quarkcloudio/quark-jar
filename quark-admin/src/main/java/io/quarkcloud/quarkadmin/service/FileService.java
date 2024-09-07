package io.quarkcloud.quarkadmin.service;

import io.quarkcloud.quarkadmin.entity.FileEntity;
import io.quarkcloud.quarkadmin.mapper.FileMapper;

public interface FileService extends ResourceService<FileMapper, FileEntity> {
    
    /*
     * 获取URL访问路径
     * 
     * 根据提供的标识符获取对应的路径字符串
     * 
     * @param id 标识符，用于定位路径
     * @return 返回与标识符对应的路径字符串
     */
    public String getPath(Object id);

    /*
     * 获取文件路径
     * 
     * 根据提供的标识符获取对应的路径字符串
     * 
     * @param id 标识符，用于定位路径
     * @return 返回与标识符对应的路径字符串
     */
    public String getFilePath(Object id);
}
