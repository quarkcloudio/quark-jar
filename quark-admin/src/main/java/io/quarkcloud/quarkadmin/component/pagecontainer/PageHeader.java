package io.quarkcloud.quarkadmin.component.pagecontainer;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.quarkcloud.quarkadmin.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class PageHeader extends Component  {

    /**
     * 标题栏旁的头像
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Object avatar;

    /**
     * 自定义 back icon，如果为 false 不渲染 back icon
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Object backIcon;

    /**
     * 面包屑的配置
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Object breadcrumb;

    /**
     * 自定义面包屑区域的内容
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Object breadcrumbRender;

    /**
     * 操作区，位于 title 行的行尾
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Object extra;

    /**
     * PageHeader 的页脚，一般用于渲染 TabBar
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Object footer;

    /**
     * pageHeader 的类型，将会改变背景颜色
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean ghost;

    /**
     * 自定义的二级标题文字
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String subTitle;

    /**
     * title 旁的 tag 列表
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Object tags;

    /**
     * 自定义标题文字
     */
    public String title;

    /**
     * 构造函数
     */
    public PageHeader() {
        this.component = "pageHeader";
        this.setComponentKey();
        this.style = new HashMap<>();
    }
}
