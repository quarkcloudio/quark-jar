package io.quarkcloud.quarkadmin.template.resource.impl.action;

import java.util.List;

import io.quarkcloud.quarkadmin.template.resource.impl.ActionImpl;
import io.quarkcloud.quarkcore.service.Context;

public class Modal extends ActionImpl {

    // 抽屉弹出层宽度
    private int width;

    // 关闭时销毁弹出层里的子元素
    private boolean destroyOnClose;

    // 初始化
    public Modal(Context ctx) {
        this.setActionType("modal");
        this.width = 520;
    }

    // 宽度
    public int getWidth() {
        return this.width;
    }

    // 关闭时销毁 Modal 里的子元素
    public boolean isDestroyOnClose() {
        return this.destroyOnClose;
    }

    // 内容
    public Object getBody(Context ctx) {
        return null;
    }

    // 弹窗行为
    public List<Object> getActions(Context ctx) {
        return null;
    }

    // Setters
    public void setWidth(int width) {
        this.width = width;
    }

    public void setDestroyOnClose(boolean destroyOnClose) {
        this.destroyOnClose = destroyOnClose;
    }
}
