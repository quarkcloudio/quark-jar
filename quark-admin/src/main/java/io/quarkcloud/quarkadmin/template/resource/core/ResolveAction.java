package io.quarkcloud.quarkadmin.template.resource.core;

import java.lang.foreign.Linker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.quarkcloud.quarkadmin.component.drawer.Drawer;
import io.quarkcloud.quarkadmin.component.modal.Modal;
import io.quarkcloud.quarkadmin.template.resource.Action;
import io.quarkcloud.quarkadmin.template.resource.impl.action.Link;
import io.quarkcloud.quarkcore.service.Context;

public class ResolveAction {

    // actions
    public List<Action> actions;

    // context
    public Context context;

    // 构造函数
    public ResolveAction() {}

    // 构造函数
    public ResolveAction(List<Action> actions, Context context) {
        this.actions = actions;
        this.context = context;
    }

    // 列表行为
    public Object getIndexTableActions() {
        List<Object> result = new ArrayList<>();
        for (Action action : actions) {
            if (action.shownOnIndex()) {
                result.add(buildAction(context, action));
            }
        }

        return result;
    }

    // 构建行为
    public Object buildAction(Context ctx, Action action) {

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
            api = buildActionApi(ctx, params, uriKey);
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
                Link linkActioner = (Link) action;

                // 是否显示箭头图标
                String href = linkActioner.getHref(ctx);

                // 相当于 a 链接的 target 属性，href 存在时生效
                String target = linkActioner.getTarget(ctx);

                // 设置跳转链接
                actioncomponent.setLink(href, target);
                break;

            case "modal":
            Modal modalActioner = (Modal) action;

                // 宽度
                int modalWidth = modalActioner.getWidth();

                // 关闭时销毁 Modal 里的子元素
                boolean modalDestroyOnClose = modalActioner.isDestroyOnClose();

                // 内容
                Object modalBody = modalActioner.getBody();

                // 弹窗行为
                List<Object> modalActions = modalActioner.getActions();

                // 设置弹窗
                actioncomponent.setModal(new Modal()
                    .setTitle(name)
                    .setWidth(modalWidth)
                    .setBody(modalBody)
                    .setActions(modalActions)
                    .setDestroyOnClose(modalDestroyOnClose));
                break;

            case "drawer":
                Drawer drawerActioner = (Drawer) item;

                // 宽度
                int drawerWidth = drawerActioner.getWidth();

                // 关闭时销毁 Drawer 里的子元素
                boolean drawerDestroyOnClose = drawerActioner.getDestroyOnClose();

                // 内容
                Object drawerBody = drawerActioner.getBody(ctx);

                // 弹窗行为
                List<ActionComponent> drawerActions = drawerActioner.getActions(ctx);

                // 构建弹窗
                actioncomponent.setDrawer(drawer -> drawer
                    .setTitle(name)
                    .setWidth(drawerWidth)
                    .setBody(drawerBody)
                    .setActions(drawerActions)
                    .setDestroyOnClose(drawerDestroyOnClose));
                break;

            case "modalForm":
                ModalFormer modalFormerActioner = (ModalFormer) item;

                // 表单数据接口
                String initApi = buildFormInitApi(ctx, params, uriKey);

                // 字段
                List<FormField> modalFormFields = modalFormerActioner.getFields(ctx);

                // 解析表单组件内的字段
                List<FormField> formFields = formFieldsParser(ctx, modalFormFields);

                // 表单初始数据
                Map<String, Object> modalFormData = new HashMap<>();

                // 宽度
                int modalFormWidth = modalFormerActioner.getWidth();

                // 关闭时销毁 Modal 里的子元素
                boolean modalFormDestroyOnClose = modalFormerActioner.getDestroyOnClose();

                // 构建表单组件
                FormComponent formComponent = new FormComponent()
                    .setKey(uriKey, false)
                    .setStyle(new HashMap<String, Object>() {{
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
                List<ActionComponent> modalFormActions = Arrays.asList(
                    new ActionComponent().init().setLabel(modalFormCancelText).setActionType("cancel"),
                    new ActionComponent().init().setLabel(modalFormSubmitText).setWithLoading(true).setReload(reload).setActionType("submit").setType("primary", false).setSubmitForm(uriKey)
                );

                // 设置弹窗
                actioncomponent.setActionType("modal").setModal(modal -> modal
                    .setTitle(name)
                    .setWidth(modalFormWidth)
                    .setBody(formComponent)
                    .setActions(modalFormActions)
                    .setDestroyOnClose(modalFormDestroyOnClose));
                break;

            case "drawerForm":
                DrawerFormer drawerFormerActioner = (DrawerFormer) item;

                // 表单数据接口
                String initApiDrawer = buildFormInitApi(ctx, params, uriKey);

                // 字段
                List<FormField> drawerFormFields = drawerFormerActioner.getFields(ctx);

                // 解析表单组件内的字段
                List<FormField> drawerFormFieldComponents = formFieldsParser(ctx, drawerFormFields);

                // 表单初始数据
                Map<String, Object> drawerFormData = new HashMap<>();

                // 宽度
                int drawerFormWidth = drawerFormerActioner.getWidth();

                // 关闭时销毁 Drawer 里的子元素
                boolean drawerFormDestroyOnClose = drawerFormerActioner.getDestroyOnClose();

                // 构建表单组件
                FormComponent drawerFormComponent = new FormComponent()
                    .setKey(uriKey, false)
                    .setApi(api)
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
                List<ActionComponent> drawerFormActions = Arrays.asList(
                    new ActionComponent().init().setLabel(drawerFormCancelText).setActionType("cancel"),
                    new ActionComponent().init().setLabel(drawerFormSubmitText).setWithLoading(true).setReload(reload).setActionType("submit").setType("primary", false).setSubmitForm(uriKey)
                );

                // 设置弹窗
                getAction = getAction.setActionType("drawer").setDrawer(drawer -> drawer
                    .setTitle(name)
                    .setWidth(drawerFormWidth)
                    .setBody(drawerFormComponent)
                    .setActions(drawerFormActions)
                    .setDestroyOnClose(drawerFormDestroyOnClose));
                break;

            case "dropdown":
                Dropdowner dropdownActioner = (Dropdowner) item;

                // 获取下拉菜单
                List<MenuItem> overlay = dropdownActioner.getMenu(ctx);

                // 下拉根元素的样式
                Map<String, Object> overlayStyle = dropdownActioner.getOverlayStyle();

                // 菜单弹出位置：bottomLeft bottomCenter bottomRight topLeft topCenter topRight
                String placement = dropdownActioner.getPlacement();

                // 触发下拉的行为, 移动端不支持 hover,Array<click|hover|contextMenu>
                List<String> trigger = dropdownActioner.getTrigger();

                // 是否显示箭头图标
                boolean arrow = dropdownActioner.getArrow();

                // 构建行为
                DropdownComponent dropdownAction = new DropdownComponent()
                    .init()
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

        for (String v : params) {
            paramsUri.append(v).append("=${").append(v).append("}&");
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
}
