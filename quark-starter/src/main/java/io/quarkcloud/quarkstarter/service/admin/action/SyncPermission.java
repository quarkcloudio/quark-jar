package io.quarkcloud.quarkstarter.service.admin.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import io.quarkcloud.quarkadmin.component.message.Message;
import io.quarkcloud.quarkadmin.entity.PermissionEntity;
import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.service.ResourceService;
import io.quarkcloud.quarkadmin.template.dashboard.impl.DashboardImpl;
import io.quarkcloud.quarkadmin.template.layout.impl.LayoutImpl;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkadmin.template.resource.impl.action.AjaxImpl;
import io.quarkcloud.quarkcore.service.Config;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkcore.service.ClassLoader;

public class SyncPermission<M, T> extends AjaxImpl<ResourceMapper<T>, T> {

    // 构造函数
    public SyncPermission() {

        // 设置按钮名称
        this.name = "同步权限";

        //  执行成功后刷新的组件
        this.reload = "table";

        // 设置展示位置
        this.setOnlyOnIndex(true);
    }

    // 执行行为句柄
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object handle(Context context, UpdateWrapper<T> updateWrapper, ResourceService<ResourceMapper<T>, T> resourceService) {
        Map<String, RequestMappingHandlerMapping> handlerMappingBeans = context.getSpringBootContext().getBeansOfType(RequestMappingHandlerMapping.class);

        // 获取配置文件
        String[] basePackages = Config.getInstance().getBasePackages("admin");
        if (basePackages.length == 0) {
            return Message.error("无法加载包！");
        }

        List<Map<String, String>> urlPaths = new ArrayList<>();

        // Specify the base package to scan
        String basePackage = basePackages[0];

        // Create a Reflections object with the specified package
        Reflections reflections = new Reflections(basePackage, Scanners.SubTypes, Scanners.TypesAnnotated);

        for (Map.Entry<String, RequestMappingHandlerMapping> entry : handlerMappingBeans.entrySet()) {
            RequestMappingHandlerMapping handlerMapping = entry.getValue();
            Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();

            for (Map.Entry<RequestMappingInfo, HandlerMethod> handlerEntry : handlerMethods.entrySet()) {
                RequestMappingInfo requestMappingInfo = handlerEntry.getKey();
                HandlerMethod handlerMethod = handlerEntry.getValue();
                Set<String> patternValues = requestMappingInfo.getPatternValues();
                Set<RequestMethod> methods = requestMappingInfo.getMethodsCondition().getMethods();
                if (patternValues.size() > 0) {
                    String patternValue = (String) patternValues.toArray()[0];
                    String methodName = null;
                    if (methods.size()>1) {
                        methodName = "Any";
                    } else if (methods.size() == 1) {
                        RequestMethod requestMethod = (RequestMethod) methods.toArray()[0];
                        methodName = requestMethod.name();
                    }
    
                    if (patternValue.startsWith("/api/admin/") && !patternValue.startsWith("/api/admin/login")) {
                        switch (handlerMethod.getMethod().getDeclaringClass().getSimpleName()) {
                            case "AdminDashboardController":
                                // 获取所有Dashboard类
                                Set<Class<? extends DashboardImpl>> dashboardClasses = reflections.getSubTypesOf(DashboardImpl.class);
                                for (Class<?> clazz : dashboardClasses) {
                                    String clazzName = clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().substring(1);
                                    String newPatternValue = patternValue.replace("{resource}", clazzName);
                                    urlPaths.add(Map.of("url", newPatternValue,"method", methodName));
                                }
                                break;
                            case "AdminLayoutController":
                                // 获取所有Layout类
                                Set<Class<? extends LayoutImpl>> layoutClasses = reflections.getSubTypesOf(LayoutImpl.class);
                                for (Class<?> clazz : layoutClasses) {
                                    String clazzName = clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().substring(1);
                                    String newPatternValue = patternValue.replace("{resource}", clazzName);
                                    urlPaths.add(Map.of("url", newPatternValue,"method", methodName));
                                }
                                break;
                            case "AdminResourceController":
                                // 获取所有Resource类
                                Set<Class<? extends ResourceImpl>> resourceClasses = reflections.getSubTypesOf(ResourceImpl.class);
                                for (Class<?> clazz : resourceClasses) {
                                    List<Object> actions = (List<Object>) new ClassLoader().setClazz(clazz).doMethod("actions", context);
                                    String clazzName = clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().substring(1);
                                    String newResourcePatternValue = patternValue.replace("{resource}", clazzName);
                                    for (Object action : actions) {
                                        String uriKey = TypeUtils.getRawType(action.getClass(), null).toString();
                                        String[] uriKeys = uriKey.split("\\.");
                                        uriKey = uriKeys[uriKeys.length - 1].replaceAll("([a-z])([A-Z])", "$1-$2").toLowerCase();
                                        String newPatternValue = newResourcePatternValue.replace("{uriKey}", uriKey);
                                        urlPaths.add(Map.of("url", newPatternValue,"method", methodName));
                                   }
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }

        // 去重
        List<Map<String, String>> list = urlPaths.stream().distinct().collect(Collectors.toList());

        boolean result = true;
        for (Map<String, String> item : list) {
            String url = item.get("url").replace("/api/admin/", "").replace("/", "_").replace("-", "_") + "_" + item.get("method");
            String name = toCamelCase(url, "?", "");
            QueryWrapper<PermissionEntity> queryWrapper = new QueryWrapper<PermissionEntity>().eq("name", name);
            PermissionEntity permissionEntity = new PermissionEntity();
            PermissionEntity hasPermissionEntity = permissionEntity.selectOne(queryWrapper);
            if (hasPermissionEntity == null) {
                permissionEntity.setPath(item.get("url"));
                permissionEntity.setMethod(item.get("method"));
                permissionEntity.setName(name);
                permissionEntity.setGuardName("admin");
                boolean getResult = permissionEntity.insert();
                if (!getResult) {
                    result = false;
                }
            }
        }

        if (!result) {
            return Message.error("操作失败！");
        }

        return Message.success("操作成功！");
    }

    public String toCamelCase(String input, String charToReplace, String replacement) {
        // Replace specific characters
        input = input.replace(charToReplace, replacement);
    
        // Convert to CamelCase
        String[] parts = input.split("_");
        StringBuilder camelCaseString = new StringBuilder();
    
        for (String part : parts) {
            if (!part.isEmpty()) {
                if (camelCaseString.length() == 0) {
                    camelCaseString.append(StringUtils.capitalize(part));
                } else {
                    camelCaseString.append(StringUtils.capitalize(part.toLowerCase()));
                }
            }
        }
    
        return camelCaseString.toString();
    }

}