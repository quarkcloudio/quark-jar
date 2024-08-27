package io.quarkcloud.quarkadmin.template.dashboard;

import java.util.List;
import io.quarkcloud.quarkadmin.template.metrics.Metrics;
import io.quarkcloud.quarkcore.service.Context;

public interface Dashboard {

    // 获取标题
    public String getTitle();

    // 获取页面子标题
    public String getSubTitle();

    // 获取页面是否携带返回Icon
    public boolean isBackIcon();

    // 内容
    public List<Metrics> cards(Context context);

    // 页面组件渲染
    public Object pageComponentRender(Context context, Object body);

    // 页面容器组件渲染
    public Object pageContainerComponentRender(Context context, Object body);

    // 组件渲染
    public Object render(Context context);
}
