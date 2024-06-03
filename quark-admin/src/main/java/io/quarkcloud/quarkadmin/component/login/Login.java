package io.quarkcloud.quarkadmin.component.login;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.quarkcloud.quarkadmin.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Login extends Component {

    public String api;

    public String redirect;

    public String logo;

    public String title;

    public String subTitle;

    public String backgroundImageUrl;

    public Map<String, Object> values;

    public Map<String, Object> initialValues;

    public Object body;

    public List<Object> actions;

    public Login() {
        this.component = "login";
        this.setComponentKey();
    }
}
