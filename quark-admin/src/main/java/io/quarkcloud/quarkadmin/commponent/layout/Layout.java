package io.quarkcloud.quarkadmin.commponent.layout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.quarkcloud.quarkadmin.commponent.Commponent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Layout extends Commponent {

    // 是否缓存layout
	boolean cache;

    // layout 的左上角 的 title
	String title;

    // layout 的左上角 logo
	String logo;

    // layout 的加载态
	boolean loading;

    // layout 的内容区 style
	Map<String, Object> contentStyle;

    // layout 的头部行为
	Object actions;

    // layout 的布局模式，side：右侧导航，top：顶部导航，mix：混合模式
	String layout;

    // 自动分割菜单
	boolean splitMenus;

    // layout 的内容模式,Fluid：定宽 1200px，Fixed：自适应
	String contentWidth;

    // 容器控件里面的内容
	String primaryColor;

    // 是否固定 header 到顶部
	boolean fixedHeader;

    // 是否固定导航
	boolean fixSiderbar;

    // 使用 IconFont 的图标配置
	String iconfontUrl;

    // 当前 layout 的语言设置，'zh-CN' | 'zh-TW' | 'en-US'
	String locale;

    // 侧边菜单宽度
	int siderWidth;

    // 菜单
	Object menu;

    // 右上角菜单
	List<Object> rightMenus;

    // 页脚
	Object footer;

    // 内容
	Object body;

    public Layout() {
        this.component = "layout";
        this.setComponentKey();
        this.style = new HashMap<>();
    }
}
