package io.quarkcloud.quarkadmin.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.quarkcloud.quarkadmin.annotation.AdminDashboard;
import io.quarkcloud.quarkadmin.component.pagecontainer.PageContainer;
import io.quarkcloud.quarkadmin.component.pagecontainer.PageHeader;
import io.quarkcloud.quarkadmin.component.card.Card;
import io.quarkcloud.quarkadmin.component.grid.Col;
import io.quarkcloud.quarkadmin.component.grid.Row;
import io.quarkcloud.quarkadmin.template.metrics.Metrics;
import io.quarkcloud.quarkcore.service.Context;

public class Dashboard {

    // 注解实例
    protected AdminDashboard annotationClass = null;

    // 页面标题
    public String title;

    // 页面子标题
    public String subTitle;

    // 页面是否携带返回Icon
	public boolean backIcon;

    // 构造函数
    public Dashboard() {

        // 获取注解对象
        if (getClass().isAnnotationPresent(AdminDashboard.class)) {
            annotationClass = getClass().getAnnotation(AdminDashboard.class);
        }
    
        // 页面标题
        title = "仪表盘";
    }

    // 获取标题
    public String getTitle() {

        // 检查是否存在注解
        if (annotationClass == null) {
            return title;
        }

        // 注解值为空返回默认值
        if (annotationClass.title().isEmpty()) {
            return title;
        }

        // 获取注解值
        return annotationClass.title();
    }

    // 获取页面子标题
    public String getSubTitle() {

        // 检查是否存在注解
        if (annotationClass == null) {
            return subTitle;
        }

        // 注解值为空返回默认值
        if (annotationClass.subTitle().isEmpty()) {
            return subTitle;
        }

        // 获取注解值
        return annotationClass.subTitle();
    }

    // 获取页面是否携带返回Icon
    public boolean isBackIcon() {

        // 检查是否存在注解
        if (annotationClass == null) {
            return backIcon;
        }

        // 获取注解值
        return annotationClass.backIcon();
    }

    // 内容
    public List<Metrics> cards(Context ctx) {
        return null;
    }

    // 页面组件渲染
    public Object pageComponentRender(Context ctx, Object body) {

        // 页面容器组件渲染
        return this.pageContainerComponentRender(ctx, body);
    }

    // 页面容器组件渲染
    public Object pageContainerComponentRender(Context ctx, Object body) {

        // 页面标题
        String title = this.getTitle();

        // 页面子标题
        String subTitle = this.getSubTitle();

        // 页面是否携带返回Icon
        boolean backIcon = this.isBackIcon();

        // 设置头部
        PageHeader header = new PageHeader()
                .setTitle(title)
                .setSubTitle(subTitle);

        if (!backIcon) {
            header.setBackIcon(false);
        }

        return new PageContainer()
                .setHeader(header)
                .setBody(body);
    }

    // 组件渲染
    public Object render(Context context) {
        List<Metrics> cards = this.cards(context);
        if (cards == null) {
            return "请实现Cards内容";
        }

        List<Object> cols = new ArrayList<>();
        List<Object> body = new ArrayList<>();
        int colNum = 0;
        for (int i = 0; i < cards.size(); i++) {
            Metrics v = cards.get(i);
            Card item = new Card();

            item = item.setBody(v.calculate());

            int col = v.getCol();
            Col colInfo = new Col().setSpan(col).setBody(item);
            cols.add(colInfo);
            colNum += col;

            if (colNum % 24 == 0) {
                Row row = new Row().setGutter(8).setBody(cols);
                if (i != 0) {
                    Map<String, Object> style = new HashMap<>();
                    style.put("marginTop", "20px");
                    row = row.setStyle(style);
                }
                body.add(row);
                cols = new ArrayList<>();
            }
        }

        if (!cols.isEmpty()) {
            Row row = new Row().setGutter(8).setBody(cols);
            if (colNum > 24) {
                Map<String, Object> style = new HashMap<>();
                style.put("marginTop", "20px");
                row = row.setStyle(style);
            }
            body.add(row);
        }

        return this.pageComponentRender(context, body);
    }
}
