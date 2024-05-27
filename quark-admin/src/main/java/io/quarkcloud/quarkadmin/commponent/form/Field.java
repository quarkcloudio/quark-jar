package io.quarkcloud.quarkadmin.commponent.form;

import io.quarkcloud.quarkadmin.commponent.form.fields.Cascader;
import io.quarkcloud.quarkadmin.commponent.form.fields.Hidden;
import io.quarkcloud.quarkadmin.commponent.form.fields.Id;
import io.quarkcloud.quarkadmin.commponent.form.fields.ImageCaptcha;
import io.quarkcloud.quarkadmin.commponent.form.fields.Password;
import io.quarkcloud.quarkadmin.commponent.form.fields.Text;

public class Field {

    public static Text text(String name) {
        return new Text().setName(name);
    }

    public static Text text(String name, String label) {
        return new Text().setName(name).setLabel(label);
    }

    public static Password password(String name) {
        return new Password().setName(name);
    }

    public static Password password(String name, String label) {
        return new Password().setName(name).setLabel(label);
    }

    public static ImageCaptcha imageCaptcha(String name) {
        return new ImageCaptcha().setName(name);
    }

    public static ImageCaptcha imageCaptcha(String name, String label) {
        return new ImageCaptcha().setName(name).setLabel(label);
    }

    public static Cascader cascader(String name) {
        return new Cascader().setName(name);
    }

    public static Cascader cascader(String name, String label) {
        return new Cascader().setName(name).setLabel(label);
    }

    public static Id id(String name) {
        return new Id().setName(name);
    }

    public static Id id(String name, String label) {
        return new Id().setName(name).setLabel(label);
    }

    public static Hidden hidden(String name) {
        return new Hidden().setName(name);
    }

    public static Hidden hidden(String name, String label) {
        return new Hidden().setName(name).setLabel(label);
    }
}
