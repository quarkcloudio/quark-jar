package io.quarkcloud.quarkadmin.component.descriptions;

import io.quarkcloud.quarkadmin.component.Component;
import io.quarkcloud.quarkadmin.component.descriptions.fields.Text;

public class Field extends Component {

    public Field() {
        this.component = "descriptionField";
        this.setComponentKey();
    }

    // text组件
    public Text text(String param) {
        return new Text().setDataIndex(param).setLabel(param);
    }

    // text组件
    public Text text(String dataIndex, String label) {
        return new Text().setDataIndex(dataIndex).setLabel(label);
    }
}
