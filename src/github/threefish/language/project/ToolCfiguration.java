package github.threefish.language.project;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jdom.Element;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * 创建人：黄川
 * 创建时间: 2017/12/11  14:09
 * 描述此类：
 */
@State(name = "BeetlCfiguration", storages = {@Storage("BeetlCfiguration.xml")})
public class ToolCfiguration implements PersistentStateComponent<Element> {

    BeetlVO beetlVO = new BeetlVO();

    @Override
    @Nullable
    public Element getState() {
        Element element = new Element("BeetlCfiguration");
        element.setAttribute("placeholderStart", beetlVO.getPlaceholderStart());
        element.setAttribute("placeholderEnd", beetlVO.getPlaceholderEnd());
        element.setAttribute("startStatement", beetlVO.getStartStatement());
        element.setAttribute("endStatement", beetlVO.getEndStatement());
        element.setAttribute("htmlTagStart", beetlVO.getHtmlTagStart());
        element.setAttribute("htmlTagEnd", beetlVO.getHtmlTagEnd());
        element.setAttribute("htmlTagBindingAttribute", beetlVO.getHtmlTagBindingAttribute());
        element.setAttribute("btlHtmlTextField", beetlVO.getBtlHtmlTextField());
        return element;
    }

    @Override
    public void loadState(Element element) {
        beetlVO.setPlaceholderStart(element.getAttributeValue("placeholderStart"));
        beetlVO.setPlaceholderEnd(element.getAttributeValue("placeholderEnd"));
        beetlVO.setStartStatement(element.getAttributeValue("startStatement"));
        beetlVO.setEndStatement(element.getAttributeValue("endStatement"));
        beetlVO.setHtmlTagStart(element.getAttributeValue("htmlTagStart"));
        beetlVO.setHtmlTagEnd(element.getAttributeValue("htmlTagEnd"));
        beetlVO.setHtmlTagBindingAttribute(element.getAttributeValue("htmlTagBindingAttribute"));
        beetlVO.setBtlHtmlTextField(element.getAttributeValue("btlHtmlTextField"));
    }

    @Nullable
    public static ToolCfiguration getInstance() {
        ToolCfiguration cfiguration = ServiceManager.getService(ToolCfiguration.class);
        BeetlVO beetlVO=new BeetlVO();
        beetlVO.setPlaceholderStart("${");
        beetlVO.setPlaceholderEnd("}");
        beetlVO.setStartStatement("<%");
        beetlVO.setEndStatement("%>");
        beetlVO.setHtmlTagStart("<#");
        beetlVO.setHtmlTagEnd("/#>");
        beetlVO.setHtmlTagBindingAttribute("var");
        beetlVO.setBtlHtmlTextField("btl,html");
        cfiguration.setData(beetlVO);
        return cfiguration;
    }

    public void setData(BeetlVO beetlVO) {
        this.beetlVO = beetlVO;
    }

    public BeetlVO getData() {
        return beetlVO;
    }
}
