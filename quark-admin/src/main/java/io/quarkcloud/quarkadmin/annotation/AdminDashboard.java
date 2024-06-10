package io.quarkcloud.quarkadmin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminDashboard {

    // 页面标题
    String title() default "";

    // 页面子标题
    String subTitle() default "";

    // 页面是否携带返回Icon
    boolean backIcon() default false;
}
