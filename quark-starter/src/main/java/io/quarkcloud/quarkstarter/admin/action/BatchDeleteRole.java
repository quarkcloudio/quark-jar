package io.quarkcloud.quarkstarter.admin.action;

import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import io.quarkcloud.quarkadmin.component.message.Message;
import io.quarkcloud.quarkadmin.entity.RoleMenuEntity;
import io.quarkcloud.quarkadmin.entity.RolePermissionEntity;
import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.service.ResourceService;
import io.quarkcloud.quarkadmin.template.resource.impl.action.BatchImpl;
import io.quarkcloud.quarkcore.service.Context;

public class BatchDeleteRole<M, T> extends BatchImpl<ResourceMapper<T>, T> {

    // 构造函数
    public BatchDeleteRole() {

        // 设置按钮名称
        this.name = "批量删除";

        //  执行成功后刷新的组件
        this.reload = "table";

        // 当行为在表格行展示时，支持js表达式
        this.withConfirm("确定要删除吗？", "删除后数据将无法恢复，请谨慎操作！", "modal");

        // 行为接口接收的参数，当行为在表格行展示的时候，可以配置当前行的任意字段
        this.setApiParams(Arrays.asList("id"));

        // 只在表格内展示
        this.setOnlyOnIndex(true);
    }

    // 执行行为句柄
    public Object handle(Context context, UpdateWrapper<T> updateWrapper, ResourceService<ResourceMapper<T>, T> resourceService) {
        String id = context.getParameter("id");
        if (id.isEmpty()) {
            return Message.error("参数错误！");
        }
        
        boolean result = resourceService.remove(updateWrapper);
        if (!result) {
            return Message.error("操作失败！");
        }

        if (id.contains(",")) {
            String[] ids = id.split(",");
            RoleMenuEntity roleHasMenuEntity = new RoleMenuEntity();
            QueryWrapper<RoleMenuEntity> queryWrapper1 = new QueryWrapper<RoleMenuEntity>().in("role_id", Arrays.asList(ids));
            boolean result1 = roleHasMenuEntity.delete(queryWrapper1);
            if (!result1) {
                return Message.error("操作失败！");
            }
    
            RolePermissionEntity roleHasPermissionEntity = new RolePermissionEntity();
            QueryWrapper<RolePermissionEntity> queryWrapper2 = new QueryWrapper<RolePermissionEntity>().eq("role_id", Arrays.asList(ids));
            boolean result2 = roleHasPermissionEntity.delete(queryWrapper2);
            if (!result2) {
                return Message.error("操作失败！");
            }
        } else {
            RoleMenuEntity roleHasMenuEntity = new RoleMenuEntity();
            QueryWrapper<RoleMenuEntity> queryWrapper1 = new QueryWrapper<RoleMenuEntity>().eq("role_id", id);
            boolean result1 = roleHasMenuEntity.delete(queryWrapper1);
            if (!result1) {
                return Message.error("操作失败！");
            }
    
            RolePermissionEntity roleHasPermissionEntity = new RolePermissionEntity();
            QueryWrapper<RolePermissionEntity> queryWrapper2 = new QueryWrapper<RolePermissionEntity>().eq("role_id", id);
            boolean result2 = roleHasPermissionEntity.delete(queryWrapper2);
            if (!result2) {
                return Message.error("操作失败！");
            }
        }

        return Message.success("操作成功！");
    }
}
