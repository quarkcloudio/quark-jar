package io.quarkcloud.quarkadmin.annotation;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.lang.reflect.Method;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import io.quarkcloud.quarkcore.service.Config;
import io.quarkcloud.quarkcore.service.ClassLoader;

import javax.imageio.ImageIO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Aspect
@Component
public class AdminLoginCaptchaAspect {

    // 加载基础资源包路径
    public String[] basePackages = Config.getInstance().getBasePackages("admin");

    // 加载本资源包路径
    public String[] packages = {".login."};

    @Pointcut("@annotation(io.quarkcloud.quarkadmin.annotation.AdminLoginCaptcha)")
    private void AdminLoginCaptcha() {}

    // 获取加载包路径
    protected String[] getLoadPackages() {
        String[] loadPackages = {this.basePackages[0]+this.packages[0]};

        return loadPackages;
    }

    /**
     * 环绕通知
     */
    @Around("AdminLoginCaptcha()")
    public void advice(ProceedingJoinPoint joinPoint) throws Throwable {

        // 得到连接点执行的方法对象
        MethodSignature signature= (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
 
        // 得到方法上的注解
        AdminLoginCaptcha annotation = method.getAnnotation(AdminLoginCaptcha.class);
        if (annotation==null) {
            return;
        }

        // 调用原方法
        Object map = joinPoint.proceed();

        // 获取返回值
        Map<String, Object> getMap = (Map<String, Object>) map;

        // 获取资源名称
        Object resource = getMap.get("resource");
        if (resource==null) {
            return;
        }

        // 获取request对象
        Object request = getMap.get("request");
        if (request==null) {
            return;
        }

        // 获取response对象
        Object response = getMap.get("response");
        if (response==null) {
            return;
        }

        // 字符串首字母大写
        resource = resource.toString().substring(0, 1).toUpperCase() + resource.toString().substring(1);

        // 获取配置文件
        String[] basePackages = Config.getInstance().getBasePackages("admin");
        if (basePackages.length == 0) {
            return;
        }

        // 加载类，暂时只支持加载一个配置
        String[] loadPackages = getLoadPackages();
        ClassLoader classLoader = new ClassLoader(loadPackages[0]+resource);

        // 调用类方法
        classLoader.doMethod("captcha",(HttpServletRequest) request, (HttpServletResponse) response);
    }
}
