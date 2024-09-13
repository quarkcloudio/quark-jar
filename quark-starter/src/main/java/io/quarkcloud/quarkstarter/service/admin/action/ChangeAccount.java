package io.quarkcloud.quarkstarter.service.admin.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.hutool.jwt.JWT;
import io.quarkcloud.quarkadmin.component.message.Message;
import io.quarkcloud.quarkadmin.entity.AdminEntity;
import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.service.AdminService;
import io.quarkcloud.quarkadmin.service.ResourceService;
import io.quarkcloud.quarkadmin.template.resource.impl.action.AjaxImpl;
import io.quarkcloud.quarkcore.service.Context;

public class ChangeAccount<M, T> extends AjaxImpl<ResourceMapper<T>, T> {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AdminService adminService;

    // 执行行为句柄
    public Object handle(Context context, UpdateWrapper<T> updateWrapper, ResourceService<ResourceMapper<T>, T> resourceService) {
        JWT jwt = context.parseToken();
        AdminEntity adminEntity = context.getRequestBody(AdminEntity.class);
        Long adminId = Long.parseLong(jwt.getPayload("id").toString());
        if (adminEntity.getAvatar() != null) {
            try {
                adminEntity.setAvatar(objectMapper.writeValueAsString(adminEntity.getAvatar()));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        if (!adminEntity.getPassword().isEmpty()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            adminEntity.setPassword(encoder.encode(adminEntity.getPassword()));
        }

        // 获取登录管理员信息
        AdminEntity adminInfo = adminService.getById(adminId);
        if (adminInfo == null) {
            return Message.error("管理员信息获取失败");
        }

        adminEntity.setId(adminId);

        // 更新管理员信息
        boolean result = adminService.updateById(adminEntity);
        if (!result) {
            return Message.error("操作失败！");
        }
        
        return Message.success("操作成功！");
    }
}
