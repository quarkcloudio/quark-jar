package io.quarkcloud.quarkadmin.commponent.form;

import io.quarkcloud.quarkadmin.commponent.form.fields.Cascader;
import io.quarkcloud.quarkadmin.commponent.form.fields.Checkbox;
import io.quarkcloud.quarkadmin.commponent.form.fields.Date;
import io.quarkcloud.quarkadmin.commponent.form.fields.Daterange;
import io.quarkcloud.quarkadmin.commponent.form.fields.Datetime;
import io.quarkcloud.quarkadmin.commponent.form.fields.Datetimerange;
import io.quarkcloud.quarkadmin.commponent.form.fields.Dependency;
import io.quarkcloud.quarkadmin.commponent.form.fields.Display;
import io.quarkcloud.quarkadmin.commponent.form.fields.Editor;
import io.quarkcloud.quarkadmin.commponent.form.fields.Fieldset;
import io.quarkcloud.quarkadmin.commponent.form.fields.File;
import io.quarkcloud.quarkadmin.commponent.form.fields.Geofence;
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

    public static Checkbox checkbox(String name) {
        return new Checkbox().setName(name);
    }

    public static Checkbox checkbox(String name, String label) {
        return new Checkbox().setName(name).setLabel(label);
    }

    public static Date date(String name) {
        return new Date().setName(name);
    }

    public static Date date(String name, String label) {
        return new Date().setName(name).setLabel(label);
    }

    public static Daterange daterange(String name) {
        return new Daterange().setName(name);
    }

    public static Daterange daterange(String name, String label) {
        return new Daterange().setName(name).setLabel(label);
    }

    public static Datetime datetime(String name) {
        return new Datetime().setName(name);
    }

    public static Datetime datetime(String name, String label) {
        return new Datetime().setName(name).setLabel(label);
    }

    public static Datetimerange datetimerange(String name) {
        return new Datetimerange().setName(name);
    }

    public static Datetimerange datetimerange(String name, String label) {
        return new Datetimerange().setName(name).setLabel(label);
    }

    public static Dependency dependency(String name) {
        return new Dependency().setName(name);
    }

    public static Dependency dependency(String name, String label) {
        return new Dependency().setName(name).setLabel(label);
    }

    public static Display display(String name) {
        return new Display().setName(name);
    }

    public static Display display(String name, String label) {
        return new Display().setName(name).setLabel(label);
    }

    public static Editor editor(String name) {
        return new Editor().setName(name);
    }

    public static Editor editor(String name, String label) {
        return new Editor().setName(name).setLabel(label);
    }

    public static Fieldset fieldset(String name) {
        return new Fieldset().setName(name);
    }

    public static Fieldset fieldset(String name, String label) {
        return new Fieldset().setName(name).setLabel(label);
    }

    public static File file(String name) {
        return new File().setName(name);
    }

    public static File file(String name, String label) {
        return new File().setName(name).setLabel(label);
    }

    public static Geofence geofence(String name) {
        return new Geofence().setName(name);
    }

    public static Geofence geofence(String name, String label) {
        return new Geofence().setName(name).setLabel(label);
    }
}
