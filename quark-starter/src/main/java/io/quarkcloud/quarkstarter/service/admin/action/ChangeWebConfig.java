package io.quarkcloud.quarkstarter.service.admin.action;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkcloud.quarkadmin.component.message.Message;
import io.quarkcloud.quarkadmin.entity.ConfigEntity;
import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.service.ResourceService;
import io.quarkcloud.quarkadmin.template.resource.impl.action.AjaxImpl;
import io.quarkcloud.quarkcore.service.Context;

public class ChangeWebConfig<M, T> extends AjaxImpl<ResourceMapper<T>, T> {

    // 执行行为句柄
    public Object handle(Context context, UpdateWrapper<T> updateWrapper, ResourceService<ResourceMapper<T>, T> resourceService) {
        Map<?,?> data = context.getRequestBody(Map.class);
        if (data.isEmpty()) {
            return Message.error("参数错误！");
        }

        boolean result = true;
        for (Map.Entry<?, ?> entry : data.entrySet()) {
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            ConfigEntity configEntity = new ConfigEntity();
            UpdateWrapper<T> newUpdateWrapper = new UpdateWrapper<>();
            newUpdateWrapper.eq("name", key);
            if (value instanceof List<?>) {
                try {
                    value = new ObjectMapper().writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            } else if (value instanceof Map<?, ?>) {
                try {
                    value = new ObjectMapper().writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            configEntity.setValue(value);
            boolean updateResult = resourceService.update((T) configEntity, newUpdateWrapper);
            if (!updateResult) {
                result = false;
            }
        }
        if (!result) {
            return Message.error("操作失败，请重试！");
        }
        return Message.success("操作成功！");
    }
}
