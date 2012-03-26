package org.boc.ui.ty;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.boc.dao.DaoCalendar;
import org.boc.dao.DaoPublic;
import org.boc.delegate.DelSiZhuMain;
import org.boc.delegate.DelYiJingMain;
import org.boc.ui.Main;

public class TaiYiFrame
    extends JInternalFrame {
  private Main parent;
  private JTextArea jTextArea;
  private JScrollPane jScrollPane;
  private Container jContentPane;

  private DelSiZhuMain dels;
  private DelYiJingMain dely;
  private DaoPublic pub;
  private DaoCalendar c;
  private String str;

  public TaiYiFrame() {
    pub = new DaoPublic();
    c = new DaoCalendar();
    dels = new DelSiZhuMain();
    dely = new DelYiJingMain();
  }

  public TaiYiFrame(String name, Main sm) {
    super(name, true, true, true);
    parent = sm;
    setBounds(0, 0, 640, 300);

    jContentPane = getContentPane();
    //1. 录入框为3个面板，左中右
    jContentPane.add(getInputPanel(), BorderLayout.NORTH);

    //2. 文本框
    jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
    pub = new DaoPublic();
    c = new DaoCalendar();
    dels = new DelSiZhuMain();
    dely = new DelYiJingMain();
  }

  /**
   * 由三大面板组成录入面板
   * @return
   */
  private JPanel getInputPanel() {
    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
    //inputPanel.add(getUpPanel());
    //inputPanel.add(getCenterPanel());
    //inputPanel.add(getDownPanel());

    return inputPanel;
  }

  /**
   * 得到滚动的文本显示框
   * @return
   */
  private JScrollPane getJScrollPane() {
    jTextArea = new JTextArea();
    jScrollPane = new JScrollPane(jTextArea);
    return jScrollPane;
  }

}
