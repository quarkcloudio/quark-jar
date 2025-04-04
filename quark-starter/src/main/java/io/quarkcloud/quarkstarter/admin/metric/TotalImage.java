package io.quarkcloud.quarkstarter.admin.metric;

import java.util.Map;
import io.quarkcloud.quarkadmin.entity.AttachmentEntity;
import io.quarkcloud.quarkadmin.template.metrics.impl.Value;

public class TotalImage extends Value {

    // 构造方法
    public TotalImage() {
        this.title = "图片数量";
        this.col = 6;
    }

    // 计算
    public Object calculate() {
        long count = new AttachmentEntity().selectCount(null);
        
        return this.value(count).setValueStyle(Map.of("color", "#cf1322"));
    }
}
