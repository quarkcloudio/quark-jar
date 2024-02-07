package io.quarkcloud.quarkengine.message;

import com.alibaba.fastjson.JSON;

public class Message<T> {

    // 状态码
    public int code;

    // 消息
    public String msg;

    // 数据
    public T data;

    // 返回错误信息，Error("内部服务调用异常")
    @SuppressWarnings("unchecked")
    public static String error(String msg) {
        @SuppressWarnings("rawtypes")
        Message message = new Message();

        message.code = 10001;
        message.msg = msg;

        return JSON.toJSONString(message);
    }

    // 返回正确信息
    @SuppressWarnings("unchecked")
    public static <T> String success(String msg) {
        @SuppressWarnings("rawtypes")
        Message message = new Message();

        message.code = 200;
        message.msg = msg;

        return JSON.toJSONString(message);
    }

    // 返回正确信息
    @SuppressWarnings("unchecked")
    public static <T> String success(String msg,T data) {
        @SuppressWarnings("rawtypes")
        Message message = new Message();

        message.code = 200;
        message.msg = msg;
        message.data = data;

        return JSON.toJSONString(message);
    }
}
