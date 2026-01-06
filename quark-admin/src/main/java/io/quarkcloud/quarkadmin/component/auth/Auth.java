package io.quarkcloud.quarkadmin.component.auth;

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
public class Auth extends Component {

    public String loginApi;

    public String userInfoApi;

    public String userRoutesApi;

    public String redirect;

    public Object logo;

    public String title;

    public String backgroundImageUrl;

    public Map<String, Object> values;

    public Map<String, Object> initialValues;

    public Object body;

    public List<Object> actions;

    public Auth() {
        this.component = "auth";
        this.setComponentKey();
    }
}
