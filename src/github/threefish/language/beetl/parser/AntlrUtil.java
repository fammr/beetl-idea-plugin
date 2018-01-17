package github.threefish.language.beetl.parser;

import github.threefish.language.project.BeetlVO;
import github.threefish.language.project.ToolCfiguration;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.beetl.core.Transformator;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.parser.BeetlLexer;
import org.beetl.core.parser.BeetlParser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * 创建人：黄川
 * 创建时间: 2018/1/16  18:28
 * 描述此类：
 */
public class AntlrUtil {


    public static void parser(String text) throws BeetlException, IOException {
        BeetlVO beetlVO = ToolCfiguration.getInstance().getData();
        IdeaBeetlAntlrErrorStrategy antlrErrorStrategy = new IdeaBeetlAntlrErrorStrategy();
        IdeaBeetlSyntaxErrorListener syntaxError = new IdeaBeetlSyntaxErrorListener();
        Reader reader = new StringReader(text);
        Transformator sf = new Transformator(
                beetlVO.getPlaceholderStart(),
                beetlVO.getPlaceholderEnd(),
                beetlVO.getStartStatement(),
                beetlVO.getEndStatement()
        );

        sf.enableHtmlTagSupport(beetlVO.getHtmlTagStart(), beetlVO.getHtmlTagEnd(), beetlVO.getHtmlTagBindingAttribute());
        Reader scriptReader = sf.transform(reader);
        ANTLRInputStream input = new ANTLRInputStream(scriptReader);
        BeetlLexer lexer = new BeetlLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(syntaxError);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BeetlParser parser = new BeetlParser(tokens);
        //测试代码
        parser.setErrorHandler(antlrErrorStrategy);
        parser.prog();
    }
}
