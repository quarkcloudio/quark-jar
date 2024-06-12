package io.quarkcloud.quarkadmin.template.metrics;

import io.quarkcloud.quarkadmin.component.statistic.Statistic;

public class Value implements Metrics {
    
    // 标题
    public String title;

    // 列数
    public int col;

    public Statistic value(long value) {
        return result(value);
    }

    public String getTitle() {
        return title;
    }

    public int getCol() {
        return col;
    }

    public Statistic result(Object value) {
        return new Statistic().setTitle(this.title).setValue(value);
    }

    public Object calculate() {
        return null;
    }
}
