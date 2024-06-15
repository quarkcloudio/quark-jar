package io.quarkcloud.quarkadmin.component.menu;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.quarkcloud.quarkadmin.component.Component;
import io.quarkcloud.quarkadmin.component.modal.Modal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Item extends Component {
    private String title;
    private String label;
    private boolean block;
    private boolean danger;
    private boolean disabled;
    private boolean ghost;
    private String icon;
    private String shape;
    private String size;
    private String type;
    private String actionType;
    private Object submitForm;
    private String href;
    private String target;
    private Object modal;
    private Object drawer;
    private String confirmTitle;
    private String confirmText;
    private String confirmType;
    private String api;
    private String reload;
    private boolean withLoading;

    // Initialization
    public Item() {
        this.component = "menuItem";
        this.size = "default";
        this.type = "default";
        this.setComponentKey();
    }

    // Set style
    public Item setStyle(Map<String, Object> style) {
        this.style = style;
        return this;
    }

    // Set title
    public Item setTitle(String title) {
        this.title = title;
        return this;
    }

    // Set label
    public Item setLabel(String label) {
        this.label = label;
        return this;
    }

    // Set block
    public Item setBlock(boolean block) {
        this.block = block;
        return this;
    }

    // Set danger
    public Item setDanger(boolean danger) {
        this.danger = danger;
        return this;
    }

    // Set disabled
    public Item setDisabled(boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    // Set ghost
    public Item setGhost(boolean ghost) {
        this.ghost = ghost;
        return this;
    }

    // Set icon
    public Item setIcon(String icon) {
        this.icon = "icon-" + icon;
        return this;
    }

    // Set shape
    public Item setShape(String shape) {
        this.shape = shape;
        return this;
    }

    // Set type
    public Item setType(String type, boolean danger) {
        this.type = type;
        this.danger = danger;
        return this;
    }

    // Set size
    public Item setSize(String size) {
        this.size = size;
        return this;
    }

    // Set action type
    public Item setActionType(String actionType) {
        this.actionType = actionType;
        return this;
    }

    // Set submit form
    public Item setSubmitForm(Object submitForm) {
        this.submitForm = submitForm;
        return this;
    }

    // Set href
    public Item setHref(String href) {
        this.href = href;
        return this;
    }

    // Set target
    public Item setTarget(String target) {
        this.target = target;
        return this;
    }

    // Set link
    public Item setLink(String href, String target) {
        this.setHref(href);
        this.setTarget(target);
        this.actionType = "link";
        return this;
    }

    // 回调函数
    @FunctionalInterface
    interface Closure {
        Object callback();
    }

    // Set modal
    public Item setModal(Closure callback) {
        Modal modal = new Modal();
        this.modal = ((Modal) callback).apply(modal);
        return this;
    }

    // Set drawer
    public Item setDrawer(Object callback) {
        drawer.Component component = new drawer.Component().init();
        this.drawer = ((drawer.Component) callback).apply(component);
        return this;
    }

    // Set with confirm
    public Item setWithConfirm(String title, String text, String confirmType) {
        this.confirmTitle = title;
        this.confirmText = text;
        this.confirmType = confirmType;
        return this;
    }

    // Set API
    public Item setApi(String api) {
        this.api = api;
        this.actionType = "ajax";
        return this;
    }

    // Set reload
    public Item setReload(String reload) {
        this.reload = reload;
        return this;
    }

    // Set with loading
    public Item setWithLoading(boolean loading) {
        this.withLoading = loading;
        return this;
    }
}
