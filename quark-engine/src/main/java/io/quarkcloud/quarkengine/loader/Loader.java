package io.quarkcloud.quarkengine.loader;

import java.lang.reflect.Method;
import org.apache.commons.lang3.StringUtils;

public class Loader {

    // 类加载路径
    public String classPath;

    // 类名
    public String className;

    // 实例化对象
    public Object instance;

    // 构造函数
    public Loader (String classPath, String className)  {
        this.classPath = classPath;

        // 转换首字母大写
        this.className = StringUtils.capitalize(className);
    }

    // 设置类加载路径
    public Loader setClassPath(String classPath) {
        this.classPath = classPath;
        return this;
    }

    // 设置类名
    public Loader setClassName(String className) {
        this.className = className;
        return this;
    }

    public Object callMethod(String methodName) {
        
        // 获取当前线程上下文的ClassLoader对象
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        
        // 指定要加载的类名称（包括包路径）
        String fullClassPath = this.classPath+"."+this.className;
        
        try {

            // 使用ClassLoader动态加载类
            Class<?> dynamicClass = classLoader.loadClass(fullClassPath);
            
            // 创建该类的实例
            Object instance = dynamicClass.getDeclaredConstructor().newInstance();
            
            // 调用类的方法或访问其属性等操作
            Method method = dynamicClass.getMethod(methodName);

            // 调用方法
            return method.invoke(instance);
        } catch (Exception e) {
            System.out.println("Failed to load or instantiate the class.");
            e.printStackTrace();
        }

        return null;
    }

    public Object callMethod(String methodName, Class<?> args) {
        
        // 获取当前线程上下文的ClassLoader对象
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        // 指定要加载的类名称（包括包路径）
        String fullClassPath = this.classPath+"."+this.className;
        
        try {

            // 使用ClassLoader动态加载类
            Class<?> dynamicClass = classLoader.loadClass(fullClassPath);
            
            // 创建该类的实例
            Object instance = dynamicClass.getDeclaredConstructor().newInstance();
            
            // 调用类的方法或访问其属性等操作
            Method method = dynamicClass.getMethod(methodName);

            // 调用方法
            return method.invoke(instance, args);
        } catch (Exception e) {
            System.out.println("Failed to load or instantiate the class.");
            e.printStackTrace();
        }

        return null;
    }
}
