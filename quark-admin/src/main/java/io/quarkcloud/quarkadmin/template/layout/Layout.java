package io.quarkcloud.quarkadmin.template.layout;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.quarkcloud.quarkcore.service.Context;

public interface Layout {

    // 获取标题
    public String getTitle();

    // 获取Logo
    public Object getLogo();

    // 获取layout的头部行为
    public Object getActions();

    /**
     * 获取当前布局模式。
     * 
     * @return 当前布局的字符串表示。布局模式可能包括但不限于：side（侧边导航）、top（顶部导航）、mix（混合模式）。
     */
    public String getLayout();

    /**
     * 获取是否启用拆分菜单的设置
     * 
     * @return 如果返回true，则表示启用拆分菜单；如果返回false，则表示不启用拆分菜单。
     */
    public boolean isSplitMenus();

    /**
     * layout 的内容模式
     * 
     * @return layout 的内容模式,Fluid：定宽 1200px，Fixed：自适应
     */
    public String getContentWidth();

    /**
     * layout 的内容模式
     * 
     * @return layout 的内容模式,Fluid：定宽 1200px，Fixed：自适应
     */
    public String getPrimaryColor();

    /**
     * 获取是否固定导航
     * 
     * @return 是否固定导航
     */
    public boolean isFixedHeader();

    /**
     * 获取是否固定 header 到顶部
     * 
     * @return 是否固定 header 到顶部
     */
    public boolean isFixSiderbar();

    /**
     * 获取使用 IconFont 的图标配置
     * 
     * @return 使用 IconFont 的图标配置
     */
    public String getIconfontUrl();

    /**
     * 获取当前 layout 的语言设置，'zh-CN' | 'zh-TW' | 'en-US'
     * 
     * @return 'zh-CN' | 'zh-TW' | 'en-US'
     */
    public String getLocale();

    /**
     * 获取侧边菜单宽度
     * 
     * @return 侧边菜单宽度
     */
    public int getSiderWidth();

    /**
     * 获取网站版权 "QuarkJar"
     * 
     * @return "QuarkJar"
     */
    public String getCopyright();

    /**
     * 获取友情链接
     * 
     * @return 友情链接
     */
    public List<Map<String, Object>> getLinks();

    /**
     * 获取右上角菜单
     * 
     * @return "右上角菜单"
     */
    public List<Object> getRightMenus();

    /**
     * 获取权限菜单
     * 
     * @return "权限菜单"
     */
    public ArrayNode getMenus(Context context);

    // 组件渲染
    public Object render(Context context);
}
