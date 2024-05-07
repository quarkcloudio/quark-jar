package io.quarkcloud.quarkcore.util;

import java.util.HashMap;

public class Message {

    // 成功
    public static HashMap<String, Object> success(String message) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("code", 200);
        map.put("data", null);
        map.put("msg", message);
        return map;
    }

    // 成功
    public static HashMap<String, Object> success(String message, Object data) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("code", 200);
        map.put("data", data);
        map.put("msg", message);
        return map;
    }

    // 失败
    public static HashMap<String, Object> error(String message) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("code", 10001);
        map.put("message", message);
        return map;
    }
}
