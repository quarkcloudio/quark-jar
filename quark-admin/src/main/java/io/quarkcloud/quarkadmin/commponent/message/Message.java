package io.quarkcloud.quarkadmin.commponent.message;

import java.util.Map;
import io.quarkcloud.quarkadmin.commponent.Commponent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Message extends Commponent {

    public String className;

    public String type;

    public Object content;

    public int duration;

    public String icon;

    public String backgroundImageUrl;

    public Map<String,?> style;

    public Object data;

    public String url;

    public Message() {
        this.component = "message";
        this.setComponentKey();
    }

    // 返回成功
    public static Object success(String message) {
        return new Message().
        setType("success").
        setContent(message);
    }

    // 返回成功
    public static Object success(String message,String url,Object data) {
        return new Message().
        setType("success").
        setContent(message).
        setUrl(url).
        setData(data);
    }

    // 返回失败
    public static Object error(String message) {
        return new Message().
        setType("success").
        setContent(message);
    }

    // 返回失败
    public static Object error(String message,String url) {
        return new Message().
        setType("error").
        setContent(message).
        setUrl(url);
    }
}
