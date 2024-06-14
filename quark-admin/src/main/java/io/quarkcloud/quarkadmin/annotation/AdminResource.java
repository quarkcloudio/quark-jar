package io.quarkcloud.quarkadmin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminResource {

    // 页面标题
    String title() default "";

    // 页面子标题
    String subTitle() default "";

    // 页面是否携带返回Icon
    boolean backIcon() default false;

    // 列表页分页配置
    int perPage() default 0;

    // 列表页表格标题后缀
    String tableTitleSuffix() default "";

    // 列表页表格行为列显示文字，既字段的列名
    String tableActionColumnTitle() default "";

    // 列表页表格行为列的宽度
    int tableActionColumnWidth() default 0;

    // 列表页表格是否轮询数据
    int tablePolling() default 0;

    // 全局排序规则
    String queryOrder() default "";

    // 列表页排序规则
    String indexQueryOrder() default "";

    // 导出数据排序规则
    String exportQueryOrder() default "";

    // 是否具有导出功能
    boolean withExport() default false;
}
