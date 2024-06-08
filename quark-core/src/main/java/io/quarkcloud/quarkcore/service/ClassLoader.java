package io.quarkcloud.quarkcore.service;

import java.lang.reflect.InvocationTargetException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ClassLoader implements ApplicationContextAware {

    // 类路径
    public String classPath;

    // spring上下文
    private static ApplicationContext applicationContext = null;

    // 加载当前类
    public ClassLoader setClassPath(String classPath) {
        this.classPath = classPath;

        return this;
    }

    @Override
    public void setApplicationContext(@SuppressWarnings("null") ApplicationContext applicationContext) throws BeansException {
        if (ClassLoader.applicationContext == null) {
            ClassLoader.applicationContext = applicationContext;
        }
    }

    //获取 applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 加载指定路径的类。
     * 
     * @param classPath 要加载的类的路径。
     * @return 返回加载的类的实例，如果加载失败或无法实例化，则返回null。
     */
    public Object getInstance() {
        String classPath = this.classPath;
        if (classPath=="") {
            return null;
        }

        Object classInstance = null;
        try {
            Class<?> clazz = Class.forName(classPath);
            classInstance = ClassLoader.getApplicationContext().getBean(clazz);
        } catch (BeansException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return classInstance;
    }

    // 执行指定方法
    public Object doMethod(String methodName) {
        Object classInstance = getInstance();
        if (classInstance==null) {
            return null;
        }

        Object result = null;
        try {
            Class<?> clazz = Class.forName(classPath);
            try {
                result = clazz.getMethod(methodName).invoke(classInstance);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }

    // 执行指定方法
    public Object doMethod(String methodName, Context context) {
        Object classInstance = getInstance();
        if (classInstance==null) {
            return null;
        }

        Object result = null;
        try {
            Class<?> clazz = Class.forName(classPath);
            try {
                result = clazz.getMethod(methodName,Context.class).invoke(classInstance, context);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }
}
