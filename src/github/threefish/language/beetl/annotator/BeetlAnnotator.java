package github.threefish.language.beetl.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.impl.source.html.HtmlFileImpl;
import github.threefish.language.beetl.parser.AntlrUtil;
import github.threefish.language.beetl.parser.IdeaBeetlGrammarToken;
import github.threefish.language.project.BeetlVO;
import github.threefish.language.project.ToolCfiguration;
import github.threefish.language.utils.MessageUtil;
import org.beetl.core.exception.BeetlException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * 创建人：黄川
 * 创建时间: 2018/1/16  10:35
 * 描述此类：
 */
public class BeetlAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        if (psiElement instanceof PsiWhiteSpace) {
            return;
        }
        if (!(psiElement instanceof HtmlFileImpl)) {
            return;
        }
        BeetlVO beetlVO = ToolCfiguration.getInstance().getData();
        List<String> files = Arrays.asList(beetlVO.getBtlHtmlTextField().replace("，", ",").split(","));
        VirtualFile virtualFile = psiElement.getContainingFile().getVirtualFile();
        if (!files.contains(virtualFile.getExtension().toLowerCase())) {
            return;
        }
        PsiFile psiFile = PsiManager.getInstance(psiElement.getProject()).findFile(virtualFile);
        try {
            AntlrUtil.parser(psiFile.getText());
        } catch (BeetlException ex) {
            ErrorInfo info = new ErrorInfo(ex);
            String showMsg = "错误符号:" + info.getErrorTokenText();
            if (info != null) {
                showMsg = showMsg + " 信息:" + info.getMsg();
            }
            if (ex.token instanceof IdeaBeetlGrammarToken) {
                IdeaBeetlGrammarToken token = (IdeaBeetlGrammarToken) ex.token;
                List<String> lines = Arrays.asList(psiFile.getText().split("\n"));
                HashMap<Integer, lineInfo> linesInfo = new HashMap<>(lines.size());
                for (int i = 1; i <= lines.size(); i++) {
                    String lineStr = lines.get(i - 1);
                    lineInfo lineInfo = new lineInfo();
                    lineInfo.setLine(i);
                    lineInfo.setLength(lineStr.length());
                    if (i == 1) {
                        lineInfo.setStart(0);
                        lineInfo.setEnd(lineStr.length());
                    } else {
                        //上一行
                        lineInfo prevLine = linesInfo.get(i - 1);
                        lineInfo.setStart(prevLine.getStart() + prevLine.getLength());
                        lineInfo.setEnd(lineInfo.getStart() + lineStr.length());
                    }
                    linesInfo.put(i, lineInfo);
                }
                linesInfo.forEach((line, lineInfo) -> {
                    lineInfo.setStart(lineInfo.getStart() + line);
                    lineInfo.setEnd(lineInfo.getEnd() + line);
                    linesInfo.put(line, lineInfo);
                });
                lineInfo lineInfo = linesInfo.get(token.line);
                int start = lineInfo.getEnd() - (lineInfo.getLength() - token.getToken().getCharPositionInLine());
                int end = start + token.text.length();
                TextRange textRange = TextRange.create(start, end);
                annotationHolder.createErrorAnnotation(textRange, showMsg);
            } else {
                MessageUtil.showTopic(psiElement.getProject(), info.getType(), showMsg, NotificationType.ERROR);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class lineInfo {
        private int line;
        private int length;
        private int start;
        private int end;

        public int getLine() {
            return line;
        }

        public void setLine(int line) {
            this.line = line;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
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
}
