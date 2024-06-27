package io.quarkcloud.quarkstarter.service.admin.metric;

import java.util.Map;
import io.quarkcloud.quarkadmin.entity.PictureEntity;
import io.quarkcloud.quarkadmin.template.metrics.impl.Value;

public class TotalPicture extends Value {

    // 构造方法
    public TotalPicture() {
        this.title = "图片数量";
        this.col = 6;
    }

    // 计算
    public Object calculate() {
        long count = new PictureEntity().selectCount(null);
        
        return this.value(count).setValueStyle(Map.of("color", "#cf1322"));
    }
}
