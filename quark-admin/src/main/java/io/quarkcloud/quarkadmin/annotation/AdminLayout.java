package io.quarkcloud.quarkadmin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminLayout {

    // logo
    String logo() default "";

    // title
    String title() default "";

    // subTitle
    String subTitle() default "";
}
