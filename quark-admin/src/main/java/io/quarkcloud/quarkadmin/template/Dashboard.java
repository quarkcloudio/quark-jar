package io.quarkcloud.quarkadmin.template;

import com.fasterxml.jackson.databind.node.ArrayNode;

import io.quarkcloud.quarkadmin.annotation.AdminDashboard;
import io.quarkcloud.quarkadmin.component.footer.Footer;
import io.quarkcloud.quarkcore.service.Context;

public class Dashboard {

    // 注解实例
    protected AdminDashboard annotationClass = null;

    // layout 的左上角 的 title
    public String title;

    // 构造函数
    public Dashboard() {

        // layout 的左上角 的 title
        title = "QuarkJar";
    }

    // 组件渲染
    public Object render(Context context) {

        return "admin/dashboard/index";
    }
}
