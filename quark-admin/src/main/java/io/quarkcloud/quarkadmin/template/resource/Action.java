package io.quarkcloud.quarkadmin.template.resource;

import java.util.List;

import io.quarkcloud.quarkcore.service.Context;

public interface Action {

    // 行为key
    String getUriKey(Object action);

    // 获取名称
    String getName();

    // 执行成功后刷新的组件
    String getReload();

    // 行为接口接收的参数，当行为在表格行展示的时候，可以配置当前行的任意字段
    List<String> getApiParams();

    // 执行行为的接口
    String getApi();

    // 【必填】这是 action 最核心的配置，来指定该 action 的作用类型，支持：ajax、link、url、drawer、dialog、confirm、cancel、prev、next、copy、close。
    String getActionType();

    // 当 action 的作用类型为submit的时候，可以指定提交哪个表格，submitForm为提交表单的key值，为空时提交当前表单
    String getSubmitForm();

    // 设置按钮类型，primary | ghost | dashed | link | text | default
    String getType();

    // 设置按钮大小,large | middle | small | default
    String getSize();

    // 是否具有loading，当action 的作用类型为ajax,submit时有效
    boolean getWithLoading();

    // 设置按钮的图标组件
    String getIcon();

    // 行为表单字段
    List<Object> getFields(Context ctx);

    // 确认标题
    String getConfirmTitle();

    // 确认文字
    String getConfirmText();

    // 确认类型
    String getConfirmType();

    // 设置名称
    void setName(String name);

    // 设置执行成功后刷新的组件
    void setReload(String componentKey);

    // 行为接口接收的参数，当行为在表格行展示的时候，可以配置当前行的任意字段
    void setApiParams(List<String> apiParams);

    // 执行行为的接口
    void setApi(String api);

    // 【必填】这是 action 最核心的配置，来指定该 action 的作用类型，支持：ajax、link、url、drawer、dialog、confirm、cancel、prev、next、copy、close。
    void setActionType(String actionType);

    // 当 action 的作用类型为submit的时候，可以指定提交哪个表格，submitForm为提交表单的key值，为空时提交当前表单
    void setSubmitForm(String submitForm);

    // 设置按钮类型，primary | ghost | dashed | link | text | default
    void setType(String buttonType);

    // 设置按钮大小,large | middle | small | default
    void setSize(String size);

    // 是否具有loading，当action 的作用类型为ajax,submit时有效
    void setWithLoading(boolean loading);

    // 设置按钮的图标组件
    void setIcon(String icon);

    // 行为表单字段
    void setFields(List<Object> fields);

    // 确认标题
    void setConfirmTitle(String confirmTitle);

    // 确认文字
    void setConfirmText(String confirmText);

    // 确认类型
    void setConfirmType(String confirmType);

    // 设置行为前的确认操作
    void withConfirm(String title, String text, String confirmType);

    // 只在列表页展示
    void setOnlyOnIndex(boolean value);

    // 除了列表页外展示
    void setExceptOnIndex();

    // 只在表单页展示
    void setOnlyOnForm(boolean value);

    // 除了表单页外展示
    void setExceptOnForm();

    // 除了表单页右上角自定义区域外展示
    void setOnlyOnFormExtra(boolean value);

    // 只在详情页展示
    void setOnlyOnDetail(boolean value);

    // 除了详情页外展示
    void setExceptOnDetail();

    // 只在详情页右上角自定义区域展示
    void setOnlyOnDetailExtra(boolean value);

    // 除了详情页右上角自定义区域外展示
    void setExceptOnDetailExtra();

    // 在表格行内展示
    void setOnlyOnIndexTableRow(boolean value);

    // 除了表格行内外展示
    void setExceptOnIndexTableRow();

    // 在表格多选弹出层展示
    void setOnlyOnIndexTableAlert(boolean value);

    // 除了表格多选弹出层外展示
    void setExceptOnIndexTableAlert();

    // 在列表页展示
    void setShowOnIndex();

    // 在表单页展示
    void setShowOnForm();

    // 在表单页右上角自定义区域展示
    void setShowOnFormExtra();

    // 在详情页展示
    void setShowOnDetail();

    // 在详情页右上角自定义区域展示
    void setShowOnDetailExtra();

    // 在表格行内展示
    void setShowOnIndexTableRow();

    // 在多选弹出层展示
    void setShowOnIndexTableAlert();

    // 判断是否在列表页展示
    boolean shownOnIndex();

    // 判断是否在表单页展示
    boolean shownOnForm();

    // 判断是否在详情页展示
    boolean shownOnDetail();

    // 判断是否在表格行内展示
    boolean shownOnIndexTableRow();

    // 判断是否在多选弹出层展示
    boolean shownOnIndexTableAlert();

    // 判断是否在表单页右上角自定义区域展示
    boolean shownOnFormExtra();

    // 判断是否在详情页右上角自定义区域展示
    boolean shownOnDetailExtra();
}
