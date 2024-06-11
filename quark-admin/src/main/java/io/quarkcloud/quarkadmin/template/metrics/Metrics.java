package io.quarkcloud.quarkadmin.template.metrics;

public interface Metrics {

    // 标题
    public String getTitle();

    // 列数
    public int getCol();

    // 计算结果
    public Object result(Object value);

    // 计算
    public Object calculate();
}
