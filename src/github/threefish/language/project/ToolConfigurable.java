package github.threefish.language.project;

import com.google.gson.Gson;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import github.threefish.language.project.ui.SettingConfigUi;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * 创建人：黄川
 * 创建时间: 2017/12/11  14:07
 * 描述此类：
 */
public class ToolConfigurable implements Configurable {

    private ToolCfiguration configuration = ToolCfiguration.getInstance();

    private SettingConfigUi ui;

    @Nls
    @Override
    public String getDisplayName() {
        return "Beetl-Plugin";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (null == this.ui) {
            this.ui = new SettingConfigUi();
        }
        return this.ui.getRootPanel();
    }

    @Override
    public boolean isModified() {
        String oldData = new Gson().toJson(configuration.getData());
        String newData = new Gson().toJson(getBeetlVO());
        return !oldData.equals(newData);
    }

    @Override
    public void apply() throws ConfigurationException {
        configuration.setData(getBeetlVO());
    }

    @Override
    public void reset() {
        BeetlVO beetlVO = configuration.getData();
        ui.getPlaceholderStart().setText(beetlVO.getPlaceholderStart());
        ui.getPlaceholderEnd().setText(beetlVO.getPlaceholderEnd());
        ui.getStartStatement().setText(beetlVO.getStartStatement());
        ui.getEndStatement().setText(beetlVO.getEndStatement());
        ui.getHtmlTagStart().setText(beetlVO.getHtmlTagStart());
        ui.getHtmlTagEnd().setText(beetlVO.getHtmlTagEnd());
        ui.getHtmlTagBindingAttribute().setText(beetlVO.getHtmlTagBindingAttribute());
        ui.getBtlHtmlTextField().setText(beetlVO.getBtlHtmlTextField());
    }

    private BeetlVO getBeetlVO() {
        BeetlVO beetlVO = new BeetlVO();
        beetlVO.setPlaceholderStart(ui.getPlaceholderStart().getText());
        beetlVO.setPlaceholderEnd(ui.getPlaceholderEnd().getText());
        beetlVO.setStartStatement(ui.getStartStatement().getText());
        beetlVO.setEndStatement(ui.getEndStatement().getText());
        beetlVO.setHtmlTagStart(ui.getHtmlTagStart().getText());
        beetlVO.setHtmlTagEnd(ui.getHtmlTagEnd().getText());
        beetlVO.setHtmlTagBindingAttribute(ui.getHtmlTagBindingAttribute().getText());
        beetlVO.setBtlHtmlTextField(ui.getBtlHtmlTextField().getText());
        return beetlVO;
    }

}
