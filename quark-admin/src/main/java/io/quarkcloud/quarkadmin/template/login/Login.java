package io.quarkcloud.quarkadmin.template.login;

import io.quarkcloud.quarkcore.service.Context;

public interface Login {

    // 获取接口
    public String getApi();

    // 登录成功后跳转地址
    public String getRedirect();

    // 获取标题
    public String getTitle();

    // 获取子标题
    public String getSubTitle();

    // 获取字段
    public Object[] fields(Context context);

    // 获取验证码ID
    public Object captchaId(Context context);

    // 获取验证码
    public void captcha(Context context);

    // 包裹在组件内的创建页字段
    public Object fieldsWithinComponents(Context context);

    // 解析创建页表单组件内的字段
    public Object formFieldsParser(Context context, Object fields);

    // 执行登录
    public Object handle(Context context);
    
    // 组件渲染
    public Object render(Context context);
}
