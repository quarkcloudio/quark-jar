package io.quarkcloud.quarkadmin.component.footer;

import java.util.HashMap;
import java.util.Map;

import io.quarkcloud.quarkadmin.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Footer extends Component {

    // 版权信息
    public String copyright;

    // 版权信息
    public Map<String, Object>[] links;

    public Footer() {
        this.component = "footer";
        this.setComponentKey();
        this.style = new HashMap<>();
    }

}
