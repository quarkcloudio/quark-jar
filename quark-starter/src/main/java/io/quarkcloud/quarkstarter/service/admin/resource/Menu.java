package io.quarkcloud.quarkstarter.service.admin.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.entity.MenuEntity;
import io.quarkcloud.quarkadmin.mapper.MenuMapper;
import io.quarkcloud.quarkadmin.template.resource.impl.ResourceImpl;
import io.quarkcloud.quarkcore.service.Context;
import io.quarkcloud.quarkcore.util.Lister;
import io.quarkcloud.quarkstarter.service.admin.action.Create;
import io.quarkcloud.quarkstarter.service.admin.action.Delete;
import io.quarkcloud.quarkstarter.service.admin.action.Edit;
import io.quarkcloud.quarkstarter.service.admin.action.FormBack;
import io.quarkcloud.quarkstarter.service.admin.action.FormExtraBack;
import io.quarkcloud.quarkstarter.service.admin.action.FormReset;
import io.quarkcloud.quarkstarter.service.admin.action.FormSubmit;
import io.quarkcloud.quarkstarter.service.admin.search.Input;
import io.quarkcloud.quarkstarter.service.admin.search.Status;

@Component(value = "menuResource")
public class Menu extends ResourceImpl<MenuMapper, MenuEntity> {

    // 构造函数
    public Menu() {
        this.entity = new MenuEntity();
        this.title = "菜单";
        this.perPage = false;
        this.queryOrder = Map.of("sort", "asc");
    }

    public Object beforeIndexShowing(Context context, List<MenuEntity> list) {
        String search = context.getParameter("search");
        if (search!=null && !search.isEmpty() && !search.equals("{}")) {
            // 返回原始列表
            return list;
        }

        // 转换成树形结构
        return Lister.listToTree(list, "id", "pid", "children", 0L);
    }

    // 字段
    public List<Object> fields(Context context) {
        return Arrays.asList(
            Field.hidden("id", "ID"),
            Field.hidden("pid", "PID").onlyOnIndex(),
            Field.text("name", "名称"),
            Field.switchField("status", "状态").
                setTrueValue("正常").
                setFalseValue("禁用").
                setEditable(true).
                setDefaultValue(true)
        );
    }

    // 搜索表单
    public List<Object> searches(Context context) {
        return Arrays.asList(
            new Input<MenuEntity>("name", "名称"),
            new Input<MenuEntity>("path", "路由"),
            new Status<MenuEntity>()
        );
    }
    
    // 行为
    public List<Object> actions(Context context) {
        return Arrays.asList(
            new Create<MenuMapper, MenuEntity>(this.getTitle()),
            new Edit<MenuMapper, MenuEntity>(),
            new Delete<MenuMapper, MenuEntity>(),
            new FormExtraBack<MenuMapper, MenuEntity>(),
            new FormSubmit<MenuMapper, MenuEntity>(),
            new FormReset<MenuMapper, MenuEntity>(),
            new FormBack<MenuMapper, MenuEntity>()
        );
    }
}
