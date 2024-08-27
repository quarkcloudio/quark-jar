package io.quarkcloud.quarkadmin.template.resource.impl;

import java.util.List;

import org.apache.commons.lang3.reflect.TypeUtils;

import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.template.resource.Action;
import io.quarkcloud.quarkcore.service.Context;

public class ActionImpl<M extends ResourceMapper<T>, T> implements Action<T> {
    
    public String name;
    public String reload;
    public List<String> apiParams;
    public String api;
    public String actionType;
    public String submitForm;
    public String icon;
    public String type;
    public String size;
    public boolean withLoading;
    public List<Object> fields;
    public String confirmTitle;
    public String confirmText;
    public String confirmType;
    public boolean onlyOnIndex;
    public boolean onlyOnForm;
    public boolean onlyOnDetail;
    public boolean showOnIndex;
    public boolean showOnIndexTableRow;
    public boolean showOnIndexTableAlert;
    public boolean showOnForm;
    public boolean showOnFormExtra;
    public boolean showOnDetail;
    public boolean showOnDetailExtra;

    // 构造函数
    public ActionImpl() {
        this.actionType = "ajax";
    }

    // 执行行为句柄
    public Object handle(Context context, Object query) throws Exception {
        return "Method not implemented";
    }

    // 行为key
    public String getUriKey(Object action) {
        String uriKey = TypeUtils.getRawType(action.getClass(), null).toString();
        String[] uriKeys = uriKey.split("\\.");
        uriKey = uriKeys[uriKeys.length - 1].replaceAll("([a-z])([A-Z])", "$1-$2").toLowerCase();
        return uriKey;
    }

    // 获取名称
    public String getName() {
        return name;
    }

    // 执行成功后刷新的组件
    public String getReload() {
        return reload;
    }

    // 行为接口接收的参数，当行为在表格行展示的时候，可以配置当前行的任意字段
    public List<String> getApiParams() {
        return apiParams;
    }

    // 执行行为的接口
    public String getApi() {
        return api;
    }

    // 【必填】这是 action 最核心的配置，来指定该 action 的作用类型，支持：ajax、link、url、drawer、dialog、confirm、cancel、prev、next、copy、close。
    public String getActionType() {
        return actionType;
    }

    // 当 action 的作用类型为submit的时候，可以指定提交哪个表格，submitForm为提交表单的key值，为空时提交当前表单
    public String getSubmitForm() {
        return submitForm;
    }

    // 设置按钮类型，primary | ghost | dashed | link | text | default
    public String getType() {
        return type;
    }

    // 设置按钮大小,large | middle | small | default
    public String getSize() {
        return size;
    }

    // 是否具有loading，当action 的作用类型为ajax,submit时有效
    public boolean getWithLoading() {
        return withLoading;
    }

    // 设置按钮的图标组件
    public String getIcon() {
        return icon;
    }

    // 行为表单字段
    public List<Object> fields(Context context) {
        return fields;
    }

    // 确认标题
    public String getConfirmTitle() {
        return confirmTitle;
    }

    // 确认文字
    public String getConfirmText() {
        return confirmText;
    }

    // 确认类型
    public String getConfirmType() {
        return confirmType;
    }

    // 设置名称
    public void setName(String name) {
        this.name = name;
    }

    // 设置执行成功后刷新的组件
    public void setReload(String componentKey) {
        this.reload = componentKey;
    }

    // 行为接口接收的参数，当行为在表格行展示的时候，可以配置当前行的任意字段
    public void setApiParams(List<String> apiParams) {
        this.apiParams = apiParams;
    }

    // 执行行为的接口
    public void setApi(String api) {
        this.api = api;
    }

    // 【必填】这是 action 最核心的配置，来指定该 action 的作用类型，支持：ajax、link、url、drawer、dialog、confirm、cancel、prev、next、copy、close。
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    // 当 action 的作用类型为submit的时候，可以指定提交哪个表格，submitForm为提交表单的key值，为空时提交当前表单
    public void setSubmitForm(String submitForm) {
        this.submitForm = submitForm;
    }

    // 设置按钮类型，primary | ghost | dashed | link | text | default
    public void setType(String buttonType) {
        this.type = buttonType;
    }

    // 设置按钮大小,large | middle | small | default
    public void setSize(String size) {
        this.size = size;
    }

    // 是否具有loading，当action 的作用类型为ajax,submit时有效
    public void setWithLoading(boolean loading) {
        this.withLoading = loading;
    }

    // 设置按钮的图标组件
    public void setIcon(String icon) {
        this.icon = icon;
    }

    // 行为表单字段
    public void setFields(List<Object> fields) {
        this.fields = fields;
    }

    // 确认标题
    public void setConfirmTitle(String confirmTitle) {
        this.confirmTitle = confirmTitle;
    }

    // 确认文字
    public void setConfirmText(String confirmText) {
        this.confirmText = confirmText;
    }

    // 确认类型
    public void setConfirmType(String confirmType) {
        this.confirmType = confirmType;
    }

    // 设置行为前的确认操作
    public void withConfirm(String title, String text, String confirmType) {
        this.confirmTitle = title;
        this.confirmText = text;
        this.confirmType = confirmType;
    }

    // 只在列表页展示
    public void setOnlyOnIndex(boolean value) {
        this.onlyOnIndex = value;
        this.showOnIndex = value;
        this.showOnDetail = !value;
        this.showOnIndexTableRow = !value;
        this.showOnIndexTableAlert = !value;
        this.showOnForm = !value;
        this.showOnFormExtra = !value;
        this.showOnDetail = !value;
        this.showOnDetailExtra = !value;
    }

    // 除了列表页外展示
    public void setExceptOnIndex() {
        this.showOnDetail = true;
        this.showOnIndexTableRow = true;
        this.showOnIndexTableAlert = true;
        this.showOnForm = true;
        this.showOnFormExtra = true;
        this.showOnDetail = true;
        this.showOnDetailExtra = true;
        this.showOnIndex = false;
    }

    // 只在表单页展示
    public void setOnlyOnForm(boolean value) {
        this.showOnForm = value;
        this.showOnIndexTableAlert = !value;
        this.showOnIndex = !value;
        this.showOnDetail = !value;
        this.showOnIndexTableRow = !value;
        this.showOnFormExtra = !value;
        this.showOnDetail = !value;
        this.showOnDetailExtra = !value;
    }

    // 除了表单页外展示
    public void setExceptOnForm() {
        this.showOnIndexTableAlert = true;
        this.showOnIndex = true;
        this.showOnDetail = true;
        this.showOnIndexTableRow = true;
        this.showOnForm = false;
        this.showOnFormExtra = true;
        this.showOnDetail = true;
        this.showOnDetailExtra = true;
    }

    // 只在表单页右上角自定义区域展示
    public void setOnlyOnFormExtra(boolean value) {
        this.showOnForm = !value;
        this.showOnIndexTableAlert = !value;
        this.showOnIndex = !value;
        this.showOnDetail = !value;
        this.showOnIndexTableRow = !value;
        this.showOnFormExtra = value;
        this.showOnDetail = !value;
        this.showOnDetailExtra = !value;
    }

    // 除了表单页右上角自定义区域外展示
    public void setExceptOnFormExtra() {
        this.showOnIndexTableAlert = true;
        this.showOnIndex = true;
        this.showOnDetail = true;
        this.showOnIndexTableRow = true;
        this.showOnForm = true;
        this.showOnFormExtra = false;
        this.showOnDetail = true;
        this.showOnDetailExtra = true;
    }

    // 只在详情页展示
    public void setOnlyOnDetail(boolean value) {
        this.onlyOnDetail = value;
        this.showOnDetail = value;
        this.showOnIndex = !value;
        this.showOnIndexTableRow = !value;
        this.showOnIndexTableAlert = !value;
        this.showOnForm = !value;
        this.showOnFormExtra = !value;
        this.showOnDetailExtra = !value;
    }

    // 除了详情页外展示
    public void setExceptOnDetail() {
        this.showOnIndex = true;
        this.showOnDetail = false;
        this.showOnIndexTableRow = true;
        this.showOnIndexTableAlert = true;
        this.showOnForm = true;
        this.showOnFormExtra = true;
        this.showOnDetailExtra = true;
    }

    // 只在详情页右上角自定义区域展示
    public void setOnlyOnDetailExtra(boolean value) {
        this.showOnForm = !value;
        this.showOnIndexTableAlert = !value;
        this.showOnIndex = !value;
        this.showOnDetail = !value;
        this.showOnIndexTableRow = !value;
        this.showOnFormExtra = !value;
        this.showOnDetail = !value;
        this.showOnDetailExtra = value;
    }

    // 除了详情页右上角自定义区域外展示
    public void setExceptOnDetailExtra() {
        this.showOnIndexTableAlert = true;
        this.showOnIndex = true;
        this.showOnDetail = true;
        this.showOnIndexTableRow = true;
        this.showOnForm = true;
        this.showOnFormExtra = true;
        this.showOnDetail = true;
        this.showOnDetailExtra = false;
    }

    // 在表格行内展示
    public void setOnlyOnIndexTableRow(boolean value) {
        this.showOnIndexTableRow = value;
        this.showOnIndex = !value;
        this.showOnDetail = !value;
        this.showOnIndexTableAlert = !value;
        this.showOnForm = !value;
        this.showOnFormExtra = !value;
        this.showOnDetail = !value;
        this.showOnDetailExtra = !value;
    }

    // 除了表格行内外展示
    public void setExceptOnIndexTableRow() {
        this.showOnIndexTableRow = false;
        this.showOnIndex = true;
        this.showOnDetail = true;
        this.showOnIndexTableAlert = true;
        this.showOnForm = true;
        this.showOnFormExtra = true;
        this.showOnDetail = true;
        this.showOnDetailExtra = true;
    }

    // 在表格多选弹出层展示
    public void setOnlyOnIndexTableAlert(boolean value) {
        this.showOnIndexTableAlert = value;
        this.showOnIndex = !value;
        this.showOnDetail = !value;
        this.showOnIndexTableRow = !value;
        this.showOnForm = !value;
        this.showOnFormExtra = !value;
        this.showOnDetail = !value;
        this.showOnDetailExtra = !value;
    }

    // 除了表格多选弹出层外展示
    public void setExceptOnIndexTableAlert() {
        this.showOnIndexTableAlert = false;
        this.showOnIndex = true;
        this.showOnDetail = true;
        this.showOnIndexTableRow = true;
        this.showOnForm = true;
        this.showOnFormExtra = true;
        this.showOnDetail = true;
        this.showOnDetailExtra = true;
    }

    // 在列表页展示
    public void setShowOnIndex() {
        this.showOnIndex = true;
    }

    // 在表单页展示
    public void setShowOnForm() {
        this.showOnForm = true;
    }

    // 在表单页右上角自定义区域展示
    public void setShowOnFormExtra() {
        this.showOnFormExtra = true;
    }

    // 在详情页展示
    public void setShowOnDetail() {
        this.showOnDetail = true;
    }

    // 在详情页右上角自定义区域展示
    public void setShowOnDetailExtra() {
        this.showOnDetailExtra = true;
    }

    // 在表格行内展示
    public void setShowOnIndexTableRow() {
        this.showOnIndexTableRow = true;
    }

    // 在多选弹出层展示
    public void setShowOnIndexTableAlert() {
        this.showOnIndexTableAlert = true;
    }

    // 判断是否在列表页展示
    public boolean shownOnIndex() {
        if (onlyOnIndex) {
            return true;
        }
        if (onlyOnDetail || onlyOnForm) {
            return false;
        }
        return showOnIndex;
    }

    // 判断是否在表单页展示
    public boolean shownOnForm() {
        if (onlyOnForm) {
            return true;
        }
        if (onlyOnDetail || onlyOnIndex) {
            return false;
        }
        return showOnForm;
    }

    // 判断是否在详情页展示
    public boolean shownOnDetail() {
        if (onlyOnDetail) {
            return true;
        }
        if (onlyOnIndex || onlyOnForm) {
            return false;
        }
        return showOnDetail;
    }

    // 判断是否在表格行内展示
    public boolean shownOnIndexTableRow() {
        return showOnIndexTableRow;
    }

    // 判断是否在多选弹出层展示
    public boolean shownOnIndexTableAlert() {
        return showOnIndexTableAlert;
    }

    // 判断是否在表单页右上角自定义区域展示
    public boolean shownOnFormExtra() {
        return showOnFormExtra;
    }

    // 判断是否在详情页右上角自定义区域展示
    public boolean shownOnDetailExtra() {
        return showOnDetailExtra;
    }
}
