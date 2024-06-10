package io.quarkcloud.quarkadmin.component.pagecontainer;

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
public class PageContainer extends Component {

    // 内容区
    public Object content;

    // 额外内容区，位于 content 的右侧
    public Object extraContent;

    // tab 标题列表
    public List<Map<String, String>> tabList;

    // 当前高亮的 tab 项
    public String tabActiveKey;

    // tab bar 上额外的元素
    public Object tabBarExtraContent;

    // PageHeader 的所有属性
    public Object header;

    // 配置头部区域的背景颜色为透明
    public boolean ghost;

    // 固定 pageHeader 的内容到顶部，如果页面内容较少，最好不要使用，会有严重的遮挡问题
    public boolean fixedHeader;

    // 固钉的配置，与 antd 完全相同
    public Object affixProps;

    // 悬浮在底部的操作栏，传入一个数组，会自动加空格
    public Object footer;

    // 容器控件里面的内容
    public Object body;

    // 配置水印，Layout 会透传给 PageContainer，但是以 PageContainer 的配置优先
    public Object waterMarkProps;

    // tab 的配置
    public Object tabProps;

    // 构造函数
    public PageContainer() {
        this.component = "pageContainer";
        this.setComponentKey("pageContainer");
        this.style = new HashMap<>();
    }
}
