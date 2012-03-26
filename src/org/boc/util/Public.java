package org.boc.util;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;

public class Public {
	public static final int QM = 1;
	public static final int LY = 2;
	public static final int SZ = 3;
	
  public static final String DATA = "dat";
  public static final String LOG = "log";
  public static final String CONF = "conf";
  public static final String QMXXDZ = "database/奇门/信息定制";
  public static final String QMGZDZ = "database/奇门/规则定制";
  public static final String QMGJDZ = "database/奇门/格局定制";
  public static final String QMQDSZ = "database/奇门/启动设置/qdsz.ini";
  public static final String LYQDSZ = "database/六爻/启动设置/qdsz.ini";
  public static final String NUM1 = "wang";
  
  public static final boolean DEBUGSWITCH = false;  //true将System.out转向输出到日志文件中
  public static final String LOGFILE = "log.txt";
  public static final boolean zc = true; //是否注册，false为无
  public static final String helpdoc = "/conf"+File.separator+"help.pdf";  //帮助文档名称
  public static final String NOW = "现在时间"; //第一次使用时，qm.xml或sz.xml没有生成，缺省在其下创建该子节点，即<奇门><现在时间></></>
  
	public static final int SPLASH = 0;
  public final static String[] DAXIAO = new String[]{" ","一","二","三",
      "四","五","六","七","八","九","十","十一","十二","十三",
      "十四","十五","十六","十七","十八","十九","二十","二十一",
      "二十二","二十三","二十四","二十五","二十六","二十七","二十八",
      "","","",};
  
  /**
   * 修改用户目录为当前目录，simon 2011-10-10
   */
  public static String HOME = "";
  //public static String HOME = System.getProperty("user.home") + File.separator + ".jyijing" + File.separator;
  /**
   * 标题，对话框内容
   */

  public static final String infoTitle = "温馨提示";
  public static final String  status = "★丁丁道人★版权所有，严禁用于商业用途，违者必究●";
  public static final String  title = "始皇预测＠古典术数预测大全";
  public static final String TREEROOT = "始皇字典";

  public static String[] keyRoot = {"太乙","奇门","六壬",
      "紫微","铁板","四柱","六爻","玄空","八宅","姓名","称骨","鬼谷"};
  public static String[] valueRoot = {"ty","qm","lr",
      "zw","tb","sz","ly","xk","bz","xm","cg","gg"};
  public static final String[][] tabTitle = new String[valueRoot.length][10];
  static {
    //tabTitle[1] = new String[] {"","信息列表", "详细信息", "局一","局二","终生命运","考试升学","工作就业","财运","婚姻","疾病"};
  	tabTitle[1] = new String[] {"","奇门信息列表", "奇门起局"};
    tabTitle[2] = new String[] {"","信息列表", "详细信息", "起课","分析"};
    tabTitle[3] = new String[] {"","信息列表", "详细信息", "排盘","分析"};
    tabTitle[4] = new String[] {"","信息列表", "详细信息", "神数","分析"};
    tabTitle[5] = new String[]{"","信息列表", "详细信息", "八字","终身卦","格局","事业","婚姻","六亲","性格","病凶"};
    tabTitle[6] = new String[] {"","六爻信息列表", "六爻起卦"};
    tabTitle[7] = new String[] {"","信息列表", "详细信息", "星盘","分析","择时"};
    tabTitle[8] = new String[] {"","信息列表", "详细信息", "游星盘","分析"};
    tabTitle[9] = new String[] {"","信息列表", "详细信息", "分析"};
    tabTitle[10] = new String[] {"","信息列表", "详细信息", "袁天罡称骨"};
    tabTitle[11] = new String[] {"","信息列表", "详细信息", "鬼谷分定术"};
  }
  /**
   * 缓存所创建的BasicJPanel::XxxxFrame的面板，不用每次双击树时都重新创建了Frame了；  
   * 在BasicJTabbedPane的构造方法中调用
   */
  //public static HashMap<String,BasicJPanel> frameCache = new HashMap<String,BasicJPanel>(10);
  
  public static final String[] strClass = {
      "org.boc.ui.ty.TaiYiFrame","org.boc.ui.qm.QiMenFrame","org.boc.ui.lr.LiuRenFrame",
      "org.boc.ui.zw.ZiWeiFrame","org.boc.ui.tb.TiebanFrame",
      "org.boc.ui.sz.SiZhuFrame","org.boc.ui.ly.LiuYaoFrame",
      "org.boc.ui.xk.XuanKongFrame","org.boc.ui.bz.BaZhaiFrame",
      "org.boc.ui.xm.XingMingFrame","org.boc.ui.cg.ChengGuFrame","org.boc.ui.gg.GuiGuFrame"};
  /**
   * 类的装载判断
   * 判断三式、五数、四六、姓名、称骨、鬼谷是否打开
   */
  public static Map<String,String> mapClass = new HashMap<String,String>();
  static {
    for(int i=0; i<keyRoot.length; i++) {
      mapClass.put(valueRoot[i], strClass[i]);
    }
  }

  /**
   * 由value的值如qm得到是数组中的第几个
   */
  public static int getValueIndex(String value) {
    for(int i=0; i<valueRoot.length; i++) {
      if(valueRoot[i].equals(value))
        return i;
    }
    return -1;
  }

  /**
   * 判断预测术基本信息面板是否已打开
   * 判断三式、五数、四六、姓名、称骨、鬼谷是否打开
   */
  public static Map<String,Boolean> mapKeyIsOpen = new HashMap<String,Boolean>();
  static {
    for(int i=0; i<keyRoot.length; i++) {
      mapKeyIsOpen.put(valueRoot[i], new Boolean(false));
    }
  }

  /**
   * 判断预测技术是否已装载到了树
   * 判断三式、五数、四六、姓名、称骨、鬼谷是否打开
   */
  public static Map<String,Boolean> mapKeyIsLoaded = new HashMap<String,Boolean>();
  static {
    for(int i=0; i<keyRoot.length; i++) {
      mapKeyIsLoaded.put(valueRoot[i], new Boolean(false));
    }
  }
  /**
   * 键值对
   */
  public static Map<String,String> mapRootKeyValue = new HashMap<String,String>();
  static {
    for(int i=0; i<keyRoot.length; i++) {
      mapRootKeyValue.put(keyRoot[i],valueRoot[i]);
    }
  }
  public static Map<String,String> mapRootValueKey = new HashMap<String,String>();
  static {
    for(int i=0; i<keyRoot.length; i++) {
      mapRootValueKey.put(valueRoot[i],keyRoot[i]);
    }
  }


  /**
   * 设置bool型map中key的值为boolean值
   */
  public static void setKeyValue(Map<String,Boolean> map, String value, boolean b) {
    map.put(value, new Boolean(b));
  }
  public static boolean getKeyValue(Map<String,Boolean> map, String value) {
    return ((Boolean)map.get(value)).booleanValue();
  }

  /**
   * 由vlaue值取树的key值即由字母取汉字
   */
  public static String getRootKey(String value) {
    return (String)mapRootValueKey.get(value);
  }

  /**
   * 由key值取树的value值即由汉字取字母
   */
  public static String getRootValue(String key) {
    return (String)mapRootKeyValue.get(key);
  }


  //单独写xml文件的树
  public static Map<String,String> mapFile ;
  public static void setMapFile() {
    mapFile = new HashMap<String,String>();
//    mapFile.put("root",HOME+DATA+"/tree.xml");
    mapFile.put("ty",HOME+DATA+"/ty.xml");
    mapFile.put("qm",HOME+DATA+"/qm.xml");
    mapFile.put("lr",HOME+DATA+"/lr.xml");

    mapFile.put("zw",HOME+DATA+"/zw.xml");
    mapFile.put("tb",HOME+DATA+"/tb.xml");

    mapFile.put("xk",HOME+DATA+"/xk.xml");
    mapFile.put("bz",HOME+DATA+"/bz.xml");

    mapFile.put("sz",HOME+DATA+"/sz.xml");
    mapFile.put("ly",HOME+DATA+"/ly.xml");

    mapFile.put("xm",HOME+DATA+"/xm.xml");
    mapFile.put("cg",HOME+DATA+"/cg.xml");
    mapFile.put("gg",HOME+DATA+"/gg.xml");
  }

  public static Font getFont() {
    return new Font("宋体", Font.PLAIN, 12);
  }

  /**
   * 将对象作为一个Collection写入文件，如果存在了，要删除再添加
   * @param vo Serializable
   */
  public static void writeObject2File(VO vo) {
    ObjectOutputStream out = null;
    String fileId = vo.getFileId();
    String rowId = vo.getRowId();
    String pathName = HOME + DATA +"/"+fileId+".dat";
    try {
      Collection<VO> coll = getObjectFromFile(fileId);
      out = new ObjectOutputStream(new FileOutputStream(pathName));
      if(coll!=null) {
        Iterator<VO> it = coll.iterator();
        while(it.hasNext()) {
          VO vo_ = it.next();
          if(vo_.getRowId().equals(rowId)) {
            coll.remove(vo_);
            break;
          }
        }
      } else{
        coll = new ArrayList<VO>();
      }
      coll.add(vo);
      out.writeObject(coll);
      out.close();
    }
    catch (IOException ex) {
      Messages.error("Public::writeObject2File(将对象作为一个VO写入文件出错："+ex+")");
      //return ;
    }
  }
  public static void writeObject2File(String fileId, Collection c) {
    ObjectOutputStream out = null;
    String pathName = HOME + DATA + "/"+fileId+".dat";
    try {
      out = new ObjectOutputStream(new FileOutputStream(pathName));
      out.writeObject(c);
      out.close();
    }
    catch (IOException ex) {
      Messages.error("Public::writeObject2File(将对象作为一个Collection写入文件出错："+ex+")");
      //return ;
    }
  }


  /**
   * 从文件读入对象Collection,由指定的rowId得到其具体的值
   * @param pathName
   * @return
   */
  @SuppressWarnings("unchecked")
	public static Collection<VO> getObjectFromFile(String fileId) {
    String pathName = HOME + DATA+"/"+fileId+".dat";
    Collection<VO> coll = null;
    ObjectInputStream in = null;
    try {
    	if(!new File(pathName).exists()) return null;  //如果不存在，直接返回，不要报错
      in = new ObjectInputStream(new FileInputStream(pathName));
      coll = (Collection<VO>)in.readObject();
      return coll;
    }
    catch (Exception ex) {
    	ex.printStackTrace();
      Messages.error("Public::getObjectFromFile2(从"+fileId+"文件读入对象出错："+ex+")");
      //return null;
    }finally{
      try {
        if(in!=null) in.close();
      } catch (IOException ex) { }
    }
    return null;
  }

  public static Object getObjectFromFile(String fileId, String rowId) {
  	Collection<VO> coll = getObjectFromFile(fileId); 
  	if(coll==null) return null;
  	for(VO vo : coll) {
  		if(vo.getRowId().equals(rowId)) {
        return vo;
      }
  	}
    return null;
  }

   @SuppressWarnings("unchecked")
	public static void delObjsFromFile(String fileId, String[] id) {
    String pathName = HOME + DATA+"/"+fileId+".dat";
     Collection<VO> coll = null;
     Collection<VO> c = new ArrayList<VO>();
     ObjectInputStream in = null;
     try {
       in = new ObjectInputStream(new FileInputStream(pathName));
       coll = (Collection<VO>)in.readObject();

       //复制一个
       if(coll!=null && coll.size()>0) {
         for(Iterator<VO> it = coll.iterator(); it.hasNext();) {
           c.add(it.next());
         }
       }

       if(coll!=null && coll.size()>0) {
         for(Iterator<VO> it = coll.iterator(); it.hasNext();) {
           VO vo = (VO)it.next();
           for(int j=0; j<id.length; j++) {
             if (vo.getRowId().equals(id[j])) {
               c.remove(vo);
               break;
             }
           }
         }
       }
       writeObject2File(fileId, c);
     }
     catch (Exception ex) {
       ex.printStackTrace();
       //Messages.error("Public::delObjsFromFile(从文件删除多个对象出错："+ex+")");
       //return ;
     }finally{
       try {
         if(in!=null)
           in.close();
       }
       catch (IOException ex) {
       }
     }
   }

   public static java.sql.Date getDateValue() {
     java.sql.Date columnValue = new java.sql.Date(System.currentTimeMillis());
     return columnValue;
   }

   public static Time getTimeValue() {
     Time columnValue = new Time(System.currentTimeMillis());
     return columnValue;
   }

   public static Timestamp getTimestampValue() {
     Timestamp columnValue = new Timestamp(System.currentTimeMillis());
     return columnValue;
   }

   /**
    * 根据阳历日期推算干支纪日
    * G = 4C + [C / 4] + 5y + [y / 4] + [3 * (M + 1) / 5] + d - 3
    * Z = 8C + [C / 4] + 5y + [y / 4] + [3 * (M + 1) / 5] + d + 7 + i
    * @param yy int
    * @param m int
    * @param d int
    */
  public static void getDayGZ(int yy, int m, int d) {
    int i = m/2==0 ? 6 : 0;
    int c = yy/100;
    int y = yy%100;
    int g = 4*c+c/4+5*y+y/4+(3*(m+1)/5)+d-3;
    int z = 8*c+c/4+5*y+y/4+(3*(m+1)/5)+d+7-i;
    String[] gan = new String[]{"癸","甲","乙","丙","丁","戊","己","庚","辛","壬","癸"};
    String[] zi  = new String[]{"亥","子","丑","寅","卯","辰","巳","午","未","申","酉","戌","亥"};
    System.out.println("阳历"+yy+"-"+m+"-"+d+" 干支："+gan[g%10]+zi[z%12]);
  }

  /**
   * 得到公历1至x月y日的天数
   */
  public static int getTotal(int x, int y) {
    int[] arr = new int[]{0,31,28,31,30,31,30,31,31,30,31,30,31};
    int t = 0;
    for(int i=1; i<x; i++) {
      t += arr[i];
    }
    return t+y;
  }
  
  public static InputStream getFileFromJar(String dir) {
  	if(dir==null) return null;
//  	Messages.info("0"+Public.class.getResource("."));
//  	Messages.info("0"+Public.class.getResource(""));
//  	Messages.info("0"+Public.class.getResource("/"));
//  	Messages.info(""+Public.class.getResource(dir));  jar:file:/E:/..../jyijing.jar!/conf/a.txt
  	
  	if(!dir.startsWith("/")) dir = "/"+dir;  	
//  	dir = Public.class.getResource(dir).getPath();  	
//  	if(dir.startsWith("file:")) dir = dir.substring(6);
//  	else if(dir.startsWith("jar:file:")) dir = dir.substring(10);
  	
  	InputStream is=Public.class.getResourceAsStream(dir);    

  	return is;
  }

  /**
   * 阳历转化成阴历公式
   * 公元年数－1977（或1901）＝４Ｑ＋Ｒ
   * 阴历日期=14Q+10.6(R+1)+年内日期序数-29.5n
   * @param yy int
   * @param m int
   * @param d int
   */
  public static void yang2Yin(int yy, int m, int d) {
    int q = (yy-1977)/4;
    int r = (yy-1977)%4;
    double t = 14*q+10.6*(r+1)+getTotal(m,d);
    int mm = (int)(t/29.5);
    int dd = (int)(t - mm*29.5);
    System.out.println("阳历"+yy+"-"+m+"-"+d+" 农历是："+yy+":"+mm+":"+dd);
  }
  /**
   * 得到北京时间
   */
  public static Calendar getCalendarForBeijing() {
  	//TimeZone tz   =   TimeZone.getTimeZone( "Asia/Shanghai ");//北京时间
  	//Calendar c 生成的时间是 GMT 时间 比北京时间少 8 小时 c.getHour()也会少 8 小时 得到的日期也会因此产生错误 （北京这六点 GMT时间是22点 日期 小一天）所以要先用
  	TimeZone tz = TimeZone.getTimeZone("GMT+8");
    GregorianCalendar clr = new GregorianCalendar(tz);
  	//Calendar clr = Calendar.getInstance();
  	//clr.setTimeZone(tz);
  	return clr;
  }
  
  public static int getCurrentTime(int i) {
  	Calendar clr = getCalendarForBeijing();
  	return i==1 ? clr.get(Calendar.YEAR) : 
  				 i==2 ? clr.get(Calendar.MONTH):
  				 i==3 ? clr.get(Calendar.DAY_OF_MONTH):
  				 i==4 ? clr.get(Calendar.HOUR_OF_DAY) :
  				 i==5 ? clr.get(Calendar.MINUTE): clr.get(Calendar.SECOND);
  }
  
  public static void main(String[] args) {
    Public.yang2Yin(1977,5,10);
    Public.yang2Yin(1994,5,7);
  }
}
