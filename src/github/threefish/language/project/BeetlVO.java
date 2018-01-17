package github.threefish.language.project;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * 创建人：黄川
 * 创建时间: 2018/1/16  19:16
 * 描述此类：
 */
public class BeetlVO {

    private String placeholderStart;
    private String placeholderEnd;
    private String startStatement;
    private String endStatement;
    private String htmlTagStart;
    private String htmlTagEnd;
    private String htmlTagBindingAttribute;
    private String btlHtmlTextField;

    public BeetlVO() {
    }

    public BeetlVO(String placeholderStart, String placeholderEnd, String startStatement, String endStatement, String htmlTagStart, String htmlTagEnd, String htmlTagBindingAttribute, String btlHtmlTextField) {
        this.placeholderStart = placeholderStart;
        this.placeholderEnd = placeholderEnd;
        this.startStatement = startStatement;
        this.endStatement = endStatement;
        this.htmlTagStart = htmlTagStart;
        this.htmlTagEnd = htmlTagEnd;
        this.htmlTagBindingAttribute = htmlTagBindingAttribute;
        this.btlHtmlTextField = btlHtmlTextField;
    }

    public String getPlaceholderStart() {
        return placeholderStart;
    }

    public void setPlaceholderStart(String placeholderStart) {
        this.placeholderStart = placeholderStart;
    }

    public String getPlaceholderEnd() {
        return placeholderEnd;
    }

    public void setPlaceholderEnd(String placeholderEnd) {
        this.placeholderEnd = placeholderEnd;
    }

    public String getStartStatement() {
        return startStatement;
    }

    public void setStartStatement(String startStatement) {
        this.startStatement = startStatement;
    }

    public String getEndStatement() {
        return endStatement;
    }

    public void setEndStatement(String endStatement) {
        this.endStatement = endStatement;
    }

    public String getHtmlTagStart() {
        return htmlTagStart;
    }

    public void setHtmlTagStart(String htmlTagStart) {
        this.htmlTagStart = htmlTagStart;
    }

    public String getHtmlTagEnd() {
        return htmlTagEnd;
    }

    public void setHtmlTagEnd(String htmlTagEnd) {
        this.htmlTagEnd = htmlTagEnd;
    }

    public String getHtmlTagBindingAttribute() {
        return htmlTagBindingAttribute;
    }

    public void setHtmlTagBindingAttribute(String htmlTagBindingAttribute) {
        this.htmlTagBindingAttribute = htmlTagBindingAttribute;
    }

    public String getBtlHtmlTextField() {
        return btlHtmlTextField;
    }

    public void setBtlHtmlTextField(String btlHtmlTextField) {
        this.btlHtmlTextField = btlHtmlTextField;
    }
}
