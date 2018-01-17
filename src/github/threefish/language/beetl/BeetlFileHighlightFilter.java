package github.threefish.language.beetl;

import com.intellij.openapi.util.Condition;
import com.intellij.openapi.vfs.VirtualFile;
import github.threefish.language.beetl.parser.AntlrUtil;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * 创建人：黄川
 * 创建时间: 2018/1/16  14:34
 * 描述此类：
 */
public class BeetlFileHighlightFilter implements Condition {
    @Override
    public boolean value(Object o) {
        if (o instanceof VirtualFile) {
            try {
                VirtualFile virtualFile = (VirtualFile) o;
                List<String> lines = Files.readAllLines(Paths.get(virtualFile.getCanonicalPath()));
                StringBuilder sb = new StringBuilder();
                lines.forEach(s -> sb.append(s));
                AntlrUtil.parser(sb.toString());
                return false;
            } catch (Exception e) {
                return true;
            }
        } else {
            return false;
        }
    }
}
