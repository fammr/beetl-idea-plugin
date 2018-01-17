package github.threefish.language.beetl.parser;

import groovyjarjarantlr.CommonToken;
import org.antlr.v4.runtime.Token;
import org.beetl.core.statement.GrammarToken;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * 创建人：黄川
 * 创建时间: 2018/1/16  13:11
 * 描述此类：
 */
public class IdeaBeetlGrammarToken extends GrammarToken {

    private int start = 0;

    private int end = 0;

    private Token token;


    public IdeaBeetlGrammarToken(String text, int line, int col) {
        super(text, line, col);
    }

    public IdeaBeetlGrammarToken(GrammarToken token, int start, int end) {
        super(token.text, token.line, token.col);
        this.start = start;
        this.end = end;
    }
    public IdeaBeetlGrammarToken(GrammarToken grammarToken, Token token) {
        super(grammarToken.text, grammarToken.line, grammarToken.col);
        this.token = token;
        this.start = token.getStartIndex();
        this.end = token.getStopIndex();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
