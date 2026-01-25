package io.quarkcloud.quarkstarter.admin.action;

import java.util.List;
import java.util.Map;

import io.quarkcloud.quarkadmin.component.action.Action;
import io.quarkcloud.quarkadmin.component.form.Form;
import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkadmin.template.resource.impl.action.DrawerImpl;
import io.quarkcloud.quarkcore.service.Context;

public class EditDrawer<M extends ResourceMapper<T>, T> extends DrawerImpl<ResourceMapper<T>, T> {

    // API
    public String api;

    // initApi
    public String initApi;

    // 字段数据
    public Object fields;

    // 资源
    private final ResourceImpl<M, T> resource;

    // 初始化
    public EditDrawer(Context context, ResourceImpl<M, T> resource) {
        this.resource = resource;
        this.setTitle("编辑");
        this.setApi(this.resource.editApi(context));
        this.setInitApi(this.resource.editValueApi(context));
        this.setFields(this.resource.editFields(context));
        this.setType("link");
        this.setSize("small");
        this.setDestroyOnClose(true);
        this.setReload("table");
        this.setOnlyOnIndexTableRow(true);
    }

    // 标题
    public EditDrawer<M, T> setTitle(String title) {
        this.name = title;
        return this;
    }

    // API
    public EditDrawer<M, T> setApi(String api) {
        this.api = api;
        return this;
    }

    // InitApi
    public EditDrawer<M, T> setInitApi(String initApi) {
        this.initApi = initApi;
        return this;
    }

    // 字段
    public EditDrawer<M, T> setFields(Object fields) {
        this.fields = fields;
        return this;
    }

    // 内容
    public Object getBody(Context context) {
        Form form = new Form();
        form.setComponentKey("editDrawerForm", false);
        return form.setApi(this.api)
            .setInitApi(this.initApi)
            .setBody(this.fields)
            .setLabelCol(Map.of("span", 6))
            .setWrapperCol(Map.of("span", 18));
    }

    // 弹窗行为
    public List<Object> getActions(Context context) {
        return List.of(
            new Action()
                .setLabel("取消")
                .setActionType("cancel"),

            new Action()
                .setLabel("提交")
                .setWithLoading(true)
                .setReload("table")
                .setActionType("submit")
                .setType("primary", false)
                .setSubmitForm("editDrawerForm")
        );
    }
}
