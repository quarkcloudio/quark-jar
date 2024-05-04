package io.quarkcloud.quarkcore.service;

import java.lang.reflect.InvocationTargetException;

public class ClassLoader {

    // 类路径
    public String classPath;

    // 加载当前类
    public ClassLoader(String classPath) {
        this.classPath = classPath;
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
            classInstance = clazz.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
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
}