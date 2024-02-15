package io.quarkcloud.quarkengine.loader;

import java.lang.reflect.Method;

public class Loader {

    // 类加载路径
    public String classPath;

    // 实例化对象
    public Object instance;

    // 构造函数
    public Loader (String classPath)  {
        this.classPath = classPath;
    }

    // 设置类加载路径
    public Loader setClassPath(String classPath) {
        this.classPath = classPath;
        return this;
    }

    public void loadClass(String[] args) throws Exception {
        
        // 获取当前线程上下文的ClassLoader对象
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        
        // 指定要加载的类名称（包括包路径）
        String className = "io.quarkcloud.MyDynamicClass";
        
        try {

            // 使用ClassLoader动态加载类
            Class<?> dynamicClass = classLoader.loadClass(className);
            
            // 创建该类的实例
            Object instance = dynamicClass.getDeclaredConstructor().newInstance();
            
            // 调用类的方法或访问其属性等操作...
            Method method = dynamicClass.getMethod("myMethod");
            method.invoke(instance);
        } catch (Exception e) {
            System.out.println("Failed to load or instantiate the class.");
            e.printStackTrace();
        }
    }
}
