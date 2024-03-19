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

@Aspect
@Component
public class AdminResourceFieldsAspect {
    @Pointcut("@annotation(io.quarkcloud.quarkadmin.annotation.AdminResourceFields)")
    private void AdminResourceFields() {}

    /**
     * 环绕通知
     */
    @Around("AdminResourceFields()")
    public Object advice(ProceedingJoinPoint joinPoint) throws Throwable {

        //得到连接点执行的方法对象
        MethodSignature signature= (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
 
        //得到方法上的注解
        AdminResourceFields annotation = method.getAnnotation(AdminResourceFields.class);
        if (annotation!=null){
            //获取注解属性的value值
            String value = annotation.value();
            return value;
        }

        // 这里是调用原来的方法
        return joinPoint.proceed();
    }

    @Before("AdminResourceFields()")
    public void record(JoinPoint joinPoint) {
        System.out.println("Before");
    }

    @After("AdminResourceFields()")
    public void after() {
        System.out.println("After");
    }
}
