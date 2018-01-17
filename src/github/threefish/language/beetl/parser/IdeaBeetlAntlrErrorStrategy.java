package github.threefish.language.beetl.parser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.IntervalSet;
import org.antlr.v4.runtime.misc.NotNull;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.exception.BeetlParserException;
import org.beetl.core.statement.GrammarToken;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * antlr 语法解析错的处理策略。对所有语法错误，都抛出以阻止继续解析
 *
 * @author joelli
 */
public class IdeaBeetlAntlrErrorStrategy extends DefaultErrorStrategy {

    static HashSet<String> keys = new HashSet<String>();
    static Map<String, String> expects = new HashMap<String, String>();

    static {
        expects.put("LEFT_PAR", "(");
        expects.put("RIGHT_PAR", ")");

    }

    static {
        keys.add("select");
        keys.add("if");
        keys.add("for");
        keys.add("elsefor");
        keys.add("while");
        keys.add("switch");
        keys.add("return");
        keys.add("break");
        keys.add("var");
        keys.add("continue");
        keys.add("directive");
        keys.add("in");
        keys.add("case");
        keys.add("default");
        keys.add("try");
        keys.add("catch");

    }

    @Override
    public void recover(Parser recognizer, RecognitionException e) {
        //不会执行到此处，因为在report部分就抛出异常了
        super.recover(recognizer, e);
    }

    @Override
    public void reportError(Parser recognizer, RecognitionException e) {
        if (inErrorRecoveryMode(recognizer)) {
            return;
        }
        beginErrorCondition(recognizer);
        if (e instanceof NoViableAltException) {
            reportNoViableAlternative(recognizer, (NoViableAltException) e);
        } else if (e instanceof InputMismatchException) {
            reportInputMismatch(recognizer, (InputMismatchException) e);
        } else if (e instanceof FailedPredicateException) {
            reportFailedPredicate(recognizer, (FailedPredicateException) e);
        } else {
            BeetlException exception = new BeetlException(BeetlException.PARSER_UNKNOW_ERROR, e.getClass().getName(), e);
            exception.pushToken(this.getGrammarToken(e.getOffendingToken()));

            throw exception;
        }
    }

    @Override
    protected void reportNoViableAlternative(@NotNull Parser recognizer, @NotNull NoViableAltException e) {
        TokenStream tokens = recognizer.getInputStream();
        String input;
        if (tokens instanceof TokenStream) {
            if (e.getStartToken().getType() == Token.EOF) {
                input = "<文件尾>";
            } else {
                input = tokens.getText(e.getStartToken(), e.getOffendingToken());
            }
        } else {
            input = "<未知输入>";
        }
        BeetlException exception = null;
        if (keys.contains(e.getOffendingToken().getText())) {
            exception = new BeetlParserException(BeetlException.PARSER_VIABLE_ERROR,
                    "不允许" + e.getOffendingToken().getText() + "关键出现在这里" + ":" + escapeWSAndQuote(input), e);
        } else {
            exception = new BeetlParserException(BeetlException.PARSER_VIABLE_ERROR,
                    escapeWSAndQuote(input), e);
        }
        exception.pushToken(this.getGrammarToken(e.getOffendingToken()));
        throw exception;
    }

    @Override
    protected void reportInputMismatch(@NotNull Parser recognizer, @NotNull InputMismatchException e) {
        Token t1 = recognizer.getInputStream().LT(-1);
        String msg = "缺少输入在 " + getTokenErrorDisplay(t1) + " 后面， 期望 "
                + e.getExpectedTokens().toString(recognizer.getTokenNames());
        BeetlException exception = new BeetlParserException(BeetlException.PARSER_MISS_ERROR, msg, e);
        exception.pushToken(this.getGrammarToken(t1));

        throw exception;

    }

    @Override
    protected void reportFailedPredicate(@NotNull Parser recognizer, @NotNull FailedPredicateException e) {
        String ruleName = recognizer.getRuleNames()[recognizer.getContext().getRuleIndex()];
        BeetlException exception = new BeetlParserException(BeetlException.PARSER_PREDICATE_ERROR, ruleName, e);
        exception.pushToken(this.getGrammarToken(e.getOffendingToken()));
        throw exception;
    }

    /**
     * Make sure we don't attempt to recover from problems in subrules.
     */
    @Override
    public void sync(Parser recognizer) {
    }

    /**
     * Make sure we don't attempt to recover inline; if the parser
     * successfully recovers, it won't throw an exception.
     */
    @Override
    public Token recoverInline(Parser recognizer) throws RecognitionException {
        // SINGLE TOKEN DELETION
        Token matchedSymbol = singleTokenDeletion(recognizer);
        if (matchedSymbol != null) {
            // we have deleted the extra token.
            // now, move past ttype token as if all were ok
            recognizer.consume();
            return matchedSymbol;
        }

        // SINGLE TOKEN INSERTION
        if (singleTokenInsertion(recognizer)) {
            return getMissingSymbol(recognizer);
        }

//		IdeaBeetlException exception = new BeetlParserException(IdeaBeetlException.PARSER_MISS_ERROR);
//		exception.pushToken(this.getGrammarToken(recognizer.getCurrentToken()));
//		throw exception;
        throw new InputMismatchException(recognizer);
    }

    @Override
    protected void reportMissingToken(@NotNull Parser recognizer) {
        if (inErrorRecoveryMode(recognizer)) {
            return;
        }

        beginErrorCondition(recognizer);

//		Token t = recognizer.getCurrentToken();
        Token t = recognizer.getTokenStream().LT(-1);
        IntervalSet expecting = getExpectedTokens(recognizer);
        String expect = expecting.toString(recognizer.getTokenNames());
        if (expects.containsKey(expect)) {
            expect = expects.get(expect);
        }
        if (expect.equals("'>>'")) {
            expect = "'模板的占位结束符号'";
        }

        String tokenStr = getTokenErrorDisplay(t);

        String msg = "缺少输入 " + expect + " 在 " + tokenStr + " 后面";

        BeetlException exception = new BeetlParserException(BeetlException.PARSER_MISS_ERROR, msg);
        exception.pushToken(this.getGrammarToken(t));
        throw exception;
    }

    @Override
    protected void reportUnwantedToken(@NotNull Parser recognizer) {
        if (inErrorRecoveryMode(recognizer)) {
            return;
        }

        beginErrorCondition(recognizer);

        Token t = recognizer.getCurrentToken();
        String tokenName = getTokenErrorDisplay(t);
        IntervalSet expecting = getExpectedTokens(recognizer);
        String msg = "多余输入 " + tokenName + " 期望 " + expecting.toString(recognizer.getTokenNames());
        BeetlException exception = new BeetlParserException(BeetlException.PARSER_MISS_ERROR, msg);
        exception.pushToken(this.getGrammarToken(t));
        throw exception;
    }

    protected GrammarToken getGrammarToken(Token token) {
        if (token != null) {
            return new IdeaBeetlGrammarToken(GrammarToken.createToken(token.getText(), token.getLine()), token);
        }
        return null;
    }

}
