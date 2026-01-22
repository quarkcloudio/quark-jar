package io.quarkcloud.quarkstarter.admin.action;

import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import io.quarkcloud.quarkcore.common.Message;
import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.service.ResourceService;
import io.quarkcloud.quarkadmin.template.resource.impl.action.BatchImpl;
import io.quarkcloud.quarkcore.service.Context;

public class BatchDisable<M, T> extends BatchImpl<ResourceMapper<T>, T> {

    // 构造函数
    public BatchDisable() {

        // 设置按钮名称
        this.name = "批量禁用";

        //  执行成功后刷新的组件
        this.reload = "table";

        // 当行为在表格行展示时，支持js表达式
        this.withConfirm("确定要禁用吗？", "禁用后数据将无法使用，请谨慎操作！", "modal");

        // 行为接口接收的参数，当行为在表格行展示的时候，可以配置当前行的任意字段
        this.setApiParams(Arrays.asList("id"));
    }

    // 构造函数
    public BatchDisable(String name) {

        // 设置按钮名称
        this.name = name;

        //  执行成功后刷新的组件
        this.reload = "table";

        // 当行为在表格行展示时，支持js表达式
        this.withConfirm("确定要禁用吗？", "禁用后数据将无法使用，请谨慎操作！", "modal");
        
        // 行为接口接收的参数，当行为在表格行展示的时候，可以配置当前行的任意字段
        this.setApiParams(Arrays.asList("id"));
    }

    // 执行行为句柄
    public Object handle(Context context, UpdateWrapper<T> updateWrapper, ResourceService<ResourceMapper<T>, T> resourceService) {
        updateWrapper.set("status", 0);
        boolean result = resourceService.update(updateWrapper);
        if (!result) {
            return Message.error("操作失败！");
        }
        return Message.success("操作成功！");
    }
}
