package io.quarkcloud.quarkstarter.service.admin.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.entity.ActionLogEntity;
import io.quarkcloud.quarkadmin.mapper.ActionLogMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkstarter.service.admin.action.Delete;
import io.quarkcloud.quarkstarter.service.admin.search.Input;
import io.quarkcloud.quarkstarter.service.admin.search.Status;

@Component(value = "actionLogResource")
public class ActionLog extends ResourceImpl<ActionLogMapper, ActionLogEntity> {

    // 构造函数
    public ActionLog() {
        this.entity = new ActionLogEntity();
        this.title = "操作日志";
        this.perPage = 10;
    }

    // 字段
    public List<Object> fields(Context context) {
        return Arrays.asList(
            Field.id("id", "ID"),
            Field.text("username", "用户"),
            Field.text("url", "行为"),
            Field.radio("ip", "IP"),
            Field.datetime("createdAt", "发生时间")
        );
    }

    // 搜索表单
    public List<Object> searches(Context context) {
        return Arrays.asList(
            new Input<ActionLogEntity>("url", "行为"),
            new Input<ActionLogEntity>("ip", "IP"),
            new Status<ActionLogEntity>()
        );
    }
    
    // 行为
    public List<Object> actions(Context context) {
        return Arrays.asList(
            new Delete<ActionLogMapper, ActionLogEntity>()
        );
    }
}
