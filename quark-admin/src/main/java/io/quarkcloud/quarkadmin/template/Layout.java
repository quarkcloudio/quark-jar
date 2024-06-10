package io.quarkcloud.quarkadmin.template;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.node.ArrayNode;

import cn.hutool.jwt.JWT;
import io.quarkcloud.quarkadmin.annotation.AdminLayout;
import io.quarkcloud.quarkadmin.component.action.Action;
import io.quarkcloud.quarkadmin.component.footer.Footer;
import io.quarkcloud.quarkadmin.service.AdminService;
import io.quarkcloud.quarkcore.service.Context;

public class Layout {

    @Autowired
    AdminService adminService;

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

    // 右上角菜单
    public List<Object> rightMenus;

    // 构造函数
    public Layout() {

        // 获取注解对象
        if (getClass().isAnnotationPresent(AdminLayout.class)) {
            annotationClass = getClass().getAnnotation(AdminLayout.class);
        }

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
        rightMenus = Arrays.asList(
            new Action().
                setLabel("个人设置").
                setActionType("link").
                setType("link", false).
                setIcon("setting").
                setStyle(Map.of("color","rgb(0 0 0 / 88%)")).
                setHref("#/layout/index?api=/api/admin/account/setting/form").
                setSize("small"),

            new Action().
                setLabel("退出登录").
                setActionType("ajax").
                setType("link", false).
                setIcon("logout").
                setStyle(Map.of("color","rgb(0 0 0 / 88%)")).
                setApi("/api/admin/logout/index/handle").
                setSize("small")
        );
    }

    // 获取标题
    public String getTitle() {

        // 检查是否存在注解
        if (annotationClass == null) {
            return title;
        }

        // 注解值为空返回默认值
        if (annotationClass.title().isEmpty()) {
            return title;
        }

        // 获取注解值
        return annotationClass.title();
    }

    // 获取Logo
    public Object getLogo() {

        // 检查是否存在注解
        if (annotationClass == null) {
            return logo;
        }

        // 注解值为空返回默认值
        if (annotationClass.logo().isEmpty()) {
            return logo;
        }

        // 获取注解值
        return annotationClass.logo();
    }

    // 获取layout的头部行为
    public Object getActions() {
        return actions;
    }

    /**
     * 获取当前布局模式。
     * 
     * @return 当前布局的字符串表示。布局模式可能包括但不限于：side（侧边导航）、top（顶部导航）、mix（混合模式）。
     */
    public String getLayout() {

        // 检查是否存在注解
        if (annotationClass == null) {
            return layout;
        }

        // 注解值为空返回默认值
        if (annotationClass.layout().isEmpty()) {
            return layout;
        }

        // 获取注解值
        return annotationClass.layout();
    }

    /**
     * 获取是否启用拆分菜单的设置
     * 
     * @return 如果返回true，则表示启用拆分菜单；如果返回false，则表示不启用拆分菜单。
     */
    public boolean isSplitMenus() {
        
        // 检查是否存在注解
        if (annotationClass == null) {
            return splitMenus;
        }

        // 获取注解值
        return annotationClass.splitMenus();
    }

    /**
     * layout 的内容模式
     * 
     * @return layout 的内容模式,Fluid：定宽 1200px，Fixed：自适应
     */
    public String getContentWidth() {

        // 检查是否存在注解
        if (annotationClass == null) {
            return contentWidth;
        }

        // 注解值为空返回默认值
        if (annotationClass.contentWidth().isEmpty()) {
            return contentWidth;
        }

        return contentWidth;
    }

    /**
     * layout 的内容模式
     * 
     * @return layout 的内容模式,Fluid：定宽 1200px，Fixed：自适应
     */
    public String getPrimaryColor() {

        // 检查是否存在注解
        if (annotationClass == null) {
            return primaryColor;
        }

        // 注解值为空返回默认值
        if (annotationClass.primaryColor().isEmpty()) {
            return primaryColor;
        }

        return primaryColor;
    }

    /**
     * 获取是否固定导航
     * 
     * @return 是否固定导航
     */
    public boolean isFixedHeader() {
        
        // 检查是否存在注解
        if (annotationClass == null) {
            return fixedHeader;
        }

        // 获取注解值
        return annotationClass.fixedHeader();
    }

    /**
     * 获取是否固定 header 到顶部
     * 
     * @return 是否固定 header 到顶部
     */
    public boolean isFixSiderbar() {
        
        // 检查是否存在注解
        if (annotationClass == null) {
            return fixSiderbar;
        }

        // 获取注解值
        return annotationClass.fixSiderbar();
    }

    /**
     * 获取使用 IconFont 的图标配置
     * 
     * @return 使用 IconFont 的图标配置
     */
    public String getIconfontUrl() {
        
        // 检查是否存在注解
        if (annotationClass == null) {
            return iconfontUrl;
        }

        // 注解值为空返回默认值
        if (annotationClass.iconfontUrl().isEmpty()) {
            return iconfontUrl;
        }

        // 获取注解值
        return annotationClass.iconfontUrl();
    }

    /**
     * 获取当前 layout 的语言设置，'zh-CN' | 'zh-TW' | 'en-US'
     * 
     * @return 'zh-CN' | 'zh-TW' | 'en-US'
     */
    public String getLocale() {
        
        // 检查是否存在注解
        if (annotationClass == null) {
            return locale;
        }

        // 注解值为空返回默认值
        if (annotationClass.locale().isEmpty()) {
            return locale;
        }

        // 获取注解值
        return annotationClass.locale();
    }

    /**
     * 获取侧边菜单宽度
     * 
     * @return 侧边菜单宽度
     */
    public int getSiderWidth() {
        
        // 检查是否存在注解
        if (annotationClass == null) {
            return siderWidth;
        }

        // 获取注解值
        return annotationClass.siderWidth();
    }

    /**
     * 获取网站版权 "QuarkJar"
     * 
     * @return "QuarkJar"
     */
    public String getCopyright() {
        
        // 检查是否存在注解
        if (annotationClass == null) {
            return copyright;
        }

        // 注解值为空返回默认值
        if (annotationClass.copyright().isEmpty()) {
            return copyright;
        }

        // 获取注解值
        return annotationClass.copyright();
    }

    /**
     * 获取友情链接
     * 
     * @return 友情链接
     */
    public List<Map<String, Object>> getLinks() {
        return links;
    }

    /**
     * 获取右上角菜单
     * 
     * @return "右上角菜单"
     */
    public List<Object> getRightMenus() {
        return rightMenus;
    }

    /**
     * 获取权限菜单
     * 
     * @return "权限菜单"
     */
    public ArrayNode getMenus(Context context) {
        // 获取当前登录用户Token
        JWT jwt = context.parseToken();

        // 获取当前登录用户ID
        Long adminId = Long.parseLong(jwt.getPayload("id").toString());

        return adminService.getMenuTreeById(adminId);
    }

    // 组件渲染
    public Object render(Context context) {

        // Layout组件
        io.quarkcloud.quarkadmin.component.layout.Layout component = new io.quarkcloud.quarkadmin.component.layout.Layout();

        // 获取标题
        title = this.getTitle();

        // 获取Logo
        logo = this.getLogo();

        // 获取layout的头部行为
        actions = this.getActions();

        // 获取layout 的菜单模式,side：右侧导航，top：顶部导航，mix：混合模式
        layout = this.getLayout();

        // 获取是否启用拆分菜单的设置
        splitMenus = this.isSplitMenus();

        // layout 的内容模式,Fluid：定宽 1200px，Fixed：自适应
        contentWidth = this.getContentWidth();

        // 获取是否固定 header 到顶部
        fixedHeader = this.isFixedHeader();

        // 获取是否固定导航
        fixSiderbar = this.isFixSiderbar();

        // 获取使用 IconFont 的图标配置
        iconfontUrl = this.getIconfontUrl();

        // 获取当前 layout 的语言设置，'zh-CN' | 'zh-TW' | 'en-US'
        locale = this.getLocale();

        // 获取侧边菜单宽度
        siderWidth = this.getSiderWidth();

        // 获取网站版权 "QuarkJar"
        copyright = this.getCopyright();

        // 友情链接
        links = this.getLinks();

        // 获取右上角菜单
        rightMenus = this.getRightMenus();

        // 获取右上角菜单
        ArrayNode menus = this.getMenus(context);

        // 页脚信息
        Footer footer = new Footer().setCopyright(copyright).setLinks(links);

        // 设置组件属性
        component.
            setTitle(title).
            setLogo(logo).
            setActions(actions).
            setLayout(layout).
            setSplitMenus(splitMenus).
            setContentWidth(contentWidth).
            setFixedHeader(fixedHeader).
            setFixSiderbar(fixSiderbar).
            setIconfontUrl(iconfontUrl).
            setLocale(locale).
            setSiderWidth(siderWidth).
            setRightMenus(rightMenus).
            setMenu(menus).
            setFooter(footer);

        return component;
    }
}
