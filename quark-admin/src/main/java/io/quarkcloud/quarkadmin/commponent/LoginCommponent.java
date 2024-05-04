package io.quarkcloud.quarkadmin.commponent;

import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginCommponent extends ElementCommponent {

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

    public LoginCommponent() {
        this.component = "login";
        this.setComponentKey();
    }
}
