package io.quarkcloud.quarkadmin.component.form;

import io.quarkcloud.quarkadmin.component.form.fields.Cascader;
import io.quarkcloud.quarkadmin.component.form.fields.Checkbox;
import io.quarkcloud.quarkadmin.component.form.fields.Compact;
import io.quarkcloud.quarkadmin.component.form.fields.Date;
import io.quarkcloud.quarkadmin.component.form.fields.DateRange;
import io.quarkcloud.quarkadmin.component.form.fields.Datetime;
import io.quarkcloud.quarkadmin.component.form.fields.DatetimeRange;
import io.quarkcloud.quarkadmin.component.form.fields.Dependency;
import io.quarkcloud.quarkadmin.component.form.fields.Display;
import io.quarkcloud.quarkadmin.component.form.fields.Editor;
import io.quarkcloud.quarkadmin.component.form.fields.Fieldset;
import io.quarkcloud.quarkadmin.component.form.fields.File;
import io.quarkcloud.quarkadmin.component.form.fields.Geofence;
import io.quarkcloud.quarkadmin.component.form.fields.Group;
import io.quarkcloud.quarkadmin.component.form.fields.Hidden;
import io.quarkcloud.quarkadmin.component.form.fields.Icon;
import io.quarkcloud.quarkadmin.component.form.fields.Id;
import io.quarkcloud.quarkadmin.component.form.fields.Image;
import io.quarkcloud.quarkadmin.component.form.fields.ImageCaptcha;
import io.quarkcloud.quarkadmin.component.form.fields.List;
import io.quarkcloud.quarkadmin.component.form.fields.MapField;
import io.quarkcloud.quarkadmin.component.form.fields.Month;
import io.quarkcloud.quarkadmin.component.form.fields.Number;
import io.quarkcloud.quarkadmin.component.form.fields.Password;
import io.quarkcloud.quarkadmin.component.form.fields.Quarter;
import io.quarkcloud.quarkadmin.component.form.fields.Radio;
import io.quarkcloud.quarkadmin.component.form.fields.Search;
import io.quarkcloud.quarkadmin.component.form.fields.SelectField;
import io.quarkcloud.quarkadmin.component.form.fields.Selects;
import io.quarkcloud.quarkadmin.component.form.fields.SmsCaptcha;
import io.quarkcloud.quarkadmin.component.form.fields.Space;
import io.quarkcloud.quarkadmin.component.form.fields.SwitchField;
import io.quarkcloud.quarkadmin.component.form.fields.Text;
import io.quarkcloud.quarkadmin.component.form.fields.Textarea;
import io.quarkcloud.quarkadmin.component.form.fields.Time;
import io.quarkcloud.quarkadmin.component.form.fields.TimeRange;
import io.quarkcloud.quarkadmin.component.form.fields.Transfer;
import io.quarkcloud.quarkadmin.component.form.fields.Tree;
import io.quarkcloud.quarkadmin.component.form.fields.TreeSelect;
import io.quarkcloud.quarkadmin.component.form.fields.Week;
import io.quarkcloud.quarkadmin.component.form.fields.Year;

public class Field {

    public static Cascader cascader(String name) {
        return new Cascader().setName(name);
    }

    public static Cascader cascader(String name, String label) {
        return new Cascader().setName(name).setLabel(label);
    }

    public static Checkbox checkbox(String name) {
        return new Checkbox().setName(name);
    }

    public static Checkbox checkbox(String name, String label) {
        return new Checkbox().setName(name).setLabel(label);
    }

    public static Compact compact(String name) {
        return new Compact().setName(name);
    }

    public static Compact compact(String name, String label) {
        return new Compact().setName(name).setLabel(label);
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

    public static Hidden hidden(String name) {
        return new Hidden().setName(name);
    }

    public static Hidden hidden(String name, String label) {
        return new Hidden().setName(name).setLabel(label);
    }

    public static Icon icon(String name) {
        return new Icon().setName(name);
    }

    public static Icon icon(String name, String label) {
        return new Icon().setName(name).setLabel(label);
    }

    public static Id id(String name) {
        return new Id().setName(name);
    }

    public static Id id(String name, String label) {
        return new Id().setName(name).setLabel(label);
    }

    public static Image image(String name) {
        return new Image().setName(name);
    }

    public static Image image(String name, String label) {
        return new Image().setName(name).setLabel(label);
    }

    public static ImageCaptcha imageCaptcha(String name) {
        return new ImageCaptcha().setName(name);
    }

    public static ImageCaptcha imageCaptcha(String name, String label) {
        return new ImageCaptcha().setName(name).setLabel(label);
    }

    public static List list(String name) {
        return new List().setName(name);
    }

    public static List list(String name, String label) {
        return new List().setName(name).setLabel(label);
    }

    public static MapField mapField(String name) {
        return new MapField().setName(name);
    }

    public static MapField mapField(String name, String label) {
        return new MapField().setName(name).setLabel(label);
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

    public static Password password(String name) {
        return new Password().setName(name);
    }

    public static Password password(String name, String label) {
        return new Password().setName(name).setLabel(label);
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

    public static Search search(String name) {
        return new Search().setName(name);
    }

    public static Search search(String name, String label) {
        return new Search().setName(name).setLabel(label);
    }

    public static SelectField select(String name) {
        return new SelectField().setName(name);
    }

    public static SelectField select(String name, String label) {
        return new SelectField().setName(name).setLabel(label);
    }

    public static SelectField selectField(String name) {
        return new SelectField().setName(name);
    }

    public static SelectField selectField(String name, String label) {
        return new SelectField().setName(name).setLabel(label);
    }

    public static Selects selects(String name) {
        return new Selects().setName(name);
    }

    public static Selects selects(String name, String label) {
        return new Selects().setName(name).setLabel(label);
    }

    public static SmsCaptcha smsCaptcha(String name) {
        return new SmsCaptcha().setName(name);
    }

    public static SmsCaptcha smsCaptcha(String name, String label) {
        return new SmsCaptcha().setName(name).setLabel(label);
    }

    public static Space space(String name) {
        return new Space().setName(name);
    }

    public static Space space(String name, String label) {
        return new Space().setName(name).setLabel(label);
    }

    public static SwitchField switchField(String name) {
        return new SwitchField().setName(name);
    }

    public static SwitchField switchField(String name, String label) {
        return new SwitchField().setName(name).setLabel(label);
    }

    public static Text text(String name) {
        return new Text().setName(name);
    }

    public static Text text(String name, String label) {
        return new Text().setName(name).setLabel(label);
    }

    public static Text text(String name, String label, Closure callback) {
        return new Text().setName(name).setLabel(label).setCallback(callback);
    }

    public static Textarea textarea(String name) {
        return new Textarea().setName(name);
    }

    public static Textarea textarea(String name, String label) {
        return new Textarea().setName(name).setLabel(label);
    }

    public static Time time(String name) {
        return new Time().setName(name);
    }

    public static Time time(String name, String label) {
        return new Time().setName(name).setLabel(label);
    }

    public static TimeRange timeRange(String name) {
        return new TimeRange().setName(name);
    }

    public static TimeRange timeRange(String name, String label) {
        return new TimeRange().setName(name).setLabel(label);
    }

    public static Transfer transfer(String name) {
        return new Transfer().setName(name);
    }

    public static Transfer transfer(String name, String label) {
        return new Transfer().setName(name).setLabel(label);
    }

    public static Tree tree(String name) {
        return new Tree().setName(name);
    }

    public static Tree tree(String name, String label) {
        return new Tree().setName(name).setLabel(label);
    }

    public static TreeSelect treeSelect(String name) {
        return new TreeSelect().setName(name);
    }

    public static TreeSelect treeSelect(String name, String label) {
        return new TreeSelect().setName(name).setLabel(label);
    }

    public static Week week(String name) {
        return new Week().setName(name);
    }

    public static Week week(String name, String label) {
        return new Week().setName(name).setLabel(label);
    }

    public static Year year(String name) {
        return new Year().setName(name);
    }

    public static Year year(String name, String label) {
        return new Year().setName(name).setLabel(label);
    }
}
