package org.boc.ui.ly;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.boc.dao.DaoCalendar;
import org.boc.dao.DaoPublic;
import org.boc.dao.ly.DaoYiJingMain;
import org.boc.db.Calendar;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.delegate.DelYiJingMain;
import org.boc.ui.BasicJPanel;
import org.boc.ui.CommandAction;
import org.boc.util.Messages;
import org.boc.util.Public;
import org.boc.util.VoLiuYao;

public class LiuYaoFrame2 extends BasicJPanel{
  private JTextArea jTxtAreaYg;
  private ButtonGroup groupQiguafs;  //起卦方式
  private ButtonGroup groupYYli;     //阴阳历
  private ButtonGroup groupYongShen; //用神
  private ButtonGroup groupCurOrIns; //录入或输入

  private JButton button1;     //初爻
  private JButton button2;     //二爻
  private JButton button3;     //三爻
  private JButton button4;     //四爻
  private JButton button5;     //五爻
  private JButton button6;     //上爻

  private JRadioButton radioCur;  //当前时间
  private JRadioButton radioIns;  //录入时间
  private JRadioButton radioYin;  //阴历
  private JRadioButton radioYang;  //阳历

  private JTextField textSg;   //上卦
  private JTextField textXg;   //下卦
  private JTextField textDy;   //动爻

  private JCheckBox checkboxYun; //是否闰月
  private JTextField textYear;   //年
  private JTextField textMonth;   //月
  private JTextField textDay;   //日
  private JTextField textHour;   //时分秒

  public int year,month,day,hour,minute,second;
  public boolean isYun;         //是否是闰月
  public boolean isYin;         //是否是阴历
  public boolean isBoy;         //是否是男孩
 
  private int sg;      //上卦卦数
  private int xg;      //下卦卦数
  private int yongshen;   //用神
  private static int yiyao;      //初爻数
  private static int eryao;      //二爻数
  private static int shanyao;    //三爻数
  private static int siyao;      //四爻数
  private static int wuyao;      //五爻数
  private static int liuyao;     //六爻数
  private static int[] changes = new int[6];  //动爻数组
  private static String dongyaos = "";             //动爻
  private DaoYiJingMain daoYJ;
  private int yg,yz,mg,mz,dg,dz,hg,hz;
  private boolean typeSjOrgz ;

  private DelYiJingMain del;
  private DaoCalendar c;

  private String fileId;
  private String rowId;
  private String parentNode;
  private String memo;
  public VoLiuYao vo;
  private String yText, mText, dText, hText;

  public LiuYaoFrame2() {
    this.setLayout(new BorderLayout());
    del = new DelYiJingMain();
    c = new DaoCalendar();
    daoYJ = new DaoYiJingMain();
    jTxtAreaYg = new JTextArea();
    jTxtAreaYg.setBackground(this.getBackground());
    jTxtAreaYg.setForeground(Color.blue);
    Font f = Public.getFont();
    jTxtAreaYg.setFont(f);
  }

  public void finalize() {
    del = null;
    c = null;
    daoYJ = null;
    vo = null;
  }

  public void init(String fileId, String rowId, String parentNode1) {
    vo = (VoLiuYao) Public.getObjectFromFile(fileId, rowId);
    if(vo!=null) {
      if (vo.getYear() == 0) {
        yText = vo.getNg() + "," + vo.getNz();
        mText = vo.getYg() + "," + vo.getYz();
        dText = vo.getRg() + "," + vo.getRz();
        hText = vo.getSg() + "," + vo.getSz();
      }
      else {
        yText = "" + vo.getYear();
        mText = "" + vo.getMonth();
        dText = "" + vo.getDay();
        hText = vo.getHour() + ":" + vo.getMinute() + ":00" ;
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

    //1. 录入框为3个面板，左中右
    this.add(getInputPanel(), BorderLayout.NORTH);
    //2. 文本框
    this.add(getOtherInfoPane(this.getSaveJButton(),memo), BorderLayout.CENTER);
    setGuaXiangEnable(false);
    setButtonEnable(false);
    setYearMDHEnable(false);
  }

  private JButton getSaveJButton() {
    CommandAction actionQk = new CommandAction("保存", null, "", ' ',
                                               new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          String choice = null;
          String checks = null;
          //检查
          if(groupQiguafs.getSelection()==null) {
            checks = "请先选择起卦方式";
            JOptionPane.showMessageDialog(getThis(), checks, "提示信息",JOptionPane.INFORMATION_MESSAGE);
            return;
          }
          else {
            choice = groupQiguafs.getSelection().getActionCommand();
            getYYli();
            isYunYue();
            getYongShen();
            checks = checkInputs(choice);
            if(checks != null) {
              JOptionPane.showMessageDialog(getThis(), checks, "提示信息",JOptionPane.INFORMATION_MESSAGE);
              return;
            }
            getParameter(choice);
          }
          //起卦方式，1为卦象，2为时间起卦，3为摇卦。用神世爻为0
          if(choice.trim().equals("gx")) {
            vo = new VoLiuYao(1,yongshen,sg,xg,
            		dongyaos,new int[]{0,yg,yz,mg,mz,dg,dz,hg,hz},
                              year, month, day, hour, minute,
                              isYin, isYun,"",0,0);
          }else if(choice.trim().equals("sj")) {
            if(!typeSjOrgz) {
              int upGua = (SiZhu.nz + Calendar.MONTHN1 + Calendar.DAYN1) % 8 ==
                  0 ? 8 : (SiZhu.nz + Calendar.MONTHN1 + Calendar.DAYN1) % 8;
              int downGua = (upGua + SiZhu.sz) % 8 == 0 ? 8 :
                  (upGua + SiZhu.sz) % 8;
              int change = (SiZhu.nz + Calendar.MONTHN1 + Calendar.DAYN1 +
                                SiZhu.sz) % 6 == 0 ? 6 :
                  (SiZhu.nz + Calendar.MONTHN1 + Calendar.DAYN1 + SiZhu.sz) % 6;
              vo = new VoLiuYao(2,yongshen,upGua,downGua,
              		dongyaos,new int[]{0,yg,yz,mg,mz,dg,dz,hg,hz},
                year, month, day, hour, minute,
                isYin, isYun,"",0,0);
            }else{
              JOptionPane.showMessageDialog(getThis(), "仅输入干支没法起卦", "提示信息",JOptionPane.INFORMATION_MESSAGE);
              return;
            }
          }else {
            button1.setEnabled(true);
            button2.setEnabled(false);
            button3.setEnabled(false);
            button4.setEnabled(false);
            button5.setEnabled(false);
            button6.setEnabled(false);
            if(yg == 0)
              _getCurShiJian();
            vo = new VoLiuYao(3,yongshen,
                YiJing.XIANGGUA[siyao][wuyao][liuyao],
                YiJing.XIANGGUA[yiyao][eryao][shanyao],
                dongyaos, new int[]{0,yg,yz,mg,mz,dg,dz,hg,hz},
                year, month, day, hour, minute,
                isYin, isYun,"",0,0);
          }
        }
        catch (Exception ex) {
          Messages.error("LiuYaoFrame("+ex+")");
        }

        vo.setRowId(rowId);
        vo.setFileId(fileId);
        //不取子目录了 一律取预测术的根目录，即取root的值
        vo.setRoot(Public.valueRoot[6]);  //此处是六爻的值
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
    String rs = "";

    if (vo == null)return "";
    String chags = vo.getDy();
    String[] s = chags.split(",");

    int j=0;
    for(int i=0; i<s.length; i++) {
      if(s[i]==null || s[i].trim().equals("0") || "".equals(s[i].trim()))
        continue;
      j++;
    }
    int[] chgs = new int[j];
    j = 0;
    for(int i=0; i<s.length; i++) {
      if(s[i]==null || s[i].trim().equals("0") || "".equals(s[i].trim()))
        continue;
      chgs[j++] = Integer.valueOf(s[i]).intValue();
    }

//    if(vo.getYear()>0 && vo.getUp()==0) //按时间起卦
//      rs = del.getGuaXiang(vo.getYear(), vo.getMonth(), vo.getDay(), vo.getHour(), vo.getMinute(),
//                           vo.isIsYin(), vo.isIsYun(), vo.getYs(),
//                           -1, -1,0,vo.isIsBoy());
//    else if(vo.getYear()>0 && vo.getUp()>0) //按卦象起卦
//      rs = del.getGuaXiang(vo.getYear(), vo.getMonth(), vo.getDay(), vo.getHour(), vo.getMinute(),
//                           vo.isIsYin(), vo.isIsYun(), vo.getYs(),
//                           vo.getUp() , vo.getDown(),chgs,
//                           -1, -1,0,vo.isIsBoy());
//    else
//      rs = del.getGuaXiang(vo.getUp() , vo.getDown(),chgs,
//                           new int[]{0,vo.getNg(),vo.getNz(),vo.getYg(),
//                           vo.getYz(),vo.getRg(),vo.getRz(),vo.getSg(),vo.getSz()},
//                           vo.getYs(),true);

    return rs;
  }

  /**
   * 由三大面板组成录入面板
   * @return
   */
  private JPanel getInputPanel() {
    JPanel bigPane = new JPanel();
    bigPane.setLayout(new BoxLayout(bigPane, BoxLayout.Y_AXIS));
    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
    inputPanel.add(getLeftPanel());
    inputPanel.add(new JLabel("       "));
    inputPanel.add(getCenterPanel());
    inputPanel.add(new JLabel("       "));
    inputPanel.add(getRightPanel());
    bigPane.add(new JLabel("   "));
    bigPane.add(inputPanel);
    bigPane.add(jTxtAreaYg);

    return bigPane;
  }

  private JPanel getCenterPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.add(getPanelCenterUp(), BorderLayout.NORTH);
    panel.add(getPanelCenterCenter(), BorderLayout.CENTER);
    panel.add(getPanelCenterDown(), BorderLayout.SOUTH);

    return panel;
  }

  private Box getPanelCenterCenter() {
    Box box = new Box(BoxLayout.X_AXIS);

    radioCur = new JRadioButton("当前");
    radioCur.setActionCommand("dangqian");
    radioIns = new JRadioButton("输入",true);
    radioIns.setActionCommand("shuru");

    class TimeActionListener implements ActionListener {
      public void actionPerformed(ActionEvent ev) {
        String choice = groupCurOrIns.getSelection( ).getActionCommand( );
        if(choice.trim().equals("dangqian")) {
            setYearMDHEnable(false);
            radioCur.setEnabled(true);
            radioIns.setEnabled(true);
            radioYin.setEnabled(false);
            radioYang.setEnabled(false);
          }else {
            setYearMDHEnable(true);
            radioYin.setEnabled(true);
            radioYang.setEnabled(true);
          }
      }
    }
    ActionListener tlisten = new TimeActionListener( );
    radioCur.addActionListener(tlisten);
    radioIns.addActionListener(tlisten);

    groupCurOrIns = new ButtonGroup();
    groupCurOrIns.add(radioCur);
    groupCurOrIns.add(radioIns);

    box.add(radioCur);
    box.add(radioIns);
    box.add(new JLabel(" "));

    //makeField("年", 4, box, "TEXTFIELD");
    JLabel labelYear = new JLabel("年");
    box.add(labelYear);
    textYear = new JTextField(4);
    textYear.setText(yText);
    box.add(textYear);
    box.add(new JLabel(" "));

    checkboxYun = new JCheckBox("闰", vo!=null?vo.isIsYun():false);
    //c.addItemListener(new TabManager(new JLabel("yun")));
    box.add(checkboxYun);

    //makeField("月", 2, box, "TEXTFIELD");
    JLabel labelMonth = new JLabel("月");
    box.add(labelMonth);
    textMonth = new JTextField(2);
    textMonth.setText(mText);
    box.add(textMonth);
    box.add(new JLabel(" "));

    //makeField("日", 2, box, "TEXTFIELD");
    JLabel labelDay = new JLabel("日");
    box.add(labelDay);
    textDay = new JTextField(2);
    textDay.setText(dText);
    box.add(textDay);
    box.add(new JLabel(" "));

    //makeField("时分秒", 8, box, "TEXTFIELD");
    JLabel labelHour = new JLabel("时分秒");
    box.add(labelHour);
    textHour = new JTextField("1:3:58",8);
    textHour.setText(hText);
    box.add(textHour);
    box.add(new JLabel(" "));

    return box;
  }

  private Box getPanelCenterUp() {
    Box box_ = new Box(BoxLayout.Y_AXIS);
    Box box =  new Box(BoxLayout.X_AXIS);
    Box box1 = new Box(BoxLayout.X_AXIS);

    JRadioButton radio1 = new JRadioButton("世爻",vo!=null && vo.getYs()==0);
    radio1.setActionCommand("shiyao");
    JRadioButton radio2 = new JRadioButton("父母",vo!=null && vo.getYs()==5);
    radio2.setActionCommand("fumu");
    JRadioButton radio3 = new JRadioButton("兄弟",vo!=null && vo.getYs()==1);
    radio3.setActionCommand("xiongdi");
    JRadioButton radio4 = new JRadioButton("子孙",vo!=null && vo.getYs()==2);
    radio4.setActionCommand("zisun");
    JRadioButton radio5 = new JRadioButton("妻财",vo!=null && vo.getYs()==3);
    radio5.setActionCommand("qicai");
    JRadioButton radio6 = new JRadioButton("官鬼",vo!=null && vo.getYs()==4);
    radio6.setActionCommand("guangui");
    groupYongShen = new ButtonGroup();
    groupYongShen.add(radio1);
    groupYongShen.add(radio2);
    groupYongShen.add(radio3);
    groupYongShen.add(radio4);
    groupYongShen.add(radio5);
    groupYongShen.add(radio6);

    box1.add(new JLabel("用神："));
    box1.add(radio1);
    box1.add(radio2);
    box1.add(radio3);
    box1.add(radio4);
    box1.add(radio5);
    box1.add(radio6);

    //makeField("上卦", 3, box, "TEXTFIELD");
    JLabel labelSg = new JLabel("上卦");
    box.add(labelSg);
    textSg = new JTextField(2);
    textSg.setText(YiJing.JINGGUANAME[vo!=null?vo.getUp():0]);
    //textSg.setSize(new Dimension(20,14));
    box.add(textSg);
    box.add(new JLabel("  "));

    //makeField("下卦", 3, box, "TEXTFIELD");
    JLabel labelXg = new JLabel("下卦");
    box.add(labelXg);
    textXg = new JTextField(2);
    textXg.setText(YiJing.JINGGUANAME[vo!=null?vo.getDown():0]);
    //textXg.setSize(new Dimension(20,14));
    box.add(textXg);
    box.add(new JLabel("  "));

    //makeField("动爻", 3, box, "TEXTFIELD");
    JLabel labelDy = new JLabel("动爻");
    box.add(labelDy);
    textDy = new JTextField(11);
    textDy.setText(vo!=null?vo.getDy():"");
    //textDy.setSize(new Dimension(20,14));
    box.add(textDy);
    box.add(new JLabel("  "));

    box_.add(box1);
    box_.add(box);

    return box_;
  }

  private String getYaoGuaXiang(int yao,int local) {
    return "    "+
        YiJing.YAONAME[yao]+
        "    "+
        (dongyaos.indexOf(""+local)>=0?YiJing.YAODONG[yao]:"");
  }

  /**
   * 摇卦，一次从1、2两个数字中得到三个，如果是111，则为阳动，222为阴动，112为阴，122为阳
   * 原理：取三个随机数，按偶数阳，奇数阴，按上取。
   * @return
   */
  private Box getPanelCenterDown() {
    //Box box = new Box(BoxLayout.Y_AXIS);
    Box boxX = new Box(BoxLayout.X_AXIS);
    CommandAction action1 = new CommandAction("初爻", null, "摇卦的第一爻象", ' ',
                                              new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        yiyao = getYaoGuaYinYang(1);
        button1.setEnabled(false);
        button2.setEnabled(true);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
        button6.setEnabled(false);
        jTxtAreaYg.setText("");
        jTxtAreaYg.append("\r\n");
        jTxtAreaYg.append("    "+getYaoGuaXiang(yiyao,1));
        jTxtAreaYg.append("\r\n");
      }
    });
    button1 = new JButton(action1);
    CommandAction action2 = new CommandAction("二爻", null,
                                              "摇卦的第二爻象", ' ',
                                              new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        eryao = getYaoGuaYinYang(2);
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(true);
        button4.setEnabled(false);
        button5.setEnabled(false);
        button6.setEnabled(false);
        jTxtAreaYg.setText("");
        jTxtAreaYg.append("\r\n");
        jTxtAreaYg.append("    "+getYaoGuaXiang(eryao,2));
        jTxtAreaYg.append("\r\n");
        jTxtAreaYg.append("    "+getYaoGuaXiang(yiyao,1));
        jTxtAreaYg.append("\r\n");
      }
    });
    button2 = new JButton(action2);
    CommandAction action3 = new CommandAction("三爻", null,
                                              "摇卦的第三爻象", ' ',
                                              new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        shanyao = getYaoGuaYinYang(3);
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(true);
        button5.setEnabled(false);
        button6.setEnabled(false);
        jTxtAreaYg.setText("");
        jTxtAreaYg.append("\r\n");
        jTxtAreaYg.append("    "+getYaoGuaXiang(shanyao,3));
        jTxtAreaYg.append("\r\n");
        jTxtAreaYg.append("    "+getYaoGuaXiang(eryao,2));
        jTxtAreaYg.append("\r\n");
        jTxtAreaYg.append("    "+getYaoGuaXiang(yiyao,1));
        jTxtAreaYg.append("\r\n");
      }
    });
    button3 = new JButton(action3);
    CommandAction action4 = new CommandAction("四爻", null,
                                              "摇卦的第四爻象", ' ',
                                              new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        siyao = getYaoGuaYinYang(4);
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(true);
        button6.setEnabled(false);
        jTxtAreaYg.setText("");
        jTxtAreaYg.append("\r\n");
        jTxtAreaYg.append("    "+getYaoGuaXiang(siyao,4));
        jTxtAreaYg.append("\r\n");
        jTxtAreaYg.append("    "+getYaoGuaXiang(shanyao,3));
        jTxtAreaYg.append("\r\n");
        jTxtAreaYg.append("    "+getYaoGuaXiang(eryao,2));
        jTxtAreaYg.append("\r\n");
        jTxtAreaYg.append("    "+getYaoGuaXiang(yiyao,1));
        jTxtAreaYg.append("\r\n");
      }
    });
    button4 = new JButton(action4);
    CommandAction action5 = new CommandAction("五爻", null,
                                              "摇卦的第五爻象", ' ',
                                              new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        wuyao = getYaoGuaYinYang(5);
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
        button6.setEnabled(true);
        jTxtAreaYg.setText("");
        jTxtAreaYg.append("\r\n");
        jTxtAreaYg.append("    "+getYaoGuaXiang(wuyao,5));
        jTxtAreaYg.append("\r\n");
        jTxtAreaYg.append("    "+getYaoGuaXiang(siyao,4));
        jTxtAreaYg.append("\r\n");
        jTxtAreaYg.append("    "+getYaoGuaXiang(shanyao,3));
        jTxtAreaYg.append("\r\n");
        jTxtAreaYg.append("    "+getYaoGuaXiang(eryao,2));
        jTxtAreaYg.append("\r\n");
        jTxtAreaYg.append("    "+getYaoGuaXiang(yiyao,1));
        jTxtAreaYg.append("\r\n");
      }
    });
    button5 = new JButton(action5);
    CommandAction action6 = new CommandAction("上爻", null,
                                              "摇卦的第上爻象", ' ',
                                              new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        liuyao = getYaoGuaYinYang(6);
        getYaoGuaDongYao();
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
        button6.setEnabled(false);
        jTxtAreaYg.setText("");
        jTxtAreaYg.append("\r\n");
        jTxtAreaYg.append("    "+getYaoGuaXiang(liuyao,6));
        jTxtAreaYg.append("\r\n");
        jTxtAreaYg.append("    "+getYaoGuaXiang(wuyao,5));
        jTxtAreaYg.append("\r\n");
        jTxtAreaYg.append("    "+getYaoGuaXiang(siyao,4));
        jTxtAreaYg.append("\r\n");
        jTxtAreaYg.append("    "+getYaoGuaXiang(shanyao,3));
        jTxtAreaYg.append("\r\n");
        jTxtAreaYg.append("    "+getYaoGuaXiang(eryao,2));
        jTxtAreaYg.append("\r\n");
        jTxtAreaYg.append("    "+getYaoGuaXiang(yiyao,1));
        jTxtAreaYg.append("\r\n");
        clear();
      }
    });
    button6 = new JButton(action6);

    boxX.add(button1);
    boxX.add(button2);
    boxX.add(button3);
    boxX.add(button4);
    boxX.add(button5);
    boxX.add(button6);
    boxX.add(new JLabel(""));

    return boxX;
  }

  /**
   * 模拟摇卦，得到阴或阳
   * 2为阴，1为阳
   * @return
   */
  private int getYaoGuaYinYang(int local) {
    int i1 = (int)(Math.random()*100);
    int i2 = (int)(Math.random()*100);
    int i3 = (int)(Math.random()*100);
    int count = 0;

    if(i1%2==0) ++count;
    if(i2%2==0) ++count;
    if(i3%2==0) ++count;
    //Debug.out("    "+i1+":"+i2+":"+i3+":"+count);
    if(local<6) {
      if (count == 0 || count == 3)
        dongyaos += local + ",";
    }else{
      if (count == 0 || count == 3)
        dongyaos += local;
    }

    return count%2==0?1:2;
  }

  /**
   * 清除掉摇卦的一些变量数值
   */
  private void clear() {
    dongyaos = "";
    //changes = new int[6];
  }

  /**
   * 模拟摇卦，得到阴或阳
   * @return
   */
  private void getYaoGuaDongYao() {
    changes = new int[6];
    for(int i=0; i<changes.length; i++) {
      changes[i]=0;
    }

    String[] dy_ = dongyaos.split(",");
    for(int i=0; i<dy_.length; i++) {
      //Debug.out(i+"="+dy_[i]);
      if(dy_[i]==null || dy_[i].trim().equals(""))
        break;
      changes[i] = Integer.valueOf(dy_[i]).intValue();
    }

    int len = 0;

    for(int i=0; i<changes.length; i++) {
      if(changes[i]>0) len++;
    }

    int[] _changes = new int[len];
    for(int i=0; i<len; i++) {
      _changes[i] = changes[i];
    }

    changes = _changes;

  }

  /**
   * 由标签统一返回其标签其输入栏
   * @param labelText
   * @param len
   * @param box
   * @param type
   * @return
   */
  protected Box makeField(String labelText, int len, Box box, String type) {
    if (type.equals("TEXTFIELD")) {
      JLabel label = new JLabel(labelText);
      box.add(label);
      JTextField text = new JTextField(len);
      text.setSize(new Dimension(20,14));
      box.add(text);
    }
    else if (type.equals("CHECKBOX")) {
      JCheckBox c = new JCheckBox(labelText, false);
      c.addItemListener(new TabManager(new JLabel("yun")));
      box.add(c);
    }

    return box;
  }

  class TabManager
      implements ItemListener {
    Component tab;
    public TabManager(Component tabToManage) {
      tab = tabToManage;
    }

    public void itemStateChanged(ItemEvent ie) {
    }
  }
  public JTextArea getJTextArea() {
    return this.jTextArea;
  }
  public void getParameter(String type) {
    String _y = textYear.getText().trim();
    String _m = textMonth.getText().trim();
    String _d = textDay.getText().trim();
    String hms = textHour.getText().trim();
    String type1 = groupCurOrIns.getSelection().getActionCommand();

    memo = getJTextArea().getText();

    if(!type.trim().equals("yg")) {
      if(!"dangqian".equals(type1)) {
        if (_y.indexOf(",") == -1) {
          typeSjOrgz = false;
          year = _y.equals("") ? 0 : Integer.valueOf(_y).intValue();
        }
        else {
          typeSjOrgz = true;
          year = 0;
          String _y1 = _y.substring(0, _y.indexOf(","));
          String _y2 = _y.substring(_y.indexOf(",") + 1);
          yg = Integer.valueOf(_y1).intValue();
          yz = Integer.valueOf(_y2).intValue();
        }
        if (!typeSjOrgz) {
          month = _m.equals("") ? 0 : Integer.valueOf(_m).intValue();
        }
        else {
          month = 0;
          String _y1 = _m.substring(0, _m.indexOf(","));
          String _y2 = _m.substring(_m.indexOf(",") + 1);
          mg = Integer.valueOf(_y1).intValue();
          mz = Integer.valueOf(_y2).intValue();
        }

        if (!typeSjOrgz) {
          day = _d.equals("") ? 0 : Integer.valueOf(_d).intValue();
        }
        else {
          day = 0;
          String _y1 = _d.substring(0, _d.indexOf(","));
          String _y2 = _d.substring(_d.indexOf(",") + 1);
          dg = Integer.valueOf(_y1).intValue();
          dz = Integer.valueOf(_y2).intValue();
        }

        if (!typeSjOrgz) {
          String[] h = hms.split(":");
          if (h.length == 1) {
            hour = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
            minute = 0;
            second = 0;
          }
          if (h.length == 2) {
            hour = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
            minute = h[1] == null ? 0 : Integer.valueOf(h[1]).intValue();
            second = 0;
          }
          if (h.length == 3) {
            hour = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
            minute = h[1] == null ? 0 : Integer.valueOf(h[1]).intValue();
            second = h[2] == null ? 0 : Integer.valueOf(h[2]).intValue();
          }
        }
        else {
          hour = 0;
          minute = 0;
          second = 0;
          String _y1 = hms.substring(0, hms.indexOf(","));
          String _y2 = hms.substring(hms.indexOf(",") + 1);
          hg = Integer.valueOf(_y1).intValue();
          hz = Integer.valueOf(_y2).intValue();
        }
      }
    }

    if(type.trim().equals("gx")) {
      getGuaXiang();
      if(typeSjOrgz) {
        SiZhu.yinli="";
        SiZhu.yangli="";
      }
    }

    if(type.trim().equals("yg") || "dangqian".equals(type1)) {
      //isYY = false;
      _getCurShiJian();
    }
    //只要不是干支，不管是输入时间还是当前时间都要转化成干支，在卦象、时间、摇卦时用
    if(!typeSjOrgz) {
      c.calculate(year,month,day,hour,minute,isYin,true,-1,-1);
      yg = SiZhu.ng;
      yz = SiZhu.nz;
      mg = SiZhu.yg;
      mz = SiZhu.yz;
      dg = SiZhu.rg;
      dz = SiZhu.rz;
      hg = SiZhu.sg;
      hz = SiZhu.sz;
    }
  }

  private int getGuaShu(String s) {
    for(int i=0; i<YiJing.JINGGUANAME.length; i++) {
      if (s.trim().equals(YiJing.JINGGUANAME[i]))
        return i;
    }
    return Integer.valueOf(s).intValue();
  }

  /**
   * 检查各个框所填的格式是否正确
   * @return
   */
  private String checkInputs(String type) {
    String err = null;
    if(type.trim().equals("gx")) {
      int sg1 = textSg.getText().trim().equals("")?0:getGuaShu(textSg.getText());
      int xg1 = textXg.getText().trim().equals("")?0:getGuaShu(textXg.getText());
      String dy1 = textDy.getText().trim();

      if(sg1>8 || sg1<1) return "上卦数必须是先天八卦数，在1与8之间";
      if(xg1>8 || xg1<1) return "下卦数必须是先天八卦数，在1与8之间";
      if(dy1.startsWith(",") || dy1.length()>12) return "动爻必须以','分隔，且不能以','开头，最多只能六个爻动，如：1,2,3,4,5,6";
      if(!dy1.trim().equals("")) {
        String[] dys = dy1.split(",");
        for (int i = 0; i < dys.length; i++) {
          int j = 0;
          try{
            j = Integer.valueOf(dys[i]).intValue();
          }catch(Exception e) {
            return "动爻必须在1与6之间，如4、5、6爻均动，则填写为：4,5,6";
          }
          if (j > 6 || j < 1) {
            return "多动爻形式为：1,3,6，其中每个动爻均必须在1与6之间";
          }
        }
      }
    }

    if(!type.trim().equals("yg")) {
      String type1 = groupCurOrIns.getSelection().getActionCommand();
      if (!"dangqian".equals(type1)) {
        String _y = textYear.getText().trim();
        String _m = textMonth.getText().trim();
        String _d = textDay.getText().trim();
        String hms = textHour.getText().trim();

        DaoPublic pub = new DaoPublic();
        return pub.checks(_y, _m, _d, hms, isYin, isYun);
      }
    }

    return err;
  }

  private void _getCurShiJian() {
    typeSjOrgz = false;
    yg=yz=mg=mz=dg=dz=hg=hz=0;
    isYin = false;   //当前时间是阳历
    isYun = true;   //当前时间不管闰不闰，为闰月总不会错
    Date date = new Date();
    year = date.getYear()+1900;
    month = date.getMonth()+1;
    day = date.getDate();
    hour = date.getHours();
    minute = date.getMinutes();
    second = date.getSeconds();
    c.calculate(year,month,day,hour,minute,isYin,true,-1,-1);
    yg = SiZhu.ng;
    yz = SiZhu.nz;
    mg = SiZhu.yg;
    mz = SiZhu.yz;
    dg = SiZhu.rg;
    dz = SiZhu.rz;
    hg = SiZhu.sg;
    hz = SiZhu.sz;
  }

  /**
   * 得到右边的输入框
   * @return
   */
  private Box getRightPanel() {
    JPanel leftPanel = new JPanel();

    radioYin = new JRadioButton("农历",vo!=null && vo.isIsYin());
    radioYin.setActionCommand("yinli");
    radioYang = new JRadioButton("阳历",vo!=null && !vo.isIsYin());
    radioYang.setActionCommand("yangli");
    groupYYli = new ButtonGroup();
    groupYYli.add(radioYin);
    groupYYli.add(radioYang);


    Box buttonBox = new Box(BoxLayout.Y_AXIS);
    buttonBox.add(new JLabel("选择日历："));
    buttonBox.add(radioYin);
    buttonBox.add(radioYang);

    return buttonBox;
  }

  protected Component getThis() {
    return this;
  }

  /**
   * 设置所有摇卦的按钮的可用性
   */
  private void setButtonEnable(boolean bool) {
    button1.setEnabled(bool);
    button2.setEnabled(bool);
    button3.setEnabled(bool);
    button4.setEnabled(bool);
    button5.setEnabled(bool);
    button6.setEnabled(bool);
  }

  /**
   * 设置所有按卦象起卦的文本框的可用性
   */
  private void setGuaXiangEnable(boolean bool) {
    textSg.setEnabled(bool);
    textXg.setEnabled(bool);
    textDy.setEnabled(bool);
  }

  /**
   * 设置所有时间的文本框的可用性
   */
  private void setYearMDHEnable(boolean bool) {
    textYear.setEnabled(bool);
    textDay.setEnabled(bool);
    textMonth.setEnabled(bool);
    textHour.setEnabled(bool);
    checkboxYun.setEnabled(bool);
    radioCur.setEnabled(bool);
    radioIns.setEnabled(bool);
  }

  /**
   * 得到用神
   * @return
   */
  private void getYongShen() {
    if(groupYongShen.getSelection()==null) {
      yongshen = YiJing.SHIYAO;
      return;
    }

    String ys = groupYongShen.getSelection().getActionCommand();
    if("shiyao".equals(ys)) {
      yongshen = YiJing.SHIYAO;
    }else if("fumu".equals(ys)) {
      yongshen = YiJing.FUMU;
    }else if("zisun".equals(ys)) {
      yongshen = YiJing.ZISUI;
    }else if("qicai".equals(ys)) {
      yongshen = YiJing.QICAI;
    }else if("xiongdi".equals(ys)) {
      yongshen = YiJing.XIONGDI;
    }else {
      yongshen = YiJing.GUANGUI;
    }
  }

  /**
   * 得到阴历或阳历
   * @return
   */
  private void getYYli() {
    if(groupYYli.getSelection()==null) {
      isYin = false;
      return;
    }

    String type = groupYYli.getSelection().getActionCommand();
    if("yinli".equals(type)) {
      isYin = true;
    }else{
      isYin = false;
    }
  }

  /**
   * 得到是否是闰月
   * @return
   */
  private void isYunYue() {
    isYun = checkboxYun.isSelected();
  }

  /**
   * 得到上下卦及动爻数
   * @return
   */
  private void getGuaXiang() {
    sg = getGuaShu(textSg.getText())%8==0?8:getGuaShu(textSg.getText())%8;
    xg = getGuaShu(textXg.getText())%8==0?8:getGuaShu(textXg.getText())%8;
    String dy = textDy.getText();
    String[] dys1 = dy.split(",");
    changes = new int[dys1.length];
    for(int i=0; i<dys1.length; i++) {
      if(dys1[i]==null || dys1[i].trim().equals(""))
        break;
      changes[i] = Integer.valueOf(dys1[i]).intValue();
    }
  }

  /**
   * 得到左边的面板
   * @return
   */
  private Box getLeftPanel() {
    JPanel leftPanel = new JPanel();

    JRadioButton radio1 = new JRadioButton("卦    象",vo!=null && vo.getQgfs()==1);
    radio1.setActionCommand("gx");
    JRadioButton radio2 = new JRadioButton("时    间", vo!=null && vo.getQgfs()==2);
    radio2.setActionCommand("sj");
    JRadioButton radio3 = new JRadioButton("摇    卦", vo!=null && vo.getQgfs()==3);
    radio3.setActionCommand("yg");

    class QiguaActionListener implements ActionListener {
      public void actionPerformed(ActionEvent ev) {
        String choice = groupQiguafs.getSelection( ).getActionCommand( );
        if(choice.trim().equals("gx")) {
            setButtonEnable(false);
            setGuaXiangEnable(true);
            setYearMDHEnable(true);
            radioYin.setEnabled(true);
            radioYang.setEnabled(true);
            radioIns.setSelected(true);
          }else if(choice.trim().equals("sj")) {
            setButtonEnable(false);
            setGuaXiangEnable(false);
            setYearMDHEnable(true);
            radioYin.setEnabled(true);
            radioYang.setEnabled(true);
            radioIns.setSelected(true);
          }else {
            setButtonEnable(true);
            setGuaXiangEnable(false);
            setYearMDHEnable(false);
            radioYin.setEnabled(false);
            radioYang.setEnabled(false);
            button1.setEnabled(true);
            button2.setEnabled(false);
            button3.setEnabled(false);
            button4.setEnabled(false);
            button5.setEnabled(false);
            button6.setEnabled(false);
          }
      }
    }
    ActionListener alisten = new QiguaActionListener();
    radio1.addActionListener(alisten);
    radio2.addActionListener(alisten);
    radio3.addActionListener(alisten);

    groupQiguafs = new ButtonGroup();
    groupQiguafs.add(radio1);
    groupQiguafs.add(radio2);
    groupQiguafs.add(radio3);

    Box buttonBox = new Box(BoxLayout.Y_AXIS);
    buttonBox.add(new JLabel("起卦方式："));
    buttonBox.add(radio1);
    buttonBox.add(radio2);
    buttonBox.add(radio3);

    return buttonBox;
  }
  
  /**
   * 得到人员的其它信息
   * 如附注，单位， 职务， 工作地址，居住地址
   * 邮政编码， 电话， 手机， email, QQ， MSN
   * @return
   */
  private JTextArea jTextArea;
  private JScrollPane jScrollPane;
  public Box getOtherInfoPane(JButton saveB, String memo) {
    Box box = new Box(BoxLayout.Y_AXIS);

    box.add(new JLabel("  "));
    box.add(new JLabel("  "));
    box.add(new JLabel("  "));

    Box p1 = new Box(BoxLayout.X_AXIS);
    p1.add(new JLabel("附注信息：                                           "));
    p1.add(saveB);
    box.add(p1);
    jTextArea = new JTextArea();
    jTextArea.setLineWrap(false);
    jTextArea.setText(memo);
  //以下分别是显示第一行，不用每次都滚动到最后一行显示
    jTextArea.setCaretPosition(0);
    jTextArea.setSelectionStart(0);
    jTextArea.setSelectionEnd(0);
    
    //jTextArea.setForeground(java.awt.Color.white);
    //jTextArea.setRows(20);
    //jTextArea.setColumns(100);
    jScrollPane = new JScrollPane(jTextArea);
    box.add(jScrollPane);

    return box;
  }

  public static void main(String[] args) {
    //YijingFrame frame = new YijingFrame();
    //frame.setVisible(true);
    String hms = "10,9";
    String _y1 = hms.substring(0,hms.indexOf(","));
    String _y2 = hms.substring(hms.indexOf(",")+1);
    int _i1 = Integer.valueOf(_y1).intValue();
    int _i2 = Integer.valueOf(_y2).intValue();

    System.out.println(_i1+":"+_i2);
    /*
    Date date = new Date();
    int year = date.getYear();
    int month = date.getMonth();
    int day = date.getDate();
    int hour = date.getHours();
    int minute = date.getMinutes();
    int second = date.getSeconds();
    Debug.err(year+"-"+month+"-"+day+":"+hour);
        */

  }
}
