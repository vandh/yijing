package org.boc.calendar.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import org.boc.calendar.util.Lunar;
import org.boc.ui.UIPublic;
import org.boc.ui.qm.QiMenFrame;
import org.boc.util.Messages;


/**
 * 具体的万年历界面
 * 改进：
 * 1）点击当前时间切换到现在；
 * 2）点击干支历，显示干支录入方式；
 * 3）双击某日，隐藏对话框; 
 * 4)添加小时和分钟
 */
public class CalendarForm2 extends JFrame implements ActionListener {
	private static final long serialVersionUID = 6376610934523560443L;
	private final static Font font = new Font("宋体", Font.TRUETYPE_FONT, 12);
	Calendar clr = Calendar.getInstance();
	private QiMenFrame frame;   //父窗口
	private Lunar lunar ;
	private GanziMouseAdapter ganziMouseAdapter ;
	private FocusListener focusListener;
	
	private final int MAXYEAR = 9999;
	private final int MINYEAR = 1900;
	private final String LINKTEXT = "当前时间";
	private Container main;
	private CalendarTableModel model;
	private TimesLabel time;
	private JTable table;
	private JTableHeader th ;
	private JLabel hLabel, mLabel, timeLabel;  //小时、分钟、当前时间	
	private JLabel nzhu, yzhu, rzhu, szhu;     //年、月、日、时柱	
	private JComboBox ng,nz,yg,yz,rg,rz,sg,sz; //年月日时干支下拉选择框
	private JButton buttonGanzi;
	JTextField tf; //年份
	JTextField hf; //小时
	JTextField mf; //分钟
	JComboBox select = new JComboBox();
	BasicArrowButton arrowUp = new BasicArrowButton(BasicArrowButton.NORTH);
	BasicArrowButton arrowDown = new BasicArrowButton(BasicArrowButton.SOUTH);
	BasicArrowButton arrowUph = new BasicArrowButton(BasicArrowButton.NORTH);
	BasicArrowButton arrowDownh = new BasicArrowButton(BasicArrowButton.SOUTH);
	BasicArrowButton arrowUpm = new BasicArrowButton(BasicArrowButton.NORTH);
	BasicArrowButton arrowDownm = new BasicArrowButton(BasicArrowButton.SOUTH);
	JEditorPane html = null;

	public CalendarForm2() {		
		this.main = this.getContentPane();
		this.main.setLayout(null);
		this.main.setFont(CalendarForm2.font);

		this.model = new CalendarTableModel();
		this.table = new JTable(model);
		focusListener = new FocusListener();
		ganziMouseAdapter = new GanziMouseAdapter();
		
		select.setFont(font);
		String[] vs = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
		for (int i=0; i<vs.length; i++)
			select.addItem(vs[i] + "月");
		select.setBounds(5, 5, 80, 24);
		select.addActionListener(this);  //当月份改变，触发这个事件
		select.setSelectedIndex(this.model.getCurrentMonth());
		this.main.add(select);
		
		tf = new JTextField(String.valueOf(this.model.getCurrentYear()));
		tf.setBounds(95, 5, 70, 24);
		tf.setEditable(true);
		tf.addFocusListener(focusListener); 		
		this.main.add(tf);
		
		arrowUp.setBounds(165, 5, 20, 12);
		arrowUp.addActionListener(this);  //上一年，触发
		this.main.add(arrowUp);
		arrowDown.setBounds(165, 17, 20, 12);
		arrowDown.addActionListener(this); //下一年，触发
		this.main.add(arrowDown);
		arrowUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		arrowDown.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		table.setBackground(this.getBackground());
		table.setFont(CalendarForm2.font);

		th = table.getTableHeader();
		th.setResizingAllowed(false);
		th.setReorderingAllowed(false);
		th.setBackground(Color.GRAY);
		th.setBounds(5, 30, 180, 20);
		main.add(th);
		
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		dtcr.setFont(font);
		dtcr.setForeground(Color.BLACK);
		table.setDefaultRenderer(Object.class, dtcr);
		dtcr = new DefaultTableCellRenderer();   
        dtcr.setForeground(Color.RED);
        dtcr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(dtcr);
        
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setAutoCreateColumnsFromModel(false);
    table.setCellSelectionEnabled(true);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setSelectionForeground(new Color(0xFF8800));
    table.setSelectionBackground(this.main.getBackground());
    table.setBorder(LineBorder.createBlackLineBorder());
		table.setBounds(5, 50, 180, 80);   //(5, 50, 180, 96) 
		table.addMouseListener(new TableMouseAdapter());
		main.add(table);
		table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));		
		
		Font labelFont = new Font("宋体", Font.PLAIN, 12);
		hLabel = new JLabel("小时");
		hLabel.setFont(labelFont);
		hLabel.setBounds(10, 131, 30, 24);
		main.add(hLabel);
		hf = new JTextField(String.valueOf(this.model.getCurrentHour()));
		hf.setBounds(40, 131, 30, 24);
		hf.setEditable(true);
		hf.addFocusListener(focusListener); 		
		main.add(hf);
		arrowUph.setBounds(70, 131, 20, 12);
		arrowUph.addActionListener(this);  //上一小时，触发
		this.main.add(arrowUph);
		arrowDownh.setBounds(70, 142, 20, 12);
		arrowDownh.addActionListener(this); //下一下时，触发
		this.main.add(arrowDownh);
		arrowDownh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		arrowUph.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));		
		
		mLabel = new JLabel("分钟");
		mLabel.setFont(labelFont);
		mLabel.setBounds(100, 131, 30, 24);
		main.add(mLabel);
		mf = new JTextField(String.valueOf(this.model.getCurrentMinute()));
		mf.setBounds(130, 131, 30, 24);
		mf.setEditable(true);
		mf.addFocusListener(focusListener); 		
		main.add(mf);
		arrowUpm.setBounds(160, 131, 20, 12);
		arrowUpm.addActionListener(this);  //上一分钟，触发
		this.main.add(arrowUpm);
		arrowDownm.setBounds(160, 142, 20, 12);
		arrowDownm.addActionListener(this); //下一分钟，触发
		this.main.add(arrowDownm);
		arrowUpm.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		arrowDownm.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));		
		
		timeLabel = new JLabel("");
		timeLabel.setFont(CalendarForm2.font);
		timeLabel.setBounds(7, 157, 60, 21);  //(5, 147, 180, 14)
		LinkLabel link = new LinkLabel();
		link.initText(timeLabel);
		timeLabel.addMouseListener(link);
		timeLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		main.add(timeLabel);
		time = new TimesLabel();
		time.setFont(CalendarForm2.font);
		time.setBounds(67, 160, 120, 18);  //(5, 147, 180, 14)
		main.add(time);		

    html = new JEditorPane("text/html", "<html></html>");
    html.setEditable(false);
    html.setBounds(190, 5, 112, 169);
    html.setFont(CalendarForm2.font);
    html.setBackground(main.getBackground());
    html.setBorder(LineBorder.createBlackLineBorder());
    html.addMouseListener(new GanziAdapter());
    main.add(html);
    
    initGanzi();
        
		//Image icon = this.getToolkit().getImage("res/icon.gif");
		//this.setIconImage(icon);
		//this.setTitle("Calendar - ialvin.cn");
		//this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(310, 180);
		this.setResizable(false);
		//WindowView.moveToScreenCenter(this);
		this.setUndecorated(true);
		this.initLunar();
		showWhich(true);
	}
	
	/**
	 * 初始化时皆为全干、全支，防止双击树叶子时找到支的现象
	 */
	private void initGanzi() {		
		nzhu = new JLabel("年柱：");
		yzhu = new JLabel("月柱：");
		rzhu = new JLabel("日柱：");
		szhu = new JLabel("时柱：");
		ng = this.getSelectOfGanzi(lunar.Tianan0,ganziMouseAdapter);
		ng.setName("niangan");
		yg = this.getSelectOfGanzi(lunar.Tianan0,ganziMouseAdapter);
		yg.setName("yuegan");
		rg = this.getSelectOfGanzi(lunar.Tianan0,ganziMouseAdapter);
		rg.setName("rigan");
		sg = this.getSelectOfGanzi(lunar.Tianan0,ganziMouseAdapter);
		sg.setName("shigan");
		nz = this.getSelectOfGanzi(lunar.Deqi0,ganziMouseAdapter);
		nz.setName("nianzi");
		yz = this.getSelectOfGanzi(lunar.Deqi0,ganziMouseAdapter);
		yz.setName("yuezi");
		rz = this.getSelectOfGanzi(lunar.Deqi0,ganziMouseAdapter);
		rz.setName("rizi");
		sz = this.getSelectOfGanzi(lunar.Deqi0,ganziMouseAdapter);
		sz.setName("shizi");
		buttonGanzi = UIPublic.getInitImageButton("yes","yes","干支排盘",new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				//必须放到这里更新，防止第一次不选就直接起局.此处在下位框值更新时也必须调用，否则点击保存时就不会有值.
	      updateFrameganzi();
				frame.pan(false);
			}
		});
		//双击事件，切换到时间录入面板
		main.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					showWhich(true);
				}
			}
		});
		nzhu.setBounds(40, 13, 50, 24);		
		ng.setBounds(110,13,50,24);
		nz.setBounds(190,13,50,24);
		
		yzhu.setBounds(40, 38, 50, 24);		
		yg.setBounds(110,38,50,24);
		yz.setBounds(190,38,50,24);
		
		rzhu.setBounds(40, 63, 50, 24);		
		rg.setBounds(110,63,50,24);
		rz.setBounds(190,63,50,24);
		
		szhu.setBounds(40, 88, 50, 24);		
		sg.setBounds(110,88,50,24);
		sz.setBounds(190,88,50,24);
		
		buttonGanzi.setBounds(120, 115, 70, 60);
		
		main.add(nzhu);
		main.add(ng);
		main.add(nz);
		
		main.add(yzhu);
		main.add(yg);
		main.add(yz);
		
		main.add(rzhu);
		main.add(rg);
		main.add(rz);
		
		main.add(szhu);
		main.add(sg);
		main.add(sz);
		main.add(buttonGanzi);
	}
	
	/**
	 * 显示面板，显示阴阳历对照
	 */
	private void initLunar() {
		if (this.html == null) return;
		clr.set(this.model.getSelectYear(), this.model.getSelectMonth(), this.model.getSelectDay());
		lunar = new Lunar(clr.getTimeInMillis());
		StringBuffer htm = new StringBuffer();
		htm.append("<html><div style='margin:5 auto;' align='center' margin='10'><font color='blue' size='4'><b>" + 
			lunar.getSolarYear() + "年" +
			lunar.getSolarMonth() + "月" + 
			lunar.getSolarDay() + "日</b></font></div>");
		htm.append("<div style='margin:1 auto;' align='center'><font size='4' color='green'><b>星期" + 
				this.model.titles[lunar.getDayOfWeek()-1] + "</b></font></div>");
		htm.append("<div style='margin:5 auto;' align='center'><font size='3' color='red'><b>" + 
				lunar.getLunarYear() + "年" +				
				lunar.getLunarMonthString() + "月" +
				lunar.getLunarDayString() + "</b></font></div>");		
		
		htm.append("<div style='margin:2 auto;' align='center'>"+lunar.getCyclicaYear()+"年<br>");
		htm.append(lunar.getCyclicaMonth()+"月<br>");
		htm.append(""+lunar.getCyclicaDay()+"日<br>");
		htm.append(""+lunar.getCyclicaHour()+"时<br>");
		//htm.append(""+Lunar.Animals[lunar.getDeqiD()]+"日冲" + Lunar.Animals[(lunar.getDeqiD()+6)%12] + "生年</div>");

		String tip = "<html>" + lunar.getDescription();
		if (!"".equals(lunar.getLFestivalName()))
			tip += "<br>" + lunar.getLFestivalName();
		if (!"".equals(lunar.getSFestivalName()))
			tip += "<br>" + lunar.getSFestivalName();
		if (lunar.isHoliday())
			tip += "(放假)";
		if (!"".equals(lunar.getTermString()))
			tip += "<br>今日" + lunar.getTermString();
		if (lunar.isBlackFriday())
			tip += "<br>黑色星期五";
		
		this.html.setText(htm.toString());
		this.html.setToolTipText(tip + "</html>");
	}

	/**
	 * 翻动上一年或下一年或选择月份时触发这个事件
	 */
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		int num = 0;
		if (this.arrowUp.equals(o)) {   //上一年
			num = this.model.getSelectYear();
			if(num>=MAXYEAR) num=MAXYEAR-1;			
			this.tf.setText(String.valueOf(++num));
			this.model.setSelectYear(num);
		} else if (this.arrowDown.equals(o)) {  //下一年
			num = this.model.getSelectYear();
			if(num<=MINYEAR) num=MINYEAR+1;
			this.tf.setText(String.valueOf(--num));
			this.model.setSelectYear(num);
		} else if (this.arrowUph.equals(o)) {   //上一小时
			num = Integer.valueOf(hf.getText());
			if(num>=23) num=-1;			
			this.hf.setText(String.valueOf(++num));
		} else if (this.arrowDownh.equals(o)) {  //下一小时
			num = Integer.valueOf(hf.getText());
			if(num<=0) num=24;
			this.hf.setText(String.valueOf(--num));
		} else if (this.arrowUpm.equals(o)) {   //上一分钟
			num = Integer.valueOf(mf.getText());
			if(num>=59) num=-1;			
			this.mf.setText(String.valueOf(++num));
			reDo(5);
			return;
		} else if (this.arrowDownm.equals(o)) {  //下一分钟
			num = Integer.valueOf(mf.getText());
			if(num<=0) num=60;
			this.mf.setText(String.valueOf(--num));
			reDo(5);
			return;
		} else if (this.select.equals(o)) {  //月份改变
			this.model.setSelectMonth(this.select.getSelectedIndex());			
			this.model.fireTableDataChanged();
			this.initLunar();
			reDo(2);
			return;
		}
		this.model.fireTableDataChanged();
		this.initLunar();
		reDo(0);   //重新起局
	}
	
	/**
	 * 为年份/小时/分钟加一个值改变监听事件
	 */
	class FocusListener extends FocusAdapter {
		public void focusGained(final FocusEvent arg0) {
			((JTextField)arg0.getComponent()).selectAll();
		}
		public void focusLost(FocusEvent e) {
			if(e.getSource().equals(tf)) {
				String syear = tf.getText();
				int iyear = 0 ;
				try{
					iyear = Integer.valueOf(syear);
					if(iyear<1900 ) iyear = MINYEAR;														
					if(iyear>9999) iyear = MAXYEAR;
					tf.setText(iyear+"");
					CalendarForm2.this.model.setSelectYear(iyear);
				}catch(Exception exception) {
					tf.setText(CalendarForm2.this.model.getSelectYear()+"");
				}
				CalendarForm2.this.model.fireTableDataChanged();
				CalendarForm2.this.initLunar();
				reDo(1);   //重新起局
			}else if(e.getSource().equals(hf)) {
				String shour = hf.getText();
				int ihour = 0 ;
				try{
					ihour = Integer.valueOf(shour);
					if(ihour<0 ) ihour = 0;														
					if(ihour>23) ihour = 23;
					hf.setText(ihour+"");
				}catch(Exception exception) {
					hf.setText(CalendarForm2.this.model.getCurrentHour()+"");
				}
				reDo(4);   //重新起局
			}else if(e.getSource().equals(mf)) {
				String sminute = mf.getText();
				int iminute = 0 ;
				try{
					iminute = Integer.valueOf(sminute);
					if(iminute<0 ) iminute = 0;														
					if(iminute>59) iminute = 59;
					mf.setText(iminute+"");
				}catch(Exception exception) {
					mf.setText(CalendarForm2.this.model.getCurrentMinute()+"");
				}
				reDo(5);   //重新起局，否则不能保存分钟
			}			
		}
	};

	/**
	 * 支持超链接形式
	 */
	class LinkLabel extends MouseAdapter {
		public void mouseEntered(MouseEvent e) {
			setText(e,true);
		}
		public void mouseExited(MouseEvent e) {
			setText(e,false);
		}
		public void mouseClicked(MouseEvent e) {
			CalendarForm2.this.model.setSelectYear(CalendarForm2.this.model.getCurrentYear());
			CalendarForm2.this.model.setSelectMonth(CalendarForm2.this.model.getCurrentMonth());
			CalendarForm2.this.model.setSelectDay(CalendarForm2.this.model.getCurrentDay());
			CalendarForm2.this.tf.setText(CalendarForm2.this.model.getCurrentYear()+"");
			CalendarForm2.this.select.setSelectedIndex(CalendarForm2.this.model.getCurrentMonth());
			CalendarForm2.this.hf.setText(CalendarForm2.this.model.getCurrentHour()+"");
			CalendarForm2.this.mf.setText(CalendarForm2.this.model.getCurrentMinute()+"");
			
			CalendarForm2.this.model.fireTableDataChanged();
			CalendarForm2.this.initLunar();
			reDo(0);  //要重新起局
		}
		public void initText(JLabel o) {
			o.setText("<html><div style='vertical-align:top;' ><font color=blue><U>" + CalendarForm2.this.LINKTEXT +"</U></font></div></html>");
		}
		private void setText(MouseEvent e, boolean b) {
			JLabel o = (JLabel)e.getComponent();			
			if (!b)
				o.setText("<html><div style='vertical-align:top;'><font color=blue><U>" + CalendarForm2.this.LINKTEXT +"</U></font></div></html>");
			else
				o.setText("<html><div style='vertical-align:top;' ><font color=blue><U><b>" + CalendarForm2.this.LINKTEXT +"</b></U></font></div></html>");
		}
	}
	
	/**
	 * 日历表中的鼠标单击事件与双击事件
	 * 单击选中
	 * 双击隐藏
	 */
	Timer mouseTimer = null;
	class TableMouseAdapter extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {			
			final int day = model.mapValue(table.getSelectedRow(), table.getSelectedColumn());
			if (day < 0) return;
			
//			if (e.getClickCount() == 1) {
//				mouseTimer = new Timer(350, new ActionListener() {
//					public void actionPerformed(ActionEvent evt) {
							model.setSelectDay(day);
							initLunar();				
							reDo(3);  //要重新起局
//						mouseTimer.stop();
//					}
//				});
//				mouseTimer.restart();
//			} else if (e.getClickCount() == 2 && mouseTimer.isRunning()) {
//				mouseTimer.stop();
//				CalendarForm2.this.setVisible(false);
//			}			
		}
	}
	/**
	 * 重新起局排盘
	 * 为防止重复触发起局事件，需要判断frame的年月日时与日历面板选中值不一样才需要更新
	 * 0：更新frame中所有的年月日时分值，然后重新起局；
	 * 1，2，3，4：分别为年、月、日、时；如果相同则不用更新和起局，否则要更新frame的值并要重新起局；
	 * 5：为分，需要更新frame的值，不需要起局；
	 * 当双击叶子全部更新年月日时，或从干支切换到年月日时，也需要触发所有的年月日时，真是麻烦
	 */
	private void reDo(int type) {
		if(frame == null) return;
		
		int iyear = this.model.getSelectYear();
		int ihour = Integer.valueOf(this.hf.getText());
		int iminute = Integer.valueOf(this.mf.getText());
		int iday = this.model.getSelectDay();
		int imonth = this.model.getSelectMonth()+1;
		
		//System.out.println("type="+type+";iyear="+iyear+";imonth="+imonth+";iday="+iday+";ihour="+ihour+";frame.year="+frame.getYear()+";frame.month="+frame.getMonth()+";frame.day="+frame.getDay()+";frame.hour="+frame.getHour());
		//如果是阴历，不管是否是，一律按阳历录入
		if(type==5) {  //如果为分钟，则直接更新值后返回
			frame.setMinute(iminute);
			return;
		}else if(type==4 && frame.getHour()==ihour) { //小时相同则不用更新和起局，否则都要
			return;
		}else if(type==3 && frame.getDay()==iday) { //日子相同则不用更新和起局，否则都要
			return;
		}else if(type==2 && frame.getMonth()==imonth) { //月份相同则不用更新和起局，否则都要
			//if(frame.getYear()*frame.getMonth()*frame.getDay()==0) return;
			return;
		}else if(type==1 && frame.getYear()==iyear) { //年份相同则不用更新和起局，否则都要
			return;
		}
		if(iyear<org.boc.db.Calendar.IYEAR || iyear >org.boc.db.Calendar.MAXYEAR) {
			Messages.info("目前支持年份在"+org.boc.db.Calendar.IYEAR+"~"+org.boc.db.Calendar.MAXYEAR+"之间！");
			return;
		}
		frame.setInputParameter(iyear, imonth, iday, ihour, iminute, true, false);
		frame.setInputGanzi(0, 0, 0, 0, 0, 0, 0, 0);
		frame.pan(false);
	}
	
	private int forUpdateGanzi(int gan,int zi) {
		int index =  gan%2==1?(zi+1)/2:zi/2;
		//System.out.println("gan="+gan+";zi="+zi+";index="+index);
		return index;
	}
	
	/**
	 * 更新当前的干支面板
	 * 干一设置，就会自动触发支的改变，不用担心找不到支的问题
	 */
	public void updateGanzi(int ng1,int nz1,int yg1,int yz1,int rg1,int rz1,int sg1,int sz1) {
		showWhich(false);
		//1=1,3=2,5=3;   2=1,4=2,6=3,8=4,10=5,12=6
		ng.setSelectedIndex(ng1);  
		nz.setSelectedIndex(forUpdateGanzi(ng1,nz1));
		yg.setSelectedIndex(yg1);
		yz.setSelectedIndex(forUpdateGanzi(yg1,yz1));
		rg.setSelectedIndex(rg1);
		rz.setSelectedIndex(forUpdateGanzi(rg1,rz1));
		sg.setSelectedIndex(sg1);
		sz.setSelectedIndex(forUpdateGanzi(sg1,sz1));
	}
	
	//更新当前的时间面板
	public void updateDate(int year,int month,int day,int hour,int minute) {
		showWhich(true);
		this.model.setSelectYear(year);
		this.model.setSelectMonth(month);
		this.model.setSelectDay(day);		
		
		//以下更新重复触发起局事件，是个问题，需要判断frame的年月日时与日历面板选中值不一样才需要更新
		this.tf.setText(year+"");
		this.select.setSelectedIndex(month-1);		
		this.hf.setText(hour+"");
		this.mf.setText(minute+"");
		
		CalendarForm2.this.model.fireTableDataChanged();
		CalendarForm2.this.initLunar();  //主要是更新html显示面板
	}
		
	public void setParent(JPanel frame) {
		this.frame = (QiMenFrame)frame;
	}

	private void showWhich(boolean bool) {
		select.setVisible(bool);
		tf.setVisible(bool);
		arrowUp.setVisible(bool);
		arrowDown.setVisible(bool);
		th.setVisible(bool);
		table.setVisible(bool);
		hLabel.setVisible(bool);
		hf.setVisible(bool);
		arrowUph.setVisible(bool);
		arrowDownh.setVisible(bool);
		mLabel.setVisible(bool);
		mf.setVisible(bool);		
		arrowUpm.setVisible(bool);
		arrowDownm.setVisible(bool);
		timeLabel.setVisible(bool);
		time.setVisible(bool);
		html.setVisible(bool);		
		
		nzhu.setVisible(!bool);
		yzhu.setVisible(!bool);
		rzhu.setVisible(!bool);
		szhu.setVisible(!bool);
		ng.setVisible(!bool);
		nz.setVisible(!bool);
		yg.setVisible(!bool);
		yz.setVisible(!bool);
		rg.setVisible(!bool);
		rz.setVisible(!bool);
		sg.setVisible(!bool);
		sz.setVisible(!bool);
		buttonGanzi.setVisible(!bool);
		
		//缺省获取焦点
		html.grabFocus();
		html.requestFocus();
		html.requestFocusInWindow();
	}
	
	// 得到干或支的下拉框
	private JComboBox getSelectOfGanzi(String[] gz, ActionListener adapter) {		 
		JComboBox combo = new JComboBox(gz);		
		Dimension d = combo.getPreferredSize();
		d.width = 30;
		d.height = 25;
		combo.setMaximumSize(d);
		combo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		combo.setSelectedIndex(1);
		combo.addActionListener(adapter);
		return combo;
	}
	/**
	 * 干支下拉框单击事件
	 */
	class GanziMouseAdapter implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JComboBox o = (JComboBox) e.getSource();				
			int index = o.getSelectedIndex();			
			if(index<=0) return;  //为无效点击
			//System.out.println("当前o="+o.getName()+";index="+index);
      if(o.getName().equals(ng.getName())) {
      	//System.out.println("ng="+ng.getName()+";index="+index);
      	nz.removeAllItems();
      	for (int i = 0; i < 7; i++) {
          nz.addItem(index%2==0 ? lunar.Deqi2[i] : lunar.Deqi1[i]);
        }
      	nz.setSelectedIndex(1);
      }else if(o.getName().equals(yg.getName())) {
      	//System.out.println("yg="+yg.getName()+";index="+index);
      	yz.removeAllItems();
      	for (int i = 0; i < 7; i++) {
          yz.addItem(index%2==0 ? lunar.Deqi2[i] : lunar.Deqi1[i]);
        }
      	yz.setSelectedIndex(1);
      }else if(o.getName().equals(rg.getName())) {
      	//System.out.println("rg="+rg.getName()+";index="+index);
      	rz.removeAllItems();
      	for (int i = 0; i < 7; i++) {
          rz.addItem(index%2==0 ? lunar.Deqi2[i] : lunar.Deqi1[i]);
        }
      	rz.setSelectedIndex(1);
      }else if(o.getName().equals(sg.getName())) {
      	//System.out.println("sg="+sg.getName()+";index="+index);
      	sz.removeAllItems();
      	for (int i = 0; i < 7; i++) {
          sz.addItem(index%2==0 ? lunar.Deqi2[i] : lunar.Deqi1[i]);
        }
      	sz.setSelectedIndex(1);
      }
      //必须放到这里更新，否则保存时就不会有值.在干支保存时也必须调用，防止第一次不选就直接起局
      updateFrameganzi();
		}
	}
	private void updateFrameganzi() {
		int ing = ng.getSelectedIndex();
    int iyg = yg.getSelectedIndex();
    int irg = rg.getSelectedIndex();
    int isg = sg.getSelectedIndex();
    int inz = ing%2==1 ? (nz.getSelectedIndex()-1)*2+1 : nz.getSelectedIndex()*2;
    int iyz = iyg%2==1 ? (yz.getSelectedIndex()-1)*2+1 : (yz.getSelectedIndex()*2);
    int irz = irg%2==1 ? (rz.getSelectedIndex()-1)*2+1 : (rz.getSelectedIndex()*2);
    int isz = isg%2==1 ? (sz.getSelectedIndex()-1)*2+1 : (sz.getSelectedIndex()*2);
    if(frame==null) return;
    frame.setInputGanzi(ing, inz,	iyg, iyz,	irg, irz,	isg, isz);
    frame.setInputParameter(0, 0, 0, 0, 0, false, false);
	}
	/**
	 * html面板单击事件
	 */
	class GanziAdapter extends MouseAdapter {
  	public void mouseClicked(MouseEvent e) {  		
  		if(e.getClickCount()==2) {
  			showWhich(false);
  		}
  	}
	}
	
	public static void main(String[] args) {
		//JFrame.setDefaultLookAndFeelDecorated(true);
		new CalendarForm2().setVisible(true);
	}
}