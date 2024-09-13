package io.quarkcloud.quarkadmin.component.tabs;

import java.util.Map;

import io.quarkcloud.quarkadmin.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Tabs  extends Component {

    /** 是否居中显示标签 */
    public boolean centered; 
    
    /** 默认激活的 tab 面板的 key */
    public String defaultActiveKey; 
    
    /** 组件大小 (large, default, small) */
    public String size; 
    
    /** tab bar 上额外的元素 */
    public Object tabBarExtraContent; 
    
    /** tabs 之间的间隙 (以像素为单位) */
    public int tabBarGutter; 
    
    /** tab bar 的样式对象 */
    public Object tabBarStyle; 
    
    /** 页签位置 (top, right, bottom, left) */
    public String tabPosition; 
    
    /** 页签的基本样式 (line, card, editable-card) */
    public String type; 
    
    /** tab 的内容 */
    public Object tabPanes;

    public Object body;

    // 初始化
    public Tabs() {
        this.component = "tabs";
        this.size = "default";
        this.tabPosition = "top";
        this.type = "line";
        this.tabBarGutter = 35;
        this.setComponentKey();
    }

    // 设置样式
    public Tabs setStyle(Map<String, Object> style) {
        this.tabBarStyle = style;
        return this;
    }
}
