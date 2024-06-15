package io.quarkcloud.quarkadmin.component.menu;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.quarkcloud.quarkadmin.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ItemGroup extends Component {
    private String title;
    private Object items;

    // 初始化
    public ItemGroup() {
        this.component = "menuItemGroup";
        this.setComponentKey();
    }
}
