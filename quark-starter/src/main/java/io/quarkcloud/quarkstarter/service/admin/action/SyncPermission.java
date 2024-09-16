package io.quarkcloud.quarkstarter.service.admin.action;

import java.util.Map;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import io.quarkcloud.quarkadmin.component.message.Message;
import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.service.ResourceService;
import io.quarkcloud.quarkadmin.template.resource.impl.action.AjaxImpl;
import io.quarkcloud.quarkcore.service.Context;

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
    public Object handle(Context context, UpdateWrapper<T> updateWrapper, ResourceService<ResourceMapper<T>, T> resourceService) {
        Map<String, RequestMappingHandlerMapping> handlerMappingBeans = context.getSpringBootContext().getBeansOfType(RequestMappingHandlerMapping.class);

        for (Map.Entry<String, RequestMappingHandlerMapping> entry : handlerMappingBeans.entrySet()) {
            RequestMappingHandlerMapping handlerMapping = entry.getValue();
            Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();

            for (Map.Entry<RequestMappingInfo, HandlerMethod> handlerEntry : handlerMethods.entrySet()) {
                RequestMappingInfo requestMappingInfo = handlerEntry.getKey();
                HandlerMethod handlerMethod = handlerEntry.getValue();

                System.out.println("HTTP Methods: " + requestMappingInfo.getMethodsCondition().getMethods());
                System.out.println("Handler Method: " + handlerMethod);
                System.out.println("Pattern Values: " + requestMappingInfo.getPatternValues());
                System.out.println("-----------------------------------------------------");
            }
        }

        return Message.success("操作成功！");
    }
}
