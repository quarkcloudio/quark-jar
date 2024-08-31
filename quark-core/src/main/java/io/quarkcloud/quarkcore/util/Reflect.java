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
        boolean checkResult = false;

        // 检查字段名
        if (fieldName==null || obj==null) {
            return checkResult;
        }

        // 检查字段
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            if (field != null) {
                checkResult = true;
            }
        } catch (NoSuchFieldException e) {
            checkResult = false;
        }
        if (checkResult) {
            return checkResult;
        }

        // 检查父类字段
        try {
            Field superField = obj.getClass().getSuperclass().getDeclaredField(fieldName);
            if (superField != null) {
                checkResult = true;
            }
        } catch (NoSuchFieldException e) {
            checkResult = false;
        }

        return checkResult;
    }

    // 检查字段是否存在
    public boolean checkFieldExist(String fieldName) {
        boolean checkResult = false;

        // 检查字段名
        if (fieldName==null || obj==null) {
            return checkResult;
        }

        // 检查字段
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            if (field != null) {
                checkResult = true;
            }
        } catch (NoSuchFieldException e) {
            checkResult = false;
        }
        if (checkResult) {
            return checkResult;
        }

        // 检查父类字段
        try {
            Field superField = obj.getClass().getSuperclass().getDeclaredField(fieldName);
            if (superField != null) {
                checkResult = true;
            }
        } catch (NoSuchFieldException e) {
            checkResult = false;
        }

        return checkResult;
    }

    // 获取字段值
    public Object getFieldValue() {
        Field field;
        if (fieldName==null || obj==null) {
            return null;
        }
        try {
            field = obj.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            field = null;
        }
        if (field!=null) {
            field.setAccessible(true);
            try {
                return field.get(obj);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                return null;
            }
        }

        // 检查父类字段
        try {
            Field superField = obj.getClass().getSuperclass().getDeclaredField(fieldName);
            if (superField != null) {
                field = superField;
            }
        } catch (NoSuchFieldException e) {
            field = null;
        }

        if (field!=null) {
            field.setAccessible(true); // 使私有字段可访问
            try {
                return field.get(obj);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                return null;
            }
        }

        return null;
    }

    // 获取字段值
    public Object getFieldValue(String fieldName) {
        Field field;
        if (fieldName==null || obj==null) {
            return null;
        }
        try {
            field = obj.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            field = null;
        }
        if (field!=null) {
            field.setAccessible(true);
            try {
                return field.get(obj);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                return null;
            }
        }

        // 检查父类字段
        try {
            Field superField = obj.getClass().getSuperclass().getDeclaredField(fieldName);
            if (superField != null) {
                field = superField;
            }
        } catch (NoSuchFieldException e) {
            field = null;
        }

        if (field!=null) {
            field.setAccessible(true); // 使私有字段可访问
            try {
                return field.get(obj);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                return null;
            }
        }

        return null;
    }

    // 设置字段值
    public void setFieldValue(Object fieldValue) {
        Field field;
        if (fieldName==null || obj==null) {
            return;
        }
        try {
            field = obj.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            field = null;
        }
        if (field!=null) {
            field.setAccessible(true);
            try {
                field.set(obj, fieldValue);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                return;
            }
        }

        // 检查父类字段
        try {
            Field superField = obj.getClass().getSuperclass().getDeclaredField(fieldName);
            if (superField != null) {
                field = superField;
            }
        } catch (NoSuchFieldException e) {
            field = null;
        }

        if (field!=null) {
            field.setAccessible(true); // 使私有字段可访问
            try {
                field.set(obj, fieldValue);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                return;
            }
        }

        return;
    }

    // 设置字段值
    public void setFieldValue(String fieldName, Object fieldValue) {
        Field field;
        if (fieldName==null || obj==null) {
            return;
        }
        try {
            field = obj.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            field = null;
        }
        if (field!=null) {
            field.setAccessible(true);
            try {
                field.set(obj, fieldValue);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                return;
            }
        }

        // 检查父类字段
        try {
            Field superField = obj.getClass().getSuperclass().getDeclaredField(fieldName);
            if (superField != null) {
                field = superField;
            }
        } catch (NoSuchFieldException e) {
            field = null;
        }

        if (field!=null) {
            field.setAccessible(true); // 使私有字段可访问
            try {
                field.set(obj, fieldValue);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                return;
            }
        }

        return;
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
    public Object invoke(String methodName, Class<?> parameterTypes, Object... args) {
        Object result = new Object();
        if (methodName==null || obj==null) {
            return false;
        }
        try {
            result = obj.getClass().getMethod(methodName, parameterTypes).invoke(obj, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
