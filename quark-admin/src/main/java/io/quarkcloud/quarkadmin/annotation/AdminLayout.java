package io.quarkcloud.quarkadmin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminLayout {

    // layout 的左上角 的 logo
    String logo() default "";

    // layout 的左上角 的 title
    String title() default "";

    // layout 的菜单模式,side：右侧导航，top：顶部导航，mix：混合模式
    String layout() default "";

    // layout 的菜单模式为mix时，是否自动分割菜单
    boolean splitMenus() default false;

    // layout 的内容模式,Fluid：定宽 1200px，Fixed：自适应
    String contentWidth() default "";

    // 主题色,"#1890ff"
    String primaryColor() default "";

    // 是否固定 header 到顶部
    boolean fixedHeader() default false;
    
    // 是否固定导航
    boolean fixSiderbar() default false;

    // 使用 IconFont 的图标配置
    String iconfontUrl() default "";

    // 当前 layout 的语言设置，'zh-CN' | 'zh-TW' | 'en-US'
    String locale() default "";

    // 侧边菜单宽度
    int siderWidth() default 0;

    // 网站版权 time.Now().Format("2006") + " QuarkJar"
    String copyright() default "";
}
