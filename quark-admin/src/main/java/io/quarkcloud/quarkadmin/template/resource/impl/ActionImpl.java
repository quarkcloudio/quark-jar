package io.quarkcloud.quarkadmin.template.resource.impl;

import java.util.List;

import org.apache.commons.lang3.reflect.TypeUtils;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.service.ResourceService;
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
    public Object fields;
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
    public Object handle(Context context, UpdateWrapper<T> updateWrapper, ResourceService<ResourceMapper<T>, T> resourceService) {
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
    public Object fields(Context context) {
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
    public ActionImpl<M, T> setName(String name) {
        this.name = name;
        return this;
    }

    // 设置执行成功后刷新的组件
    public ActionImpl<M, T> setReload(String componentKey) {
        this.reload = componentKey;
        return this;
    }

    // 行为接口接收的参数，当行为在表格行展示的时候，可以配置当前行的任意字段
    public ActionImpl<M, T> setApiParams(List<String> apiParams) {
        this.apiParams = apiParams;
        return this;
    }

    // 执行行为的接口
    public ActionImpl<M, T> setApi(String api) {
        this.api = api;
        return this;
    }

    // 【必填】这是 action 最核心的配置，来指定该 action 的作用类型，支持：ajax、link、url、drawer、dialog、confirm、cancel、prev、next、copy、close。
    public ActionImpl<M, T> setActionType(String actionType) {
        this.actionType = actionType;
        return this;
    }

    // 当 action 的作用类型为submit的时候，可以指定提交哪个表格，submitForm为提交表单的key值，为空时提交当前表单
    public ActionImpl<M, T> setSubmitForm(String submitForm) {
        this.submitForm = submitForm;
        return this;
    }

    // 设置按钮类型，primary | ghost | dashed | link | text | default
    public ActionImpl<M, T> setType(String buttonType) {
        this.type = buttonType;
        return this;
    }

    // 设置按钮大小,large | middle | small | default
    public ActionImpl<M, T> setSize(String size) {
        this.size = size;
        return this;
    }

    // 是否具有loading，当action 的作用类型为ajax,submit时有效
    public ActionImpl<M, T> setWithLoading(boolean loading) {
        this.withLoading = loading;
        return this;
    }

    // 设置按钮的图标组件
    public ActionImpl<M, T> setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    // 行为表单字段
    public ActionImpl<M, T> setFields(Object fields) {
        this.fields = fields;
        return this;
    }

    // 确认标题
    public ActionImpl<M, T> setConfirmTitle(String confirmTitle) {
        this.confirmTitle = confirmTitle;
        return this;
    }

    // 确认文字
    public ActionImpl<M, T> setConfirmText(String confirmText) {
        this.confirmText = confirmText;
        return this;
    }

    // 确认类型
    public ActionImpl<M, T> setConfirmType(String confirmType) {
        this.confirmType = confirmType;
        return this;
    }

    // 设置行为前的确认操作
    public ActionImpl<M, T> withConfirm(String title, String text, String confirmType) {
        this.confirmTitle = title;
        this.confirmText = text;
        this.confirmType = confirmType;
        return this;
    }

    // 只在列表页展示
    public ActionImpl<M, T> setOnlyOnIndex(boolean value) {
        this.onlyOnIndex = value;
        this.showOnIndex = value;
        this.showOnDetail = !value;
        this.showOnIndexTableRow = !value;
        this.showOnIndexTableAlert = !value;
        this.showOnForm = !value;
        this.showOnFormExtra = !value;
        this.showOnDetail = !value;
        this.showOnDetailExtra = !value;
        return this;
    }

    // 除了列表页外展示
    public ActionImpl<M, T> setExceptOnIndex() {
        this.showOnDetail = true;
        this.showOnIndexTableRow = true;
        this.showOnIndexTableAlert = true;
        this.showOnForm = true;
        this.showOnFormExtra = true;
        this.showOnDetail = true;
        this.showOnDetailExtra = true;
        this.showOnIndex = false;
        return this;
    }

    // 只在表单页展示
    public ActionImpl<M, T> setOnlyOnForm(boolean value) {
        this.showOnForm = value;
        this.showOnIndexTableAlert = !value;
        this.showOnIndex = !value;
        this.showOnDetail = !value;
        this.showOnIndexTableRow = !value;
        this.showOnFormExtra = !value;
        this.showOnDetail = !value;
        this.showOnDetailExtra = !value;
        return this;
    }

    // 除了表单页外展示
    public ActionImpl<M, T> setExceptOnForm() {
        this.showOnIndexTableAlert = true;
        this.showOnIndex = true;
        this.showOnDetail = true;
        this.showOnIndexTableRow = true;
        this.showOnForm = false;
        this.showOnFormExtra = true;
        this.showOnDetail = true;
        this.showOnDetailExtra = true;
        return this;
    }

    // 只在表单页右上角自定义区域展示
    public ActionImpl<M, T> setOnlyOnFormExtra(boolean value) {
        this.showOnForm = !value;
        this.showOnIndexTableAlert = !value;
        this.showOnIndex = !value;
        this.showOnDetail = !value;
        this.showOnIndexTableRow = !value;
        this.showOnFormExtra = value;
        this.showOnDetail = !value;
        this.showOnDetailExtra = !value;
        return this;
    }

    // 除了表单页右上角自定义区域外展示
    public ActionImpl<M, T> setExceptOnFormExtra() {
        this.showOnIndexTableAlert = true;
        this.showOnIndex = true;
        this.showOnDetail = true;
        this.showOnIndexTableRow = true;
        this.showOnForm = true;
        this.showOnFormExtra = false;
        this.showOnDetail = true;
        this.showOnDetailExtra = true;
        return this;
    }

    // 只在详情页展示
    public ActionImpl<M, T> setOnlyOnDetail(boolean value) {
        this.onlyOnDetail = value;
        this.showOnDetail = value;
        this.showOnIndex = !value;
        this.showOnIndexTableRow = !value;
        this.showOnIndexTableAlert = !value;
        this.showOnForm = !value;
        this.showOnFormExtra = !value;
        this.showOnDetailExtra = !value;
        return this;
    }

    // 除了详情页外展示
    public ActionImpl<M, T> setExceptOnDetail() {
        this.showOnIndex = true;
        this.showOnDetail = false;
        this.showOnIndexTableRow = true;
        this.showOnIndexTableAlert = true;
        this.showOnForm = true;
        this.showOnFormExtra = true;
        this.showOnDetailExtra = true;
        return this;
    }

    // 只在详情页右上角自定义区域展示
    public ActionImpl<M, T> setOnlyOnDetailExtra(boolean value) {
        this.showOnForm = !value;
        this.showOnIndexTableAlert = !value;
        this.showOnIndex = !value;
        this.showOnDetail = !value;
        this.showOnIndexTableRow = !value;
        this.showOnFormExtra = !value;
        this.showOnDetail = !value;
        this.showOnDetailExtra = value;
        return this;
    }

    // 除了详情页右上角自定义区域外展示
    public ActionImpl<M, T> setExceptOnDetailExtra() {
        this.showOnIndexTableAlert = true;
        this.showOnIndex = true;
        this.showOnDetail = true;
        this.showOnIndexTableRow = true;
        this.showOnForm = true;
        this.showOnFormExtra = true;
        this.showOnDetail = true;
        this.showOnDetailExtra = false;
        return this;
    }

    // 在表格行内展示
    public ActionImpl<M, T> setOnlyOnIndexTableRow(boolean value) {
        this.showOnIndexTableRow = value;
        this.showOnIndex = !value;
        this.showOnDetail = !value;
        this.showOnIndexTableAlert = !value;
        this.showOnForm = !value;
        this.showOnFormExtra = !value;
        this.showOnDetail = !value;
        this.showOnDetailExtra = !value;
        return this;
    }

    // 除了表格行内外展示
    public ActionImpl<M, T> setExceptOnIndexTableRow() {
        this.showOnIndexTableRow = false;
        this.showOnIndex = true;
        this.showOnDetail = true;
        this.showOnIndexTableAlert = true;
        this.showOnForm = true;
        this.showOnFormExtra = true;
        this.showOnDetail = true;
        this.showOnDetailExtra = true;
        return this;
    }

    // 在表格多选弹出层展示
    public ActionImpl<M, T> setOnlyOnIndexTableAlert(boolean value) {
        this.showOnIndexTableAlert = value;
        this.showOnIndex = !value;
        this.showOnDetail = !value;
        this.showOnIndexTableRow = !value;
        this.showOnForm = !value;
        this.showOnFormExtra = !value;
        this.showOnDetail = !value;
        this.showOnDetailExtra = !value;
        return this;
    }

    // 除了表格多选弹出层外展示
    public ActionImpl<M, T> setExceptOnIndexTableAlert() {
        this.showOnIndexTableAlert = false;
        this.showOnIndex = true;
        this.showOnDetail = true;
        this.showOnIndexTableRow = true;
        this.showOnForm = true;
        this.showOnFormExtra = true;
        this.showOnDetail = true;
        this.showOnDetailExtra = true;
        return this;
    }

    // 在列表页展示
    public ActionImpl<M, T> setShowOnIndex() {
        this.showOnIndex = true;
        return this;
    }

    // 在表单页展示
    public ActionImpl<M, T> setShowOnForm() {
        this.showOnForm = true;
        return this;
    }

    // 在表单页右上角自定义区域展示
    public ActionImpl<M, T> setShowOnFormExtra() {
        this.showOnFormExtra = true;
        return this;
    }

    // 在详情页展示
    public ActionImpl<M, T> setShowOnDetail() {
        this.showOnDetail = true;
        return this;
    }

    // 在详情页右上角自定义区域展示
    public ActionImpl<M, T> setShowOnDetailExtra() {
        this.showOnDetailExtra = true;
        return this;
    }

    // 在表格行内展示
    public ActionImpl<M, T> setShowOnIndexTableRow() {
        this.showOnIndexTableRow = true;
        return this;
    }

    // 在多选弹出层展示
    public ActionImpl<M, T> setShowOnIndexTableAlert() {
        this.showOnIndexTableAlert = true;
        return this;
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
