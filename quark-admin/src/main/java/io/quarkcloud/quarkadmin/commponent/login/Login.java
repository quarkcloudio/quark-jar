package io.quarkcloud.quarkadmin.commponent.login;

import java.util.Map;

import io.quarkcloud.quarkadmin.commponent.Commponent;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Login extends Commponent {

    public String api;

    public String redirect;

    public String logo;

    public String title;

    public String subTitle;

    public String backgroundImageUrl;

    public Map<String,?> values;

    public Map<String,?> initialValues;

    public Object body;

    public Object[] actions;

    public Login() {
        this.component = "login";
        this.setComponentKey();
    }
}