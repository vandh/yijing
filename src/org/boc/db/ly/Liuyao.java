package org.boc.db.ly;

import org.boc.db.YiJing;

public class Liuyao {
	public static final String HH="<BR>&nbsp;&nbsp;&nbsp;&nbsp;";
	public static final String XMLHH="<BR>";
	
	public static boolean go = true;		//六爻为true，否则为梅花
	public static boolean ALL = true;  //全部显示隐藏
	public static boolean JXGE = false;
	public static boolean WSXQ = false;
	public static boolean MA = true;	
	public static boolean TIP = true;
	public static boolean NOW = true;
	public static boolean HEAD = true;  //是否显示隐藏头部日期信息
	public static final int LEFTMIN = 1;  //左边的树的最小宽度
	public static final int LEFTMAX = 175;  //左边的树的最合适的宽度
	public static int LEFT = LEFTMIN;  //左边的树的宽度，默认为隐藏左边的，其宽度为1
	
	public static final String FUHAOYI="\\|";   //｜符号
	public static final String FUHAODOT=",";   //,符号
	public static final String DUN="、";   //、顿号
	public static final String FILL1 = "　";   //占二个字符
	public static final String FILL2 = " ";   //占一个字符，用于微调
	
	public static boolean TOOL = true;  //是否显示工具栏，默认显示
	public static boolean INPUT = false;  //是否显示高级面板，默认显示
	public static boolean IO = true;  //显示内置还是自定义的提示信息、规则引擎、格局定制。默认为内置
		
	public final static int YAO = 1;			//摇卦
	public final static int SJDZ = 2;			//时间+地支
	public final static int SJTG = 3;			//时间+天干
	public final static int SXD = 4;      //上卦+下卦+动爻
	public final static int Ming = 5;			//卦名+动爻
	public final static int SJHZ = 6;
	public final static int SJSZ = 7;
	public final static int SJFM = 8;
	public final static int SHUZI = 9;
	public final static int HANZI = 10;
	public final static int SUIJI = 11;
	public final static String[] QGFS = {"","摇卦","时间+年支","时间+年干",
		"上卦+下卦+动爻","卦名+动爻","时间+汉字","时间+数字","分秒","数字","汉字","随机"};
	
  //规则定制
  public static final int ZSMY = 1;
  public static final int JDYQ = 2;
  public static final int SWBD = 3;
  public static final int LAHY = 4;
  public static final int QXKS = 5;
  public static final int GZJY = 6;
  public static final int JYQC = 7;
  public static final int CXCG = 8;
  public static final int RTJB = 9;
  public static final int TSQX = 10;
  public static final int DLFS = 11;
  public static final int XRZS = 12;
  public static final int HYFW = 13;
  public static final int TYJS = 14;
  public static final String[] rules = {"  ", "终生命运","近段运气","失物被盗","恋爱婚姻",
  	"求学考试","工作就业","经营求财","出行出国","人体疾病","天时气象","地理风水","行人走失","怀孕分娩","体育竞赛"};
  
//用神选择
  public static final String[] yongshen = {"  ", YiJing.LIUQINNAME[1],YiJing.LIUQINNAME[2],
  	YiJing.LIUQINNAME[3],YiJing.LIUQINNAME[4],YiJing.LIUQINNAME[5]};
}
