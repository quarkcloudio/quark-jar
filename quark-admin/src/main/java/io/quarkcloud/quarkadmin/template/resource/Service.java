package io.quarkcloud.quarkadmin.template.resource;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import io.quarkcloud.quarkcore.service.Context;

public interface Service<T> extends IService<T> {

    // 获取列表
    public List<T> list(Context ctx);
}
