package io.quarkcloud.quarkadmin.annotation;

import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import io.quarkcloud.quarkcore.service.Config;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkcore.service.ClassLoader;

@Aspect
@Component
public class AdminResourceEditRenderAspect {

    // 加载基础资源包路径
    public String[] basePackages = Config.getInstance().getBasePackages("admin");

    // 加载本资源包路径
    public String[] packages = {".resource."};

    @Pointcut("@annotation(io.quarkcloud.quarkadmin.annotation.AdminResourceEditRender)")
    private void AdminResourceEditRender() {}

    // 获取加载包路径
    protected String[] getLoadPackages() {
        String[] loadPackages = {this.basePackages[0]+this.packages[0]};

        return loadPackages;
    }

    /**
     * 环绕通知
     */
    @Around("AdminResourceEditRender()")
    public Object advice(ProceedingJoinPoint joinPoint) throws Throwable {

        // 得到连接点执行的方法对象
        MethodSignature signature= (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
 
        // 得到方法上的注解
        AdminResourceEditRender annotation = method.getAnnotation(AdminResourceEditRender.class);
        if (annotation==null) {
            return joinPoint.proceed();
        }

        // 调用原方法
        Object context = joinPoint.proceed();

        // 获取返回值
        Context newContext = (Context) context;

        // 设置连接点
        newContext.setJoinPoint(joinPoint);

        // 获取资源名称
        Object resource = newContext.getPathVariable("resource");
        if (resource == null) {
            return joinPoint.proceed();
        }

        // 字符串首字母大写
        resource = resource.toString().substring(0, 1).toUpperCase() + resource.toString().substring(1);

        // 获取配置文件
        String[] basePackages = Config.getInstance().getBasePackages("admin");
        if (basePackages.length == 0) {
            return joinPoint.proceed();
        }

        // 加载类，暂时只支持加载一个配置
        String[] loadPackages = getLoadPackages();
        ClassLoader classLoader = new ClassLoader();

        // 调用类方法
        return classLoader.setClassPath(loadPackages[0]+resource).doMethod("editRender", newContext);
    }
}
