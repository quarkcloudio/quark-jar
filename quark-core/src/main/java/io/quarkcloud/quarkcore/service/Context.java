package io.quarkcloud.quarkcore.service;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
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
