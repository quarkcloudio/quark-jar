package io.quarkcloud.quarkstarter.service.admin.action;

import java.util.List;
import java.util.Map;

import io.quarkcloud.quarkadmin.component.action.Action;
import io.quarkcloud.quarkadmin.component.form.Form;
import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.action.ModalImpl;
import io.quarkcloud.quarkcore.service.Context;

public class EditModal<M, T> extends ModalImpl<ResourceMapper<T>, T> {

    // API
    public String api;

    // initApi
    public String initApi;

    // 字段数据
    public Object fields;

    // 初始化
    public EditModal(String title, String api, String initApi, Object fields) {
        this.api = api;
        this.fields = fields;
        this.initApi = initApi;
        this.setName(title);
        this.setType("link");
        this.setSize("small");
        this.setDestroyOnClose(true);
        this.setReload("table");
        this.setOnlyOnIndexTableRow(true);
    }

    // 内容
    public Object getBody(Context context) {
        Form form = new Form();
        form.setComponentKey("editModalForm", false);
        return form.setStyle(Map.of("paddingTop", "24px"))
            .setApi(api)
            .setInitApi(initApi)
            .setBody(fields)
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
                .setSubmitForm("editModalForm")
        );
    }
}
