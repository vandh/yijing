package org.boc.ui.xm;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.boc.delegate.DelXmMain;
import org.boc.ui.BasicJPanel;
import org.boc.ui.CommandAction;
import org.boc.util.Public;
import org.boc.util.VO;

public class XingMingFrame
    extends BasicJPanel {
  private DelXmMain xm;

  private JTextField txtName ;     //姓名
  private ButtonGroup xing;     //单或复姓
  private JRadioButton dan;     //单姓
  private JRadioButton fu;      //复姓

  private String xingm ;
  private boolean isDan;

  private String fileId;
  private String rowId;
  private String parentNode;
  private String memo;
  public VO vo;

  public XingMingFrame() {
    this.setLayout(new BorderLayout());
    xm = new DelXmMain();
  }

  public void init(String fileId, String rowId, String parentNode) {
    vo = (VO)Public.getObjectFromFile(fileId, rowId);
    this.fileId = fileId;
    this.rowId = rowId;
    this.parentNode = parentNode;

    //1. 录入框为3个面板，左中右
    this.add(getInputPanel(), BorderLayout.NORTH);
    //2. 文本框
    this.add(getOtherInfoPane(getSaveJButton(), memo),BorderLayout.CENTER);
  }

  public JButton getSaveJButton() {
    CommandAction actionXX = new CommandAction("保存", null, "", ' ', new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String rs = check();
        if(rs!=null) {
          JOptionPane.showMessageDialog(getThis(), rs, "提示信息",
                                        JOptionPane.INFORMATION_MESSAGE);
          return ;
        }
        getInputs();
        vo = new VO();
        vo.setName(xingm);                  //姓名
        vo.setBqt1(isDan);                  //是否是单姓
        vo.setRowId(rowId);
        vo.setFileId(fileId);
        //不取子目录了 一律取预测术的根目录，即取root的值
        vo.setRoot(Public.valueRoot[9]);  //此处是姓名的值
        vo.setParent(parentNode);
        vo.setYcsj(Public.getTimestampValue().toString());
        vo.setMemo(memo);
        Public.writeObject2File(vo);
        JOptionPane.showMessageDialog(getThis(), "保存成功！", "提示信息",
                                      JOptionPane.INFORMATION_MESSAGE);
        clear();
      }
    });
    JButton buttonXX = new JButton(actionXX);
    return buttonXX;
  }

  /**
   * 输出姓名信息
   */
  public String do1() {
    if (vo == null)return "";
    return xm.fx(vo.getName(), vo.isBqt1());
  }

  /**
   * 由一个面板组成录入面板
   * @return
   */
  private JPanel getInputPanel() {
    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
    inputPanel.add(getUpPanel());

    return inputPanel;
  }

  private Box getUpPanel() {
    Box box = new Box(BoxLayout.X_AXIS);

    box.add(new JLabel(" "));
    box.add(new JLabel("请输入姓名："));
    txtName = new JTextField(4);
    if(vo==null || vo.getName()==null)
      txtName.setText(rowId);
    else
      txtName.setText(vo.getName());
    box.add(txtName);
    box.add(new JLabel("    "));

    dan = new JRadioButton("单姓",true);
    dan.setActionCommand("dan");
    fu = new JRadioButton("复姓");
    fu.setActionCommand("fu");
    xing = new ButtonGroup();
    xing.add(dan);
    xing.add(fu);
    box.add(dan);
    box.add(fu);
    box.add(new JLabel("      "));
    return box;
  }

  private void getInputs() {
    memo = getJTextArea().getText();
    xingm = txtName.getText();
    isDan = xing.getSelection().getActionCommand().equals("dan");
  }

  private String check() {
    String xm = txtName.getText();
    if(xm==null || "".equals(xm.trim()) || xm.length()<2)
      return "姓名("+xm+")不能为空，且至少具有姓和名";

    if(xm.length()>4)
      return "阁下姓名("+xm+")太长，本软件智力有限已不能处理，请与它的主人联系";


    String df = xing.getSelection().getActionCommand();
    boolean b = df.equals("fu");
    if(b && xm.length()<3) {
      return "复姓("+xm+")至少要有个单名";
    }else if(!b && xm.length()>3)
      return "单姓("+xm+")名字有点过长哟！";

    return null;
  }

  protected Component getThis() {
    return this;
  }

  private void clear() {
  }
}
