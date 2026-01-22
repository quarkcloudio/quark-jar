package io.quarkcloud.quarkcore.common;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Message {
    private int code;
    private String msg;
    private Object data;
    private String url;

    // 构造函数
    public Message() {}

    public Message(int code, String msg, Object data, String url) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.url = url;
    }

    // Getters and Setters
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    
    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    // 状态码常量
    public static final int STATUS_OK = 200;
    public static final int STATUS_UNAUTHORIZED = 401;
    public static final int STATUS_FORBIDDEN = 403;
    public static final int STATUS_ERROR = 10001;
    public static final int STATUS_PARAM_ERROR = 10002;

    // 错误码映射
    private static final Map<Integer, String> CODE_MESSAGES = new HashMap<Integer, String>() {{
        put(STATUS_OK, "ok");
        put(STATUS_ERROR, "Internal Server Error");
        put(STATUS_PARAM_ERROR, "Param Error");
        put(STATUS_UNAUTHORIZED, "Unauthorized");
        put(STATUS_FORBIDDEN, "Forbidden");
    }};

    // 根据code获取错误信息
    public static String getMsgByCode(int code) {
        return CODE_MESSAGES.getOrDefault(code, "");
    }

    // 返回成功信息
    public static Message success(String message, Object data) {
        return new Message(STATUS_OK, message, data, null);
    }

    public static Message success(String message) {
        return success(message, null);
    }

    public static Message success(Object data) {
        return success("ok", data);
    }

    public static Message success() {
        return success("ok", null);
    }

    // 返回错误信息
    public static Message error(Object... params) {
        String msg = params.length > 0 ? params[0].toString() : "";
        Object data = params.length > 1 ? params[1] : null;
        return new Message(STATUS_ERROR, msg, data, null);
    }

    // 根据错误码返回错误信息
    public static Message errorByCode(Object... params) {
        int code = params.length > 0 ? (Integer) params[0] : STATUS_ERROR;
        Object data = params.length > 1 ? params[1] : null;
        String msg = getMsgByCode(code);
        return new Message(code, msg, data, null);
    }

    // 返回重定向信息
    public static Message redirectTo(Object... params) {
        String msg = "";
        String url = "";
        int code = 200;

        if (params.length >= 1) {
            if (params.length == 1) {
                url = params[0].toString();
            } else if (params.length == 2) {
                msg = params[0].toString();
                url = params[1].toString();
            } else {
                msg = params[0].toString();
                url = params[1].toString();
                code = (Integer) params[2];
            }
        }

        return new Message(code, msg, null, url);
    }
}