package io.quarkcloud.quarkstarter.service.admin.action;

import java.util.Arrays;

import io.quarkcloud.quarkadmin.template.resource.impl.ActionImpl;
import io.quarkcloud.quarkcore.service.Context;

public class Delete extends ActionImpl {

    // 构造函数
    public Delete() {

        // 设置按钮名称
        this.name = "删除";

        // 设置按钮类型,primary | ghost | dashed | link | text | default
        this.type = "link";

        // 设置按钮大小,large | middle | small | default
        this.size = "small";

        //  执行成功后刷新的组件
        this.reload = "table";

        // 当行为在表格行展示时，支持js表达式
        this.withConfirm("确定要删除吗？", "删除后数据将无法恢复，请谨慎操作！", "modal");

        // 在表格行内展示
        this.setOnlyOnIndexTableRow(true)
;
        // 行为接口接收的参数，当行为在表格行展示的时候，可以配置当前行的任意字段
        this.setApiParams(Arrays.asList("id"));
    }

    // 构造函数
    public Delete(String name) {

        // 设置按钮名称
        this.name = name;

        // 设置按钮类型,primary | ghost | dashed | link | text | default
        this.type = "link";

        // 设置按钮大小,large | middle | small | default
        this.size = "small";

        //  执行成功后刷新的组件
        this.reload = "table";

        // 当行为在表格行展示时，支持js表达式
        this.withConfirm("确定要删除吗？", "删除后数据将无法恢复，请谨慎操作！", "modal");

        // 在表格行内展示
        this.setOnlyOnIndexTableRow(true)
;
        // 行为接口接收的参数，当行为在表格行展示的时候，可以配置当前行的任意字段
        this.setApiParams(Arrays.asList("id"));
    }

    // 执行行为句柄
    public Object handle(Context context, Object query) throws Exception {
        return "Method not implemented";
    }
}
