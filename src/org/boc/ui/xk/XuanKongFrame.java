package org.boc.ui.xk;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.boc.dao.DaoPublic;
import org.boc.db.BaZhai;
import org.boc.db.Xuank;
import org.boc.delegate.DelFs_Xkfx;
import org.boc.ui.BasicJPanel;
import org.boc.ui.CommandAction;
import org.boc.util.Public;
import org.boc.util.VoXuanKong;

public class XuanKongFrame extends BasicJPanel {
  private DaoPublic pub;
  private DelFs_Xkfx del;

  private int y1,m1,d1,h1,mi1,s1;  //房主
  private int y2,m2,d2,h2,mi2,s2;  //年月飞星
  private int y3;                  //修造
  private int iWx;                 //屋向
  private int iDmw;                //大门位
  private int iWf;                 //房位
  private int iCfw;                //厨房位
  private int iCsw;                //厕所
  private int iJy;                 //九运
  private boolean isyun1,isyun2,isyy,isJIan;  //闰月、阴阳历

  private JComboBox comboWx;      //山向
  private JComboBox comboDmw;     //大门位
  private JComboBox comboWf;      //主卧
  private JComboBox comboCfw;     //厨房位
  private JComboBox comboCsw;     //厕所
  private JComboBox comboYun;     //三元九运

  private JTextField txty1,txtm1,txtd1,txth1;    //房主
  private JTextField txty2,txtm2,txtd2,txth2;    //年月飞星
  private JTextField txty3;    //修造

  private JCheckBox yun1, yun2; //是否闰月
  private JCheckBox jian;       //是否兼向

  private ButtonGroup gLifa;     //阴阳历
  private JRadioButton yin;      //阴历
  private JRadioButton yang;     //阳历

  private String fileId;
  private String rowId;
  private String parentNode;
  private String memo;
  public VoXuanKong vo;
  private String yText1, mText1, dText1, hText1;
  private String yText2, mText2, dText2, hText2;

  public XuanKongFrame() {
    this.setLayout(new BorderLayout());
    del = new DelFs_Xkfx();
    pub = new DaoPublic();
  }

  public void init(String fileId, String rowId, String parentNode1) {
    vo = (VoXuanKong) Public.getObjectFromFile(fileId, rowId);
    if(vo!=null) {
      iDmw = vo.getDm();
      iWf = vo.getWf();
      iCfw = vo.getCf();
      iCsw = vo.getCesuo();

      iJy = vo.getSyjy();
      iWx = vo.getSx();
      y3 = vo.getBuild();

      isyy = vo.isIsYin();
      isJIan = vo.isIsjian();
      isyun1 = vo.isIsYun();
      isyun2 = vo.isIsfxyun();
    }

    if(vo!=null) {
      if (vo.getNg() != 0) {
        yText1 = vo.getNg() + "," + vo.getNz();
        mText1 = vo.getYg() + "," + vo.getYz();
        dText1 = vo.getRg() + "," + vo.getRz();
        hText1 = vo.getSg() + "," + vo.getSz();
      }
      else if(vo.getYear()!=0) {
        yText1 = "" + vo.getYear();
        mText1 = "" + vo.getMonth();
        dText1 = "" + vo.getDay();
        hText1 = vo.getHour() + ":" + vo.getMinute() + ":00" ;
      }else {
        yText1 = "";
        mText1 = "";
        dText1 = "";
        hText1 = "";
      }

      if (vo.getFxng() != 0) {
        yText2 = vo.getFxng() + "," + vo.getFxnz();
        mText2 = vo.getFxyg() + "," + vo.getFxyz();
        dText2 = vo.getFxrg() + "," + vo.getFxrz();
        hText2 = vo.getFxsg() + "," + vo.getFxsz();
      }
      else if (vo.getFxyear() != 0) {
        yText2 = "" + vo.getFxyear();
        mText2 = "" + vo.getFxmonth();
        dText2 = "" + vo.getFxday();
        hText2 = vo.getFxhour() + ":" + vo.getFxminute() + ":00" ;
      }else{
        yText1 = "";
        mText1 = "";
        dText1 = "";
        hText1 = "";
      }
      memo = vo.getMemo();
     }
     this.fileId = fileId;
     this.rowId = rowId;
     if(parentNode1==null) {
       if(vo!=null)
         parentNode1=vo.getParent();
     }
     this.parentNode = parentNode1;
    //1. 录入框为3大面板，上中下
    this.add(getInputPanel(), BorderLayout.NORTH);
    //2. 文本框
    this.add(getOtherInfoPane(this.getSaveJButton(),memo), BorderLayout.CENTER);
  }

  private JButton getSaveJButton() {
    CommandAction actionQk = new CommandAction("保存", null, "", ' ',new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String rs = check(1);
        if(rs!=null) {
          JOptionPane.showMessageDialog(getThis(), rs, "提示信息",
                                        JOptionPane.INFORMATION_MESSAGE);
          return ;
        }
        getInputs();
        vo = new VoXuanKong(y3, iJy,iWx,
                                       new int[]{0,iDmw,iWf,iCfw,iCsw},
                                       new int[]{0,y1,m1,d1,h1,mi1,s1},
                                       new int[]{0,y2,m2,d2,h2,mi2,s2},
                                       new boolean[]{false,isyy,isJIan, isyun1,isyun2});
        vo.setRowId(rowId);
        vo.setFileId(fileId);
        //不取子目录了 一律取预测术的根目录，即取root的值
        vo.setRoot(Public.valueRoot[7]);  //此处是玄空的值
        vo.setParent(parentNode);
        vo.setYcsj(Public.getTimestampValue().toString());
        vo.setMemo(memo);
        Public.writeObject2File(vo);
        JOptionPane.showMessageDialog(getThis(), "保存成功！", "提示信息",
                                      JOptionPane.INFORMATION_MESSAGE);
        clear();
      }
    });
    JButton buttonQK = new JButton(actionQk);
    return buttonQK;
  }

  /**
   * 输出起卦信息
   * @return JPanel
   */
  public String do1() {
    if (vo == null)return "";
    return del.pp(vo.getBuild(), vo.getSyjy(), vo.getSx(),
                  new int[]{0, vo.getDm(),vo.getWf(),vo.getCf(),vo.getCesuo()},
                  new int[]{0,vo.getYear(),vo.getMonth(),vo.getDay(),vo.getHour(),vo.getMinute(),vo.getSecond()},
                  new int[]{0, vo.getFxyear(),vo.getFxmonth(),vo.getFxday(),vo.getFxhour(),vo.getFxminute(),0},
                  new boolean[]{false,vo.isIsYin(),vo.isIsjian(),vo.isIsYun(),vo.isIsfxyun()});
  }

  public String do3() {
    if (vo == null)return "";
    return del.zheri(vo.getBuild(), vo.getSyjy(), vo.getSx(),
                  new int[]{0, vo.getDm(),vo.getWf(),vo.getCf(),vo.getCesuo()},
                  new int[]{0,vo.getYear(),vo.getMonth(),vo.getDay(),vo.getHour(),vo.getMinute(),vo.getSecond()},
                  new int[]{0, vo.getFxyear(),vo.getFxmonth(),vo.getFxday(),vo.getFxhour(),vo.getFxminute(),0},
                  new boolean[]{false,vo.isIsYin(),vo.isIsjian(),vo.isIsYun(),vo.isIsfxyun()});
  }

  /**
   * 由三大面板组成录入面板
   * @return
   */
  private JPanel getInputPanel() {
    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
    inputPanel.add(getUpPanel());
    inputPanel.add(getCenterPanel());
    inputPanel.add(getDownPanel());

    return inputPanel;
  }

  /**
   * 得到第一排的面板
   * @return
   */
  private Box getUpPanel() {
    Box box = new Box(BoxLayout.X_AXIS);

    box.add(new JLabel(" "));
    box.add(new JLabel("山向："));
    box.add(this.getWuXiang());
    jian = new JCheckBox(" 兼向", vo!=null && vo.isIsjian());
    box.add(jian);
    box.add(new JLabel("  "));

    box.add(new JLabel("大门："));
    box.add(this.getDaMenWei());
    box.add(new JLabel("      "));

    box.add(new JLabel("卧室："));
    box.add(this.getWoFang());
    box.add(new JLabel("  "));

    box.add(new JLabel("厨房："));
    box.add(this.getChuFang());
    box.add(new JLabel("  "));

    box.add(new JLabel("厕所："));
    box.add(this.getCesuo());
    box.add(new JLabel(" "));

    return box;
  }

  /**
   * 得到第二排的面板
   * @return
   */
  private Box getCenterPanel() {
    Box box = new Box(BoxLayout.X_AXIS);

    box.add(new JLabel(" "));
    box.add(new JLabel("命主："));
    txty1 = new JTextField(4);
    txty1.setText(yText1);
    box.add(txty1);
    box.add(new JLabel("年  "));

    yun1 = new JCheckBox("闰", vo!=null && vo.isIsYun());
    box.add(yun1);
    txtm1 = new JTextField(2);
    txtm1.setText(mText1);
    box.add(txtm1);
    box.add(new JLabel("月  "));

    txtd1 = new JTextField(2);
    txtd1.setText(dText1);
    box.add(txtd1);
    box.add(new JLabel("日  "));

    txth1 = new JTextField(8);
    txth1.setText(hText1);
    box.add(txth1);
    box.add(new JLabel("时"));

    box.add(new JLabel("      岁月飞星："));
    txty2 = new JTextField(4);
    txty2.setText(yText2);
    box.add(txty2);
    box.add(new JLabel("年  "));

    yun2 = new JCheckBox("闰", vo!=null && vo.isIsfxyun());
    box.add(yun2);
    txtm2 = new JTextField(2);
    txtm2.setText(mText2);
    box.add(txtm2);
    box.add(new JLabel("月  "));

    txtd2 = new JTextField(2);
    txtd2.setText(dText2);
    box.add(txtd2);
    box.add(new JLabel("日  "));

    txth2 = new JTextField(8);
    txth2.setText(hText2);
    box.add(txth2);
    box.add(new JLabel("时 "));

    return box;
  }



  /**
   * 得到第三排的面板
   * @return
   */
  private Box getDownPanel() {
    Box box = new Box(BoxLayout.X_AXIS);

    box.add(new JLabel(" "));
    box.add(new JLabel("建造："));
    txty3 = new JTextField(4);
    txty3.setText(vo==null || vo.getBuild()==0?"":""+vo.getBuild());
    box.add(txty3);
    box.add(new JLabel("年或"));
    box.add(new JLabel("运："));
    box.add(this.getJiuYun());
    box.add(new JLabel("  "));

    yang = new JRadioButton("阳历",vo!=null && !vo.isIsYin());
    yang.setActionCommand("yang");
    yin = new JRadioButton("阴历",vo!=null && vo.isIsYin());
    yin.setActionCommand("yin");
    gLifa = new ButtonGroup();
    gLifa.add(yang);
    gLifa.add(yin);
    box.add(yin);
    box.add(yang);
    box.add(new JLabel("                                                               "));

    return box;
  }

  /**
   * 当排盘1时，只要输入山向，建造或九运即可
   * 当分析2时，还需要八字、各大门等方向
   * 当择日3时，还要岁月飞星
   * @param type
   * @return
   */
  private String check(int type) {
    String rili = "yin";
    if(gLifa.getSelection()!=null)
      rili = gLifa.getSelection().getActionCommand();
    boolean isyy = rili.equals("yin");
    boolean isyun = false;
    String y = null;
    String m = null;
    String d = null;
    String h = null;
    String checks = null;

    if(type==2 || !txty1.getText().trim().equals("")) {
      isyun = yun1.isSelected();
      y = txty1.getText();
      m = txtm1.getText();
      d = txtd1.getText();
      h = txth1.getText();
      checks = pub.checks(y, m, d, h, isyy, isyun, true);
    }
    if(checks!=null) {
      return checks;
    }

    if(type==3 || !txty2.getText().trim().equals("")) {
      isyun = yun2.isSelected();
      y = txty2.getText();
      m = txtm2.getText();
      d = txtd2.getText();
      h = txth2.getText();
      checks = pub.checks(y, m, d, h, isyy, isyun, true);
    }
    if(checks!=null) {
      return checks;
    }

    if(this.iWx==0)
      return "房屋山向必须选择，并确认是否兼向，立极五决乘、止、截、虚、气";
    if(txty3.getText().equals("") && iJy==0) {
      return "必须输入建造年份或选择三元九运，以后者为主。";
    }
    if(type==2) {
      if (this.iDmw == 0)
        return "大门位置必须选择，依住宅大太极方向而定";
      if (this.iWf == 0)
        return "主卧位置必须选择，依住宅大太极方向而定";
      if (this.iCfw == 0)
        return "厨房必须选择，依住宅大太极方向而定";
      if (this.iCsw == 0)
        return "厕所位置必须选择，依住宅大太极方向而定";
    }
    return null;
  }

  private void clear() {
  }

  protected Component getThis() {
    return this;
  }

  /**
   * 得到输入参数
   */
  private void getInputs() {
    try{
      memo = getJTextArea().getText();
      String rili = gLifa.getSelection().getActionCommand();
      isyy = rili.equals("yin");
      isJIan = jian.isSelected();
      y3 = Integer.valueOf(txty3.getText()).intValue();
      isyun1 = yun1.isSelected();
      isyun2 = yun2.isSelected();
    }catch(Exception e) {
    }
    try{
      _getInputs1();
    }catch(Exception e) {
    }
    try{
      _getInputs2();
    }catch(Exception e) {
    }
  }

  private void _getInputs1() {
    y1 = Integer.valueOf(txty1.getText()).intValue();
    m1 = Integer.valueOf(txtm1.getText()).intValue();
    d1 = Integer.valueOf(txtd1.getText()).intValue();
    String[] h = (txth1.getText()).split(":");
    if (h.length == 1) {
      h1 = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
      mi1 = 0;
      s1 = 0;
    }
    if (h.length == 2) {
      h1 = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
      mi1 = h[1] == null ? 0 : Integer.valueOf(h[1]).intValue();
      s1 = 0;
    }
    if (h.length == 3) {
      h1 = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
      mi1 = h[1] == null ? 0 : Integer.valueOf(h[1]).intValue();
      s1 = h[2] == null ? 0 : Integer.valueOf(h[2]).intValue();
    }
  }

  private void _getInputs2() {
    y2 = Integer.valueOf(txty2.getText()).intValue();
    m2 = Integer.valueOf(txtm2.getText()).intValue();
    d2 = Integer.valueOf(txtd2.getText()).intValue();
    String[] h = (txth2.getText()).split(":");
    if (h.length == 1) {
      h2 = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
      mi2 = 0;
      s2 = 0;
    }
    if (h.length == 2) {
      h2 = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
      mi2 = h[1] == null ? 0 : Integer.valueOf(h[1]).intValue();
      s2 = 0;
    }
    if (h.length == 3) {
      h2 = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
      mi2 = h[1] == null ? 0 : Integer.valueOf(h[1]).intValue();
      s2 = h[2] == null ? 0 : Integer.valueOf(h[2]).intValue();
    }
  }


  /**
   * 得到屋向的下拉框
   * @return
   */
  private Box getWuXiang() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboWx = new JComboBox(Xuank.s24name);
    comboWx.setSelectedIndex(vo==null?0:vo.getSx());
    comboWx.addActionListener(new ActionListener( ) {
      public void actionPerformed(ActionEvent e) {
        iWx = ((JComboBox) e.getSource()).getSelectedIndex();
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
    comboDmw.setSelectedIndex(vo==null?0:vo.getDm());
    comboDmw.addActionListener(new ActionListener( ) {
      public void actionPerformed(ActionEvent e) {
        iDmw = ((JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboDmw);

    return box;
  }

  /**
   * 得到卧房的下拉框
   * @return
   */
  private Box getWoFang() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboWf = new JComboBox(BaZhai.fx);
    comboWf.setSelectedIndex(vo==null?0:vo.getWf());
    comboWf.addActionListener(new ActionListener( ) {
      public void actionPerformed(ActionEvent e) {
        iWf = ((JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboWf);

    return box;
  }

  /**
   * 得到厨房的下拉框
   * @return
   */
  private Box getChuFang() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboCfw = new JComboBox(BaZhai.fx);
    comboCfw.setSelectedIndex(vo==null?0:vo.getCf());
    comboCfw.addActionListener(new ActionListener( ) {
      public void actionPerformed(ActionEvent e) {
        iCfw = ((JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboCfw);

    return box;
  }

  /**
   * 得到厕所的下拉框
   * @return
   */
  private Box getCesuo() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboCsw = new JComboBox(BaZhai.fx);
    comboCsw.setSelectedIndex(vo==null?0:vo.getCesuo());
    comboCsw.addActionListener(new ActionListener( ) {
      public void actionPerformed(ActionEvent e) {
        iCsw = ((JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboCsw);

    return box;
  }

  /**
   * 得到三元九运的下拉框
   * @return
   */
  private Box getJiuYun() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboYun = new JComboBox(new String[]{"","一运","二运","三运","四运","五运","六运","七运","八运","九运"});
    comboYun.setSelectedIndex(vo==null?0:vo.getSyjy());
    comboYun.addActionListener(new ActionListener( ) {
      public void actionPerformed(ActionEvent e) {
        iJy = ((JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboYun);

    return box;
  }


}
