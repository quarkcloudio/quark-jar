package io.quarkcloud.quarkcore.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Reflect {

    // 被操作对象
    Object obj;

    // 被操作方法名
    String methodName;

    // 被操作字段名
    String fieldName;

    // 方法参数
    Class<?>[] parameterTypes;

    // 构造方法
    public Reflect(Object obj) {
        this.obj = obj;
    }

    // 检查字段是否存在
    public boolean checkFieldExist() {
        if (fieldName==null || obj==null) {
            return false;
        }
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            return field != null; // 字段存在
        } catch (NoSuchFieldException e) {
            return false; // 字段不存在
        }
    }

    // 检查字段是否存在
    public boolean checkFieldExist(String fieldName) {
        if (fieldName==null || obj==null) {
            return false;
        }

        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            return field != null; // 字段存在
        } catch (NoSuchFieldException e) {
            return false; // 字段不存在
        }
    }

    // 获取字段值
    public Object getFieldValue() {
        if (fieldName==null || obj==null) {
            return false;
        }
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true); // 使私有字段可访问
            return field.get(obj); // 获取字段的值
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null; // 字段不存在或不可访问
        }
    }

    // 获取字段值
    public Object getFieldValue(String fieldName) {
        if (fieldName==null || obj==null) {
            return false;
        }
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true); // 使私有字段可访问
            return field.get(obj); // 获取字段的值
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null; // 字段不存在或不可访问
        }
    }

    // 检查方法是否存在
    public boolean checkMethodExist() {
        if (obj == null) {
            return false;
        }
        try {
            Method method = this.obj.getClass().getMethod(methodName);
            return method != null; // 方法存在
        } catch (NoSuchMethodException e) {
            return false; // 方法不存在
        }
    }

    // 检查方法是否存在
    public boolean checkMethodExist(String methodName) {
        if (obj == null) {
            return false;
        }
        try {
            Method method = this.obj.getClass().getMethod(methodName);
            this.methodName = methodName;
            return method != null; // 方法存在
        } catch (NoSuchMethodException e) {
            return false; // 方法不存在
        }
    }

    // 检查方法是否存在
    public boolean checkMethodExist(String methodName, Class<?>... parameterTypes) {
        if (obj == null) {
            return false;
        }
        try {
            Method method = this.obj.getClass().getMethod(methodName, parameterTypes);
            this.methodName = methodName;
            this.parameterTypes = parameterTypes;
            return method != null; // 方法存在
        } catch (NoSuchMethodException e) {
            return false; // 方法不存在
        }
    }

    // 反射执行方法
    public Object invoke() {
        Object result = new Object();
        if (methodName==null||obj==null) {
            return result;
        }
        try {
            result = obj.getClass().getMethod(methodName).invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 反射执行方法
    public Object invoke(String methodName) {
        Object result = new Object();
        if (methodName==null || obj==null) {
            return false;
        }
        try {
            result = obj.getClass().getMethod(methodName).invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // invoke
    public Object invoke(String methodName, Class<?>... parameterTypes) {
        Object result = new Object();
        if (methodName==null || obj==null) {
            return false;
        }
        try {
            result = obj.getClass().getMethod(methodName, parameterTypes).invoke(obj, parameterTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
