package io.quarkcloud.quarkcore.service;

import java.lang.reflect.InvocationTargetException;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

@Component
public class ClassLoader {

    // 类路径
    public String classPath;

    // 类实例
    public Class<?> clazz;

    // 加载当前类
    public ClassLoader setClassPath(String classPath) {
        this.classPath = classPath;
        return this;
    }

    // 加载当前类
    public ClassLoader setClazz(Class<?> clazz) {
        this.clazz = clazz;
        return this;
    }

    /**
     * 加载指定路径的类。
     * 
     * @param classPath 要加载的类的路径。
     * @return 返回加载的类的实例，如果加载失败或无法实例化，则返回null。
     */
    public Object getInstance() {
        Object classInstance = null;
        if (classPath!=null && !classPath.isEmpty()) {
            try {
                clazz = Class.forName(classPath);
            } catch (BeansException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (clazz != null) {
            try {
                classInstance = Context.getApplicationContext().getBean(clazz);
            } catch (BeansException e) {
                e.printStackTrace();
            }
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

        return result;
    }
}
