package io.quarkcloud.quarkadmin.component.message;

import java.util.Map;

import io.quarkcloud.quarkadmin.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Message extends Component {

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
    public static Object success(Object message) {
        return new Message().
        setType("success").
        setContent(message);
    }

    // 返回成功
    public static Object success(Object message,String url) {
        return new Message().
        setType("success").
        setContent(message).
        setUrl(url);
    }

    // 返回成功
    public static Object success(Object message,String url,Object data) {
        return new Message().
        setType("success").
        setContent(message).
        setUrl(url).
        setData(data);
    }

    // 返回失败
    public static Object error(Object message) {
        return new Message().
        setType("error").
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
