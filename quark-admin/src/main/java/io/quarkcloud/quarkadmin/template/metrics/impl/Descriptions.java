package io.quarkcloud.quarkadmin.template.metrics.impl;

import io.quarkcloud.quarkadmin.template.metrics.Metrics;

public class Descriptions implements Metrics {

    // 标题
    public String title;

    // 列数
    public int col;

    public String getTitle() {
        return title;
    }

    public int getCol() {
        return col;
    }

    public Object result(Object value) {
        return new io.quarkcloud.quarkadmin.component.descriptions.Descriptions().setTitle(this.title).setItems(value);
    }

    public Object calculate() {
        return null;
    }
}
