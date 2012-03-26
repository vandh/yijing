package org.boc.delegate;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.io.PrintWriter;
import org.boc.util.Debug;
import org.boc.dao.DaoPublic;
import org.boc.dao.DaoZiWeiMain;
import org.boc.dao.DaoCalendar;
import org.boc.util.Messages;

public class DelZiWeiMain {
  private StringBuffer str;
  private PrintWriter pw;
  private DaoPublic pub;
  private DaoZiWeiMain zw;
  private DaoCalendar cal;

  public DelZiWeiMain() {
    pub = new DaoPublic();
    str = new StringBuffer();
    pw = DelLog.getLogObject();
    zw = new DaoZiWeiMain();
    cal = new DaoCalendar();
  }

  /**
   * 输出命运
   * @param y 年
   * @param m 月
   * @param d 日
   * @param h 时
   * @param mi 分
   * @param type 是阴历还阳历时间，true为阴历
   * @param yun  是闰月还是非闰月，true为闰月
   * @param isMan 是否男女
   * @param str
   */
  public String getMingYun(
      int y, int m, int d, int h,int mi,
      boolean type, boolean yun, int sheng ,int shi, boolean isMan) {
    try{
      str.delete(0,str.length());
      //输出时间信息
      //str.append(pub.getStandHead(y,m,d,h,mi,type,yun,sheng,shi));

      int[] bz = cal.getSiZhu(y,m,d,h,mi,yun,type);
      /**
       * 生月一般不论节气？若论则屏蔽下句。
       * 转为不论节气的月支
       */
      bz[4]=(bz[13]+2)%12==0?12:(bz[13]+2)%12;
      Calendar c = new GregorianCalendar();
      int ly = c.get(Calendar.YEAR);
      int[] lnbz = cal.getSiZhu(ly,6,6,6,6,true,false);
      printOut(bz, lnbz, bz[14], isMan,
               pub.getStandHead(y,m,d,h,mi,type,yun,sheng,shi));

      //Debug.out(str.toString());
    }catch(Exception ex) {
      Messages.error("DelZiWeiMain("+ex+")");
    }

    return str.toString();
  }

  /**
   * 由八字推算命运
   */
  public String getMingYun(
      int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
      boolean yun, boolean isMan) {
    try{
      str.delete(0,str.length());
      //输出时间信息
      //dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan, str);
      //printOut();
    }catch(Exception ex) {
      Messages.error("DelZiWeiMain("+ex+")");
    }

    return str.toString();
  }

  /**
   * 打印出所有的基本信息如大运，小运，流年等
   * @return
   * @throws java.lang.Exception
   */
  private void printOut(int[] bz, int[] lnbz, int day,
                        boolean sex, String head) throws Exception{
    str.append("\r\n");
    str.append(zw.getTianPan(bz, lnbz, day, sex, head));
  }

  public static void main(String[] args) {
    DelZiWeiMain del = new DelZiWeiMain();
    String s = del.getMingYun(1977,3,23,6,30,true,true,0,1,true); //阴阳 闰 男女
    System.out.println(s);
  }
}
