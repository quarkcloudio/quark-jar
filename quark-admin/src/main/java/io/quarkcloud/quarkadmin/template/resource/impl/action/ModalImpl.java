package io.quarkcloud.quarkadmin.template.resource.impl.action;

import java.util.List;

import io.quarkcloud.quarkadmin.mapper.ResourceMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.ActionImpl;
import io.quarkcloud.quarkcore.service.Context;

public class ModalImpl<M, T> extends ActionImpl<ResourceMapper<T>, T> {

    // 抽屉弹出层宽度
    public int width;

    // 关闭时销毁弹出层里的子元素
    public boolean destroyOnClose;

    // 初始化
    public ModalImpl() {
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
    public Object getBody(Context context) {
        return null;
    }

    // 弹窗行为
    public List<Object> getActions(Context context) {
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
