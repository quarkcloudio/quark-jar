package io.quarkcloud.quarkbase.controller;

import io.quarkcloud.quarkbase.message.Message;

public class BaseController<T> {

    // 错误
    public String jsonError(String msg)
    {
        return Message.error(msg);
    }

    // 成功
    public String jsonOk(String msg)
    {
        return Message.success(msg);
    }

    // 成功
    public String jsonOk(String msg,T data)
    {
        return Message.success(msg, data);
    }
}
