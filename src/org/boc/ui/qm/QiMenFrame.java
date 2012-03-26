package org.boc.ui.qm;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import org.boc.calendar.ui.CalendarForm;
import org.boc.dao.DaoPublic;
import org.boc.db.Calendar;
import org.boc.db.YiJing;
import org.boc.db.qm.QiMen;
import org.boc.db.qm.QiMen2;
import org.boc.db.qm.QimenFloatToolbar;
import org.boc.delegate.DelQiMenMain;
import org.boc.event.qm.QmClickListener;
import org.boc.event.qm.TipMouseMotionAdaption;
import org.boc.ui.BasicJPanel;
import org.boc.ui.Main;
import org.boc.ui.MyTextPane;
import org.boc.ui.ResultPanel;
import org.boc.util.Debug;
import org.boc.util.Messages;
import org.boc.util.Public;
import org.boc.util.VoQiMen;

public class QiMenFrame extends BasicJPanel {
	private boolean isYun = true; // 是否是闰月，因为阳历输入，是否闰由万年历那设置，永远是闰月
	private boolean isYin = false; // 是否是阴历，由万年历输入，永远是阳历
	private boolean isBoy = true; // 是否是男孩，默认男
	private int iZf = QiMen2.ZF ? 1 : 2; // 转盘或飞盘，默认转盘为1，飞为2
	private int iZy = QiMen2.RB ? 1 : 2; // 置闰或拆补，默认拆补为2，置闰为1
	private int iZfs = QiMen2.TD ? 1 : 2; // 小值符使，默认随天盘
	private int iJigong = QiMen2.KG ? 2 : 8; // 寄宫数, 缺省值为2，寄坤二宫为2，寄艮八宫为8

	private int yydunNum; // 阴遁或阳遁的局数
	private int ysNum = 3; // 用神对应的四柱，分别是年1月2日3时4
  private int isheng=-1;                //省的序号
  private int ishi=-1;                  //市的序号  
  
	private int ng, nz, yg, yz, rg, rz, sg, sz;
	private int year, month, day, hour, minute, second;
	private String mzText; // 命主的干支或数字形式，为1993;1992; |1,1; 2,2;等形式，不用转换，用时再处理	

	private DaoPublic pub;
	private DelQiMenMain qm;
	private QimenInputPanel inputPanel;  //奇门高级面板的容器
	private JToolBar toolbar; 	// 奇门工具栏
	private JToolBar inputToolBar; 	//高级输入面板
	private java.util.Calendar clr;
	
	private String fileId;
	private String rowId;
	private String parentNode;
	private String memo;
	private VoQiMen vo;

	public QiMenFrame() {
		qm = new DelQiMenMain();
		this.setLayout(new BorderLayout());
		pub = new DaoPublic();		
		qm.setJigong(iJigong); // 设置中五宫寄何宫		
		
		inputPanel = new QimenInputPanel(this);
		toolbar = new QimenFloatToolbar(this).getFloatToolbar();	
		inputToolBar = inputPanel.getInputPanel();
		ResultPanel rp = new ResultPanel();		// 创建一个结果显示面板				
		this.setResultPane(rp); 
		if(QiMen2.TOOL) addTool(toolbar);
		if(QiMen2.INPUT) addInput(inputToolBar);
		this.setListner(rp);  //注册监听事件
		
		Box box = new Box(BoxLayout.Y_AXIS);		
		box.add(rp);
		this.add(box, BorderLayout.CENTER);
	}
	
	public QimenInputPanel getQimenInputPanel() {
		return this.inputPanel;
	}
	public DelQiMenMain getDelQiMenMain() {
		return this.qm;
	}
	public JToolBar getToolbar() {
		return this.toolbar;
	}
	public JToolBar getInputpanel() {
		return this.inputToolBar;
	}

	public void init(String fileId, String rowId, String parentNode1) {
		this.fileId = fileId;
		this.rowId = rowId;
		vo = (VoQiMen) Public.getObjectFromFile(fileId, rowId);
		if (vo != null) {
			mzText = vo.getMingzhu();
			ysNum = vo.getYongshen();

			isYun = vo.isIsYun();
			isBoy = vo.isIsBoy();
			isYin = vo.isIsYin();

			iZf = vo.getIZf();
			iZy = vo.getIZy();
			iZfs = vo.getIZfs();
			iJigong = vo.getIqt1(); // 中五宫寄何宫

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
			hour = vo.getHour();
			minute = vo.getMinute();

			isheng = vo.getIsheng();
			ishi = vo.getIshi();
			yydunNum = vo.getYydunNum();
			memo = vo.getMemo();		
			
			//System.out.println("构造 ： "+vo.getFileId()+":"+vo.getRowId()+"::vo!=null ::::　ng="+ng+";nz="+nz+";year="+year);
		}		
		if (parentNode1 == null) {
			if (vo != null)
				parentNode1 = vo.getParent();
		}
		this.parentNode = parentNode1;		

		// 第一次打开时，将默认保存当前的时间，并开始排盘，否则显示保存的内容
		if (vo == null) {			
			initData();
			initDate();
		}
			
		pan(false);
		this.getResultPane().getTextPane().roll20();
		//System.out.println(memo);
		update1();
		update2(QiMen2.CALENDAR ? 1 : 2);
		update3();
	}
	
	//更新时间面板与录入面板，备注信息面板
	public void update1() {
		//1. 同步更新输入面板		
		if(inputPanel.isVisible()) 
			inputPanel.update(isBoy,mzText,isheng,ishi,ysNum,yydunNum);		
		//2. 更新备注信息面板
		if(inputPanel.getMemoWin()!=null && inputPanel.getMemoWin().isVisible())
			inputPanel.showMemo();
	}
	/**
	 * 
	 * @param isShow: 0: 不设置显隐，1设为显示，2设为隐藏
	 */
	public void update2(int isShow) {		
		//更新时间面板
		CalendarForm cal = ((QmClickListener)clickListner).getCalendarForm();
		if(isShow!=0) {
			cal.setVisible(isShow==1);
		}
		if(clickListner!=null && cal.isVisible()) {		
			if (ng!=0) {//2.1 如果是干支，则要更新面板到干支样式
				cal.updateGanzi(ng,nz,yg,yz,rg,rz,sg,sz);
				return;
			}
			if(isYin) {  //2.2 如果是农历，要先计算，因此时还没有调用起局按钮，时间没有换算。公历则不用先起局，会自动触发起局的
				//Messages.info("农历： "+Calendar.YEARP+"-"+Calendar.MONTHP+"-"+Calendar.DAYP+" "+hour+":"+minute);
				pan(false);
				cal.updateDate(Calendar.YEARP,Calendar.MONTHP,Calendar.DAYP,hour,minute);
			}else{  //2.3 是公历，则直接更新
				//Messages.info("公历："+year+"-"+month+"-"+day+" "+hour+":"+minute);
				cal.updateDate(year,month,day,hour,minute);  //将面板显示为当前树节点双击的时间
			}
		}
	}
	
	//更新小值符、拆补等
	public void update3() {
		QiMen2.ZF = this.iZf==1;
		QiMen2.RB = this.iZy==1;
		QiMen2.TD = this.iZfs==1;
		QiMen2.KG = this.iJigong==2;
		Main.setStatusInfo(Public.QM);
	}

	/**
	 * 输出排盘信息 do1:"格局一，即现在的格局详解" do2:"格局二，以下的全部删除了" do3:"命运流年" do4: "考学" do5: "工作"
	 * do6: "财运" do7: "婚姻" do8: "疾病"
	 * 
	 * @return JPanel
	 */
	// 供排判按钮调用，不需要保存为false，但第一次双击树打开时，将默认为当前时间，需要保存一次
	public String pan(boolean isSave) {
//	Messages.info("year="+year+"; month="+month+"; day="+day+"; hour="+hour+"; minute="+minute+
//	";\r\n isYin="+ isYin+"; isYun="+isYun+"; isheng="+isheng+"; ishi="+ishi+"; isBoy="+isBoy+
//	"\r\nng="+ng+"; nz="+nz+"; yg="+yg+"; yz="+yz+"; rg="+rg+"; rz="+rz+"; sg="+sg+"; sz="+sz+
//	"\r\n vo.getNg="+(vo!=null ? vo.getNg():vo));
		// 先保存，再排盘
		if (!saving(isSave))
			return null;
		qm.setJigong(iJigong); // 设置中五宫寄何宫
		if (ng==0)
			return qm.getGeju1(getResultPane(), year, month, day, hour, minute,
					ysNum, isYin, isYun, isheng, ishi, isBoy, iZf, iZy, iZfs, yydunNum,mzText);
		return qm.getGeju1(getResultPane(), ng, nz, yg, yz, rg, rz, sg, sz, ysNum, isYun,
				isheng, ishi, isBoy, iZf, iZy, iZfs, yydunNum,mzText);
	}

	// 在上下年月日时的ImageMouseListener中调用此方法
//	public void pan(int y1, int m1, int d1, int h1) {
//		if (y1 == -1)
//			y1 = year;
//		if (m1 == -1)
//			m1 = month;
//		if (d1 == -1)
//			d1 = day;
//		if (h1 == -1)
//			h1 = hour;
//		// 先保存，再排盘
//		if (!saving(false))
//			return;
//		if (vo == null)
//			return;
//		qm.setJigong(iJigong); // 设置中五宫寄何宫
//		if (vo.getNg() == 0)
//			qm.getGeju1(getResultPane(), y1, m1, d1, h1, minute, isYin, isYun,
//					isheng, ishi, isBoy, iZf, iZy, iZfs, yydunNum);
//		else
//			qm.getGeju1(getResultPane(), ng, nz, yg, yz, rg, rz, sg, sz, isYun,
//					isheng, ishi, isBoy, iZf, iZy, iZfs, yydunNum);
//	}

	private void getParameterAndCheck() {
		Debug.out("month=" + month + "day=" + day + "hour=" + hour + "minute="
				+ minute + "isYin=" + "\r\n" + isYin + "isYun=" + isYun + "isheng="
				+ isheng + "ishi=" + ishi + "isBoy=" + isBoy + "\r\n" + "ng=" + ng
				+ "nz=" + nz + "yg=" + yg + "yz=" + yz + "rg=" + rg + "rz=" + rz
				+ "sg=" + sg + "sz=" + sz + "isYun=" + isYun + "isBoy=" + isBoy);

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
	 * 保存,点击保存按钮是，会保存到文件，点击排盘按钮时，不写文件
	 */
	private boolean saving(boolean iswritefile) {
		if (iswritefile) {
			int rs = Messages.question("［"+fileId+"］节点下名为［"+rowId+"］内容已修改，确定要将以｛"+(ng!=0?"干支":"时间")+"｝形式保存吗？");
			if (rs == 1)
				return false;
		}		
		if (ng==0) {
			// 新增后，将自身的vo设置
			vo = new VoQiMen(mzText, ysNum, year, month, day, hour, minute, isBoy,
					isYin, isYun, isheng, ishi, iZf, iZy, iZfs, yydunNum, memo);
			//System.out.println("1:"+vo.getFileId() +":"+vo.getRowId()+"::vo.getNg="+vo.getNg()+";ng="+ng+";year="+vo.getYear());
		} else {			
			String checkgz = checkGZ(yg, yz, yydunNum);
			if (checkgz != null) {
				JOptionPane.showMessageDialog(getThis(), checkgz, "提示信息",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}

			vo = new VoQiMen(mzText, ysNum, ng, nz, yg, yz, rg, rz, sg, sz, isBoy,
					isYun, isheng, ishi, iZf, iZy, iZfs, yydunNum, memo);
			//System.out.println("2:"+vo.getFileId() +":"+vo.getRowId()+"::vo.getNg="+vo.getNg()+";ng="+ng+";year="+vo.getYear());
		}		
		vo.setIqt1(iJigong); // 不能再改vo的结构了，只能这样设置
		vo.setRowId(rowId);
		vo.setFileId(fileId);
		// 不取子目录了 一律取预测术的根目录，即取root的值
		vo.setRoot(Public.valueRoot[1]);
		vo.setParent(parentNode);
		vo.setYcsj(Public.getTimestampValue().toString());

		if (!iswritefile)
			return true; // 如果不保存，此处就可以返回了		
		Public.writeObject2File(vo);		
		clear();
		//System.out.println(vo.getFileId() +":"+vo.getRowId()+"::ng="+vo.getNg()+";year="+vo.getYear());
		
		return true;
	}
	
  /**
   * 为防止重复添加，必须判断是否已经加入，在构造时加入不会重复，如果在多面板切换时加入，则肯定会重复
   * 录入面板信息已经更新，所以如果已经加入的必须删除掉了再加
   * @param rs
   */
  public void setListner(ResultPanel rs) {
  	MyTextPane pane = rs.getTextPane();

  	//1. 添加了一个弹出菜单事件
  	MouseListener[] lis1 = pane.getMouseListeners();
  	for(int i=0; i<lis1.length; i++) {
  		if(lis1[i] instanceof QimenPopupMenu) {
  			pane.removeMouseListener(lis1[i]);
  		}
  	}  	
		QimenPopupMenu popmenu = QimenPopupMenu.getSharedInstance();
  	popmenu.setFrame(this);
  	pane.addMouseListener(popmenu);

  	//2. 添加了一个鼠标移动事件
  	MouseMotionListener[] lis2 = pane.getMouseMotionListeners();
  	for(int i=0; i<lis2.length; i++) {
  		if(lis2[i] instanceof TipMouseMotionAdaption) {
  			pane.removeMouseMotionListener(lis2[i]);
  		}
  	}  	
  	mouseMotionListener = new TipMouseMotionAdaption(pane,this);
  	if(QiMen2.TIP){	 //默认是关闭，不显示提示信息的
	  	pane.addMouseMotionListener(mouseMotionListener);
	  }
  	
  	//3. 添加一个鼠标双击事件
  	clickListner =  new QmClickListener(this);
  	pane.addMouseListener(clickListner);
  }

	/**
	 * 检查各个框所填的格式是否正确
	 * 
	 * @return
	 */
	public String checkInputs(String y, String m, String d, String h) {
		return pub.checks(y, m, d, h, isYin, isYun);
	}

	/**
	 * 检查干支起局的节气与选局是否一致 如果yz=2，则只可能是雨水，惊蜇，春分三节的阴阳遁局数
	 */
	private String checkGZ(int yg, int yz, int yydunNum) {
		int yz_ = yz;
		yz = yz < 3 ? yz + 10 : yz - 2;
		int[] jies = { yz * 2 - 2 == 0 ? 24 : yz * 2 - 2, yz * 2 - 1, yz * 2 };
		int[] jus = new int[10];
		int k = 0;
		boolean bl = false;
		String _s = "";

		for (int i = 0; i < 3; i++) {
			int[] ju_ = QiMen.yydun[jies[i]];

			for (int m = 1; m < ju_.length; m++) {
				bl = false;
				for (int j = 0; j < jus.length; j++) {
					if (jus[j] != 0 && jus[j] == ju_[m]) {
						bl = true;
						break;
					}
				}
				if (!bl) {
					jus[k++] = ju_[m];
				}
			}
		}

		for (int i = 0; i < jus.length; i++) {
			if (jus[i] == 0)
				continue;
			int _ii = jus[i] <= 0 ? 9 - jus[i] : jus[i];
			_s += QiMen.yydunju[_ii] + ",";
		}
		if (_s.length() > 1)
			_s = _s.substring(0, _s.length() - 1);

		if (yydunNum <= 0) {
			// return "干支起局必须选择阴遁或阳遁局数";
			return null;
		}
		for (int i = 0; i < jus.length; i++) {
			if (jus[i] == 0)
				continue;
			if (jus[i] < 0 && 9 - jus[i] == yydunNum)
				return null;
			if (jus[i] > 0 && jus[i] == yydunNum)
				return null;
		}
		return YiJing.DIZINAME[yz_] + "月必须为下列某局：\r\n" + _s;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	private void initDate() {
		java.util.Calendar clr = Public.getCalendarForBeijing();
		year = clr.get(java.util.Calendar.YEAR);
		month = clr.get(java.util.Calendar.MONTH)+1;
		day = clr.get(java.util.Calendar.DAY_OF_MONTH);
		hour = clr.get(java.util.Calendar.HOUR_OF_DAY);
		minute = clr.get(java.util.Calendar.MINUTE);
		second = clr.get(java.util.Calendar.SECOND);
	}
	/**
	 * 如果不归零，前面vo不为空，后面一个数据为空，则这些字断不会归零
	 */
	private void initData() {
		isYun = true; 
		isYin = false; 
		isBoy = true; 
		iZf = QiMen2.ZF ? 1 : 2; 
		iZy = QiMen2.RB ? 1 : 2; 
		iZfs = QiMen2.TD ? 1 : 2; 
		iJigong = QiMen2.KG ? 2 : 8; 

		yydunNum=0; 
		ysNum = 3; 
	  isheng=-1; 
	  ishi=-1; 
	  ng=nz=yg=yz=rg=rz=sg=sz=0;
		mzText="";	
	}

	/**
	 * 设置输入的时间参数，给CalendarForm的单击日历框表时用
	 * 
	 * @param y
	 * @param m
	 * @param d
	 * @param h
	 * @param mi
	 */
	public void setInputParameter(int y, int m, int d, int h, int mi,
			boolean run, boolean yin) {
		this.year = y;
		this.month = m;
		this.day = d;
		this.hour = h;
		this.minute = mi;
		this.isYun = run;
		this.isYin = yin;
		this.yydunNum = 0;
	}
	
	//设置年月日时干支
	public void setInputGanzi(int ng,int nz, int yg,int yz,int rg,int rz,int sg,int sz) {
		this.ng = ng;
		this.yg = yg;
		this.rg = rg;
		this.sg = sg;
		this.nz = nz;
		this.rz = rz;
		this.yz = yz;
		this.sz = sz;
	}

	// type 阴历为真，阳历为假
	public boolean isYinli() {
		return this.isYin;
	}
	public void setYinli(boolean yin) {
		this.isYin = yin;
	}
	public boolean isYun() {
		return this.isYun;
	}
	public void setYun(boolean run) {
		this.isYun = run;
	}

	public int getProvince() {
		return this.isheng;
	}
	public void setProvince(int province) {
		this.isheng = province;
	}
	public int getCity() {
		return this.ishi;
	}
	public void setCity(int city) {
		this.ishi = city;
	}
	public boolean isBoy() {
		return this.isBoy;
	}
	public void setBoy(boolean isboy) {
		this.isBoy = isboy;
	}

	public int getZhuanFei() {
		return iZf;
	}

	public void setZhuanFei(boolean iszhuan) {
		this.iZf = iszhuan ? 1 : 2; // 转为真，为1；飞为假，为2
	}

	public int getCaiYun() {
		return iZy;
	}

	public void setCaiYun(boolean isrun) {
		this.iZy = isrun ? 1 : 2; // 置闰为真，为1； 拆补为假，为2；
	}

	public int getXiaozhifu() {
		return iZfs;
	}

	public void setXiaozhifu(boolean istian) {
		this.iZfs = istian ? 1 : 2;
	}

	public int getJigong() {
		return this.iJigong;
	}

	public void setJigong(boolean iskun) {
		this.iJigong = iskun ? 2 : 8;
	}
	public int getJu() {
		return yydunNum;
	}
	public void setJu(int ju) {
		yydunNum = ju;
	}

	public int getYsnum() {
		return this.ysNum;
	}
	public void setYsnum(int num) {
		this.ysNum = num;
	}

	public String getMztext() {
		return mzText;
	}
	public void setMztext(String mz) {
		this.mzText = mz;
	}
	public String getMemo() {
		return this.memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getNg() {
		return ng;
	}

	public void setNg(int ng) {
		this.ng = ng;
	}

	public int getNz() {
		return nz;
	}

	public void setNz(int nz) {
		this.nz = nz;
	}

	public int getYg() {
		return yg;
	}

	public void setYg(int yg) {
		this.yg = yg;
	}

	public int getYz() {
		return yz;
	}

	public void setYz(int yz) {
		this.yz = yz;
	}

	public int getRg() {
		return rg;
	}

	public void setRg(int rg) {
		this.rg = rg;
	}

	public int getRz() {
		return rz;
	}

	public void setRz(int rz) {
		this.rz = rz;
	}

	public int getSg() {
		return sg;
	}

	public void setSg(int sg) {
		this.sg = sg;
	}

	public int getSz() {
		return sz;
	}

	public void setSz(int sz) {
		this.sz = sz;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}
	
}
