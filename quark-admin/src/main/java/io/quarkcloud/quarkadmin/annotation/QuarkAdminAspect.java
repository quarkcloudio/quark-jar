package io.quarkcloud.quarkadmin.annotation;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.service.Config;

@Aspect
@Component
public class QuarkAdminAspect {
    @Pointcut("@annotation(io.quarkcloud.quarkadmin.annotation.QuarkAdmin)")
    private void QuarkAdmin() {}

    /**
     * 环绕通知
     */
    @Around("QuarkAdmin()")
    public Object advice(ProceedingJoinPoint joinPoint) throws Throwable {

        //得到连接点执行的方法对象
        MethodSignature signature= (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
 
        //得到方法上的注解
        QuarkAdmin annotation = method.getAnnotation(QuarkAdmin.class);
        if (annotation!=null){
            //获取注解属性的value值
            String[] value = annotation.value();
            Config.getInstance().setBasePackages(value);
            System.out.println("QuarkAdmin value:"+value);
        }

        // 这里是调用原来的方法
        return joinPoint.proceed();
    }

    @Before("QuarkAdmin()")
    public void record(JoinPoint joinPoint) {
        System.out.println("QuarkAdmin Before");
    }

    @After("QuarkAdmin()")
    public void after() {
        System.out.println("QuarkAdmin After");
    }
}
