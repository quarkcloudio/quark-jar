package io.quarkcloud.quarkadmin.template.resource;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.service.ResourceService;
import io.quarkcloud.quarkcore.service.Context;

public interface Action<T> {

    // 执行行为
    Object handle(Context context, UpdateWrapper<T> updateWrapper, ResourceService<ResourceMapper<T>, T> resourceService);

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

    // 设置按钮是否为块级元素
    boolean getBlock();

    // 是否批量操作
    boolean getBatch();

    // 危险按钮
    boolean getDanger();

    // 禁用按钮
    boolean getDisabled();

    // 是否幽灵按钮
    boolean getGhost();

    // 行为表单字段
    Object fields(Context context);

    // 确认标题
    String getConfirmTitle();

    // 确认文字
    String getConfirmText();

    // 确认类型
    String getConfirmType();

    // 设置名称
    Action<T> setName(String name);

    // 设置执行成功后刷新的组件
    Action<T> setReload(String componentKey);

    // 行为接口接收的参数，当行为在表格行展示的时候，可以配置当前行的任意字段
    Action<T> setApiParams(List<String> apiParams);

    // 执行行为的接口
    Action<T> setApi(String api);

    // 【必填】这是 action 最核心的配置，来指定该 action 的作用类型，支持：ajax、link、url、drawer、dialog、confirm、cancel、prev、next、copy、close。
    Action<T> setActionType(String actionType);

    // 当 action 的作用类型为submit的时候，可以指定提交哪个表格，submitForm为提交表单的key值，为空时提交当前表单
    Action<T> setSubmitForm(String submitForm);

    // 设置按钮类型，primary | ghost | dashed | link | text | default
    Action<T> setType(String buttonType);

    // 设置按钮大小,large | middle | small | default
    Action<T> setSize(String size);

    // 是否具有loading，当action 的作用类型为ajax,submit时有效
    Action<T> setWithLoading(boolean loading);

    // 设置按钮的图标组件
    Action<T> setIcon(String icon);

    // 设置按钮为块级元素
    Action<T> setBlock(boolean block);


    // 批量操作
    Action<T> setBatch(boolean batch);

    // 危险按钮
    Action<T> setDanger(boolean danger);

    // 禁用按钮
    Action<T> setDisabled(boolean disabled);

    // 是否幽灵按钮
    Action<T> setGhost(boolean ghost);

    // 行为表单字段
    Action<T> setFields(Object fields);

    // 确认标题
    Action<T> setConfirmTitle(String confirmTitle);

    // 确认文字
    Action<T> setConfirmText(String confirmText);

    // 确认类型
    Action<T> setConfirmType(String confirmType);

    // 设置行为前的确认操作
    Action<T> withConfirm(String title, String text, String confirmType);

    // 只在列表页展示
    Action<T> setOnlyOnIndex(boolean value);

    // 除了列表页外展示
    Action<T> setExceptOnIndex();

    // 只在表单页展示
    Action<T> setOnlyOnForm(boolean value);

    // 除了表单页外展示
    Action<T> setExceptOnForm();

    // 除了表单页右上角自定义区域外展示
    Action<T> setOnlyOnFormExtra(boolean value);

    // 只在详情页展示
    Action<T> setOnlyOnDetail(boolean value);

    // 除了详情页外展示
    Action<T> setExceptOnDetail();

    // 只在详情页右上角自定义区域展示
    Action<T> setOnlyOnDetailExtra(boolean value);

    // 除了详情页右上角自定义区域外展示
    Action<T> setExceptOnDetailExtra();

    // 在表格行内展示
    Action<T> setOnlyOnIndexTableRow(boolean value);

    // 除了表格行内外展示
    Action<T> setExceptOnIndexTableRow();

    // 在列表页展示
    Action<T> setShowOnIndex();

    // 在表单页展示
    Action<T> setShowOnForm();

    // 在表单页右上角自定义区域展示
    Action<T> setShowOnFormExtra();

    // 在详情页展示
    Action<T> setShowOnDetail();

    // 在详情页右上角自定义区域展示
    Action<T> setShowOnDetailExtra();

    // 在表格行内展示
    Action<T> setShowOnIndexTableRow();

    // 判断是否在列表页展示
    boolean shownOnIndex();

    // 判断是否在表单页展示
    boolean shownOnForm();

    // 判断是否在详情页展示
    boolean shownOnDetail();

    // 判断是否在表格行内展示
    boolean shownOnIndexTableRow();

    // 判断是否在表单页右上角自定义区域展示
    boolean shownOnFormExtra();

    // 判断是否在详情页右上角自定义区域展示
    boolean shownOnDetailExtra();
}
