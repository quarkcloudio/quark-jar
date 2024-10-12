package io.quarkcloud.quarkcore.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
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
@Component
public class Context implements ApplicationContextAware {

    // SpringBoot上下文
    private static ApplicationContext applicationContext = null;

    // 连接点
    private ProceedingJoinPoint joinPoint;

    // 得到连接点执行的方法对象
    private MethodSignature joinPointSignature;

    // 得到连接点执行的方法
    private Method joinPointMethod;

    // 请求对象
    private HttpServletRequest request;

    // 响应对象
    private HttpServletResponse response;

    // 请求体，这里可能会有性能问题
    private String readerBody;

    // 构造函数
    public Context() {}

    // 构造函数
    public Context(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        readerBody = stringBuilder.toString();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (Context.applicationContext == null) {
            Context.applicationContext = applicationContext;
        }
    }

    // 设置连接点
    public Context setJoinPoint(ProceedingJoinPoint joinPoint) {
        this.joinPointSignature = (MethodSignature) joinPoint.getSignature();
        this.joinPointMethod = joinPointSignature.getMethod();
        this.joinPoint = joinPoint;
        return this;
    }

    //获取SpringBoot上下文
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    // 获取SpringBoot上下文
    public ApplicationContext getSpringBootContext() {
        return Context.getApplicationContext();
    }

    // 获取连接点注解
    public <T extends Annotation> T getJoinPointAnnotation(Class<T> annotationClass) {
        return this.joinPointMethod.getAnnotation(annotationClass);
    }

    /**
     * 获取BufferedReader对象，用于读取字符串内容
     * 该方法将内部的readerBody字符串转换为BufferedReader对象，以便能够以流的方式读取内容
     * 
     * @return BufferedReader对象，用于读取readerBody字符串的内容
     */
    public BufferedReader getReader() {
        return new BufferedReader(new StringReader(readerBody));
    }

    /**
     * 获取当前类的RequestMapping注解的value属性值
     * 该方法主要用于AOP切点中，以获取被切方法上的RequestMapping注解信息
     * 如果被切方法没有RequestMapping注解，则返回null
     * 
     * @return RequestMapping注解的value属性值数组，如果没有注解则返回null
     */
    public String[] getRequestMapping() {
        RequestMapping requestMapping= this.getJoinPointAnnotation(RequestMapping.class);
        if (requestMapping == null) {
            return null;
        }
        return requestMapping.value();
    }

    /**
     * 获取路径变量值
     * 
     * 该方法用于从当前请求的URI中提取出特定的路径变量值
     * 它首先获取到定义了@RequestMapping注解的请求映射路径，然后将此路径与当前请求的URI进行比较，
     * 以提取出所有的路径变量如果找到了指定名称的路径变量，则返回其值
     * 
     * @param pathVariable 要提取的路径变量的名称
     * @return 如果找到了指定的路径变量，则返回其值；否则返回null
     */
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

    /**
     * 获取远程主机的名称
     * 
     * 此方法用于获取当前请求的远程主机名称，即发起请求的客户端的主机名
     * 在处理网络请求时，此信息可用于识别或审计请求来源
     * 
     * @return 返回远程主机的名称
     */
    public String getRemoteHost() {
        return request.getRemoteHost();
    }

    /**
     * 获取请求的URI
     * 
     * 此方法简单地转发HttpServletRequest对象中的getRequestURI方法
     * 用于获取当前请求的统一资源标识符字符串
     * 
     * @return 当前请求的URI字符串
     */
    public String getRequestURI() {
        return request.getRequestURI();
    }

    /**
     * 获取请求头
     * 
     * @param arg0 请求头的名称
     * @return 返回指定名称的请求头的值
     */
    public String getHeader(String arg0) {
        return request.getHeader(arg0);
    }

    /*
    * 获取GET、POST请求参数数据
    * 
    * @param arg0 参数名称，用于指定需要获取的请求参数
    * @return 返回参数的值，如果参数不存在或者出现异常，可能返回null或空字符串
    */
    public String getParameter(String arg0) {
        return request.getParameter(arg0);
    }

    /**
     * 获取请求参数的映射表
     * 
     * 此方法用于获取所有请求参数的名称和值的映射表，其中参数名称对应一个字符串数组，
     * 因为一个参数可以被多次发送，每次发送的值都包含在这个数组中
     * 
     * @return 包含请求参数名称和对应值的Map对象如果参数不存在，则对应的值为null
     */
    public Map<String, String[]> getParameterMap() {
        return request.getParameterMap();
    }

    /**
     * 获取查询字符串
     * 
     * 此方法返回当前HTTP请求中的查询字符串查询字符串是URL中跟随问号（?）的部分，包含键值对表示请求参数
     * 
     * @return 当前HTTP请求的查询字符串如果请求URL没有查询字符串，则返回null
     */
    public String getQueryString() {
        return request.getQueryString();
    }

    /**
     * 读取请求体中的JSON数据，并将其转换为指定的Java对象
     * 
     * @param valueType 指定要转换的Java对象类型
     * @param <T> 泛型标记，表示可以是任何类型
     * @return 转换后的Java对象实例如果转换失败，可能返回null
     */
    public <T> T getRequestBody(Class<T> valueType) {
        ObjectMapper mapper = new ObjectMapper();
        T map = null;
        try {
            map = mapper.readValue(this.getReader(), valueType);
        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 从请求中获取参数值
     * 
     * 该方法通过JSON请求体的字符串形式，解析出一个Map对象，并从该Map中获取指定的参数值
     * 如果在解析过程中发生错误（如JSON格式错误，数据绑定错误，或IO错误），则打印错误信息
     * 如果成功解析出非空的Map且指定参数存在，则返回该参数的值；否则，返回null
     * 
     * @param arg0 参数的名称
     * @return 参数的值，如果参数不存在或解析失败则返回null
     */
    @SuppressWarnings("unchecked")
    public Object getRequestParam(String arg0) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = null;
        try {
            map = mapper.readValue(this.getReader(), Map.class);
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

    /**
     * 根据请求参数填充指定类型的实体对象
     * 
     * @param valueType 实体对象的类型，必须提供一个无参构造函数
     * @param <T>       泛型标记，表示实体对象的类型
     * @return 填充后的实体对象实例
     */
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

    // getOutputStream
    public void setContentType(String contentType) {
        response.setContentType(contentType);
    }

    // setHeader
    public void setHeader(String arg0, String arg1) {
        response.setHeader(arg0, arg1);
    }

    // getOutputStream
    public ServletOutputStream getOutputStream() throws IOException {
        return response.getOutputStream();
    }

    // 获取头部的token
    public String getToken() {
        String authHeader = getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        String token = authHeader.substring("Bearer ".length());
        return token;
    }

    // 解析token
    public JWT parseToken() {
        String authHeader = getHeader("Authorization");
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
        String[] uri = getRequestURI().split("/");
        return uri[uri.length - 1].equals("index");
    }

    public boolean isCreating() {
        String[] uri = getRequestURI().split("/");
        String lastSegment = uri[uri.length - 1];
        return lastSegment.equals("create") || lastSegment.equals("store");
    }

    public boolean isEditing() {
        String[] uri = getRequestURI().split("/");
        String lastSegment = uri[uri.length - 1];
        return lastSegment.equals("edit") || lastSegment.equals("save");
    }

    public boolean isDetail() {
        String[] uri = getRequestURI().split("/");
        return uri[uri.length - 1].equals("detail");
    }

    public boolean isExport() {
        String[] uri = getRequestURI().split("/");
        return uri[uri.length - 1].equals("export");
    }

    public boolean isImport() {
        String[] uri = getRequestURI().split("/");
        return uri[uri.length - 1].equals("import");
    }
}
