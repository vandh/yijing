package org.boc.ui.qm;

import static org.boc.ui.UIPublic.getInputWindow;
import static org.boc.ui.UIPublic.getLinkText;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.boc.biz.qm.QMBusiness;
import org.boc.dao.qm.DaoQiMen;
import org.boc.dao.sz.DaoSiZhuMain;
import org.boc.db.Calendar;
import org.boc.db.qm.QiMen;
import org.boc.ui.MyTextPane;
import org.boc.ui.UIPublic;
import org.boc.ui.UIPublic.NormalInputWindow;
import org.boc.util.HtmlMultiLineControl;
import org.boc.util.Messages;
import org.boc.util.PrintWriter;
import org.boc.util.Public;

public class QimenInputPanel extends JPanel{
	private HtmlMultiLineControl html;
	private QiMenFrame frame;
	private DaoQiMen daoqm;
	private DaoSiZhuMain daosz;
	private PrintWriter pw = new PrintWriter();
	private NormalInputWindow memoWin;			//备注窗口
	private JDialog ruleWin;								//规则录入窗口
	private MyTextPane ruleTextPane;				//规则输入框
	private InputFocusListener inputFocus;
	private QMBusiness qmbiz;  							//奇门规则引擎
	
	private boolean isBoy;  
  private JTextField textMZhu;    //命主可为1977;1988;等多个年份或1,1;2,2;等多个干支
  private JTextField textNum;			//报数起局
  private JTextField textHour;		//报时辰起局
  private JComboBox comboyyDunJu;    //阴阳遁局
  private JComboBox comboYShen;    //用神年、月、日、时
  private JComboBox comboRule;    	//规则引擎定制
  private ButtonGroup groupNanLv;    //男或女
  private JComboBox comboSheng;      //省
  private JComboBox comboShi;        //市
  private JRadioButton radioBoy;      //男
  private JRadioButton radioGirl;     //女
  private String mzText;  //命主的干支或数字形式，为1993;1992; |1,1; 2,2;等形式，不用转换，用时再处理
  private int iprovince = -1;  
  private int icity = -1;
  private int ruleIndex = 0;  			//规则引擎选择记录
  private int yydunNum;              //阴遁或阳遁的局数
  private int ysNum; 								//用神对应的四柱，分别是年1月2日3时4  
  private String[] sheng;
  private String[] shi;
  
  public static final int Height = 25;
  public static final int Width = 140;
  public static final int Width2 = 80;
  
	public QimenInputPanel(QiMenFrame frame){
		this.frame = frame;
		daoqm = frame.getDelQiMenMain().getDaoQiMen();
		daosz = frame.getDelQiMenMain().getDaoSiZhuMain();
		
		isBoy = frame.isBoy();
		iprovince = frame.getProvince();
		icity = frame.getCity();
		yydunNum = frame.getJu();
		ysNum = frame.getYsnum();
		html = new HtmlMultiLineControl();
		inputFocus = new InputFocusListener();
		//处理备注信息
		memoWin = (NormalInputWindow)getInputWindow("备注信息",
				QimenPopupMenu.WIDTH1,QimenPopupMenu.HEIGHT1 ,true, false, false);
		//关闭备注窗口时，调用保存按钮
		try {
			memoWin.setCloseCallback(this, this.getClass().getMethod("saveMemo"));
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public NormalInputWindow getMemoWin() {
		return this.memoWin;
	}
	public JToolBar getInputPanel() {	
		MouseAdapter genderAdpater = new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
					frame.setBoy(e.getSource()==radioBoy);
			}
		};
				
		Box box9 = new Box(BoxLayout.X_AXIS);
		JLabel linkTime = null;
		try {
			linkTime = getLinkText("日历面板：",this,this.getClass().getMethod("showCalendar"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		box9.add(linkTime);
		box9.add(new JLabel("选择日历干支..."));
		
		Box box1 = new Box(BoxLayout.X_AXIS);
		if(this.isBoy) {
      radioBoy = new JRadioButton("男", true);
      radioGirl = new JRadioButton("女");
    }else{
      radioBoy = new JRadioButton("男");
      radioGirl = new JRadioButton("女", true);
    }
    radioBoy.addMouseListener(genderAdpater);
    radioGirl.addMouseListener(genderAdpater);
    box1.add(new JLabel("性别："));
    groupNanLv = new ButtonGroup();
    groupNanLv.add(radioBoy);
    groupNanLv.add(radioGirl);  
    box1.add(radioBoy);
    box1.add(radioGirl);
    
    FocusAdapter focusAdapter = new FocusAdapter() {
			public void focusGained(final FocusEvent e) {
				((JTextField)e.getComponent()).selectAll();
			}
			public void focusLost(FocusEvent e) {
				String mz = ((JTextField)e.getComponent()).getText();
				checkMZ(mz);
				frame.setMztext(mz);
			}
		};
    Box box2 = new Box(BoxLayout.X_AXIS);
		JLabel labelMing = new JLabel("年命：");
		box2.add(labelMing);
    textMZhu = new JTextField(4);
    Dimension d = textMZhu.getPreferredSize(); 
		d.width = Width;
		d.height = Height;
		textMZhu.setMaximumSize(d);
    textMZhu.setText(mzText);
    textMZhu.addFocusListener(focusAdapter);
    box2.add(textMZhu);
    textMZhu.setToolTipText(html.CovertDestionString("当预测婚姻或考试求学时，需要同时指定年命。"+
    		"<BR>可以同时指定多个年命，时间或干支均可；<BR>如时间形式：“1977|1986”；干支形式如丁巳年和丙辰年则为“4,6|3,5”；"));
    textMZhu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    
    Box box3 = new Box(BoxLayout.X_AXIS);
    box3.add(new JLabel("用神："));
    box3.add(this.getComboYShen());
    
    Box box4 = new Box(BoxLayout.X_AXIS);
    box4.add(new JLabel("籍贯："));
    box4.add(this.getComboShengShi());
    
    Box box5 = new Box(BoxLayout.X_AXIS);
    box5.add(new JLabel("用局："));
    box5.add(this.getComboYYDunJu());
    
    Box box6 = new Box(BoxLayout.X_AXIS);
		JLabel labelNum = new JLabel("报数起局：");
		box6.add(labelNum);
    textNum = new JTextField(4);
    d = textNum.getPreferredSize(); 
		d.width = Width-20;
		d.height = Height;
		textNum.setMaximumSize(d);
		textNum.addFocusListener(inputFocus);
		box6.add(textNum);
		textNum.setToolTipText(html.CovertDestionString("报任意数按当前时间起局！"));
		textNum.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		Box box7 = new Box(BoxLayout.X_AXIS);
		JLabel labelHour = new JLabel("时辰起局：");
		box7.add(labelHour);
    textHour = new JTextField(4);
    d = textHour.getPreferredSize(); 
		d.width = Width-20;
		d.height = Height;
		textHour.setMaximumSize(d);
		textHour.addFocusListener(inputFocus);
		box7.add(textHour);
		textHour.setToolTipText(html.CovertDestionString("报任意数按当前年月日加所报数对应的时辰数起局！"));
		textHour.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		Box box8 = new Box(BoxLayout.X_AXIS);
		JLabel linkLable = null;
		try {
			linkLable = getLinkText("备注信息：",this,this.getClass().getMethod("showMemo"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		box8.add(linkLable);
		box8.add(new JLabel("保存推算信息..."));
		
		Box box10 = new Box(BoxLayout.X_AXIS);
		box10.add(new JLabel("规则引擎定制："));
		box10.add(this.getComboRule());

    JToolBar toolBar = new JToolBar(SwingConstants.VERTICAL);
    box1.setAlignmentX(LEFT_ALIGNMENT);
    box2.setAlignmentX(LEFT_ALIGNMENT);
    box3.setAlignmentX(LEFT_ALIGNMENT);
    box4.setAlignmentX(LEFT_ALIGNMENT);
    box5.setAlignmentX(LEFT_ALIGNMENT);
    box6.setAlignmentX(LEFT_ALIGNMENT);
    box7.setAlignmentX(LEFT_ALIGNMENT);
    box8.setAlignmentX(LEFT_ALIGNMENT);
    box9.setAlignmentX(LEFT_ALIGNMENT);
    box10.setAlignmentX(LEFT_ALIGNMENT);
    
    toolBar.add(box9);
    toolBar.addSeparator();
    toolBar.add(box1);  //性别
    toolBar.addSeparator();
    toolBar.add(box2);	//年命
    toolBar.addSeparator();
    toolBar.add(box4);	//出生地
    toolBar.addSeparator();
    toolBar.add(box3);	//用神
    toolBar.addSeparator();
    toolBar.add(box5);	//选局
    toolBar.addSeparator();
    toolBar.add(box6);	//报数起局
    toolBar.addSeparator();
    toolBar.add(box7);	//报时辰起局
    toolBar.addSeparator();
    toolBar.add(box8);	//备注信息
    toolBar.addSeparator();
    toolBar.add(box10);	//规则定制信息

    toolBar.setFloatable(true);
    toolBar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    return toolBar;
	}
	//显示日历面板
	public void showCalendar() {
		frame.update2(1);
	}
	
	/**
	 * 显示备注信息
	 * 弹出的备注信息窗口关闭时，其实是setVisible(false)
	 */	
	public void showMemo() {		
		MyTextPane text = memoWin.getTextpane();
		Document doc = text.getDocument();
		try {
			doc.remove(0, doc.getLength());
			pw.setDocument(doc);			
			pw.sblue(frame.getMemo());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		text.roll20();  //滚到第一行
		memoWin.setVisible(true);
	}
	//一个回调函数，关闭备注窗口时，调用保存按钮
	public void saveMemo() {
		frame.setMemo(memoWin.getTextpane().getText());
		frame.pan(true);
	}
	
	/**
	 * 由QimenFrame来调用
	 */
	public void update(boolean isBoy,String mzText, 
			int isheng, int ishi, int ysNum, int yydunNum){
		radioBoy.setSelected(isBoy);
		radioGirl.setSelected(!isBoy);
		textMZhu.setText(mzText);
		comboSheng.setSelectedIndex(isheng+1);
		comboShi.setSelectedIndex(ishi+1);
		comboYShen.setSelectedIndex(ysNum);
		comboyyDunJu.setSelectedIndex(yydunNum);
	}
	
	private Box getComboYYDunJu() {
    Box box = new Box(BoxLayout.X_AXIS);
    int i = 0;

    comboyyDunJu = new JComboBox(QiMen.yydunju);
    Dimension d = comboyyDunJu.getPreferredSize(); 
		d.width = Width; 
		d.height = Height;
		comboyyDunJu.setMaximumSize(d);
    comboyyDunJu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    comboyyDunJu.setToolTipText(html.CovertDestionString("默认不用选择，由程序自动判断当前采用哪一局。<BR>"+
		"当干支录入时节气判断不好处理时，可以手动指定哪一局；"));
    comboyyDunJu.setSelectedIndex(yydunNum);
    comboyyDunJu.addActionListener(new ActionListener( ) {
      public void actionPerformed(ActionEvent e) {
        int index = ((JComboBox) e.getSource()).getSelectedIndex();
        frame.setJu(index);// 0-18
        frame.pan(false);
        //JOptionPane.showMessageDialog(getThis(),String.valueOf(yydunNum), "测试",JOptionPane.INFORMATION_MESSAGE);
      }
    });
    box.add(comboyyDunJu);

    return box;
  }
	
  /**
   * 选择用神，无非年、月、日、时
   * 八神
   * 八门
   * 九星
   * 三奇六仪
   * 九宫
   * @return
   */
  private Box getComboYShen() {
    Box box = new Box(BoxLayout.X_AXIS);
    int i = 0;

    comboYShen = new JComboBox(QiMen.yongshen);
    Dimension d = comboYShen.getPreferredSize(); 
    d.width = Width;
		d.height = Height;
		comboYShen.setMaximumSize(d);
    comboYShen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
    comboYShen.setToolTipText(html.CovertDestionString("默认日干为用神，当求测非本人，或方位为用神时，此时便可选择用神了。"+
		"<BR>如可选择年干、月干、日干、时干、一宫、二宫、伤门、死门等，用于智能推算模块。"));
    comboYShen.setSelectedIndex(ysNum);
    comboYShen.addActionListener(new ActionListener( ) {
      public void actionPerformed(ActionEvent e) {
        int index = ((JComboBox) e.getSource()).getSelectedIndex();
        frame.setYsnum(index);// 4~1
        //JOptionPane.showMessageDialog(getThis(),String.valueOf(ysNum), "测试",JOptionPane.INFORMATION_MESSAGE);
      }
    });
    box.add(comboYShen);

    return box;
  }
  /**
   * 规则引擎定制
   * @return
   */
  private Box getComboRule() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboRule = new JComboBox(QiMen.rules);
    Dimension d = comboRule.getPreferredSize(); 
    d.width = Width;
		d.height = Height;
		comboRule.setMaximumSize(d);
		comboRule.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
		comboRule.setToolTipText(html.CovertDestionString("定制自己的规则擎用于智能推算！"));
		comboRule.setSelectedIndex(0);
		comboRule.addActionListener(new ActionListener( ) {
      public void actionPerformed(ActionEvent e) {
      	ruleIndex = ((JComboBox) e.getSource()).getSelectedIndex();
      	//Messages.question("ruleIndex="+ruleIndex+";"+QiMen.rules[ruleIndex]);
        showRuleWindow();
      }
    });
    box.add(comboRule);

    return box;
  }
  
  private boolean checkMZ(String s) {
  	String info1 = "命主用年份或干支表示，多个以|分隔，如：［1977］或［1977|1989］或［4,6］或［4,6|9,9］！";
  	String info2 = "年份必须在"+Calendar.IYEAR+"与"+Calendar.MAXYEAR+"之间！";
  	String info3 = "年干必须在1～10之间，地支必须在1～12之间，以甲、子为1依此类推！";
  	
  	if(s==null || "".equals(s.trim())) return true;
  	if(s.split(",").length>2 && s.split(QiMen.FUHAOYI).length<2) {  //"2,2|1,1".split(",")=3
  		Messages.question(info1);
  		textMZhu.setFocusable(true);
  		textMZhu.setText("");
			return false;
  	}
  	
  	String[] mz = s.split(QiMen.FUHAOYI);
  	for(String text : mz) {
  		String[] gz = text.split(QiMen.FUHAODOT);
  		if(gz.length==1) {
  			try{
  				int year = Integer.valueOf(text);
  				if(year>Calendar.MAXYEAR || year<Calendar.IYEAR) {
  					Messages.question(info2);
  					textMZhu.setFocusable(true);
  					textMZhu.setText("");
  					return false;
  				}
  			}catch(Exception e) {
  				Messages.question(info1);
  				textMZhu.setFocusable(true);
  				textMZhu.setText("");
  				return false;
  			}
  		}else{
				try{
  				int gan = Integer.valueOf(gz[0]);
  				int zi = Integer.valueOf(gz[1]);
  				if(gan>10 || gan<1) {
  					Messages.question(info3);
  					textMZhu.setFocusable(true);
  					textMZhu.setText("");
  					return false;
  				}
  				if(zi>12 || zi<1) {
  					Messages.question(info3);
  					textMZhu.setFocusable(true);
  					textMZhu.setText("");
  					return false;
  				}
				}catch(Exception e) {					
  				Messages.question(info3);
  				textMZhu.setFocusable(true);
  				textMZhu.setText("");
  				return false;
  			}				
			}
  	}
  	
  	return true;
  }
  
  /**
   * 得到省市的下拉框
   * @return
   */
  public Box getComboShengShi() {
    Box box = new Box(BoxLayout.X_AXIS);
    int i = 0;
    sheng = null;
    shi = null;
    //共有多少省，因为数组后面是空的
    for (i = 0; i < Calendar.cityname.length; i++) {
      if (Calendar.cityname[i][0] == null)break;
    }
    sheng = new String[i + 1];
    for (i = 0; i < sheng.length; i++) {
      sheng[i] = Calendar.cityname[i][0];
    }

    comboSheng = new JComboBox(sheng);
    comboSheng.setToolTipText(html.CovertDestionString("调整真太阳时，默认为当地时间，不用调整。<BR>这里用来选择省份。"));
    comboSheng.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    comboSheng.insertItemAt("    ", 0);
    Dimension d = comboSheng.getPreferredSize(); 
		d.width = QimenInputPanel.Width2;
		d.height = QimenInputPanel.Height;
		comboSheng.setMaximumSize(d);
    if (iprovince >= 0) {
      comboSheng.setSelectedIndex(iprovince + 1);
      shi = Calendar.cityname[iprovince];
      comboShi = new JComboBox(shi);
      comboShi.insertItemAt("    ", 0);
      comboShi.setSelectedIndex(iprovince + 1);
    }
    else {
      comboSheng.setSelectedIndex(0);
      shi = new String[] {
          "    "};
      comboShi = new JComboBox(shi);
      comboShi.setSelectedItem("");
      d = comboShi.getPreferredSize(); 
      d.width = QimenInputPanel.Width2;
  		d.height = QimenInputPanel.Height; 
  		comboShi.setMaximumSize(d);
    }

    comboSheng.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int index = ( (JComboBox) e.getSource()).getSelectedIndex();
        if (index > 0) {
          comboShi.removeAllItems();
          comboShi.addItem("    ");
          shi = Calendar.cityname[index - 1];
          for (int i = 0; i < shi.length; i++) {
            comboShi.addItem(shi[i]);
          }
        }
        else {
          comboShi.removeAllItems();
          comboShi.addItem("    ");
        }
        frame.setProvince(index - 1);

      }
    });

    comboShi.setToolTipText(html.CovertDestionString("调整真太阳时，默认为当地时间，不用调整。<BR>这里用来选择城市，但必须先选择省份。"));
    comboShi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    comboShi.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        frame.setCity(( (JComboBox) e.getSource()).getSelectedIndex() - 1);
      }
    });

    box.add(comboSheng);
    box.add(comboShi);

    return box;
  }
  
  class InputFocusListener implements FocusListener{
  	
		public void focusGained(FocusEvent e) {
			JTextField text = (JTextField)e.getSource();
			text.selectAll();
		}
		public void focusLost(FocusEvent e) {
			//1. 得到输入数字
			JTextField text = (JTextField)e.getSource();
			text.setForeground(Color.BLUE);
			text.setFont(new Font("宋体",Font.BOLD,16));
			String snum = text.getText();
			if(snum.trim().equals("")) return;
			int num = 0;
			try{
				num = Integer.valueOf(snum);
			}catch(Exception exp) {
				text.setText("数字必须的");
				text.setForeground(Color.RED);
			}
			if(num<0) {
				text.setText("大于0必须的");
				text.setForeground(Color.RED);
			}			
			
			//2. 报数还是时辰
			if(text.equals(textNum)) {
				//此时默认时辰为当前时辰，否则失去焦点将导致报数起局出错
				runNumber(text,num);
			} else if(text.equals(textHour)) {
				runHour(text,num);
			}
			
	    frame.pan(false);
		}
		
		/**
		 * 将一个文本内容转成数字
		 * @param snum
		 * @return
		 */
		private int procTextnumber(String snum) {
			if(snum.trim().equals("")) return 0;
			int num = 0;
			try{
				num = Integer.valueOf(snum);
			}catch(Exception exp) {
				return 0;
			}
			if(num<0) {
				return 0;
			}		
			return num;
		}
		
		//由当前时间加所报的数为时辰起局，1就是子时，2就是丑时。 1=0 2=2 3=4 4=6
		private void runHour(JTextField text, int num) {
			num = num%12==0 ? 12:num%12;
//			java.util.Calendar clr = Public.getCalendarForBeijing();
//	  	int y = clr.get(java.util.Calendar.YEAR) ; 
//	  	int m = clr.get(java.util.Calendar.MONTH)+1;
//	  	int d = clr.get(java.util.Calendar.DAY_OF_MONTH);
//	  	int h = num*2 - 2;
			int y = frame.getYear();
			int m = frame.getMonth();
			int d = frame.getDay();
			int h = num*2 - 2;
	  	
	    frame.setInputParameter(y, m, d, h, 1, true, false); //true为闰月阳历无闰月只有闰年故无意义，false为阳历
	    	  	
	  	//如果有报数，以报数为局数
	  	int junum = procTextnumber(textNum.getText());
	  	if(junum!=0) {
	  		junum = junum%9==0 ? 9:junum%9;
	  	//2. 局数也以当前局的阴阳遁为准，不用再以当前时间为准了。如果没选局，当前局自然为当前时间
	  		int ju = daoqm.getJu() > 0 ? junum : 9+junum;
		    frame.setJu(ju);
	  	}
		}
		//由当前时间得到当前何局，再由报的数为局数起局
		private void runNumber(JTextField text, int num) {							
			//1. 定当前日期，有时是日历面板上选择的时间，所以应该以frame的为准 
//			java.util.Calendar clr = Public.getCalendarForBeijing();
//	  	int y = clr.get(java.util.Calendar.YEAR) ; 
//	  	int m = clr.get(java.util.Calendar.MONTH)+1;
//	  	int d = clr.get(java.util.Calendar.DAY_OF_MONTH);
//	  	int h = clr.get(java.util.Calendar.HOUR_OF_DAY) ;	  	
//	  	int mi = clr.get(java.util.Calendar.MINUTE);
			int y = frame.getYear();
			int m = frame.getMonth();
			int d = frame.getDay();
			int h = frame.getHour();
			int mi = frame.getMinute();
	  	
	  	//2. 如果报数时辰不为空，以报的时辰换算为小时分钟，子=0时0分，丑时为2时0分，寅为4时0分
	  	int junum = procTextnumber(textHour.getText());
	  	if(junum!=0) {
	  		junum = junum%12==0 ? 12:junum%12;
	  		h = (junum-1)*2;
	  		mi = 0;
	  	}
	  	
	  	//2. 局数也以当前局的阴阳遁为准，不用再以当前时间为准了。如果没选局，当前局自然为当前时间
//	  	daosz.getDateOut(y, m, d, h, mi, false, true, true, 0, 0);
//	    daoqm.getHead1(QiMen2.ZF?1:2, QiMen2.RB?1:2, QiMen2.TD?1:2, 0); //1转 2拆 1随天盘 0局
	  	//3. 按报的数定局数
			num = num%9==0 ? 9:num%9;
			int ju = daoqm.getJu() > 0 ? num : 9+num;			
			//true为闰月阳历无闰月只有闰年故无意义，false为阳历
	    frame.setInputParameter(y, m, d, h, mi, true, false); 
	    //必须在setInputParameter之后设置ju，否则不会生效
	    frame.setJu(ju);
		}
	}
  /**
   * 显示规则录入窗口时
   * 1. 初始化窗口
   * 2. 初始化规则
   */
  private void showRuleWindow() {
  	if(ruleWin==null) {
  		ruleWin = new RuleWindow();  		
  	}
  	ruleWin.setTitle(QiMen.rules[ruleIndex]+"规则引擎定制");
		Document doc = ruleTextPane.getDocument();
		try {
			doc.remove(0, doc.getLength());
			pw.setDocument(doc);		
			if(ruleIndex>0) {
				String rs = readRuleFromFile(ruleIndex);
				if(rs==null) return;
				pw.sblack(rs);
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		ruleTextPane.roll20();  
		if(!ruleWin.isVisible()) ruleWin.setVisible(true);
  }
  
  /**
   * 关闭时保存规则内容
   * 文件中的换行符是\n，所以要将\r\n替换掉，否则会多空行
   */
  private void saveRule2File(int fileIndex) {
  	BufferedWriter fw = null;	
  	String sText = ruleTextPane.getText().replaceAll("\r\n", "\n");
  	try {
  		fw = new BufferedWriter(new FileWriter(getRuleFile(fileIndex)));
  		//System.out.println(sText);
			fw.write(sText);
			//System.out.println(sText.replaceAll("\r\n", ""));
			fw.flush();
		} catch (IOException e) {			
			e.printStackTrace();
			try { fw.close(); } catch (IOException e1) {}
		}
  }
  /**
   * 从规则文件读取
   * 因为用readLine读，所以不会读出换行符，要自己加上
   */
  private String readRuleFromFile(int fileIndex) {
  	BufferedReader fr = null;
  	StringBuilder sb = new StringBuilder();
  	String sLine = null;  	
  	File file = getRuleFile(fileIndex);
  	if(!file.exists()) {
  		Messages.info(file.getPath()+"不存在，将创建新的规则文件！");
  		return "";
  	}
  	try {
  		fr = new BufferedReader(new FileReader(file));
			while((sLine = fr.readLine())!=null) {
				//if(sLine.trim().equals("")) continue;
				//System.out.println(sLine);
				sb.append(sLine+"\r\n");
				//sb.append(sLine.replaceAll("\n", "\r\n"));
			}
		} catch (IOException e) {			
			e.printStackTrace();
			try { fr.close(); } catch (IOException e1) {}
		}
  		
  	return sb.toString();
  }
  /**
   * 设置读取的文件序号
   * 按QiMen.rules中顺序
   */
  private File getRuleFile(int fileIndex) {
  	return new File(Public.QMGZDZ+"/"+QiMen.rules[fileIndex]+".txt");
  }
  private File getRuleFile() {
  	return getRuleFile(ruleIndex);
  }
  private void initQMBusiness(){
  	if(qmbiz==null)
  		qmbiz = new QMBusiness(frame.getDelQiMenMain().getQimenPublic());  //奇门规则引擎
  }
  
  /**
   * 奇门规则检查
   */
  private void checking() {
  	initQMBusiness();
  	String checkMsg = qmbiz.checkRules(ruleTextPane.getText());
  	ruleWin.setAlwaysOnTop(false);
  	Messages.error(checkMsg);
  	ruleWin.setAlwaysOnTop(true);
  }
  /**
   * 奇门规则试运行
   */
  NormalInputWindow runRs = (NormalInputWindow)UIPublic.getInputWindow("运行结果", 600, 300, true, false, false);
  MyTextPane runText = runRs.getTextpane();
  private void running() {
  	initQMBusiness();
  	String rsMsg = qmbiz.runRules(ruleTextPane.getText());
  	ruleWin.setAlwaysOnTop(false);
  	try {
			runRs.setCloseCallback(this, getClass().getMethod("showRuleWin"),null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}  	
		Document runDoc = runText.getDocument();
		try {
			runDoc.remove(0, runDoc.getLength());
			pw.setDocument(runDoc);			
			pw.sblue(rsMsg);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		runText.roll20();  //滚到第一行
		runRs.setVisible(true);		 
  }
  /**
   * 运行规则，返回结果，主要供外部函数调用
   */
  public String runRule(int fileIndex) {
  	initQMBusiness();
  	String rs = readRuleFromFile(fileIndex);
  	if(rs==null) return null;
  	return qmbiz.runRules(rs);  	
  }
  
  public void showRuleWin() {
  	ruleWin.setAlwaysOnTop(true);
  }
  
  /**
   * 规则定制窗口
   */
  class RuleWindow extends JDialog {
  	public RuleWindow() {
	  	Box box = new Box(BoxLayout.Y_AXIS);
	  	ruleTextPane = new MyTextPane();        
	    JScrollPane jScrollPane = new JScrollPane(ruleTextPane);        
	    box.add(jScrollPane, BorderLayout.CENTER);   
	    
	    Box box2 = new Box(BoxLayout.X_AXIS);
	    JButton check = new JButton("语法检查");
	    check.addActionListener(new ActionListener() {				
				public void actionPerformed(ActionEvent e) {
					checking();
				}
			});
	    JButton run = new JButton("试运行");
	    run.addActionListener(new ActionListener() {				
				public void actionPerformed(ActionEvent e) {
					running();
				}
			});
	    JButton save = new JButton("保存");
	    save.addActionListener(new ActionListener() {				
				public void actionPerformed(ActionEvent e) {
					ruleWin.setAlwaysOnTop(false);
					int rs = Messages.question("将覆盖原文件"+QiMen.rules[ruleIndex]+".txt，请确认！");
					ruleWin.setAlwaysOnTop(true);
					if (rs == 1) {						
						return;
					}
					saveRule2File(ruleIndex);
				}
			});
	    JButton close = new JButton("关闭");
	    close.addActionListener(new ActionListener() {				
				public void actionPerformed(ActionEvent e) {
					close();
				}
			});
	    box2.add(check);
	    box2.add(run);
	    box2.add(save);
	    box2.add(close);
	    box.add(box2);
	    
	    this.getContentPane().add(box,BorderLayout.CENTER);
	    this.setUndecorated(false);  
	    this.setSize(600, 500);  	    
	    this.setLocationRelativeTo(null);  
	    this.setAlwaysOnTop(true);	  	   
  	}
  	
  	public void close() {
  		this.setAlwaysOnTop(false);
  		int rs = Messages.question("将关闭"+QiMen.rules[ruleIndex]+"规则定制窗口，请确认！");
  		this.setAlwaysOnTop(true);
			if (rs == 1) return;	
			this.setVisible(false);
  	}
    
  	protected void processWindowEvent(WindowEvent e) {
			if(e.getID()==WindowEvent.WINDOW_CLOSING){
				close();
			}
  	}
  }
}
