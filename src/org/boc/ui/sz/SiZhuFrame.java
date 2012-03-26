package org.boc.ui.sz;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.boc.dao.DaoCalendar;
import org.boc.dao.DaoPublic;
import org.boc.db.Calendar;
import org.boc.db.SiZhu;
import org.boc.delegate.DelSiZhuMain;
import org.boc.delegate.DelYiJingMain;
import org.boc.ui.BasicJPanel;
import org.boc.ui.CommandAction;
import org.boc.util.Public;
import org.boc.util.VO;

public class SiZhuFrame
    extends BasicJPanel {
  private JCheckBox checkboxYun; //是否闰月
  private JTextField textYear;   //年
  private JTextField textMonth;  //月
  private JTextField textDay;    //日
  private JTextField textHour;   //时分秒
  public boolean isYun;         //是否是闰月
  public boolean isYin;         //是否是阴历
  public boolean isBoy;         //是否是男孩

  private ButtonGroup groupYYli;     //阴阳历
  private ButtonGroup groupNanLv;    //男或女
  private ButtonGroup groupType;    //日期或八字

  private JRadioButton radioBazi;     //输入八字
  private JRadioButton radioShijian;  //输入时间
  private JRadioButton radioYin;      //阴历
  private JRadioButton radioYang;     //阳历
  private JRadioButton radioBoy;      //男
  private JRadioButton radioGirl;     //女
  private int isheng=0, ishi=0;

  public int ng,nz,yg,yz,rg,rz,sg,sz;
  public int year,month,day,hour,minute,second;

  private DelSiZhuMain dels;
  private DelYiJingMain dely ;
  private DaoPublic pub ;
  private DaoCalendar c;

  private String fileId;
  private String rowId;
  private String parentNode;
  private String memo;
  public VO vo;
  private String yText, mText, dText, hText;

  public SiZhuFrame() {
    this.setLayout(new BorderLayout());
    pub = new DaoPublic();
    c = new DaoCalendar();
    dels = new DelSiZhuMain();
    dely = new DelYiJingMain();
    isheng = -1;
    ishi = -1;
    this.setRoot(Public.valueRoot[5]);
  }

  public void finalize() {
    pub = null;
    c = null;
    dels = null;
    dely = null;
  }

  public void init(String fileId, String rowId, String parentNode1) {
    vo = (VO)Public.getObjectFromFile(fileId, rowId);
    //设置对象所属根
    if (vo != null) {
      isYun = vo.isIsYun();
      isBoy = vo.isIsBoy();
      isYin = vo.isIsYin();

      isYun = vo.isIsYin();
      isBoy = vo.isIsBoy();
      isYin = vo.isIsYin();

      ng = vo.getNg();
      nz = vo.getNz();
      yg = vo.getYg();
      yz = vo.getYz();
      rg = vo.getRg();
      rz = vo.getRz();
      sg = vo.getSg();
      sz = vo.getSz();

      year = vo.getYear();
      month = vo.getMonth();
      day = vo.getDay();
      hour = vo.getHour() ;
      minute = vo.getMinute();

      if(year==0) {
        yText = ng+","+nz;
        mText = yg + "," + yz;
        dText = rg + "," + rz;
        hText = sg + "," + sz;
      }else {
        yText = ""+year;
        mText = ""+ month;
        dText = ""+ day;
        hText = hour+":"+minute+":00";
      }
      isheng = vo.getIsheng();
      ishi = vo.getIshi();
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
    this.add(getOtherInfoPane(getSaveJButton(), memo), BorderLayout.CENTER);
  }

  public JButton getSaveJButton() {
    CommandAction actionXX = new CommandAction("保存", null, "", ' ',
                                               new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String type = _check();
        if(type==null)
          return;
        if(!type.equals("shijian"))
          vo = new VO(ng,nz,yg,yz,rg,rz,sg,sz,isBoy,true, isYun, isheng, ishi);
        else
          vo = new VO(year,month,day,hour,minute,isBoy,isYin, isYun, isheng, ishi);

        vo.setRowId(rowId);
        vo.setFileId(fileId);
        //不取子目录了 一律取预测术的根目录，即取root的值
        vo.setRoot(getRoot());  //此处是四柱的值
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
   * 输出八字信息
   */
  public String do1() {
    if (vo == null)return "";
    if (vo.getYear() == 0)
      return dels.getMingYun(ng,nz,yg,yz,rg,rz,sg,sz,isYun,isBoy);
    return dels.getMingYun(year,month,day,hour,minute,isYin, isYun, isheng, ishi, isBoy);
  }

  /**
   * 输出终身卦信息
   */
  public String do2() {
    if (vo == null)return "";
    String str = "";

    str += "\r\n    按年干起终身卦：\r\n";
    int upGua = (SiZhu.ng+Calendar.MONTHN1 + Calendar.DAYN1)%8==0?8:(SiZhu.ng+Calendar.MONTHN1 + Calendar.DAYN1)%8;
    int downGua = (upGua + SiZhu.sz)%8==0?8:(upGua + SiZhu.sz)%8;
    int change = (SiZhu.ng+Calendar.MONTHN1 + Calendar.DAYN1+SiZhu.sz)%6==0?6:(SiZhu.ng+Calendar.MONTHN1 + Calendar.DAYN1+SiZhu.sz)%6;
    str += dely.getGuaXiang( upGua, downGua, new int[]{change},
                             new int[]{0,SiZhu.ng,SiZhu.nz,SiZhu.yg,SiZhu.yz,SiZhu.rg,SiZhu.rz,SiZhu.sg,SiZhu.sz},0);

    str += "\r\n    按年支起终身卦：\r\n";
    upGua = (SiZhu.nz+Calendar.MONTHN1 + Calendar.DAYN1)%8==0?8:(SiZhu.nz+Calendar.MONTHN1 + Calendar.DAYN1)%8;
    downGua = (upGua + SiZhu.sz)%8==0?8:(upGua + SiZhu.sz)%8;
    change = (SiZhu.nz+Calendar.MONTHN1 + Calendar.DAYN1+SiZhu.sz)%6==0?6:(SiZhu.nz+Calendar.MONTHN1 + Calendar.DAYN1+SiZhu.sz)%6;
    str += dely.getGuaXiang( upGua, downGua, new int[]{change},
                             new int[]{0,SiZhu.ng,SiZhu.nz,SiZhu.yg,SiZhu.yz,SiZhu.rg,SiZhu.rz,SiZhu.sg,SiZhu.sz},0);

    return str;
  }

  /**
   * 输出格局信息
   */
  public String do3() {
    if (vo == null)return "";
    if (vo.getYear() == 0)
      return dels.getGlobleInfo(ng,nz,yg,yz,rg,rz,sg,sz,isYun,isBoy);
    return dels.getGlobleInfo(year,month,day,hour,minute,isYin,isYun,isheng,ishi,isBoy);
  }

  /**
   * 输出事业信息
   */

  /**
   * 输出婚姻信息
   */
  public String do5() {
    if (vo == null)return "";
    if (vo.getYear() == 0)
      return dels.getHunYinInfo(ng,nz,yg,yz,rg,rz,sg,sz,isYun,isBoy);
    return dels.getHunYinInfo(year,month,day,hour,minute,isYin,isYun,isheng,ishi,isBoy);
  }

  /**
   * 输出六亲信息
   */

  /**
   * 输出性格信息
   */
  public String do7() {
    if (vo == null)return "";
    if (vo.getYear() == 0)
      return dels.getXingGeInfo(ng,nz,yg,yz,rg,rz,sg,sz,isYun,isBoy);
    return dels.getXingGeInfo(year,month,day,hour,minute,isYin,isYun,isheng,ishi,isBoy);
  }

  /**
   * 输出病凶信息
   */

  /**
   * 由二大面板组成录入面板
   * @return
   */
  private JPanel getInputPanel() {
    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
    inputPanel.add(getUpPanel());
    inputPanel.add(getCenterPanel());

    return inputPanel;
  }

  /**
   * 得到第一排的面板
   * @return
   */
  private Box getUpPanel() {
    Box box = new Box(BoxLayout.X_AXIS);

    radioShijian = new JRadioButton("时间",true);
    radioShijian.setActionCommand("shijian");
    radioBazi = new JRadioButton("八字");
    radioBazi.setActionCommand("bazi");

    if (this.isYin) {
      radioYin = new JRadioButton("阴历", true);
      radioYang = new JRadioButton("阳历");
    }
    else {
      radioYin = new JRadioButton("阴历");
      radioYang = new JRadioButton("阳历", true);
    }
    radioYin.setActionCommand("yinli");
    radioYang.setActionCommand("yangli");
    if (this.isBoy) {
      radioBoy = new JRadioButton("男", true);
      radioGirl = new JRadioButton("女");
    }
    else {
      radioBoy = new JRadioButton("男");
      radioGirl = new JRadioButton("女", true);
    }
    radioBoy.setActionCommand("nan");
    radioGirl.setActionCommand("lv");

    box.add(new JLabel("类型："));
    groupType = new ButtonGroup();
    groupType.add(radioShijian);
    groupType.add(radioBazi);
    box.add(radioShijian);
    box.add(radioBazi);
    box.add(new JLabel("    "));
    box.add(new JLabel("历法："));
    groupYYli = new ButtonGroup();
    groupYYli.add(radioYin);
    groupYYli.add(radioYang);
    box.add(radioYin);
    box.add(radioYang);
    box.add(new JLabel("     "));
    box.add(new JLabel("性别："));
    groupNanLv = new ButtonGroup();
    groupNanLv.add(radioBoy);
    groupNanLv.add(radioGirl);
    box.add(radioBoy);
    box.add(radioGirl);
    box.add(new JLabel("    "));
    box.add(new JLabel("出生地："));
    //box.add(this.getComboShengShi());

    return box;
  }

  /**
   * 得到第二排的面板
   * @return
   */
  private Box getCenterPanel() {
    Box box = new Box(BoxLayout.X_AXIS);


    JLabel labelYear = new JLabel("年");
    box.add(labelYear);
    textYear = new JTextField(4);
    textYear.setText(yText);
    box.add(textYear);
    box.add(new JLabel("    "));

    checkboxYun = new JCheckBox("闰", isYun);
    box.add(checkboxYun);

    JLabel labelMonth = new JLabel("月");
    box.add(labelMonth);
    textMonth = new JTextField(2);
    textMonth.setText(mText);
    box.add(textMonth);
    box.add(new JLabel("    "));

    JLabel labelDay = new JLabel("日");
    box.add(labelDay);
    textDay = new JTextField(2);
    textDay.setText(dText);
    box.add(textDay);
    box.add(new JLabel("    "));

    JLabel labelHour = new JLabel("时");
    box.add(labelHour);
    textHour = new JTextField(8);
    textHour.setText(hText);
    box.add(textHour);
    box.add(new JLabel("    "));

    return box;
  }

  private String _check() {
    String type = groupType.getSelection().getActionCommand();
    String rili = groupYYli.getSelection().getActionCommand();
    String xingbie = groupNanLv.getSelection().getActionCommand();
    String y = textYear.getText();
    String m = textMonth.getText();
    String d = textDay.getText();
    String h = textHour.getText();

    isYun = checkboxYun.isSelected();
    isYin = rili.equals("yinli")?true:false;
    isBoy = xingbie.equals("nan")?true:false;

    memo = getJTextArea().getText();

    String checks = checkInputs(y,m,d,h,type.equals("shijian"));
    if(checks != null) {
      JOptionPane.showMessageDialog(getThis(), checks, "提示信息",JOptionPane.INFORMATION_MESSAGE);
      return null;
    }
    getParameters(type, y, m, d, h);

    return type;
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
   * 得到输入参数
   */
  private void getParameters(String type, String _y, String _m,
                             String _d, String hms) {
    if (type.equals("shijian")) {
      year = _y.equals("") ? 0 : Integer.valueOf(_y).intValue();
    }
    else {
      year = 0;
      String _y1 = _y.substring(0, _y.indexOf(","));
      String _y2 = _y.substring(_y.indexOf(",") + 1);
      ng = Integer.valueOf(_y1).intValue();
      nz = Integer.valueOf(_y2).intValue();
    }

    if (type.equals("shijian")) {
      month = _m.equals("") ? 0 : Integer.valueOf(_m).intValue();
    }
    else {
      month = 0;
      String _y1 = _m.substring(0, _m.indexOf(","));
      String _y2 = _m.substring(_m.indexOf(",") + 1);
      yg = Integer.valueOf(_y1).intValue();
      yz = Integer.valueOf(_y2).intValue();
    }

    if (type.equals("shijian")) {
      day = _d.equals("") ? 0 : Integer.valueOf(_d).intValue();
    }
    else {
      day = 0;
      String _y1 = _d.substring(0, _d.indexOf(","));
      String _y2 = _d.substring(_d.indexOf(",") + 1);
      rg = Integer.valueOf(_y1).intValue();
      rz = Integer.valueOf(_y2).intValue();
    }

    if (type.equals("shijian")) {
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
      sg = Integer.valueOf(_y1).intValue();
      sz = Integer.valueOf(_y2).intValue();
    }

    if (!type.equals("shijian")) {
      SiZhu.yinli = "";
      SiZhu.yangli = "";
    }
  }

  /**
   * 检查各个框所填的格式是否正确
   * @return
   */
  private String checkInputs(String y, String m,String d, String h, boolean is) {
    return pub.checks(y,m,d,h,isYin,isYun, is);
  }
}
