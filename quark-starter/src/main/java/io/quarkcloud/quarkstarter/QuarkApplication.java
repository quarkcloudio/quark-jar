package io.quarkcloud.quarkstarter;

import io.quarkcloud.quarkadmin.autoconfigure.QuarkAdminApplication;

public class QuarkApplication {

    // Quark应用服务
    public static Object run(Class<?> primarySource, String... args) {

       // 检查是否有QuarkAdminApplication注解
       if (primarySource.isAnnotationPresent(QuarkAdminApplication.class)) {

            // 获取注解实例
            QuarkAdminApplication annotation = primarySource.getAnnotation(QuarkAdminApplication.class);

            // 设置注解值
            io.quarkcloud.quarkadmin.service.Config.getInstance().setBasePackages(annotation.value());
       }

        return null;
    }
}
