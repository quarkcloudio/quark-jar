package io.quarkcloud.quarkadmin.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ResourceAspect {
    @Pointcut("@annotation(io.quarkcloud.quarkadmin.annotation.Resource)")
    private void Resource() {}

    /**
     * 环绕通知
     */
    @Around("Resource()")
    public void advice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around begin...");
        //执行到这里走原来的方法
        joinPoint.proceed();
        System.out.println("around after....");
    }

    @Before("Resource()")
    public void record(JoinPoint joinPoint) {
        System.out.println("Before");
    }

    @After("Resource()")
    public void after() {
        System.out.println("After");
    }
}
