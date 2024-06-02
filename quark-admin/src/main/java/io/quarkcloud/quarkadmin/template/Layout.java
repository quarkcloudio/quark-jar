package io.quarkcloud.quarkadmin.template;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.quarkcloud.quarkadmin.annotation.AdminLayout;
import io.quarkcloud.quarkcore.service.Context;

public class Layout {

    // 注解实例
    protected AdminLayout annotationClass = null;

    // layout 的左上角 的 title
    public String title;

    // layout 的左上角 的 logo
    public Object logo;

    // layout 的头部行为
    public Object actions;

    // layout 的菜单模式,side：右侧导航，top：顶部导航，mix：混合模式
    public String layout;

    // layout 的菜单模式为mix时，是否自动分割菜单
    public boolean splitMenus;

    // layout 的内容模式,Fluid：定宽 1200px，Fixed：自适应
    public String contentWidth;

    // 主题色,"#1890ff"
    public String primaryColor;

    // 是否固定 header 到顶部
    public boolean fixedHeader;
    
    // 是否固定导航
    public boolean fixSiderbar;

    // 使用 IconFont 的图标配置
    public String iconfontUrl;

    // 当前 layout 的语言设置，'zh-CN' | 'zh-TW' | 'en-US'
    public String locale;

    // 侧边菜单宽度
    public int siderWidth;

    // 网站版权 time.Now().Format("2006") + " QuarkJar"
    public String copyright;

    // 友情链接
    public List<Map<String, Object>> links;

    // 友情链接
    public List<Object> rightMenus;

    // 构造函数
    public Layout() {

        // layout 的左上角 的 title
        title = "QuarkJar";

        // layout 的左上角 的 logo
        logo = false;

        // layout 的头部行为
        actions = null;

        // layout 的菜单模式,side：右侧导航，top：顶部导航，mix：混合模式
        layout = "mix";

        // layout 的菜单模式为mix时，是否自动分割菜单
        splitMenus = false;

	    // layout 的内容模式,Fluid：定宽 1200px，Fixed：自适应
        contentWidth = "Fluid";

        // 主题色,"#1890ff"
        primaryColor = "#1890ff";

        // 是否固定 header 到顶部
        fixedHeader = true;

        // 是否固定导航
        fixSiderbar = true;

        // 使用 IconFont 的图标配置
        iconfontUrl = "//at.alicdn.com/t/font_1615691_3pgkh5uyob.js";

        // 当前 layout 的语言设置，'zh-CN' | 'zh-TW' | 'en-US'
        locale = "zh-CN";

        // 侧边菜单宽度
        siderWidth = 208;

        // 网站版权 time.Now().Format("2006") + " QuarkJar"
        copyright = LocalDate.now().getYear() + " QuarkJar";

        // 友情链接
        links = Arrays.asList(
            Map.of("key","1","title", "Quark", "href", "https://quarkcloud.io/"),
            Map.of("key","2","title", "QuarkJar", "href", "https://github.com/quarkcloudio/quark-jar"),
            Map.of("key","3","title", "Github", "href", "https://github.com/quarkcloudio")
        );

        // 右上角菜单
        rightMenus = null;
    }

    // 组件渲染
    public Object render(Context context) {

        return null;
    }
}
