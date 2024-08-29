package io.quarkcloud.quarkadmin.template.resource.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import io.quarkcloud.quarkadmin.component.action.Action.Closure;
import io.quarkcloud.quarkadmin.component.drawer.Drawer;
import io.quarkcloud.quarkadmin.component.dropdown.Dropdown;
import io.quarkcloud.quarkadmin.component.form.Form;
import io.quarkcloud.quarkadmin.component.space.Space;
import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.component.modal.Modal;
import io.quarkcloud.quarkadmin.template.resource.Action;
import io.quarkcloud.quarkcore.service.Context;

public class ResolveAction<M extends ResourceMapper<T>, T> {

    // actions
    public List<Object> actions;

    // context
    public Context context;

    // 构造函数
    public ResolveAction() {}

    // 构造函数
    public ResolveAction(List<Object> actions, Context context) {
        this.actions = actions;
        this.context = context;
    }

    // 列表行为
    @SuppressWarnings("unchecked")
    public Object getIndexTableActions() {
        List<Object> items = new ArrayList<>();

        // 判断是否为空
        if (actions == null) {
            return items;
        }

        // 遍历
        for (Object item : actions) {
            Action<T> action = (Action<T>) item;
            if (action.shownOnIndex()) {
                items.add(buildAction(context, action));
            }
        }

        return new Space().setBody(items);
    }

    // 表格行内行为
    @SuppressWarnings("unchecked")
    public List<Object> getIndexTableRowActions() {
        List<Object> items = new ArrayList<>();

        // 判断是否为空
        if (actions == null) {
            return items;
        }

        // 遍历
        for (Object item : actions) {
            Action<T> action = (Action<T>) item;
            if (action.shownOnIndexTableRow()) {
                items.add(buildAction(context, action));
            }
        }

        return items;
    }
    
    // 表格多选弹出层行为
    @SuppressWarnings("unchecked")
    public Object getIndexTableAlertActions() {
        List<Object> items = new ArrayList<>();

        // 判断是否为空
        if (actions == null) {
            return items;
        }

        // 遍历
        for (Object item : actions) {
            Action<T> action = (Action<T>) item;
            if (action.shownOnIndexTableAlert()) {
                items.add(buildAction(context, action));
            }
        }

        return items;
    }
    
    // 表单页行为
    @SuppressWarnings("unchecked")
    public Object getFormActions() {
        List<Object> items = new ArrayList<>();

        // 判断是否为空
        if (actions == null) {
            return items;
        }

        // 遍历
        for (Object item : actions) {
            Action<T> action = (Action<T>) item;
            if (action.shownOnForm()) {
                items.add(buildAction(context, action));
            }
        }

        return items;
    }

    // 表单页右上角自定义区域行为
    @SuppressWarnings("unchecked")
    public Object getFormExtraActions() {
        List<Object> items = new ArrayList<>();

        // 判断是否为空
        if (actions == null) {
            return items;
        }

        // 遍历
        for (Object item : actions) {
            Action<T> action = (Action<T>) item;
            if (action.shownOnFormExtra()) {
                items.add(buildAction(context, action));
            }
        }

        return items;
    }

    // 详情页行为
    @SuppressWarnings("unchecked")
    public Object getDetailActions() {
        List<Object> items = new ArrayList<>();

        // 判断是否为空
        if (actions == null) {
            return items;
        }

        // 遍历
        for (Object item : actions) {
            Action<T> action = (Action<T>) item;
            if (action.shownOnDetail()) {
                items.add(buildAction(context, action));
            }
        }

        return items;
    }

    // 详情页右上角自定义区域行为
    @SuppressWarnings("unchecked")
    public Object getDetailExtraActions() {
        List<Object> items = new ArrayList<>();

        // 判断是否为空
        if (actions == null) {
            return items;
        }

        // 遍历
        for (Object item : actions) {
            Action<T> action = (Action<T>) item;
            if (action.shownOnDetailExtra()) {
                items.add(buildAction(context, action));
            }
        }

        return items;
    }

    // 构建行为
    @SuppressWarnings("unchecked")
    public Object buildAction(Context context, Action<T> action) {

        // 行为名称
        String name = action.getName();

        // 是否携带Loading
        boolean withLoading = action.getWithLoading();

        // 行为执行完成后刷新的组件
        String reload = action.getReload();

        // uri唯一标识
        String uriKey = action.getUriKey(action);

        // 获取api
        String api = action.getApi();

        // 获取api替换参数
        List<String> params = action.getApiParams();
        if (api == null || api.isEmpty()) {
            api = buildActionApi(context, params, uriKey);
        }

        // 行为类型
        String actionType = action.getActionType();

        // 按钮类型
        String buttonType = action.getType();

        // 按钮大小
        String size = action.getSize();

        // 按钮图标
        String icon = action.getIcon();

        // 确认操作标题
        String confirmTitle = action.getConfirmTitle();

        // 确认操作提示信息
        String confirmText = action.getConfirmText();

        // 确认操作类型
        String confirmType = action.getConfirmType();

        // 构建行为
        io.quarkcloud.quarkadmin.component.action.Action actioncomponent = new io.quarkcloud.quarkadmin.component.action.Action()
            .setLabel(name)
            .setWithLoading(withLoading)
            .setReload(reload)
            .setApi(api)
            .setActionType(actionType)
            .setType(buttonType, false)
            .setSize(size);

        if (icon != null && !icon.isEmpty()) {
            actioncomponent.setIcon(icon);
        }

        switch (actionType) {
            case "link":
                io.quarkcloud.quarkadmin.template.resource.impl.action.Link<M, T> linkActioner = (io.quarkcloud.quarkadmin.template.resource.impl.action.Link<M, T>) action;

                // 是否显示箭头图标
                String href = linkActioner.getHref(context);

                // 相当于 a 链接的 target 属性，href 存在时生效
                String target = linkActioner.getTarget(context);

                // 设置跳转链接
                actioncomponent.setLink(href, target);
                break;

            case "modal":
                io.quarkcloud.quarkadmin.template.resource.impl.action.Modal<M, T> modalActioner = (io.quarkcloud.quarkadmin.template.resource.impl.action.Modal<M, T>) action;

                // 宽度
                int modalWidth = modalActioner.getWidth();

                // 关闭时销毁 Modal 里的子元素
                boolean modalDestroyOnClose = modalActioner.isDestroyOnClose();

                // 内容
                Object modalBody = modalActioner.getBody(context);

                // 弹窗行为
                List<Object> modalActions = modalActioner.getActions(context);

                // 构建弹窗
                Closure<Modal> modalClosure = (Modal modal) -> {
                    return modal.setTitle(name).
                    setWidth(modalWidth).
                    setBody(modalBody).
                    setActions(modalActions).
                    setDestroyOnClose(modalDestroyOnClose);
                };

                // 设置弹窗
                actioncomponent.setModal(modalClosure);
                break;

            case "drawer":
                io.quarkcloud.quarkadmin.template.resource.impl.action.Drawer<M, T> drawerActioner = (io.quarkcloud.quarkadmin.template.resource.impl.action.Drawer<M, T>) action;

                // 宽度
                int drawerWidth = drawerActioner.getWidth();

                // 关闭时销毁 Drawer 里的子元素
                boolean drawerDestroyOnClose = drawerActioner.isDestroyOnClose();

                // 内容
                Object drawerBody = drawerActioner.getBody(context);

                // 弹窗行为
                List<Object> drawerActions = drawerActioner.getActions(context);

                // 构建弹窗
                Closure<Drawer> drawerClosure = (Drawer drawer) -> {
                    return drawer.setTitle(name).
                    setWidth(drawerWidth).
                    setBody(drawerBody).
                    setActions(drawerActions).
                    setDestroyOnClose(drawerDestroyOnClose);
                };

                // 构建弹窗
                actioncomponent.setDrawer(drawerClosure);
                break;

            case "modalForm":
                io.quarkcloud.quarkadmin.template.resource.impl.action.ModalForm<M, T> modalFormerActioner = (io.quarkcloud.quarkadmin.template.resource.impl.action.ModalForm<M, T>) action;

                // 表单数据接口
                String initApi = buildFormInitApi(context, params, uriKey);

                // 字段
                List<Object> modalFormFields = modalFormerActioner.fields(context);

                // 解析表单组件内的字段
                Object formFields = new ResolveField().formFieldsParser(context, modalFormFields);

                // 表单初始数据
                Map<String, Object> modalFormData = new HashMap<>();

                // 宽度
                int modalFormWidth = modalFormerActioner.getWidth();

                // 关闭时销毁 Modal 里的子元素
                boolean modalFormDestroyOnClose = modalFormerActioner.getDestroyOnClose();

                // 构建表单组件
                Form formComponent = new Form();

                // 设置组件key
                formComponent.setComponentKey(uriKey, false);

                // 设置表单组件
                formComponent.setStyle(new HashMap<String, Object>() {{
                        put("paddingTop", "24px");
                    }})
                    .setApi(api)
                    .setInitApi(initApi)
                    .setBody(formFields)
                    .setInitialValues(modalFormData)
                    .setLabelCol(new HashMap<String, Object>() {{
                        put("span", 6);
                    }})
                    .setWrapperCol(new HashMap<String, Object>() {{
                        put("span", 18);
                    }});

                // 取消按钮文案
                String modalFormCancelText = modalFormerActioner.getCancelText();

                // 提交按钮文案
                String modalFormSubmitText = modalFormerActioner.getSubmitText();

                // 弹窗行为
                List<Object> modalFormActions = Arrays.asList(
                    new io.quarkcloud.quarkadmin.component.action.Action().
                    setLabel(modalFormCancelText).
                    setActionType("cancel"),

                    new io.quarkcloud.quarkadmin.component.action.Action().
                    setLabel(modalFormSubmitText).
                    setWithLoading(true).
                    setReload(reload).
                    setActionType("submit").
                    setType("primary", false).
                    setSubmitForm(uriKey)
                );

                // 构建弹窗
                Closure<Modal> modalFormClosure = (Modal modal) -> {
                    return modal.setTitle(name).
                    setWidth(modalFormWidth).
                    setBody(formComponent).
                    setActions(modalFormActions).
                    setDestroyOnClose(modalFormDestroyOnClose);
                };

                // 设置弹窗
                actioncomponent.setActionType("modal").setModal(modalFormClosure);
                break;

            case "drawerForm":
                io.quarkcloud.quarkadmin.template.resource.impl.action.DrawerForm<M, T> drawerFormerActioner = (io.quarkcloud.quarkadmin.template.resource.impl.action.DrawerForm<M, T>) action;

                // 表单数据接口
                String initApiDrawer = buildFormInitApi(context, params, uriKey);

                // 字段
                Object drawerFormFields = drawerFormerActioner.fields(context);

                // 解析表单组件内的字段
                Object drawerFormFieldComponents = new ResolveField().formFieldsParser(context, drawerFormFields);

                // 表单初始数据
                Map<String, Object> drawerFormData = new HashMap<>();

                // 宽度
                int drawerFormWidth = drawerFormerActioner.getWidth();

                // 关闭时销毁 Drawer 里的子元素
                boolean drawerFormDestroyOnClose = drawerFormerActioner.getDestroyOnClose();

                // 构建表单组件
                Form drawerFormComponent = new Form();

                // 设置组件key
                drawerFormComponent.setComponentKey(uriKey, false);

                // 构建表单组件
                drawerFormComponent.setApi(api)
                    .setInitApi(initApiDrawer)
                    .setBody(drawerFormFieldComponents)
                    .setInitialValues(drawerFormData)
                    .setLabelCol(new HashMap<String, Object>() {{
                        put("span", 6);
                    }})
                    .setWrapperCol(new HashMap<String, Object>() {{
                        put("span", 18);
                    }});

                // 取消按钮文案
                String drawerFormCancelText = drawerFormerActioner.getCancelText();

                // 提交按钮文案
                String drawerFormSubmitText = drawerFormerActioner.getSubmitText();

                // 弹窗行为
                List<Object> drawerFormActions = Arrays.asList(
                    new io.quarkcloud.quarkadmin.component.action.Action().
                    setLabel(drawerFormCancelText).
                    setActionType("cancel"),

                    new io.quarkcloud.quarkadmin.component.action.Action().
                    setLabel(drawerFormSubmitText).
                    setWithLoading(true).
                    setReload(reload).
                    setActionType("submit").
                    setType("primary", false).
                    setSubmitForm(uriKey)
                );

                Closure<Drawer> drawerFormClosure = (Drawer drawer) -> {
                    return drawer.setTitle(name).
                    setWidth(drawerFormWidth).
                    setBody(drawerFormComponent).
                    setActions(drawerFormActions).
                    setDestroyOnClose(drawerFormDestroyOnClose);
                };

                // 设置弹窗
                actioncomponent.setActionType("drawer").setDrawer(drawerFormClosure);
                break;

            case "dropdown":
                io.quarkcloud.quarkadmin.template.resource.impl.action.Dropdown<M, T> dropdownActioner = (io.quarkcloud.quarkadmin.template.resource.impl.action.Dropdown<M, T>) action;

                // 获取下拉菜单
                Object overlay = dropdownActioner.getMenu(context);

                // 下拉根元素的样式
                Map<String, Object> overlayStyle = dropdownActioner.getOverlayStyle();

                // 菜单弹出位置：bottomLeft bottomCenter bottomRight topLeft topCenter topRight
                String placement = dropdownActioner.getPlacement();

                // 触发下拉的行为, 移动端不支持 hover,Array<click|hover|contextMenu>
                List<String> trigger = dropdownActioner.getTrigger();

                // 是否显示箭头图标
                boolean arrow = dropdownActioner.getArrow();

                // 构建行为
                Dropdown dropdownAction = new Dropdown()
                    .setLabel(name)
                    .setMenu(overlay)
                    .setOverlayStyle(overlayStyle)
                    .setPlacement(placement)
                    .setTrigger(trigger)
                    .setArrow(arrow)
                    .setType(buttonType, false)
                    .setSize(size);

                if (icon != null && !icon.isEmpty()) {
                    dropdownAction = dropdownAction.setIcon(icon);
                }

                return dropdownAction;
        }

        if (confirmTitle != null && !confirmTitle.isEmpty()) {
            actioncomponent.setWithConfirm(confirmTitle, confirmText, confirmType);
        }

        return actioncomponent;
    }

    // 构建行为Api
    public String buildActionApi(Context context, List<String> params, String uriKey) {
        StringBuilder paramsUri = new StringBuilder();
        String api = context.getRequest().getRequestURI();
        if (params!=null) {
            for (String v : params) {
                paramsUri.append(v).append("=${").append(v).append("}&");
            }
        }

        // 获取api路径
        String[] apiPaths = api.split("/");

        // 错误路径，直接返回
        if (apiPaths.length <= 2) {
            return "";
        }

        // 解析数据
        switch (apiPaths[apiPaths.length - 1]) {
            case "index":
                // 列表页接口
                api = api.replace("/index", "/action/" + uriKey);
                break;
            case "create":
                // 创建页接口
                api = api.replace("/create", "/action/" + uriKey);
                break;
            case "edit":
                // 编辑页接口
                api = api.replace("/edit", "/action/" + uriKey);
                break;
            case "detail":
                // 详情页接口
                api = api.replace("/detail", "/action/" + uriKey);
                break;
            case "form":
                // 表单页接口
                String lastPath = apiPaths[apiPaths.length - 2];
                api = api.replace(lastPath + "/form", "action/" + uriKey);
                break;
        }

        // 追加参数
        if (paramsUri.length() > 0) {
            api = api + "?" + paramsUri.toString();
        }

        return api;
    }

    // 构建表单初始化数据接口
    public String buildFormInitApi(Context context, List<String> params, String uriKey) {
        // 拼接参数
        String paramsUri = "";
        if (!params.isEmpty()) {
            StringJoiner joiner = new StringJoiner("&", "?", "");
            for (String param : params) {
                joiner.add(param + "=${" + param + "}");
            }
            paramsUri = joiner.toString();
        }

        // 构建基本 API 路径
        String api = context.getRequest().getRequestURI().replace("/index", "/action/" + uriKey + "/values");

        // 处理不同页面接口的情况
        api = api.replace("/create", "/action/" + uriKey + "/values");
        api = api.replace("/edit", "/action/" + uriKey + "/values");
        api = api.replace("/detail", "/action/" + uriKey + "/values");

        return api + paramsUri;
    }

}
