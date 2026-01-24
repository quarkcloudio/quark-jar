package io.quarkcloud.quarkstarter.admin.action;

import java.util.List;

import io.quarkcloud.quarkadmin.component.action.Action;
import io.quarkcloud.quarkadmin.component.form.Form;
import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.action.DrawerImpl;
import io.quarkcloud.quarkcore.service.Context;

public class MenuCreateDrawer<M, T> extends DrawerImpl<ResourceMapper<T>, T> {

    // API
    public String api;

    // 字段数据
    public Object fields;

    // 数据
    public Object data;

    // 初始化
    public MenuCreateDrawer() {
        this.setType("primary");
        this.setGhost(true);
        this.setIcon("ant-design:plus-outlined");
        this.setDestroyOnClose(true);
        this.setReload("table");
        this.setOnlyOnIndex(true);
        this.setWidth(750);
    }

    // 标题
    public MenuCreateDrawer<M, T> setTitle(String title) {
        this.name = title;
        return this;
    }

    // API
    public MenuCreateDrawer<M, T> setApi(String api) {
        this.api = api;
        return this;
    }

    // 字段
    public MenuCreateDrawer<M, T> setFields(Object fields) {
        this.fields = fields;
        return this;
    }

    // 数据
    public MenuCreateDrawer<M, T> setData(Object data) {
        this.data = data;
        return this;
    }

    // 内容
    public Object getBody(Context context) {
        Form form = new Form();
        form.setComponentKey("createDrawerForm", false);
        return form
            .setLayout("vertical")
            .setApi(this.api)
            .setBody(this.fields)
            .setInitialValues(this.data);
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
                .setSubmitForm("createDrawerForm")
        );
    }
}
