package io.quarkcloud.quarkadmin.commponent.form.fields;

import java.util.HashMap;
import java.util.Map;

import io.quarkcloud.quarkadmin.commponent.Commponent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Text extends Commponent {

    // 开启 grid 模式时传递给 Row, 仅在ProFormGroup, ProFormList, ProFormFieldSet 中有效，默认：{ gutter: 8 }
	Map<String, ?> rowProps;

    // 开启 grid 模式时传递给 Col，默认：{ xs: 24 }
	Map<String, ?> colProps;

    // 是否是次要控件，只针对 LightFilter 下有效
	boolean secondary; 

    // 配合 label 属性使用，表示是否显示 label 后面的冒号
	boolean colon;

    // 额外的提示信息，和 help 类似，当需要错误信息和提示文案同时出现时，可以使用这个。
	String extra;

    // 配合 valiTextStatus 属性使用，展示校验状态图标，建议只配合 Input 组件使用
	boolean hasFeedback;

    // 提示信息，如不设置，则会根据校验规则自动生成
	String help;

    // 是否隐藏字段（依然会收集和校验字段）
	boolean hidden;

    // 设置子元素默认值，如果与 Form 的 initialValues 冲突则以 Form 为准
	Object initialValue;

    // label 标签的文本
	String label;

    // 标签文本对齐方式
	String labelAlign;

    // label 标签布局，同 <Col> 组件，设置 span offset 值，如 {span: 3, offset: 12} 或 sm: {span: 3, offset: 12}。你可以通过 Form 的 labelCol 进行统一设置，不会作用于嵌套 Item。当和 Form 同时设置时，以 Item 为准
	Object labelCol;

    // 字段名，支持数组
	String name;

    // 为 true 时不带样式，作为纯字段控件使用
	boolean noStyle;

    // 必填样式设置。如不设置，则会根据校验规则自动生成
	boolean required;

    // 会在 label 旁增加一个 icon，悬浮后展示配置的信息
	String tooltip;

    // 子节点的值的属性，如 Switch 的是 'checked'。该属性为 getValueProps 的封装，自定义 getValueProps 后会失效
	String valuePropName;

    // 需要为输入控件设置布局样式时，使用该属性，用法同 labelCol。你可以通过 Form 的 wrapperCol 进行统一设置，不会作用于嵌套 Item。当和 Form 同时设置时，以 Item 为准
	Object wrapperCol;

    // 列表页、详情页中列属性
	Object column;

    // 设置列的对齐方式,left | right | center，只在列表页、详情页中有效
	String align;

    // （IE 下无效）列是否固定，可选 true (等效于 left) left rightr，只在列表页中有效
	Object fixed;

    // 表格列是否可编辑，只在列表页中有效
	boolean editable;

    // 是否自动缩略，只在列表页、详情页中有效
	boolean ellipsis;

    // 是否支持复制，只在列表页、详情页中有效
	boolean copyable;

    // 表头的筛选菜单项，当值为 true 时，自动使用 valueEnum 生成，只在列表页中有效
	Object filters;

    // 查询表单中的权重，权重大排序靠前，只在列表页中有效
	int order;

    // 可排序列，只在列表页中有效
	Object sorter;

    // 包含列的数量，只在详情页中有效
	int span;

    // 设置列宽，只在列表页中有效
	int columnWidth;

    // 获取数据接口
	String api;

    // 是否忽略保存到数据库，默认为 false
	boolean ignore;

    // 全局校验规则
	Object rules;

    // 创建页校验规则
	Object creationRules;

    // 编辑页校验规则
	Object updateRules;

    // 前端校验规则，设置字段的校验逻辑
	Object frontendRules;

    // When组件
	Object when;

    // When组件里的字段
	Object[] whenItem;

    // 在列表页展示
	boolean showOnIndex;
    
    // 在详情页展示
	boolean showOnDetail;

    // 在创建页面展示
	boolean showOnCreation;

    // 在编辑页面展示
	boolean showOnUpdate;

    // 在导出的Excel上展示
	boolean showOnExport;

    // 在导入Excel上展示
	boolean showOnImport;

    // 回调函数
    @FunctionalInterface
	interface Closure {
        Object callback();
    }

    // 带标签的 input，设置后置标签
	Object addonAfter;

    // 带标签的 input，设置前置标签
	Object addonBefore;

    // 可以点击清除图标删除内容
	boolean allowClear;

    // 是否有边框，默认true
	boolean bordered;

    // 默认的选中项
	Object defaultValue;

    // 禁用
	Object disabled;

    // 输入框的 id
	String id;

    // 最大长度
	int maxLength;

    // 是否展示字数
	boolean showCount;

    // 设置校验状态,'error' | 'warning'
	String status;
    
    // 带有前缀图标的 input
	Object prefix;

    // 控件大小。注：标准表单内的输入框大小限制为 middle，large | middle | small
	String size;

    // 带有后缀图标的 input
	Object suffix;

    // 声明 input 类型，同原生 input 标签的 type 属性，见：MDN(请直接使用 Input.TextArea 代替 type="textarea")
	String type;

    // 指定选中项,string[] | number[]
	Object value;

    // 占位符
	String placeholder;

    // 自定义样式
	Map<String, ?> style = new HashMap<>();

    public Text() {
        this.component = "textField";
        this.setComponentKey();
    }

    // Field 的长度，我们归纳了常用的 Field 长度以及适合的场景，支持了一些枚举 "xs" , "s" , "m" , "l" , "x"
    public Text setWidth(Object width) {
        Map<String, Object> style = new HashMap<>();

        this.style.forEach((key, value) -> {
            style.put(key, value);
        });
        style.put("width", width);
        this.style = style;

        return this;
    }
}
