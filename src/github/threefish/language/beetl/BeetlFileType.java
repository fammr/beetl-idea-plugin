package github.threefish.language.beetl;

import com.intellij.icons.AllIcons;
import com.intellij.lang.Language;
import com.intellij.lang.html.HTMLLanguage;
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * 创建人：黄川
 * 创建时间: 2018/1/15  23:23
 * 描述此类：
 */
public class BeetlFileType extends LanguageFileType {

    public static final BeetlFileType INSTANCE = new BeetlFileType();

    protected BeetlFileType() {
        super(HTMLLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Beetl";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Beetl Templates";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "btl";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return AllIcons.FileTypes.Html;
    }
}
