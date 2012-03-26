package org.boc.ui.ly;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JToolBar;

import org.boc.dao.DaoCalendar;
import org.boc.dao.ly.DaoYiJingMain;
import org.boc.dao.ly.LiuyaoPublic;
import org.boc.db.Calendar;
import org.boc.db.ly.Liuyao;
import org.boc.db.qm.QiMen2;
import org.boc.delegate.DelYiJingMain;
import org.boc.event.ly.LyClickListener;
import org.boc.event.ly.LyTipMouseMotion;
import org.boc.ui.BasicJPanel;
import org.boc.ui.Main;
import org.boc.ui.MyTextPane;
import org.boc.ui.ResultPanel;
import org.boc.util.Messages;
import org.boc.util.Public;
import org.boc.util.VoLiuYao;

public class LiuYaoFrame extends BasicJPanel{
	private String mzhu;			//命主的干支或数字形式，为1993|1992; 或者 1,1|2,2;等形式，不用转换，用时再处理
	private int ng,nz,yg,yz,rg,rz,sg,sz;
  public int year,month,day,hour,minute,second;
  
  public boolean isYun = true;         //是否是闰月
  public boolean isYin= false;         //是否是阴历
  public boolean isBoy = true;         //是否是男孩
 
  private int up;      					//上卦卦数
  private int down;      				//下卦卦数
  private int yshen;   					//用神
  private int mode=Liuyao.SJDZ;	//起卦方式，缺省按当前时间+年份地支起卦
  private int isheng=-1;           //省的序号
  private int ishi=-1;             //市的序号  
  private String acts;  				//动爻数组以,分隔的字符串
  
  private DaoYiJingMain daoly;  
  private DelYiJingMain delly;
  private DaoCalendar daocal;
  private LiuyaoPublic pub;
  private LiuyaoInputPanel inputPanel;
  private JToolBar toolbar; 			// 六爻工具栏
	private JToolBar inputToolBar; 	//高级输入面板
  public VoLiuYao vo;

  private String fileId;
  private String rowId;
  private String parentNode;
  private String memo;  

  public LiuYaoFrame() {
    this.setLayout(new BorderLayout());
    delly = new DelYiJingMain();
    pub = delly.getLiuyaoPublic();
    pub.setBoy(isBoy);
    daocal = delly.getDaoCalendar();
    daoly = delly.getDaoYiJingMain();           
    
    /**
     * 初始顺序有要求：1）创建结果显示面板 2）注册监听时创建双击事件、移动提示、弹菜单；3）创建高级面板、工具栏
     */
    ResultPanel rp = new ResultPanel();		// 创建一个结果显示面板				
		this.setResultPane(rp); 
		
		this.setListner(rp);  //注册监听事件
		
    inputPanel = new LiuyaoInputPanel(this);
		toolbar = new LiuyaoFloatToolbar(this).getFloatToolbar();	
		inputToolBar = inputPanel.getInputPanel();
		
		if(Liuyao.TOOL) addTool(toolbar);
		if(Liuyao.INPUT) addInput(inputToolBar);		
		
		Box box = new Box(BoxLayout.Y_AXIS);		
		box.add(rp);
		this.add(box, BorderLayout.CENTER);
  }

  public void init(String fileId, String rowId, String parentNode1) {
  	this.fileId = fileId;
    this.rowId = rowId;
    vo = (VoLiuYao) Public.getObjectFromFile(fileId, rowId);
    if(vo!=null) {
    	mode = vo.getQgfs();	//起卦方式 
    	mzhu = vo.getMzhu();
			yshen = vo.getYs();

			isYun = vo.isIsYun();
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
			hour = vo.getHour();
			minute = vo.getMinute();
			second = vo.getSecond();

			isheng = vo.getIsheng();
			ishi = vo.getIshi();			
			memo = vo.getMemo();		
     }     
     if(parentNode1==null) {
       if(vo!=null)
         parentNode1=vo.getParent();
     }
     this.parentNode = parentNode1;
     
     // 第一次打开时，将默认保存当前的时间，并开始排盘，否则显示保存的内容
 		 if (vo == null) {
 			initData();
 			initDate();
 		 }
 			
 		pan();
 		this.getResultPane().getTextPane().roll20();
 		
 		update1();
 		update2();
 		update3();
  }

  /**
   * 输出起卦信息
   */
  public void pan() {  
    //1. 得到动爻数组
    int[] chgs = pub.getActs(acts);
    
    //2. 不管何种起卦方式，最终都是按上卦、下卦、动爻来装卦
    delly.getGuaXiang(this.getResultPane(),mode,yshen,mzhu,
    		isBoy,isheng,ishi,
    		year,month,day,hour,minute,
    		isYin,isYun,
        up,down, chgs);
//    this.getResultPane().getTextPane().setText(rs);
//    this.getResultPane().getTextPane().roll20();
//    return rs;
  }
  
  /**
	 * 保存,点击保存按钮是，会保存到文件，点击排盘按钮时，不写文件
	 */
	public boolean save() {
		int rs = Messages.question("［"+fileId+"］节点下名为［"+rowId+"］内容已修改，确定要将以｛"+(ng!=0?"干支":"时间")+"｝形式保存吗？");
		if (rs == 1)
			return false;
		
		VoLiuYao vo = new VoLiuYao(mode, yshen,up, down, 
          acts, new int[]{0,ng,nz,yg,yz,rg,rz,sg,sz},
          year, month, day, hour, minute,
          isYin, isYun,mzhu,isheng,ishi);
		vo.setRowId(rowId);
		vo.setFileId(fileId);
		//不取子目录了 一律取预测术的根目录，即取root的值
		vo.setRoot(Public.valueRoot[6]);  //此处是六爻的值
		vo.setParent(parentNode);
		vo.setYcsj(Public.getTimestampValue().toString());
		vo.setMemo(memo);
		Public.writeObject2File(vo);
		clear();
		//System.out.println("后台偷偷保存了。");
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
  	LiuyaoPopupMenu popmenu = LiuyaoPopupMenu.getSharedInstance();
  	popmenu.setFrame(this);
  	pane.addMouseListener(popmenu);

  	//2. 添加了一个鼠标移动事件
  	mouseMotionListener = new LyTipMouseMotion(pane,this);
  	if(Liuyao.TIP){	 //默认是关闭，不显示提示信息的
	  	pane.addMouseMotionListener(mouseMotionListener);
	  }
  	
  	//3. 添加一个鼠标双击事件
  	clickListner =  new LyClickListener(this);
  	pane.addMouseListener(clickListner);
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
  
	//更新时间面板与录入面板，备注信息面板
	public void update1() {
		//1. 同步更新输入面板		
		if(inputPanel.isVisible()) 
			inputPanel.update(isBoy,mzhu,isheng,ishi,yshen,mode);		
		//2. 更新备注信息面板
		if(inputPanel.getMemoWin()!=null && inputPanel.getMemoWin().isVisible())
			inputPanel.showMemo();
	}
	/**
	 * 
	 * @param isShow: 0: 不设置显隐，1设为显示，2设为隐藏
	 */
	public void update2() {		
		//更新时间面板
		LyCalendarForm cal = ((LyClickListener)clickListner).getCalendarForm();
		if(clickListner!=null && cal.isVisible()) {		
			if (ng!=0) {//2.1 如果是干支，则要更新面板到干支样式
				cal.updateGanzi(ng,nz,yg,yz,rg,rz,sg,sz);
				return;
			}
			if(isYin) {  //2.2 如果是农历，要先计算，因此时还没有调用起局按钮，时间没有换算。公历则不用先起局，会自动触发起局的
				//Messages.info("农历： "+Calendar.YEARP+"-"+Calendar.MONTHP+"-"+Calendar.DAYP+" "+hour+":"+minute);
				pan();
				cal.updateDate(Calendar.YEARP,Calendar.MONTHP,Calendar.DAYP,hour,minute);
			}else{  //2.3 是公历，则直接更新
				//Messages.info("公历："+year+"-"+month+"-"+day+" "+hour+":"+minute);
				cal.updateDate(year,month,day,hour,minute);  //将面板显示为当前树节点双击的时间
			}
		}
	}
	
	//更新任务面板提示
	public void update3() {
		Main.setStatusInfo(Public.LY);
	}
	
	/**
	 * 如果不归零，前面vo不为空，后面一个数据为空，则这些字断不会归零
	 */
	private void initData() {
		isYun = true; 
		isYin = false; 
		isBoy = true; 
		up=0; 
	  down=0;
	  yshen=0;
	  mode=Liuyao.SJDZ;	
	  acts="";
	  isheng=-1; 
	  ishi=-1; 
	  ng=nz=yg=yz=rg=rz=sg=sz=0;
		mzhu="";	
	}
  
  /** 初始化时间为当前东八区时间 */
  private void initDate() {
		java.util.Calendar clr = Public.getCalendarForBeijing();
		year = clr.get(java.util.Calendar.YEAR);
		month = clr.get(java.util.Calendar.MONTH)+1;
		day = clr.get(java.util.Calendar.DAY_OF_MONTH);
		hour = clr.get(java.util.Calendar.HOUR_OF_DAY);
		minute = clr.get(java.util.Calendar.MINUTE);
		second = clr.get(java.util.Calendar.SECOND);
	}

	/** 设置输入的时间参数，给CalendarForm的单击日历框表时用 */
	public void setInputParameter(int y, int m, int d, int h, int mi, boolean run, boolean yin) {
		this.year = y;
		this.month = m;
		this.day = d;
		this.hour = h;
		this.minute = mi;
		this.isYun = run;
		this.isYin = yin;
	}
	
	/** 设置年月日时干支 */
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
	public String getMzhu() {
		return mzhu;
	}
	public void setMzhu(String mzhu) {
		this.mzhu = mzhu;
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
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	public boolean isYun() {
		return isYun;
	}
	public void setYun(boolean isYun) {
		this.isYun = isYun;
	}
	public boolean isYin() {
		return isYin;
	}
	public void setYin(boolean isYin) {
		this.isYin = isYin;
	}
	public boolean isBoy() {
		return isBoy;
	}
	public void setBoy(boolean isBoy) {
		pub.setBoy(isBoy);
		this.isBoy = isBoy;
	}
	public int getUp() {
		return up;
	}
	public void setUp(int up) {
		this.up = up;
	}
	public int getDown() {
		return down;
	}
	public void setDown(int down) {
		this.down = down;
	}
	public int getYshen() {
		return yshen;
	}
	public void setYshen(int yshen) {
		this.yshen = yshen;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public String getActs() {
		return acts;
	}
	public void setActs(String acts) {
		this.acts = acts;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getSheng() {
		return isheng;
	}
	public void setSheng(int isheng) {
		this.isheng = isheng;
	}
	public int getShi() {
		return ishi;
	}
	public void setShi(int ishi) {
		this.ishi = ishi;
	}
  public LiuyaoInputPanel getLiuyaoInputPanel() {
		return inputPanel;
	}
	public DelYiJingMain getDelYiJingMain() {
		return delly;
	}
	public JToolBar getToolbar() {
		return this.toolbar;
	}
	public LiuyaoInputPanel getInputpanel() {
		return this.inputPanel;
	}
	public JToolBar getInputToolbar() {
		return this.inputToolBar;
	}

  public void finalize() {
  	delly = null;
    daocal = null;
    daoly = null;
    vo = null;
  }
}
