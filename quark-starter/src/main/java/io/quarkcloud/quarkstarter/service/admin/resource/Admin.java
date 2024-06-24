package io.quarkcloud.quarkstarter.service.admin.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;

@Component(value = "adminResource")
public class Admin extends ResourceImpl {

    public Admin() {
        super();
        this.title = "管理员";
        this.tableTitleSuffix = "列表";
        this.tableActionColumnTitle = "操作";
        this.tableActionColumnWidth = 200;
        this.tablePolling = 3000;
        this.withExport = true;
        this.backIcon = true;
        this.queryOrder= "id desc";
    }

    // 字段
    public List<Object> fields(Context ctx) {
        return Arrays.asList(Field.text("name", "名称"));
    }
}
