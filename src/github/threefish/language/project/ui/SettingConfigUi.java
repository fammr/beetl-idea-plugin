package github.threefish.language.project.ui;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * 创建人：黄川
 * 创建时间: 2017/12/11  14:13
 * 描述此类：
 */
public class SettingConfigUi {

    public JPanel root;
    private JTextField placeholderStart;
    private JTextField placeholderEnd;
    private JTextField startStatement;
    private JTextField endStatement;
    private JTextField htmlTagStart;
    private JTextField htmlTagEnd;
    private JTextField htmlTagBindingAttribute;
    private JTextField btlHtmlTextField;

    public SettingConfigUi() {
    }

    public JPanel getRootPanel() {
        return root;
    }


    public JPanel getRoot() {
        return root;
    }

    public JTextField getPlaceholderStart() {
        return placeholderStart;
    }

    public JTextField getPlaceholderEnd() {
        return placeholderEnd;
    }

    public JTextField getStartStatement() {
        return startStatement;
    }

    public JTextField getEndStatement() {
        return endStatement;
    }

    public JTextField getHtmlTagStart() {
        return htmlTagStart;
    }

    public JTextField getHtmlTagEnd() {
        return htmlTagEnd;
    }

    public JTextField getHtmlTagBindingAttribute() {
        return htmlTagBindingAttribute;
    }

    public JTextField getBtlHtmlTextField() {
        return btlHtmlTextField;
    }
}
