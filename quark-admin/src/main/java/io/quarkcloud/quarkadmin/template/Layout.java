package io.quarkcloud.quarkadmin.template;

import io.quarkcloud.quarkcore.service.Context;

public class Layout {

    // 登录接口
    public String title;

    // 登录成功后跳转地址
    public String redirect;

    // 登录页面Logo
    public String logo;

    // 副标题
    public String subTitle;

    // 构造函数
    public Layout() {

        // 登录成功后跳转地址
        redirect = "/layout/index?api=/api/admin/dashboard/index/index";

        // 标题
        title = "QuarkJar";

        // 副标题
        subTitle = "信息丰富的世界里，唯一稀缺的就是人类的注意力";
    }

    // 组件渲染
    public Object render(Context context) {

        return null;
    }
}
