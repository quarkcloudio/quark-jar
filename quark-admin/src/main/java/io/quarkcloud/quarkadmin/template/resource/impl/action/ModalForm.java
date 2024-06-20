package io.quarkcloud.quarkadmin.template.resource.impl.action;

import java.util.HashMap;
import java.util.Map;

import io.quarkcloud.quarkcore.service.Context;

public class ModalForm extends ActionImpl {

    // 抽屉弹出层宽度
    private int width;

    // 关闭时销毁弹出层里的子元素
    private boolean destroyOnClose;

    // 获取取消按钮文案
    private String cancelText;

    // 获取提交按钮文案
    private String submitText;

    // 初始化
    public ModalForm(Context ctx) {
        this.setActionType("modalForm");
        this.width = 520;
        this.setReload("table");
        this.cancelText = "取消";
        this.submitText = "提交";
    }

    // 表单字段
    public Object[] fields(Context ctx) {
        return new Object[]{};
    }

    // 表单数据（异步获取）
    public Map<String, Object> data(Context ctx) {
        return new HashMap<>();
    }

    // 宽度
    public int getWidth() {
        return this.width;
    }

    // 关闭时销毁 Modal 里的子元素
    public boolean getDestroyOnClose() {
        return this.destroyOnClose;
    }

    // 获取取消按钮文案
    public String getCancelText() {
        return this.cancelText;
    }

    // 获取提交按钮文案
    public String getSubmitText() {
        return this.submitText;
    }
}
