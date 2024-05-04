package io.quarkcloud.quarkadmin.commponent.form.fields;

import io.quarkcloud.quarkadmin.commponent.Commponent;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Text extends Commponent {

    public String redirect;

    public Text() {
        this.component = "textField";
        this.setComponentKey();
    }
}
