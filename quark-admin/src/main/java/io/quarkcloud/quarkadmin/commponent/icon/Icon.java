package io.quarkcloud.quarkadmin.commponent.icon;

import java.util.Map;
import io.quarkcloud.quarkadmin.commponent.Commponent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Icon extends Commponent {

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

    public Icon() {
        this.component = "icon";
        this.setComponentKey();
    }
}
