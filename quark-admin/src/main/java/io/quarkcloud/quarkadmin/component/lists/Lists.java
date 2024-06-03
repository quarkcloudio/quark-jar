package io.quarkcloud.quarkadmin.component.lists;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.quarkcloud.quarkadmin.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Lists extends Component {

    // layout 的左上角 的 title
    public String rowKey;

    // 获取表格数据接口
    public String api;

    // 获取表格数据接口类型
    public String apiType;

    // 表头标题
    public String headerTitle;

    // 批量设置表格列
    public Object metas;

    // 批量操作选择项
    public Object rowSelection;

    // 设置表格滚动
    public boolean striped;

    // 表格数据
    public Object datasource;

    // 表格分页
    public Object pagination;

    // 透传 ProUtils 中的 ListToolBar 配置项
    public Object toolBar;

    public Lists() {
        this.component = "lists";
        this.setComponentKey();
        this.style = new HashMap<>();
    }

    // 表头标题
    public Lists setTitle(String title) {
        this.headerTitle = title;
        return this;
    }

    // 批量设置表格列
    public Lists setMetas(Map<String, Object> metas) {

        List<String> limits = Arrays.asList(
                "type",
                "title",
                "subTitle",
                "description",
                "avatar",
                "actions",
                "content",
                "extra");

        metas.keySet().forEach(key -> {
            if (!(key instanceof String)) {
                throw new IllegalArgumentException("meta index key must be a string!");
            }
            if (!limits.contains(key)) {
                throw new IllegalArgumentException(
                        "meta index key must be in 'type','title','subTitle','description','avatar','actions','content','extra'!");
            }
        });

        this.metas = metas;

        return this;
    }

    // 表格分页
    public Lists setPagination(int current, int pageSize, int total, int defaultCurrent) {

        this.pagination = new HashMap<>() {
            {
                put("current", current);
                put("pageSize", pageSize);
                put("total", total);
                put("defaultCurrent", defaultCurrent);
            }
        };

        return this;
    }
}
