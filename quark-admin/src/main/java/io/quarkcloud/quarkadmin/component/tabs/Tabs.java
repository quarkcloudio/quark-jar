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

    /** 组件类型标识 */
    private String element; 
    
    /** 是否居中显示标签 */
    private boolean centered; 
    
    /** 默认激活的 tab 面板的 key */
    private String defaultActiveKey; 
    
    /** 组件大小 (large, default, small) */
    private String size; 
    
    /** tab bar 上额外的元素 */
    private Object tabBarExtraContent; 
    
    /** tabs 之间的间隙 (以像素为单位) */
    private int tabBarGutter; 
    
    /** tab bar 的样式对象 */
    private Object tabBarStyle; 
    
    /** 页签位置 (top, right, bottom, left) */
    private String tabPosition; 
    
    /** 页签的基本样式 (line, card, editable-card) */
    private String type; 
    
    /** tab 的内容 */
    private Object tabPanes;

    // 初始化
    public Tabs() {
        this.element = "tabs";
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
