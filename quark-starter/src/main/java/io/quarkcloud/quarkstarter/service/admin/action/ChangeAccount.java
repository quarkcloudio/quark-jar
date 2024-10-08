package io.quarkcloud.quarkstarter.service.admin.action;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.hutool.jwt.JWT;
import io.quarkcloud.quarkadmin.component.message.Message;
import io.quarkcloud.quarkadmin.entity.UserEntity;
import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.service.ResourceService;
import io.quarkcloud.quarkadmin.template.resource.impl.action.AjaxImpl;
import io.quarkcloud.quarkcore.service.Context;

public class ChangeAccount<M, T> extends AjaxImpl<ResourceMapper<T>, T> {

    // 执行行为句柄
    @SuppressWarnings("unchecked")
    public Object handle(Context context, UpdateWrapper<T> updateWrapper, ResourceService<ResourceMapper<T>, T> resourceService) {
        JWT jwt = context.parseToken();
        UserEntity adminEntity = context.getRequestBody(UserEntity.class);
        Long adminId = Long.parseLong(jwt.getPayload("id").toString());
        if (adminEntity.getAvatar() != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                adminEntity.setAvatar(objectMapper.writeValueAsString(adminEntity.getAvatar()));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        if (adminEntity.getPassword() !=null && !adminEntity.getPassword().isEmpty()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            adminEntity.setPassword(encoder.encode(adminEntity.getPassword()));
        }

        // 获取登录管理员信息
        T adminInfo = resourceService.getById(adminId);
        if (adminInfo == null) {
            return Message.error("管理员信息获取失败");
        }

        adminEntity.setId(adminId);

        // 更新管理员信息
        boolean result = resourceService.updateById((T) adminEntity);
        if (!result) {
            return Message.error("操作失败！");
        }
        
        return Message.success("操作成功！");
    }
}
