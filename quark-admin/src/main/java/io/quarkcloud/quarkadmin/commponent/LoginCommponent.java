package io.quarkcloud.quarkadmin.commponent;

import java.util.Map;
import lombok.Data;

@Data
public class LoginCommponent<T> extends Element {

    public String api;

    public String redirect;

    public String logo;

    public String title;

    public String subTitle;

    public String backgroundImageUrl;

    public Map<String,?> values;

    public Map<String,?> initialValues;

    public T body;

    public T[] actions;

    public LoginCommponent() {
        this.component = "login";
        this.setComponentKey();
    }
}
