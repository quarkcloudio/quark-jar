package io.quarkcloud.quarkstarter.service.admin.action;

import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import io.quarkcloud.quarkadmin.component.message.Message;
import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.service.ResourceService;
import io.quarkcloud.quarkadmin.template.resource.impl.action.AjaxImpl;
import io.quarkcloud.quarkcore.service.Context;

public class ChangeStatus<M, T> extends AjaxImpl<ResourceMapper<T>, T> {

    // 构造函数
    public ChangeStatus() {

        // 行为名称，当行为在表格行展示时，支持js表达式
        this.name = "<%= (status==true ? '禁用' : '启用') %>";

        // 设置按钮类型,primary | ghost | dashed | link | text | default
        this.type = "link";

        // 设置按钮大小,large | middle | small | default
        this.size = "small";

        //  执行成功后刷新的组件
        this.reload = "table";

        // 当行为在表格行展示时，支持js表达式
        this.withConfirm("确定要<%= (status==true ? '禁用' : '启用') %>数据吗？", "", "pop");

        // 在表格行内展示
        this.setOnlyOnIndexTableRow(true)
;
        // 行为接口接收的参数，当行为在表格行展示的时候，可以配置当前行的任意字段
        this.setApiParams(Arrays.asList("id", "status"));
    }

    // 执行行为句柄
    public Object handle(Context context, UpdateWrapper<T> updateWrapper, ResourceService<ResourceMapper<T>, T> resourceService) {
        String status = context.getParameter("status");
        if (status==null || status.equals("")) {
            return Message.error("参数错误！");
        }

        // 设置状态
        if (status.equals("true")) {
            updateWrapper.set("status", 0);
        } else {
            updateWrapper.set("status", 1);
        }

        boolean result = resourceService.update(updateWrapper);
        if (!result) {
            return Message.error("操作失败！");
        }
        return Message.success("操作成功！");
    }
}
