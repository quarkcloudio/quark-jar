package io.quarkcloud.quarkstarter.service.admin.action;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.quarkcloud.quarkadmin.component.action.Action;
import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.component.form.Form;
import io.quarkcloud.quarkadmin.component.space.Space;
import io.quarkcloud.quarkadmin.component.tpl.Tpl;
import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.action.ModalImpl;
import io.quarkcloud.quarkcore.service.Context;

public class Import<M, T> extends ModalImpl<ResourceMapper<T>, T> {
    
    public Import() {
        this.name = "导入数据";
        this.setDestroyOnClose(true);
        this.setOnlyOnIndex(true);
    }

    @Override
    public Object getBody(Context context) {
        String api = "/api/admin/" + context.getPathVariable("resource") + "/import";
        Tpl getTpl = new Tpl()
                .setBody("模板文件: <a href='/api/admin/" + context.getPathVariable("resource") + "/import/template?token=" + context.getToken() + "' target='_blank'>下载模板</a>")
                .setStyle(Map.of("marginLeft", "50px"));

        Object[] fields = {
            new Space()
                .setBody(getTpl)
                .setDirection("vertical")
                .setSize("middle")
                .setStyle(Map.of("marginBottom", "20px")),
            Field.file("fileId", "导入文件")
                .setLimitNum(1)
                .setLimitType(Arrays.asList(
                    "application/vnd.ms-excel",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                ))
                .setHelp("请上传xls格式的文件")
        };

        Form form = new Form();
        form.setComponentKey("importModalForm", false);

        return form
                .setApi(api)
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
                .setSubmitForm("importModalForm")
        );
    }
}