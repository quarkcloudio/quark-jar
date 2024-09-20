package io.quarkcloud.quarkadmin.annotation;

import java.lang.reflect.Method;
import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.springframework.stereotype.Component;
import io.quarkcloud.quarkcore.service.Config;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.ClassLoader;

@Aspect
@Component
public class AdminResourceActionRenderAspect {

    // 加载基础资源包路径
    public String[] basePackages = Config.getInstance().getBasePackages("admin");

    @Pointcut("@annotation(io.quarkcloud.quarkadmin.annotation.AdminResourceActionRender)")
    private void AdminResourceActionRender() {}

    /**
     * 环绕通知
     */
    @Around("AdminResourceActionRender()")
    public Object advice(ProceedingJoinPoint joinPoint) throws Throwable {

        // 得到连接点执行的方法对象
        MethodSignature signature= (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
 
        // 得到方法上的注解
        AdminResourceActionRender annotation = method.getAnnotation(AdminResourceActionRender.class);
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

        Object result = null;

        // 扫描包含的类
        Reflections reflections = new Reflections(basePackages[0], Scanners.SubTypes, Scanners.TypesAnnotated);
        
        // 获取所有类
        @SuppressWarnings("rawtypes")
        Set<Class<? extends ResourceImpl>> classes = reflections.getSubTypesOf(ResourceImpl.class);
        for (Class<?> clazz : classes) {
            if(clazz.getSimpleName().equals(resource)) {
                result = new ClassLoader().setClazz(clazz).doMethod("actionRender", newContext);
            }
        }

        if (result == null) {
            return joinPoint.proceed();
        }

        // 调用类方法
        return result;
    }
}
