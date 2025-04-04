package io.quarkcloud.quarkadmin.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;

import io.quarkcloud.quarkadmin.entity.AttachmentEntity;
import io.quarkcloud.quarkadmin.mapper.AttachmentMapper;

public interface AttachmentService extends ResourceService<AttachmentMapper, AttachmentEntity> {

    // 根据ID获取附件访问URL
    public String getUrl(Object id);

    // 根据ID、类型获取附件访问URL
    public String getUrl(String type, Object id);

    // 根据ID获取文件访问URL
    public String getFileUrl(Object id);

    // 根据ID获取图片访问URL
    public String getImageUrl(Object id);

    // 获取多文件存储路径
    public List<String> getPaths(Object id);

    // 根据ID获取文件存储路径
    public String getFilePath(Object id);

    // 根据ID获取图片存储路径
    public String getImagePath(Object id);

    // 根据搜索条件获取列表数据
    public IPage<AttachmentEntity> getListBySearch(Object adminId,String type, Object categoryId, String name, String startDate, String endDate, Integer page);
}
