package org.boc.ui.bz;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.boc.db.BaZhai;
import org.boc.db.Calendar;
import org.boc.delegate.DelFs_BaZhai;
import org.boc.ui.BasicJPanel;
import org.boc.ui.CommandAction;
import org.boc.util.Public;
import org.boc.util.VoBaZhai;

public class BaZhaiFrame
    extends BasicJPanel {
  private DelFs_BaZhai del;
  private int yearBoy; //男命
  private int yearGirl; //女命
  private JButton butYxp; //游星盘
  private JButton butFx; //分析

  private JComboBox comboWx; //屋向
  private JTextField textBoy; //男命
  private JTextField textGirl; //女命
  private JComboBox comboDmw; //大门位
  private JComboBox comboDmx; //大门向
  private JComboBox comboWf; //房位
  private JComboBox comboFmw; //房门位
  private JComboBox comboCw; //床位
  private JComboBox comboGxw; //盥洗位
  private JComboBox comboJzw; //旧宅位
  private JComboBox comboCfw; //厨房位
  private JComboBox comboZw; //灶位
  private JComboBox comboZx; //灶向
  private JComboBox comboFz; //房主
  private JList jList;

  private int iWx; //屋向
  private int iDmw; //大门位
  private int iDmx; //大门向
  private int iWf; //房位
  private int iFmw; //房门位
  private int iCw; //床位
  private int iGxw; //盥洗位
  private int iJzw; //旧宅位
  private int iCfw; //厨房位
  private int iZw; //灶位
  private int iZx; //灶向
  private int iFz; //房主
  private int[] iHjs;

  private String fileId;
  private String rowId;
  private String parentNode;
  private String memo;
  public VoBaZhai vo;

  public BaZhaiFrame() {
    this.setLayout(new BorderLayout());
    del = new DelFs_BaZhai();
    iFz = 1;
    jList = new JList(BaZhai.hj);
  }

  public void init(String fileId, String rowId, String parentNode1) {
    vo = (VoBaZhai) Public.getObjectFromFile(fileId, rowId);
    if (vo != null) {
      yearBoy = vo.getYear();
      yearGirl = vo.getYearGirl();
      iFz = vo.isIsBoy() ? 0 : 1;
      iHjs = vo.getIHjs();
      iWx = vo.getIWx();
      iDmw = vo.getIDmw();
      iDmx = vo.getIDmx();
      iGxw = vo.getIGxw();
      iJzw = vo.getIJzw();
      iWf = vo.getIWf();
      iFmw = vo.getIFmw();
      iCw = vo.getICw();
      iCfw = vo.getICfw();
      iZw = vo.getIZw();
      iZx = vo.getIZx();
      memo = vo.getMemo();
    }

    this.fileId = fileId;
    this.rowId = rowId;
    if (parentNode1 == null) {
      if (vo != null)
        parentNode1 = vo.getParent();
    }
    this.parentNode = parentNode1;
    //1. 录入框为3个面板，左中右
    this.add(getInputPanel(), BorderLayout.NORTH);
    //2. 文本框
    this.add(getOtherInfoPane(this.getSaveJButton(), memo), BorderLayout.CENTER);
  }

  private JButton getSaveJButton() {
    CommandAction actionXX = new CommandAction("保存", null, "", ' ', new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String rs = check1();
        if (rs != null) {
          JOptionPane.showMessageDialog(getThis(), rs, "提示信息",
                                        JOptionPane.INFORMATION_MESSAGE);
          return;
        }
        getInputs();
        vo = new VoBaZhai(yearBoy, yearGirl, iFz, iHjs, iWx,
                                     iDmw, iDmx, iGxw, iJzw, iWf, iFmw,
                                     iCw, iCfw, iZw, iZx);
        vo.setRowId(rowId);
        vo.setFileId(fileId);
        //不取子目录了 一律取预测术的根目录，即取root的值
        vo.setRoot(Public.valueRoot[8]); //此处是八宅的值
        vo.setParent(parentNode);
        vo.setYcsj(Public.getTimestampValue().toString());
        vo.setMemo(memo);
        Public.writeObject2File(vo);
        JOptionPane.showMessageDialog(getThis(), "保存成功！", "提示信息",
                                      JOptionPane.INFORMATION_MESSAGE);
        clear();
      }
    });
    JButton buttonQK = new JButton(actionXX);
    return buttonQK;
  }

  public String do1() {
    if (vo == null)return "";
    return del.getBaZhaYxp(yearBoy, yearGirl, iFz, iHjs, iWx,
                             iDmw, iDmx, iGxw, iJzw, iWf, iFmw,
                             iCw, iCfw, iZw, iZx);
  }

  public String do2() {
    if (vo == null)return "";
    return del.getBaZhaiInfo(yearBoy, yearGirl, iFz, iHjs, iWx,
                             iDmw, iDmx, iGxw, iJzw, iWf, iFmw,
                             iCw, iCfw, iZw, iZx);
  }


  /**
   * 由三大面板组成录入面板
   * @return
   */
  private JPanel getInputPanel() {
    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
    inputPanel.add(getOnePanel());
    inputPanel.add(getTwoPanel());
    inputPanel.add(getThreePanel());
    inputPanel.add(getFourPanel());
    inputPanel.add(getFivePanel());
    inputPanel.add(getSixPanel());

    return inputPanel;
  }

  /**
   * 得到第一列的面板
   * @return
   */
  private Box getOnePanel() {
    Box box = new Box(BoxLayout.X_AXIS);
    box.add(new JLabel("峦头："));
    jList.setVisibleRowCount(3);
    if(iHjs != null)
      jList.setSelectedIndices(iHjs);

    JScrollPane pane = new JScrollPane(jList);
    box.add(pane);
    box.add(new JLabel("  "));

    return box;
  }

  private Box getTwoPanel() {
    Box box1 = new Box(BoxLayout.Y_AXIS);
    Box box = new Box(BoxLayout.X_AXIS);
    box.add(new JLabel("屋向："));
    box.add(this.getWuXiang());
    box.add(new JLabel("  "));
    Box box2 = new Box(BoxLayout.X_AXIS);
    box2.add(new JLabel("房门："));
    box2.add(this.getFangMenWei());
    box2.add(new JLabel("  "));
    Box box3 = new Box(BoxLayout.X_AXIS);
    box3.add(new JLabel("男命："));
    textBoy = new JTextField(4);
    textBoy.setText(""+this.yearBoy);
    box3.add(textBoy);
    box3.add(new JLabel("  "));
    box1.add(box);
    box1.add(box2);
    box1.add(box3);

    return box1;
  }

  private Box getThreePanel() {
    Box box1 = new Box(BoxLayout.Y_AXIS);
    Box box = new Box(BoxLayout.X_AXIS);
    box.add(new JLabel("大门："));
    box.add(this.getDaMenWei());
    box.add(new JLabel("  "));
    Box box2 = new Box(BoxLayout.X_AXIS);
    box2.add(new JLabel("床位："));
    box2.add(this.getChuangWei());
    box2.add(new JLabel("  "));
    Box box3 = new Box(BoxLayout.X_AXIS);
    box3.add(new JLabel("女命："));
    textGirl = new JTextField(4);
    textGirl.setText(""+this.yearGirl);
    box3.add(textGirl);
    box3.add(new JLabel("  "));
    box1.add(box);
    box1.add(box2);
    box1.add(box3);

    return box1;
  }

  private Box getFourPanel() {
    Box box1 = new Box(BoxLayout.Y_AXIS);
    Box box = new Box(BoxLayout.X_AXIS);
    box.add(new JLabel("门向："));
    box.add(this.getDaMenXiang());
    box.add(new JLabel("  "));
    Box box2 = new Box(BoxLayout.X_AXIS);
    box2.add(new JLabel("厨房："));
    box2.add(this.getChuFang());
    box2.add(new JLabel("  "));
    Box box3 = new Box(BoxLayout.X_AXIS);
    box3.add(new JLabel("房主："));
    box3.add(this.getFangZhu());
    box3.add(new JLabel("  "));

    box1.add(box);
    box1.add(box2);
    box1.add(box3);

    return box1;
  }

  /**
   * 得到第五列的面板
   * @return
   */
  private Box getFivePanel() {
    Box box1 = new Box(BoxLayout.Y_AXIS);
    Box box = new Box(BoxLayout.X_AXIS);
    box.add(new JLabel("卧房："));
    box.add(this.getWoFang());
    box.add(new JLabel("  "));

    Box box2 = new Box(BoxLayout.X_AXIS);
    box2.add(new JLabel("灶位："));
    box2.add(this.getZhaoWei());
    box2.add(new JLabel("  "));
    box1.add(box);
    box1.add(box2);

    return box1;
  }

  /**
   * 得到第六列的面板
   * @return
   */
  private Box getSixPanel() {
    Box box1 = new Box(BoxLayout.Y_AXIS);
    Box box = new Box(BoxLayout.X_AXIS);
    box.add(new JLabel("旧宅："));
    box.add(this.getJiuZhai());
    box.add(new JLabel("  "));

    Box box2 = new Box(BoxLayout.X_AXIS);
    box2.add(new JLabel("灶向："));
    box2.add(this.getZhaoXiang());
    box2.add(new JLabel("  "));
    box1.add(box);
    box1.add(box2);

    return box1;
  }

  private String checkBoy() {
    if (textBoy.getText() == null || textBoy.getText().equals("")) {
      return "男命出生年份必须在" + Calendar.MAXYEAR + "与" +
          Calendar.IYEAR + "之间";
    }
    try {
      Integer.valueOf(textBoy.getText().trim()).intValue();
    }
    catch (Exception e) {
      return "男命出生年份必须是数字，且须在" + Calendar.MAXYEAR + "与" +
          Calendar.IYEAR + "之间";
    }
    String _y = textBoy.getText();
    yearBoy = _y.equals("") ? 0 : Integer.valueOf(_y).intValue();
    if (yearBoy > Calendar.MAXYEAR || yearBoy < Calendar.IYEAR)
      return "男命年份必须在" + Calendar.MAXYEAR + "与" + Calendar.IYEAR + "之间";
    _y = textGirl.getText();

    return null;
  }

  private String checkGirl() {
    String _y = textGirl.getText();
    try {
      Integer.valueOf(textGirl.getText().trim()).intValue();
    }
    catch (Exception e) {
      return "女命出生年份必须是数字，且须在" + Calendar.MAXYEAR + "与" +
          Calendar.IYEAR + "之间";
    }
    if (textGirl.getText() == null || textGirl.getText().equals("")) {
      return "女命出生年份必须在" + Calendar.MAXYEAR + "与" +
          Calendar.IYEAR + "之间";
    }
    yearGirl = _y.equals("") ? 0 : Integer.valueOf(_y).intValue();
    if (yearGirl > Calendar.MAXYEAR || yearGirl < Calendar.IYEAR)
      return "女命年份必须在" + Calendar.MAXYEAR + "与" + Calendar.IYEAR + "之间";

    return null;
  }

  private String check1() {
    String boy = checkBoy();
    String girl = checkGirl();

    if ( (iFz == 0 || iFz == 1) && boy != null) {
      return boy;
    }
    else if (iFz == 2 && girl != null) {
      return girl;
    }
    if (iWx == 0)
      return "房屋坐向必须选择，以向为主，立极五决，乘、止、截、虚、气";
    return null;
  }

  private String check3() {
    String boy = checkBoy();
    if (boy != null)return boy;
    String girl = checkGirl();
    if (girl != null)return girl;
    if (iWx == 0)
      return "房屋坐向必须选择，以向为主，立极五决，乘、止、截、虚、气";
    return null;
  }

  private String check2() {
    String str = check3();
    if (str != null) {
      return str;
    }

    if (this.iWx == 0)
      return "房屋坐向必须选择，立极五决乘、止、截、虚、气";
    if (this.iDmw == 0)
      return "大门位置必须选择，依住宅大太极方向而定";
    if (this.iDmx == 0)
      return "大门开门方向必须选择，依住宅大太极方向而定";
    if (this.iWf == 0)
      return "卧房位置必须选择，依住宅大太极方向而定";
    if (this.iFmw == 0)
      return "卧房开门方向必须选择，依房间小太极方向而定";
    if (this.iCw == 0)
      return "床头位置必须选择，依房间小大太极方向而定";
    if (this.iCfw == 0)
      return "厨房必须选择，依住宅大太极方向而定";
    if (this.iZw == 0)
      return "灶位必须选择，依住宅大太极方向而定";
    if (this.iZx == 0)
      return "灶的朝向(以控制开关为准)必须选择，依住宅大太极方向而定";
    //if(this.iGxw==0)
    //  return "厕所位置必须选择，依住宅大太极方向而定";
    //if(this.iJzw==0)
    //  return "如果必须选择，依大太极方向而定";

    return null;
  }

  private void getInputs() {
    memo = getJTextArea().getText();
    String _boy = textBoy.getText().trim();
    String _girl = textGirl.getText().trim();
    yearBoy = _boy.equals("") ? 0 : Integer.valueOf(_boy).intValue();
    yearGirl = _girl.equals("") ? 0 : Integer.valueOf(_girl).intValue();
    iHjs = jList.getSelectedIndices();
  }

  private void clear() {
    Calendar.YEARN = 0;
    Calendar.YEARP = 0;
    Calendar.MONTHN = 0;
    Calendar.MONTHP = 0;
    Calendar.DAYN = 0;
    Calendar.DAYP = 0;
    Calendar.HOUR = 0;
  }

  protected Component getThis() {
    return this;
  }

  /**
   * 得到屋向的下拉框
   * @return
   */
  private Box getWuXiang() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboWx = new JComboBox(BaZhai.fx);
    comboWx.setSelectedIndex(this.iWx);
    comboWx.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iWx = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboWx);

    return box;
  }

  /**
   * 得到大门位的下拉框
   * @return
   */
  private Box getDaMenWei() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboDmw = new JComboBox(BaZhai.fx);
    comboDmw.setSelectedIndex(this.iDmw);
    comboDmw.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iDmw = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboDmw);

    return box;
  }

  /**
   * 得到大门向的下拉框
   * @return
   */
  private Box getDaMenXiang() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboDmx = new JComboBox(BaZhai.fx);
    comboDmx.setSelectedIndex(this.iDmx);
    comboDmx.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iDmx = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboDmx);

    return box;
  }

  /**
   * 得到卧房的下拉框
   * @return
   */
  private Box getWoFang() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboWf = new JComboBox(BaZhai.fx);
    comboWf.setSelectedIndex(this.iWf);
    comboWf.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iWf = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboWf);

    return box;
  }

  /**
   * 得到房门的下拉框
   * @return
   */
  private Box getFangMenWei() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboFmw = new JComboBox(BaZhai.fx);
    comboFmw.setSelectedIndex(this.iFmw);
    comboFmw.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iFmw = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboFmw);

    return box;
  }

  /**
   * 得到床位的下拉框
   * @return
   */
  private Box getChuangWei() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboCw = new JComboBox(BaZhai.fx);
    comboCw.setSelectedIndex(this.iCw);
    comboCw.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iCw = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboCw);

    return box;
  }

  /**
   * 得到盥洗位的下拉框
   * @return
   */
  private Box getGuanXiWei() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboGxw = new JComboBox(BaZhai.fx);
    comboGxw.setSelectedIndex(this.iGxw);
    comboGxw.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iGxw = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboGxw);

    return box;
  }

  /**
   * 得到旧宅的下拉框
   * @return
   */
  private Box getJiuZhai() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboJzw = new JComboBox(BaZhai.fx);
    comboJzw.setSelectedIndex(this.iJzw);
    comboJzw.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iJzw = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboJzw);

    return box;
  }

  /**
   * 得到厨房的下拉框
   * @return
   */
  private Box getChuFang() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboCfw = new JComboBox(BaZhai.fx);
    comboCfw.setSelectedIndex(this.iCfw);
    comboCfw.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iCfw = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboCfw);

    return box;
  }

  /**
   * 得到灶位的下拉框
   * @return
   */
  private Box getZhaoWei() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboZw = new JComboBox(BaZhai.fx);
    comboZw.setSelectedIndex(this.iZw);
    comboZw.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iZw = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboZw);

    return box;
  }

  /**
   * 得到灶向的下拉框
   * @return
   */
  private Box getZhaoXiang() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboZx = new JComboBox(BaZhai.fx);
    comboZx.setSelectedIndex(this.iZx);
    comboZx.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iZx = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboZx);

    return box;
  }

  /**
   * 得到房主下拉框
   * @return
   */
  private Box getFangZhu() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboFz = new JComboBox(new String[] {"男", "女"});
    comboFz.setSelectedIndex(this.iFz);
    comboFz.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iFz = ( (JComboBox) e.getSource()).getSelectedIndex() + 1;
      }
    });
    box.add(comboFz);

    return box;
  }

  class PrintListener
      implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      int selected[] = jList.getSelectedIndices();
      //System.out.println("Selected Elements:  ");
      //for (int i = 0; i < selected.length; i++) {
      //  String element = (String) jList.getModel().getElementAt(selected[i]);
      //  System.out.println("  " + element);
      //}
    }
  }

}
