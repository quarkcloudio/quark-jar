package io.quarkcloud.quarkadmin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminLogin {

    // api
    String api() default "";

    // logo
    String logo() default "";

    // redirect
    String redirect() default "";

    // title
    String title() default "";

    // subTitle
    String subTitle() default "";
}
