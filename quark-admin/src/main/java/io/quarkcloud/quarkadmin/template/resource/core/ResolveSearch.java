package io.quarkcloud.quarkadmin.template.resource.core;

import java.util.List;

import io.quarkcloud.quarkadmin.component.form.Field;
import io.quarkcloud.quarkadmin.component.form.fields.Cascader;
import io.quarkcloud.quarkadmin.component.form.fields.Radio;
import io.quarkcloud.quarkadmin.component.form.fields.SelectField;
import io.quarkcloud.quarkadmin.component.form.fields.TreeSelect;
import io.quarkcloud.quarkadmin.component.table.Search;
import io.quarkcloud.quarkcore.service.Context;

public class ResolveSearch<T> {
    
    // search
    public Search search;

    // searches
    public List<Object> searches;

    // 上下文
    public Context context;

    // 是否包含导出功能
    public boolean withExport;

    // 构造函数
    public ResolveSearch(Search search, List<Object> searches, Context context) {
        this.searches = searches;
        this.context = context;
    }

    // 列表行为
    public ResolveSearch<T> setWithExport(boolean withExport) {
        this.withExport = withExport;
        return this;
    }

    // 列表页搜索栏
    @SuppressWarnings("unchecked")
    public Object indexSearches(Context context) {

        // 初始化搜索组件
        Search searchComponent = new Search();
        if (withExport) {
            String resource = context.getPathVariable("resource");
            String[] paths = context.getRequestMapping();
            for (String path : paths) {
                searchComponent.setExportText("导出").setExportApi(path.replace(":resource", resource));
            }
        }
        if (searches == null) {
            return searchComponent;
        }

        // 解析搜索项
        for (Object v : searches) {

            // 搜索项实例
            Object field = new Object();

            io.quarkcloud.quarkadmin.template.resource.Search<T> search = (io.quarkcloud.quarkadmin.template.resource.Search<T>) v;

            // 获取组件名称
            String component = search.getComponent();

            // 获取label标签文本
            String label = search.getName();

            // 获取字段名，支持数组
            String name = search.getColumn(v);

            // 获取接口
            String api = search.getApi();

            // 获取属性
            Object options = search.options(context);

            // 根据组件类型构建组件
            switch (component) {
                case "textField":
                    field = Field.
                        text(name, label).
                        setWidth(null);
                    break;
                case "selectField":
                    field = Field.
                        select(name, label).
                        setWidth(null).
                        setOptions((List<SelectField.Option>) options);
                    break;
                case "radioField":
                    field = Field.
                        radio(name, label).
                        setOptions((List<Radio.Option>) options).
                        setOptionType("button").
                        setButtonStyle("solid");
                    break;
                case "multipleSelectField":
                    field = Field.
                        select(name, label).
                        setMode("multiple").
                        setWidth(null).
                        setOptions((List<SelectField.Option>) options);
                    break;
                case "dateField":
                    field = Field.
                        date(name, label).
                        setWidth(null);
                    break;
                case "datetimeField":
                    field = Field.
                        datetime(name, label).
                        setWidth(null);
                    break;
                case "dateRangeField":
                    field = Field.
                        dateRange(name, label).
                        setWidth(null);
                    break;
                case "datetimeRangeField":
                    field = Field.
                        datetimeRange(name, label).
                        setWidth(null);
                    break;
                case "cascaderField":
                    field = Field.
                        cascader(name, label).
                        setApi(api).
                        setWidth(null).
                        setOptions((List<Cascader.Option>) options);
                    break;
                case "treeSelectField":
                    field = Field.
                        treeSelect(name, label).
                        setWidth(null).
                        setTreeData((List<TreeSelect.TreeData>) options);
                    break;
            }
            if (field != null) {
                searchComponent.setItems(field);
            }
        }

        return searchComponent;
    }
}
