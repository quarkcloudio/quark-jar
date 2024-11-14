package io.quarkcloud.quarkstarter.admin.action;

import java.util.List;

import io.quarkcloud.quarkadmin.component.action.Action;
import io.quarkcloud.quarkadmin.component.form.Form;
import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.action.DrawerImpl;
import io.quarkcloud.quarkcore.service.Context;

public class MenuEditDrawer<M, T> extends DrawerImpl<ResourceMapper<T>, T> {

    // API
    public String api;

    // initApi
    public String initApi;

    // 字段数据
    public Object fields;

    // 初始化
    public MenuEditDrawer() {
        this.setType("link");
        this.setSize("small");
        this.setDestroyOnClose(true);
        this.setReload("table");
        this.setOnlyOnIndexTableRow(true);
        this.setWidth(750);
    }

    // 标题
    public MenuEditDrawer<M, T> setTitle(String title) {
        this.name = title;
        return this;
    }

    // API
    public MenuEditDrawer<M, T> setApi(String api) {
        this.api = api;
        return this;
    }

    // InitApi
    public MenuEditDrawer<M, T> setInitApi(String initApi) {
        this.initApi = initApi;
        return this;
    }

    // 字段
    public MenuEditDrawer<M, T> setFields(Object fields) {
        this.fields = fields;
        return this;
    }

    // 内容
    public Object getBody(Context context) {
        Form form = new Form();
        form.setComponentKey("editDrawerForm", false);
        return form
            .setLayout("vertical")
            .setApi(this.api)
            .setInitApi(this.initApi)
            .setBody(this.fields);
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
