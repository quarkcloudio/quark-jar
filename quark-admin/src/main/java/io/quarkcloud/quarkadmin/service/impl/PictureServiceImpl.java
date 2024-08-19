package io.quarkcloud.quarkadmin.service.impl;

import org.springframework.stereotype.Service;
import io.quarkcloud.quarkadmin.entity.PictureEntity;
import io.quarkcloud.quarkadmin.mapper.PictureMapper;
import io.quarkcloud.quarkadmin.service.PictureService;

@Service
public class PictureServiceImpl extends ResourceServiceImpl<PictureMapper, PictureEntity> implements PictureService {

}
