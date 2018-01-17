package github.threefish.language.beetl.parser;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.misc.BeetlUtil;
import org.beetl.core.statement.GrammarToken;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * 创建人：黄川
 * 创建时间: 2018/1/16  17:10
 * 描述此类：
 */
public class IdeaBeetlSyntaxErrorListener extends BaseErrorListener
{
    public void IdeaBeetlSyntaxErrorListener(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
    {
        BeetlException be = new BeetlException(BeetlException.TOKEN_ERROR);
        be.token = new GrammarToken(BeetlUtil.reportChineseTokenError(msg), line, charPositionInLine);
        throw be;
    }
}

