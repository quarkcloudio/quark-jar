package io.quarkcloud.quarkadmin.commponent.form;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.quarkcloud.quarkadmin.commponent.Commponent;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Form extends Commponent {

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

    public Form() {
        this.component = "form";
        this.setComponentKey();
    }
}
