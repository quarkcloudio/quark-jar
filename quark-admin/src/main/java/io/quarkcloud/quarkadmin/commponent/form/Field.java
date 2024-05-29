package io.quarkcloud.quarkadmin.commponent.form;

import io.quarkcloud.quarkadmin.commponent.form.fields.Cascader;
import io.quarkcloud.quarkadmin.commponent.form.fields.Checkbox;
import io.quarkcloud.quarkadmin.commponent.form.fields.Date;
import io.quarkcloud.quarkadmin.commponent.form.fields.DateRange;
import io.quarkcloud.quarkadmin.commponent.form.fields.Datetime;
import io.quarkcloud.quarkadmin.commponent.form.fields.DatetimeRange;
import io.quarkcloud.quarkadmin.commponent.form.fields.Dependency;
import io.quarkcloud.quarkadmin.commponent.form.fields.Display;
import io.quarkcloud.quarkadmin.commponent.form.fields.Editor;
import io.quarkcloud.quarkadmin.commponent.form.fields.Fieldset;
import io.quarkcloud.quarkadmin.commponent.form.fields.File;
import io.quarkcloud.quarkadmin.commponent.form.fields.Geofence;
import io.quarkcloud.quarkadmin.commponent.form.fields.Group;
import io.quarkcloud.quarkadmin.commponent.form.fields.Hidden;
import io.quarkcloud.quarkadmin.commponent.form.fields.Id;
import io.quarkcloud.quarkadmin.commponent.form.fields.Image;
import io.quarkcloud.quarkadmin.commponent.form.fields.ImageCaptcha;
import io.quarkcloud.quarkadmin.commponent.form.fields.List;
import io.quarkcloud.quarkadmin.commponent.form.fields.Mapfield;
import io.quarkcloud.quarkadmin.commponent.form.fields.Month;
import io.quarkcloud.quarkadmin.commponent.form.fields.Password;
import io.quarkcloud.quarkadmin.commponent.form.fields.Quarter;
import io.quarkcloud.quarkadmin.commponent.form.fields.Radio;
import io.quarkcloud.quarkadmin.commponent.form.fields.Text;
import io.quarkcloud.quarkadmin.commponent.form.fields.Number;

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

    public static DateRange dateRange(String name) {
        return new DateRange().setName(name);
    }

    public static DateRange dateRange(String name, String label) {
        return new DateRange().setName(name).setLabel(label);
    }

    public static Datetime datetime(String name) {
        return new Datetime().setName(name);
    }

    public static Datetime datetime(String name, String label) {
        return new Datetime().setName(name).setLabel(label);
    }

    public static DatetimeRange datetimeRange(String name) {
        return new DatetimeRange().setName(name);
    }

    public static DatetimeRange datetimeRange(String name, String label) {
        return new DatetimeRange().setName(name).setLabel(label);
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

    public static Group group(String name) {
        return new Group().setName(name);
    }

    public static Group group(String name, String label) {
        return new Group().setName(name).setLabel(label);
    }

    public static Image image(String name) {
        return new Image().setName(name);
    }

    public static Image image(String name, String label) {
        return new Image().setName(name).setLabel(label);
    }

    public static List list(String name) {
        return new List().setName(name);
    }

    public static List list(String name, String label) {
        return new List().setName(name).setLabel(label);
    }

    public static Mapfield mapfield(String name) {
        return new Mapfield().setName(name);
    }

    public static Mapfield mapfield(String name, String label) {
        return new Mapfield().setName(name).setLabel(label);
    }

    public static Month month(String name) {
        return new Month().setName(name);
    }

    public static Month month(String name, String label) {
        return new Month().setName(name).setLabel(label);
    }

    public static Number number(String name) {
        return new Number().setName(name);
    }

    public static Number number(String name, String label) {
        return new Number().setName(name).setLabel(label);
    }

    public static Quarter quarter(String name) {
        return new Quarter().setName(name);
    }

    public static Quarter quarter(String name, String label) {
        return new Quarter().setName(name).setLabel(label);
    }

    public static Radio radio(String name) {
        return new Radio().setName(name);
    }

    public static Radio radio(String name, String label) {
        return new Radio().setName(name).setLabel(label);
    }
}
