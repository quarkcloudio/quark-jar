package io.quarkcloud.quarkadmin.template.dashboard.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.quarkcloud.quarkadmin.annotation.AdminDashboard;
import io.quarkcloud.quarkadmin.component.card.Card;
import io.quarkcloud.quarkadmin.component.grid.Col;
import io.quarkcloud.quarkadmin.component.grid.Row;
import io.quarkcloud.quarkadmin.template.dashboard.Dashboard;
import io.quarkcloud.quarkadmin.template.metrics.Metrics;
import io.quarkcloud.quarkcore.common.Message;
import io.quarkcloud.quarkcore.service.Context;

public class DashboardImpl implements Dashboard {

    // 注解实例
    protected AdminDashboard annotationClass = null;

    // 构造函数
    public DashboardImpl() {

        // 获取注解对象
        if (getClass().isAnnotationPresent(AdminDashboard.class)) {
            annotationClass = getClass().getAnnotation(AdminDashboard.class);
        }
    }

    // 内容
    public List<Metrics> cards(Context context) {
        return null;
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

        return Message.success("ok", body);
    }
}
