package org.boc.dao;

import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.boc.db.Calendar;
import org.boc.db.YiJing;
import org.boc.db.qm.QiMen;
import org.boc.util.Messages;

public class DaoPublic {

  private DaoCalendar daoc;
  private static String path;

  public DaoPublic() {
    daoc = new DaoCalendar();
  }

  /**
   * 是否阳男阴女
   */
  public boolean isYnanYinlv(int ng, boolean sex) {
    if ( (ng % 2 == 1 && sex) || (ng % 2 == 0 && !sex))
      return true;

    return false;
  }

  /**
   * 将字符串转化成Unicode-utf-8编码
   * @param args String[]
   */
  public static String String2Unicode(String str) {
    try {
      return new String(str.getBytes("utf-16"));
    }
    catch (UnsupportedEncodingException ex) {
    	ex.printStackTrace();
      Messages.error("DaoPublic("+ex+")");
      return null;
    }
  }

  /**
   * 将字符串转化成ASCII编码
   * @param args String[]
   */
  public static String String2Ascii(String str) {
    String s = "";
    String _s = null;
    try {
      byte[] b = str.getBytes("utf-8");
      for (int i = 0; i < b.length; i++) {
        _s = Integer.toHexString( (int) b[i]);
        s += "%" + _s.substring(_s.length() - 2);
      }
      //System.out.println(s + "\t");
      //System.out.println(new DaoPublic().unicode2String(s));
    }
    catch (Exception ex) {
    	ex.printStackTrace();
      Messages.error("DaoPublic("+ex+")");
    }
    return s;
  }

  /**
   * 将unicode的utf-8转换成字符串
   * @param s String
   * @return String
   */
  public static String unicode2String(String s) {
    String s1 = null;
    String s2 = null;
    int i=0,j=0;

    try {
      while(s.indexOf("%")!=-1) {
        i = s.indexOf("%")+1;
        j = i+8;
        s1 = s.substring(0,i-1);
        s2 = s.substring(j);
        s = s.substring(i,j);

        s = s1+
            new String(new byte[] { (byte) Integer.parseInt(s.substring(0, 2),16),
                       (byte) Integer.parseInt(s.substring(3, 5), 16),
                       (byte) Integer.parseInt(s.substring(6, 8), 16)}, "utf-8")+
            s2;
      }
    } catch (Exception ex) {
    	ex.printStackTrace();
      Messages.error("DaoPublic("+ex+")");
    }

    return s;
  }

  /**
   * 由某宫求对宫
   * @param z int
   * @return int
   */
  public int getDuiGong(int z) {
    int[] dg = {0,YiJing.WUZ,YiJing.WEI,YiJing.SHEN,YiJing.YOU,
        YiJing.XU,YiJing.HAI,YiJing.ZI,YiJing.CHOU,
        YiJing.YIN,YiJing.MAO,YiJing.CHEN,YiJing.SI};
    return dg[z];
  }

  /**
   * x1上起y，由x1逆数至x2，y则顺数
   */
  public int getNiShu(int x1, int y, int x2) {
    return (y+(x1-x2)+120)%12==0?12:(y+(x1-x2)+120)%12;
  }

  /**
   * x1上起y，由x1顺数至x2，y则顺数
   */
  public int getShunShu(int x1, int y, int x2) {
    return (y+x2+120-x1)%12==0?12:(y+x2+120-x1)%12;
  }

  /**
   * 以地支顺数至地支12周期，天干十周期
   * @param dz1 int
   * @param tg int
   * @param dz22 int
   * @return int
   */
  public int getShunShu2(int dz1, int tg, int dz2) {
    int dz = dz2-dz1<0?dz2+12-dz1:dz2-dz1;
    return (tg+dz)%10==0?10:(tg+dz)%10;
  }


  /**
   * 返回重复的元素
   * @param str
   * @param len
   * @return
   */
  public String getRepeats(char c, int len) {
    String retStr = "";
    for(int i = 0; i<len; i++) {
      retStr += c;
    }
    return retStr;
  }


  /**
   * 得到标准的头部信息
   * @return String
   */
  public String getStandHead(int y, int m, int d, int h, int mi,
                             boolean type, boolean yun,
                             int sheng, int shi) {
    int[] sunTime = getSunTime(h, mi, sheng, shi);
    int[] dt = daoc.getSiZhu(y,m,d,sunTime[0],sunTime[1],yun,type);
    StringBuffer str = new StringBuffer();

    str.append("\r\n    阳  历： ");
    str.append(dt[9]+"-"+dt[10]+"-"+dt[11]+" "+h+":"+mi);
    str.append("\r\n    农  历： ");
    str.append(dt[12]+"年"+(dt[0]==1? "闰":"") +dt[13]+"月初"+dt[14]);
    str.append(" "+h+":"+mi+" "+ Calendar.WEEKNAME[dt[18]]);
    if(sheng>=0 && shi>0) {
      str.append("\r\n    真  阳： ");
      str.append(Calendar.YEARN1 + "年" + (dt[0]==1 ? "闰" : ""));
      str.append(dt[13] + "月初" +dt[14] + " " + sunTime[0] + ":" + sunTime[1]);
      str.append(" " + Calendar.WEEKNAME[dt[18]]);
    }
    str.append("\r\n    干  支： ");
    str.append(YiJing.TIANGANNAME[dt[1]]);
    str.append(YiJing.DIZINAME[dt[2]]);
    str.append("  ");
    str.append(YiJing.TIANGANNAME[dt[3]]);
    str.append(YiJing.DIZINAME[dt[4]]);
    str.append("  ");
    str.append(YiJing.TIANGANNAME[dt[5]]);
    str.append(YiJing.DIZINAME[dt[6]]);
    str.append("  ");
    str.append(YiJing.TIANGANNAME[dt[7]]);
    str.append(YiJing.DIZINAME[dt[8]]);

    str.append("\r\n    旬  空： ");
    str.append(getXunKongOut(dt[1],dt[2])[0]);
    str.append(getXunKongOut(dt[1],dt[2])[1]);
    str.append("  ");
    str.append(getXunKongOut(dt[3],dt[4])[0]);
    str.append(getXunKongOut(dt[3],dt[4])[1]);
    str.append("  ");
    str.append(getXunKongOut(dt[5],dt[6])[0]);
    str.append(getXunKongOut(dt[5],dt[6])[1]);
    str.append("  ");
    str.append(getXunKongOut(dt[7],dt[8])[0]);
    str.append(getXunKongOut(dt[7],dt[8])[1]);

    int[] jq = this.getJieQi(y,m,d,h,mi,yun,type);
    str.append("\r\n    上  节： ");
    str.append(QiMen.JIEQI24[jq[1]]);
    str.append(" ");
    str.append(jq[2]+"年");
    str.append(isYunJieqi(jq[2], jq[1]) ? "闰":"");
    str.append(jq[3]+"月初"+ jq[4] + "日"+ jq[5] +"时"+ jq[6] +"分");

    str.append("\r\n    下  节： ");
    str.append(QiMen.JIEQI24[jq[7]]);
    str.append(" ");
    str.append(jq[8]+"年");
    str.append(isYunJieqi(jq[8], jq[7]) ? "闰":"");
    str.append(jq[9]+"月初"+ jq[10] + "日"+ jq[11] +"时"+ jq[12] +"分");

    return str.toString();
  }

  public int[] getSunTime(int h, int mi, int sheng, int shi) {
    if(sheng<=0 && shi<=0)
      return new int[]{h,mi};
    int sunTrue = (Calendar.jingdu[sheng][shi] - 120 * 60) * 4;
    sunTrue += Calendar.zpsc[Calendar.MONTHP][Calendar.DAYP];

    int h1 = (h*60*60+mi*60+sunTrue)/60/60;
    int mi1 = (h*60*60+mi*60+sunTrue)/60 - h1*60;
    return new int[]{h1, mi1};
  }

  /**
   *得到当前class的路径
   * @return
   */
  public String getCurrentClassPath(String cls) {
    URL url = null;
    try {
      if(cls==null) {
        url = this.getClass().getResource("");
      }else{
        url = Class.forName(cls).getResource("");
      }
    }
    catch (Exception ex) {
    	ex.printStackTrace();
      Messages.error("DaoPublic("+ex+")");
    }
    String path = null;
    if(url==null)
      System.err.println("读取xml文件出错！");
    else
        path = url.getPath();
    //System.err.println("path="+path);
    return path;
  }

  /**
   *得到当前class的路径
   * @return
   */
  public static String getClassRootPath() {
    if(path==null) {
      URL url = null;
      try {
        url = new DaoPublic().getClass().getResource("/");
      	if(url==null) path = "./";
      	else
      		path = unicode2String(url.getPath());
      }
      catch (Exception ex) {
      	ex.printStackTrace();
        Messages.error("DaoPublic("+ex+")");
      }
    }
    return path;
  }



  /**
   * 由干支得到那一年
   */
  public int getYear(int g,int z) {
    int y = new java.util.Date().getYear()+1900;
    int cg = (y-Calendar.IYEAR+Calendar.IYEARG)%10==0?10:(y-Calendar.IYEAR+Calendar.IYEARG)%10;
    int cz = (y-Calendar.IYEAR+Calendar.IYEARZ)%12==0?12:(y-Calendar.IYEAR+Calendar.IYEARZ)%12;
    int cdays = getJiaziDay(cg,cz);
    int days = getJiaziDay(g,z);
    return y-(cdays-days<0?cdays-days+60:cdays-days);
  }

  /**
   * 得到距离甲子的天数
   */
  private int getJiaziDay(int g, int z) {
    int[] xun = {0, 0, 50,0,40,0,30,0,20,0,10};
    return xun[(z-g+12)%12] + g - YiJing.JIA;
  }

  /**
   * 判断xyz三者是否是指定的解
   */
  public boolean xyzMaking(int x,int y,int z,int min,int max,int sum) {
    if(x+y+z==sum && getMin(x,y,z)==min && getMax(x,y,z)==max)
      return true;
    return false;
  }

  /**
   * 返回最小的
   */
  public int getMin(int i1,int i2,int i3){
    int i_1 = Math.min(i1,i2);
    int i_2 = Math.min(i1,i3);
    return Math.min(i_1,i_2);
  }
  public int getMin(int i1,int i2,int i3,int i4){
    int i_1 = Math.min(i1,i2);
    int i_2 = Math.min(i3,i4);
    return Math.min(i_1,i_2);
  }

  /**
   * 返回最大的
   */
  public int getMax(int i1,int i2,int i3){
    int i_1 = Math.max(i1,i2);
    int i_2 = Math.max(i1,i3);
    return Math.max(i_1,i_2);
  }
  public int getMax(int i1,int i2,int i3,int i4){
    int i_1 = Math.max(i1,i2);
    int i_2 = Math.max(i3,i4);
    return Math.max(i_1,i_2);
  }

  /**
   * 得到旬空
   */
  public String[] getXunKongOut(int rigan, int rizi) {
    int xk = YiJing.KONGWANG[rigan][rizi];
    int zi1 = xk/100;
    int zi2 = xk - zi1*100;

    String[] str = new String[2];
    str[0] = YiJing.DIZINAME[zi1];
    str[1] = YiJing.DIZINAME[zi2];
    return str;
  }
  public int[] getXunKong(int rigan, int rizi) {
    int xk = YiJing.KONGWANG[rigan][rizi];
    return new int[]{xk/100, xk%100};
  }


  public String checks(String _y, String _m, String _d, String hms,
                       boolean isYY, boolean isYun) {
    String err = null;
    int yg=0,yz=0,mg=0,mz=0,dg=0,dz=0,hg=0,hz=0;
    int year=0, month=0, day=0, hour=0, minute=0, second=0;
    boolean typeSjOrgz = false;

    if(_y==null ||  _y.trim().equals("")) {
      return "年份可以是时间或干支，其中时间必须在" + Calendar.MAXYEAR + "与" +
          Calendar.IYEAR +"之间，干支必须是或x,y形式， 其中0<x<=10且0<y<=12";
    }
    if(_y.indexOf(",")==-1){
      typeSjOrgz = false;
      year = _y.equals("")?0:Integer.valueOf(_y).intValue();
      if (year > Calendar.MAXYEAR || year < Calendar.IYEAR)
        return "年份必须在" + Calendar.MAXYEAR + "与" + Calendar.IYEAR +"之间";
    }else{
      typeSjOrgz = true;
      String _y1 = _y.substring(0,_y.indexOf(","));
      String _y2 = _y.substring(_y.indexOf(",")+1);
      yg = Integer.valueOf(_y1).intValue();
      yz = Integer.valueOf(_y2).intValue();
      if(yg<=0 || yg>10 || yz<=0 || yz>12)
        return "年份必须是或x,y形式， 其中0<x<=10且0<y<=12";
    }

    if(_m==null ||  _m.trim().equals("")) {
      return "月份可以是时间或干支，其中时间必须在1与12之间，干支必须是或x,y形式， 其中0<x<=10且0<y<=12";
    }
    if(!typeSjOrgz){
      if (_m.indexOf(",")>-1)
        return "月份必须在1与12之间";
      month = _m.equals("") ? 0 :Integer.valueOf(_m).intValue();
      if (month < 1 || month > 12)
        return "月份必须在1与12之间";
    }else{
      if (_m.indexOf(",")==-1)
        return "月份必须是或x,y形式， 其中0<x<=10且0<y<=12";
      String _y1 = _m.substring(0,_m.indexOf(","));
      String _y2 = _m.substring(_m.indexOf(",")+1);
      mg = Integer.valueOf(_y1).intValue();
      mz = Integer.valueOf(_y2).intValue();
      if(mg<=0 || mg>10 || mz<=0 || mz>12)
        return "月份必须是或x,y形式， 其中0<x<=10且0<y<=12";
    }

    if(_d==null ||  _d.trim().equals("")) {
      return "日期可以是时间或干支，其中时间必须在1与31之间，干支必须是或x,y形式， 其中0<x<=10且0<y<=12";
    }
    if(!typeSjOrgz){
      if (_d.indexOf(",")>-1 )
        return "日期必须在1与31之间";
      day = _d.equals("") ? 0 : Integer.valueOf(_d).intValue();
      if (_d.indexOf(",")>-1 || day < 1 || day > 31)
        return "日期必须在1与31之间";

      if(isYY) {
        if(day>daoc.getYinDays(year,month,isYun))
          return "农历: "+year+"年"+month+"月最多只有"+daoc.getYinDays(year,month,isYun)+"天";
      }else{
        if(day>daoc.getYangDays(year,month))
          return "阳历: "+year+"年"+month+"月最多只有"+daoc.getYangDays(year,month)+"天";
      }
    }else{
      if (_d.indexOf(",")==-1)
        return "日期必须是或x,y形式， 其中0<x<=10且0<y<=12";
      String _y1 = _d.substring(0,_d.indexOf(","));
      String _y2 = _d.substring(_d.indexOf(",")+1);
      dg = Integer.valueOf(_y1).intValue();
      dz = Integer.valueOf(_y2).intValue();
      if(dg<=0 || dg>10 || dz<=0 || dz>12)
        return "日期必须是或x,y形式， 其中0<x<=10且0<y<=12";
    }

    if(hms==null ||  hms.trim().equals("")) {
      return "时柱可以是时间或干支，其中时间必须是hh:mm:ss的形式，干支必须是或x,y形式， 其中0<x<=10且0<y<=12";
    }
    if(!typeSjOrgz){
      if (hms == null || hms.trim().equals(""))
        return "时分秒不能为空";
      if (hms.startsWith(":"))
        return "时分秒不能以':'开头，且时分秒以':'分隔，如18:3:57或0:9:1";
      if(hms.indexOf(",")>-1) {
        return "时分秒必须以':'分隔，如18:3:57或0:9:1";
      }
      String[] h = hms.split(":");
      if(h.length == 0)
        return "时分秒不能为空";
      if(h.length == 1) {
        hour = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
        if (hour < 0 || hour > 23)
          return "时分秒中的小时不能小于0或大于23";
      }
      if(h.length == 2) {
        hour = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
        minute = h[1] == null ? 0 : Integer.valueOf(h[1]).intValue();
        if (hour < 0 || hour > 23)
          return "时分秒中的小时不能小于0或大于23";
        if (minute < 0 || minute > 59)
          return "时分秒中的分钟不能小于0或大于59";
      }
      if(h.length == 3) {
        hour = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
        minute = h[1] == null ? 0 : Integer.valueOf(h[1]).intValue();
        second = h[2] == null ? 0 : Integer.valueOf(h[2]).intValue();
        if (hour < 0 || hour > 23)
          return "时分秒中的小时不能小于0或大于23";
        if (minute < 0 || minute > 59)
          return "时分秒中的分钟不能小于0或大于59";
        if (second < 0 || second > 59)
          return "时分秒中的秒不能小于0或大于59";
      }
    }else{
      if(hms.indexOf(",")==-1) {
        return "时间必须是或x,y形式， 其中0<x<=10且0<y<=12";
      }
      String _y1 = hms.substring(0,hms.indexOf(","));
      String _y2 = hms.substring(hms.indexOf(",")+1);
      hg = Integer.valueOf(_y1).intValue();
      hz = Integer.valueOf(_y2).intValue();
      if(hg<=0 || hg>10 || hz<=0 || hz>12)
        return "时间必须是或x,y形式， 其中0<x<=10且0<y<=12";
    }

    return null;
  }

  public String checks(String _y, String _m, String _d, String hms,
                       boolean isYY, boolean isYun, boolean sj) {
    String err = null;
    int yg=0,yz=0,mg=0,mz=0,dg=0,dz=0,hg=0,hz=0;
    int year=0, month=0, day=0, hour=0, minute=0, second=0;

    if(sj){
      if(_y.indexOf(",")!=-1)
        return "年份必须是数字，且必须在"+Calendar.MAXYEAR+"与"+Calendar.IYEAR+"之间";
      year = _y.equals("")?0:Integer.valueOf(_y).intValue();
      if (year > Calendar.MAXYEAR || year < Calendar.IYEAR)
        return "年份必须在" + Calendar.MAXYEAR + "与" + Calendar.IYEAR +"之间";
    }else{
      if(_y.indexOf(",")==-1)
        return "年份必须是或x,y形式， 其中0<x<=10且0<y<=12";
      String _y1 = _y.substring(0,_y.indexOf(","));
      String _y2 = _y.substring(_y.indexOf(",")+1);
      yg = Integer.valueOf(_y1).intValue();
      yz = Integer.valueOf(_y2).intValue();
      if(yg<=0 || yg>10 || yz<=0 || yz>12)
        return "年份必须是或x,y形式， 其中0<x<=10且0<y<=12";
    }

    if(sj){
      if (_m.indexOf(",")>-1)
        return "月份必须在1与12之间";
      month = _m.equals("") ? 0 :Integer.valueOf(_m).intValue();
      if (month < 1 || month > 12)
        return "月份必须在1与12之间";
    }else{
      if (_m.indexOf(",")==-1)
        return "月份必须是或x,y形式， 其中0<x<=10且0<y<=12";
      String _y1 = _m.substring(0,_m.indexOf(","));
      String _y2 = _m.substring(_m.indexOf(",")+1);
      mg = Integer.valueOf(_y1).intValue();
      mz = Integer.valueOf(_y2).intValue();
      if(mg<=0 || mg>10 || mz<=0 || mz>12)
        return "月份必须是或x,y形式， 其中0<x<=10且0<y<=12";
    }

    if(sj){
      if (_d.indexOf(",")>-1 )
        return "日期必须在1与31之间";
      day = _d.equals("") ? 0 : Integer.valueOf(_d).intValue();
      if (_d.indexOf(",")>-1 || day < 1 || day > 31)
        return "日期必须在1与31之间";

      if(isYY) {
        if(day>daoc.getYinDays(year,month,isYun))
          return "农历: "+year+"年"+month+"月最多只有"+daoc.getYinDays(year,month,isYun)+"天";
      }else{
        if(day>daoc.getYangDays(year,month))
          return "阳历: "+year+"年"+month+"月最多只有"+daoc.getYangDays(year,month)+"天";
      }
    }else{
      if (_d.indexOf(",")==-1)
        return "日期必须是或x,y形式， 其中0<x<=10且0<y<=12";
      String _y1 = _d.substring(0,_d.indexOf(","));
      String _y2 = _d.substring(_d.indexOf(",")+1);
      dg = Integer.valueOf(_y1).intValue();
      dz = Integer.valueOf(_y2).intValue();
      if(dg<=0 || dg>10 || dz<=0 || dz>12)
        return "日期必须是或x,y形式， 其中0<x<=10且0<y<=12";
    }

    if(sj){
      if (hms == null || hms.trim().equals(""))
        return "时分秒不能为空";
      if (hms.startsWith(":"))
        return "时分秒不能以':'开头，且时分秒以':'分隔，如18:3:57或0:9:1";
      if(hms.indexOf(",")>-1) {
        return "时分秒必须以':'分隔，如18:3:57或0:9:1";
      }
      String[] h = hms.split(":");
      if(h.length == 0)
        return "时分秒不能为空";
      if(h.length == 1) {
        hour = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
        if (hour < 0 || hour > 23)
          return "时分秒中的小时不能小于0或大于23";
      }
      if(h.length == 2) {
        hour = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
        minute = h[1] == null ? 0 : Integer.valueOf(h[1]).intValue();
        if (hour < 0 || hour > 23)
          return "时分秒中的小时不能小于0或大于23";
        if (minute < 0 || minute > 59)
          return "时分秒中的分钟不能小于0或大于59";
      }
      if(h.length == 3) {
        hour = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
        minute = h[1] == null ? 0 : Integer.valueOf(h[1]).intValue();
        second = h[2] == null ? 0 : Integer.valueOf(h[2]).intValue();
        if (hour < 0 || hour > 23)
          return "时分秒中的小时不能小于0或大于23";
        if (minute < 0 || minute > 59)
          return "时分秒中的分钟不能小于0或大于59";
        if (second < 0 || second > 59)
          return "时分秒中的秒不能小于0或大于59";
      }
    }else{
      if(hms.indexOf(",")==-1) {
        return "时间必须是或x,y形式， 其中0<x<=10且0<y<=12";
      }
      String _y1 = hms.substring(0,hms.indexOf(","));
      String _y2 = hms.substring(hms.indexOf(",")+1);
      hg = Integer.valueOf(_y1).intValue();
      hz = Integer.valueOf(_y2).intValue();
      if(hg<=0 || hg>10 || hz<=0 || hz>12)
        return "时间必须是或x,y形式， 其中0<x<=10且0<y<=12";
    }

    return null;
  }

   /**
    * 得到上节下节，取按节气后的年与月，则本月节气必在本月，最后只调整年份即可
    * 1-6: 上节节名序号，上节年、月、日、时、分
    * 7-12: 下节
    * @param year2
    * @param month2
    * @param day2
    * @param hour2
    * @param min2
    * @param isYun
    * @param yytype 阴历为真，阳历为假
    * @return
    */
   public int[] getJieQi(int year2, int month2, int day2, int hour2,int min2,
                         boolean isYun, boolean yytype) {
     int lastjie = 0; int nextjie = 0, lastjiename=0, nextjiename=0;
     int lastjieyear=0, nextjieyear=0;
     //int[] jq = new int[9];

     //得到阴或阳历时间的干支/9-11阳历年月日、12-14阴历年月日
     int[] sizhu = daoc.getSiZhu(year2,month2,day2,hour2,min2,isYun,yytype);
     //得到月份与年份，均按节气后的年月
     int z = sizhu[16];
     int year = sizhu[15]-Calendar.IYEAR;
     //取月支的下一个节气
     int _date2 = Calendar.jieqi2[year][z*2-1 + 1];
     if(!isMoreBig(sizhu[12], sizhu[13],sizhu[14],hour2, min2, z*2-1+1, sizhu[0])) {
       lastjie = Calendar.jieqi2[year][z * 2 - 1];
       nextjie = _date2;
       lastjiename = z * 2 - 1;
       nextjiename = z * 2 - 1 + 1;
     }else{
       lastjie = _date2;
       if (z == 12) {
         nextjie = Calendar.jieqi2[year + 1][1];
         lastjiename = 24; //而非_maxMonth-1
         nextjiename = 1;
       }
       else {
         nextjie = Calendar.jieqi2[year][z * 2 - 1 + 2];
         lastjiename = z * 2 - 1 + 1;
         nextjiename = z * 2 - 1 + 2;
       }
     }

    int lys = lastjie/1000000;
    int lrs = lastjie/10000 - lys*100;
    int lss = lastjie/100 - lys*10000 - lrs*100;
    int lfs = lastjie - lys*1000000 - lrs*10000 - lss*100;

    int lyx = nextjie/1000000;
    int lrx = nextjie/10000 - lyx*100;
    int lsx = nextjie/100 - lyx*10000 - lrx*100;
    int lfx = nextjie - lyx*1000000 - lrx*10000 - lsx*100;

    /**
     * 如果上节月是12月，而下节月是1月，则可能：
     * 1. 本年1月，上年12月 2004.12 - 2005.1
     * 2. 本年12月，下年1月 2005.12 - 2006.1
     */
    if(lys==12 && lyx==1) {
      if(sizhu[16]==1) {
        lastjieyear = sizhu[15] - 1;
        nextjieyear = sizhu[15];
      }else if(sizhu[16]==12) {
        lastjieyear = sizhu[15];
        nextjieyear = sizhu[15] + 1;
      }
    }else{
      lastjieyear = sizhu[12];
      nextjieyear = sizhu[12];
    }

    return new int[]{0,lastjiename,lastjieyear,lys,lrs,lss,lfs,
                       nextjiename,nextjieyear,lyx,lrx,lsx,lfx};
  }

  /**
   * 判断当前时间与当年之节气号对应的节气谁大谁小
   * @param y
   * @param mn
   * @param dn
   * @param h
   * @param mi
   * @param jieNo
   * @param yun2
   * @return
   */
  private boolean isMoreBig(int y, int mn, int dn, int h, int mi,
                            int jieNo, int yun2) {
    boolean isYun = yun2==1?true:false;
    int yun = daoc.getYunYue(y);
    int _date2 = Calendar.jieqi2[y - Calendar.IYEAR][jieNo];
    int _m2 = _date2/1000000;

    if((mn*1000000+dn*10000+h*100+mi) < _date2 &&
       Math.abs(_m2 - mn)<=2) {
      if(isYun && !isYunJieqi(y,jieNo)) {
        return true;
      }else{
        return false;
      }
    }else{
      if(!isYun && isYunJieqi(y,jieNo)) {
        return false;
      }else{
        return true;
      }
    }
  }

  /**
   * 正二月无闰月之理
   * @param y
   * @param jieNo
   * @return
   */
  public boolean isYunJieqi(int y, int jieNo) {
    if(jieNo<2)
      return false;
    int yun = daoc.getYunYue(y);
    int jieqi0 = Calendar.jieqi2[y - Calendar.IYEAR][jieNo-2];
    int jieqi1 = Calendar.jieqi2[y - Calendar.IYEAR][jieNo-1];
    int jieqi2 = Calendar.jieqi2[y - Calendar.IYEAR][jieNo];
    if(jieqi2<jieqi1 && jieqi2/1000000==yun)
      return true;
    if(jieqi2>jieqi1 && jieqi1<jieqi0 && jieqi2/1000000==yun)
      return true;

    return false;
  }

  /**
   * 得到甲旬首的地支
   */
  public int getXunShou(int g, int z) {
    return QiMen.xunzi[(z-g+12)%12];
  }

  public static void main1(String[] args) {
    DaoPublic pub = new DaoPublic();
    int[] jq = new int[13];
    //jq = pub.getJieQi(2004,2,1,9,30,true,false);
    //jq = pub.getJieQi(2005,1,10,18,30,true,true);
    jq = pub.getJieQi(2005,2,1,18,30,true,true);
    System.out.println("\r\n    上  节： "+
              QiMen.JIEQI24[jq[1]]+" "+jq[2]+"年"+
              (pub.isYunJieqi(jq[2], jq[1]) ? "闰":"") + jq[3]+
              "月初"+jq[4]+"日"+jq[5]+"时"+jq[6]+"分");
    System.out.println("\r\n    下  节： "+
              QiMen.JIEQI24[jq[7]]+" "+jq[8]+"年"+
              (pub.isYunJieqi(jq[8], jq[7]) ? "闰":"") + jq[9]+
              "月初"+jq[10]+"日"+jq[11]+"时"+jq[12]+"分");

  }
  
  

  public static void main(String[] args) {
    DaoPublic pub = new DaoPublic();
    System.out.println("=\r\n"+pub.String2Ascii("币种"));
    //System.out.println(pub.getCurrentClassPath(""));
  }
}
