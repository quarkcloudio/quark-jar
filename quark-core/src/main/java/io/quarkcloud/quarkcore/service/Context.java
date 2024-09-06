package io.quarkcloud.quarkcore.service;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import lombok.Data;

@Data
public class Context {

    // joinPoint
    public ProceedingJoinPoint joinPoint;

    // 得到连接点执行的方法对象
    private MethodSignature joinPointSignature;

    // 得到连接点执行的方法
    private Method joinPointMethod;

    // request
    public HttpServletRequest request;

    // response
    public HttpServletResponse response;

    // constructor
    public Context(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    // setJoinPoint
    public Context setJoinPoint(ProceedingJoinPoint joinPoint) {
        this.joinPointSignature = (MethodSignature) joinPoint.getSignature();
        this.joinPointMethod = joinPointSignature.getMethod();
        this.joinPoint = joinPoint;
        return this;
    }

    // getJoinPointAnnotation
    public <T extends Annotation> T getJoinPointAnnotation(Class<T> annotationClass) {
        return this.joinPointMethod.getAnnotation(annotationClass);
    }

    // getRequestMapping
    public String[] getRequestMapping() {
        RequestMapping requestMapping= this.getJoinPointAnnotation(RequestMapping.class);
        if (requestMapping == null) {
            return null;
        }
        return requestMapping.value();
    }

    // getPathVariable
    public String getPathVariable(String pathVariable) {
        RequestMapping requestMapping= this.getJoinPointAnnotation(RequestMapping.class);
        if (requestMapping == null) {
            return null;
        }
        Map<String, String> pathVariables = new HashMap<>();
        String[] paths = requestMapping.value();
        String url = this.request.getRequestURI();
        for (int i = 0; i < paths.length; i++) {
            pathVariables = this.extractPathVariables(paths[i], url);
            if (!pathVariables.get(pathVariable).isEmpty()) {
                return pathVariables.get(pathVariable);
            }
        }
        return null;
    }

    /**
     * 从URL中提取路径变量。
     * 
     * 此方法通过比较URL模式和实际请求的URL，从模式中定义的变量部分提取出变量的值。
     * URL模式和URL都以斜杠('/')分隔的字符串形式提供。
     * 
     * @param urlPattern URL模式，用于定义URL中变量的结构。
     * @param url 实际请求的URL。
     * @return 包含提取的路径变量名称和值的映射。
     */
    private Map<String, String> extractPathVariables(String urlPattern, String url) {
        Map<String, String> pathVariables = new HashMap<>();
        String[] patternSegments = urlPattern.split("/");
        String[] urlSegments = url.split("/");
        // 遍历路径段，提取路径变量的值
        for (int i = 0; i < patternSegments.length; i++) {
            String patternSegment = patternSegments[i];
            if (patternSegment.startsWith("{") && patternSegment.endsWith("}")) {
                String variableName = patternSegment.substring(1, patternSegment.length() - 1);
                String variableValue = urlSegments[i];
                pathVariables.put(variableName, variableValue);
            }
        }
        return pathVariables;
    }

    // getHeader
    public String getHeader(String arg0) {
        return request.getHeader(arg0);
    }

    // getParameter
    public String getParameter(String arg0) {
        return request.getParameter(arg0);
    }

    // getParameterMap
    public Map<String, String[]> getParameterMap() {
        return request.getParameterMap();
    }

    // getRequestBody
    public <T> T getRequestBody(Class<T> valueType) {
        ObjectMapper mapper = new ObjectMapper();
        T map = null;
        try {
            map = mapper.readValue(request.getReader(), valueType);
        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    // getRequestParam
    @SuppressWarnings("unchecked")
    public Object getRequestParam(String arg0) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = null;
        try {
            map = mapper.readValue(request.getReader(), Map.class);
        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (map!=null && !map.isEmpty()) {
            return map.get(arg0);
        }
        return map;
    }

    // getParameterBody
    public <T> T getParameterBody(Class<T> valueType) {
        T entity = null;
        try {
            entity = valueType.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        Map<String, String[]> map = this.getParameterMap();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            String fieldName = entry.getKey();
            String[] fieldValue = entry.getValue();
            Field field;
            try {
                field = valueType.getDeclaredField(fieldName);
                field.setAccessible(true);
                try {
                    // 根据字段类型转换值
                    Class<?> fieldType = field.getType();
                    if (fieldType == String.class) {
                        field.set(entity, fieldValue[0]);
                    } else if (fieldType == int.class || fieldType == Integer.class) {
                        field.set(entity, Integer.parseInt(fieldValue[0]));
                    } else if (fieldType == long.class || fieldType == Long.class) {
                        field.set(entity, Long.parseLong(fieldValue[0]));
                    } else if (fieldType == double.class || fieldType == Double.class) {
                        field.set(entity, Double.parseDouble(fieldValue[0]));
                    } else if (fieldType == float.class || fieldType == Float.class) {
                        field.set(entity, Float.parseFloat(fieldValue[0]));
                    } else if (fieldType == short.class || fieldType == Short.class) {
                        field.set(entity, Short.parseShort(fieldValue[0]));
                    } else if (fieldType == byte.class || fieldType == Byte.class) {
                        field.set(entity, Byte.parseByte(fieldValue[0]));
                    } else if (fieldType == boolean.class || fieldType == Boolean.class) {
                        field.set(entity, Boolean.parseBoolean(fieldValue[0]));
                    } else if (fieldType == char.class || fieldType == Character.class) {
                        field.set(entity, fieldValue[0].charAt(0));
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }
        }
        return entity;
    }

    // 从search参数中获取分页数量
    @SuppressWarnings("unchecked")
    public Long getPageSizeFromSearch(Long pageSize) {
        String searchParam = this.getParameter("search");
        if (searchParam != null && searchParam != "") {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = null;
            try {
                map = mapper.readValue(searchParam, Map.class);
            } catch (JsonProcessingException e) {
                pageSize = 0L;
            }
            if (map!=null) {
                Object getPageSize = map.get("pageSize");
                if (getPageSize!=null && getPageSize instanceof String) {
                    pageSize = Long.parseLong((String) getPageSize);
                }
                if (getPageSize instanceof Number) {
                    pageSize = ((Number) getPageSize).longValue();
                }
            }
        }
        return pageSize;
    }

    // 从search参数中获取页码
    @SuppressWarnings("unchecked")
    public Long getPageFromSearch() {
        long currentPage = 1;
        String searchParam = this.getParameter("search");
        if (searchParam != null && searchParam != "") {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = null;
            try {
                map = mapper.readValue(searchParam, Map.class);
            } catch (JsonProcessingException e) {
                currentPage = 1;
            }
            if (map!=null) {
                Object getPage = map.get("page");
                if (getPage!=null && getPage instanceof String) {
                    currentPage = Long.parseLong((String) getPage);
                }
                if (getPage instanceof Number) {
                    currentPage = ((Number) getPage).longValue();
                }
            }
        }
        return currentPage;
    }

    // 解析表格列可编辑数据
    public <T> T getEditableBody(Class<T> clazz) {
        T entity = null;
        try {
            entity = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        Map<String, String[]> map = this.getParameterMap();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            String fieldName = entry.getKey();
            String[] fieldValue = entry.getValue();
            Field field;
            try {
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                try {
                    // 根据字段类型转换值
                    Class<?> fieldType = field.getType();
                    if (fieldType == String.class) {
                        field.set(entity, fieldValue[0]);
                    } else if (fieldType == int.class || fieldType == Integer.class) {
                        if (fieldValue[0].equals("true")) {
                            field.set(entity, 1);
                        } else if (fieldValue[0].equals("false")) {
                            field.set(entity, 0);
                        } else {
                            field.set(entity, Integer.parseInt(fieldValue[0]));
                        }
                    } else if (fieldType == long.class || fieldType == Long.class) {
                        field.set(entity, Long.parseLong(fieldValue[0]));
                    } else if (fieldType == double.class || fieldType == Double.class) {
                        field.set(entity, Double.parseDouble(fieldValue[0]));
                    } else if (fieldType == float.class || fieldType == Float.class) {
                        field.set(entity, Float.parseFloat(fieldValue[0]));
                    } else if (fieldType == short.class || fieldType == Short.class) {
                        field.set(entity, Short.parseShort(fieldValue[0]));
                    } else if (fieldType == byte.class || fieldType == Byte.class) {
                        field.set(entity, Byte.parseByte(fieldValue[0]));
                    } else if (fieldType == boolean.class || fieldType == Boolean.class) {
                        field.set(entity, Boolean.parseBoolean(fieldValue[0]));
                    } else if (fieldType == char.class || fieldType == Character.class) {
                        field.set(entity, fieldValue[0].charAt(0));
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }
        }
        return entity;
    }

    // 解析token
    public JWT parseToken() {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        String token = authHeader.substring("Bearer ".length());
        String appKey = Env.getProperty("app.key");
        if (!JWT.of(token).setKey(appKey.getBytes()).verify()) {
            return null;
        }
        try {
            JWTValidator.of(token).validateDate(DateUtil.date());
        } catch (Exception e) {
            return null;
        }
        final JWT jwt = JWTUtil.parseToken(token);

        return jwt;
    }

    public boolean isIndex() {
        String[] uri = this.request.getRequestURI().split("/");
        return uri[uri.length - 1].equals("index");
    }

    public boolean isCreating() {
        String[] uri = this.request.getRequestURI().split("/");
        String lastSegment = uri[uri.length - 1];
        return lastSegment.equals("create") || lastSegment.equals("store");
    }

    public boolean isEditing() {
        String[] uri = this.request.getRequestURI().split("/");
        String lastSegment = uri[uri.length - 1];
        return lastSegment.equals("edit") || lastSegment.equals("save");
    }

    public boolean isDetail() {
        String[] uri = this.request.getRequestURI().split("/");
        return uri[uri.length - 1].equals("detail");
    }

    public boolean isExport() {
        String[] uri = this.request.getRequestURI().split("/");
        return uri[uri.length - 1].equals("export");
    }

    public boolean isImport() {
        String[] uri = this.request.getRequestURI().split("/");
        return uri[uri.length - 1].equals("import");
    }
}
