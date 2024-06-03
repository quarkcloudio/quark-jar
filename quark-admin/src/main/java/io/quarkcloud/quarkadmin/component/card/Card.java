package io.quarkcloud.quarkadmin.component.card;

import java.util.HashMap;

import io.quarkcloud.quarkadmin.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Card extends Component{

    // 标题文字
    public String title;

    // 二级标题文字
    public String subTitle;

    // 标题右侧图标 hover 提示信息
    public String tip;

    // 右上角自定义区域
    public Object extra;
    
    // 内容布局，支持垂直居中 default | center
    public String layout;
    
    // 加载中，支持自定义 loading 样式
    public boolean loading;
    
    // 栅格布局宽度，24 栅格，支持指定宽度 px 或百分比, 支持响应式的对象写法 { xs: 8, sm: 16, md: 24}
    public Object colSpan;
    
    // 栅格布局宽度，24 栅格，支持指定宽度 px 或百分比, 支持响应式的对象写法 { xs: 8, sm: 16, md: 24}
    public Object gutter;
    
    // 拆分卡片的方向,vertical | horizontal
    public String split;
    
    // 是否有边框
    public boolean bordered;

    // 幽灵模式，即是否取消卡片内容区域的 padding 和 卡片的背景颜色
    public boolean ghost;
    
    // 页头是否有分割线
    public boolean headerBordered;
    
    // 页头是否有分割线
    public boolean collapsible;
    
    // 默认折叠, 受控时无效
    public boolean defaultCollapsed;
    
    // 卡牌内容
    public Object body;

    public Card() {
        this.component = "card";
        this.setComponentKey();
        this.style = new HashMap<>();
        this.layout = "default";
        this.colSpan = 24;
        this.gutter = 0;
    }
}
